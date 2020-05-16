package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.anno.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query = "INSERT INTO shopping_carts(user_id) "
                + "VALUES (?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, shoppingCart.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                shoppingCart.setId(resultSet.getLong(1));
            }
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "create Shopping cart query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * "
                    + "FROM shopping_carts "
                    + "WHERE shopping_cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getShoppingCart(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "get Shopping cart by Id query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * "
                    + "FROM shopping_carts";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<ShoppingCart> shoppingCarts = new ArrayList<>();
            if (resultSet.next()) {
                shoppingCarts.add(getShoppingCart(resultSet));
            }
            return shoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "get all Shopping carts query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        deleteProducts(shoppingCart);
        addProducts(shoppingCart);
        return shoppingCart;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE "
                + "FROM shopping_carts "
                + "WHERE cart_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "delete Shopping cart query. Stack trace: "
                    + e.getMessage());
        }
    }

    private ShoppingCart getShoppingCart(ResultSet resultSet) throws SQLException {
        Long shoppingCartId = resultSet.getLong("cart_id");
        Long userId = resultSet.getLong("user_id");
        return new ShoppingCart(shoppingCartId, getProducts(shoppingCartId), userId);
    }

    private List<Product> getProducts(Long id) {
        String query = "SELECT products.product_id, products.name, products.price "
                + "FROM shopping_cart_products "
                + "INNER JOIN products "
                + "ON  shopping_cart_products.product_id=products.product_id "
                + "WHERE shopping_cart_products.cart_id = ?";

        List<Product> products = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                products.add(new Product(productId, name, price));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "get product list. Stack trace: "
                    + e.getMessage());
        }
    }

    private void addProducts(ShoppingCart shoppingCart) {
        String query = "INSERT INTO "
                + "shopping_cart_products(cart_id, product_id) "
                + "values(?,?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Product product : shoppingCart.getProducts()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, shoppingCart.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "add products to list. Stack trace: "
                    + e.getMessage());
        }
    }

    private void deleteProducts(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE "
                    + "FROM shopping_cart_products "
                    + "WHERE cart_id=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCart.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "delete products from shopping cart. Stack trace: "
                    + e.getMessage());
        }
    }
}
