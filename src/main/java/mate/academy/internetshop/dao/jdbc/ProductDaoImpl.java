package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.anno.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product element) {
        String query = "INSERT INTO internetshop.products(name, price) "
                + "VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, element.getName());
            preparedStatement.setDouble(2, element.getPrice());
            preparedStatement.execute();
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "create product query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * "
                + "FROM internetshop.products "
                + "WHERE product_id = ?";
        Product product = new Product();

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product = getProduct(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "get product by Id query. Stack trace: "
                    + e.getMessage());
        }
        return Optional.of(product);
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * "
                + "FROM internetshop.products";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productList.add(getProduct(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "get all products query. Stack trace: "
                    + e.getMessage());
        }
        return productList;
    }

    @Override
    public Product update(Product element) {
        String query = "UPDATE internetshop.products "
                + "SET name = ?, price = ? "
                + "WHERE product_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, element.getName());
            preparedStatement.setDouble(2, element.getPrice());
            preparedStatement.setLong(3, element.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "update product query. Stack trace: "
                    + e.getMessage());
        }
        return element;
    }

    @Override
    public boolean delete(Long id) {
        boolean result = false;
        String query = "DELETE FROM internetshop.products "
                + "WHERE product_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            result = preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "deletion product query. Stack trace: "
                    + e.getMessage());
        }
        return result;
    }

    private Product getProduct(ResultSet rs) throws SQLException {
        var productId = rs.getLong("product_id");
        var productName = rs.getString("name");
        var productPrice = rs.getDouble("price");
        var product = new Product(productName, productPrice);
        product.setId(productId);
        return product;
    }
}
