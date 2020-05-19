package mate.academy.internetshop.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.Objects;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class InitDataBaseUtil {
    private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class);

    public static void init() {
        String filePath = "init_db.sql";
        Connection con = ConnectionUtil.getConnection();

        try {
            ScriptRunner sr = new ScriptRunner(con);
            Reader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    Objects.requireNonNull(Level.class.getClassLoader()
                                            .getResourceAsStream(filePath))
                            )
                    );
            sr.runScript(reader);
        } catch (Exception e) {
            LOGGER.warn("Failed to Execute" + filePath
                    + " Error: " + e.getMessage());
        }
    }
}
