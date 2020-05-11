package mate.academy.internetshop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.Objects;
import mate.academy.internetshop.util.ConnectionUtil;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class InitDataBase {
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
                                            .getResourceAsStream("init_db.sql"))
                            )
                    );
            sr.runScript(reader);
        } catch (Exception e) {
            LOGGER.warn("Failed to Execute" + filePath
                    + " Error: " + e.getMessage());
        }
    }
}
