package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.lib.anno.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class ProductDaoImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class);

    @Override
    public Product create(Product element) {
        String query = "INSERT INTO internetshop.products(name, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, element.getName());
            preparedStatement.setDouble(2, element.getPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.warn("Unable to execute create product query. Stack trace: " + e.getMessage());
        }
        return element;
    }

    @Override
    public Optional<Product> get(Long id) {
        Product product = new Product();
        String query = "select * from internetshop.products where product_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            product = getProduct(resultSet, product);
        } catch (SQLException e) {
            LOGGER.warn("Unable to execute get product by Id query. Stack trace: "
                    + e.getMessage());
        }
        return Optional.of(product);
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        String query = "select * from internetshop.products";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            getAllProducts(resultSet, productList);
        } catch (SQLException e) {
            LOGGER.warn("Unable to execute get all products query. Stack trace: " + e.getMessage());
        }
        return productList;
    }

    @Override
    public Product update(Product element) {
        String query = "update internetshop.products set name = ?, price = ? where product_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, element.getName());
            preparedStatement.setDouble(2, element.getPrice());
            preparedStatement.setLong(3, element.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.warn("Unable to execute update product query. Stack trace: " + e.getMessage());
        }
        return element;
    }

    @Override
    public boolean delete(Long id) {
        boolean result = false;
        String query = "delete from internetshop.products where product_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            result = preparedStatement.execute();

        } catch (SQLException e) {
            LOGGER.warn("Unable to execute deletion product query. Stack trace: " + e.getMessage());
        }
        return result;
    }

    private Product getProduct(ResultSet rs, Product product) throws SQLException {
        while (rs.next()) {
            product.setId(rs.getLong("product_id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));
        }
        return product;
    }

    private List<Product> getAllProducts(ResultSet rs, List<Product> productList)
            throws SQLException {
        while (rs.next()) {
            Product product = new Product((rs.getString("name")));
            product.setId(rs.getLong("product_id"));
            product.setPrice(rs.getDouble("price"));
            productList.add(product);
        }
        return productList;
    }
}
