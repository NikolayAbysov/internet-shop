package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.anno.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public Optional<User> getByLogin(String login) {
        String query = "SELECT * "
                + "FROM internetshop.users "
                + "WHERE login=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUser(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "get Shopping cart by Id query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO internetshop.users (name, login, password) "
                + "VALUES(?,?,?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            addRoles(user);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "create User query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * "
                + "FROM internetshop.users "
                + "WHERE user_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUser(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "get User query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * "
                + "FROM internetshop.users;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(getUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "getAll Users query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE internetshop.users "
                + "SET name=?, login=?, password=? WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
            deleteRoles(user);
            addRoles(user);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "update User query. Stack trace: "
                    + e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM internetshop.users "
                + "WHERE user_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "delete User query. Stack trace: "
                    + e.getMessage());
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        return new User(userId, name, login, password, getRoles(userId));
    }

    private Set<Role> getRoles(Long userId) {
        Set<Role> roles = new HashSet<>();
        String query = "SELECT roles.role_name FROM users_roles INNER JOIN roles "
                + "ON  users_roles.role_id=roles.role_id \n"
                + "WHERE users_roles.user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roles.add(Role.of(resultSet.getString("role_name")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "getRoles query. Stack trace: "
                    + e.getMessage());
        }
        return roles;
    }

    private void addRoles(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Role role: user.getRoles()) {
                String query = "INSERT INTO internetshop.users_roles (user_id, role_id) "
                        + "VALUES (?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, user.getId());
                statement.setLong(2, role.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "addRoles query. Stack trace: "
                    + e.getMessage());
        }
    }

    private void deleteRoles(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM internetshop.users_roles "
                    + "WHERE user_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to execute "
                    + "deleteRoles query. Stack trace: "
                    + e.getMessage());
        }
    }
}
