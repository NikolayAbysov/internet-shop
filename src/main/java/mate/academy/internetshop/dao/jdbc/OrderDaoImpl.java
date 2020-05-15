package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.anno.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) "
                + "VALUES(?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
            addProducts(order);
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "create product query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * "
                + "FROM orders "
                + "WHERE order_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getOrder(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "get product query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * "
                + "FROM orders;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(getOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "get all product query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public Order update(Order order) {
        deleteProducts(order);
        addProducts(order);
        return order;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM orders "
                + "WHERE order_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "delete order query. Stack trace: "
                    + e.getMessage());
        }
    }

    private void addProducts(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Product product : order.getProducts()) {
                String query = "INSERT INTO orders_products(order_id, product_id) "
                        + "values(?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, order.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "addProducts query. Stack trace: "
                    + e.getMessage());
        }
    }

    private Order getOrder(ResultSet resultSet) throws SQLException {
        Long orderId = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        return new Order(orderId, getProducts(orderId), userId);
    }

    private List<Product> getProducts(Long orderId) {
        String query = "SELECT products.product_id, products.name, products.price "
                + "FROM orders_products "
                + "INNER JOIN products "
                + "USING(product_id) "
                + "WHERE orders_products.order_id = ?";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                products.add(new Product(productId, name, price));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "getProducts query. Stack trace: "
                    + e.getMessage());
        }
    }

    private void deleteProducts(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM orders_products "
                    + "WHERE order_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "deleteProducts query. Stack trace: "
                    + e.getMessage());
        }
    }
}
