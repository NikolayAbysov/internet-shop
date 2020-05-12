package mate.academy.internetshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class ConnectionUtil {
    private static final String DB_URL
            = "jdbc:postgresql://localhost:5432/postgres?currentSchema=internetshop";
    private static final String USER = "postgres";
    private static final String PASS = "123";
    private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.warn("Can't find Postgres driver" + e.getMessage());
            throw new RuntimeException("Can't find Postgres driver", e);
        }
    }

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.warn("Connection Failed" + e.getMessage());
            throw new RuntimeException("Connection Failed" + e.getMessage());
        }
    }
}
