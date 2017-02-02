package main.java.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by admin on 02.02.2017.
 */
public class DBHelper {
    private static DataSource ds = null;
    private static Connection con = null;
    private static InitialContext ic;

    public static Connection getInstanceConnection() {
            try {
                if (con!=null)con.close();
                ic = new InitialContext();
                ds = (DataSource) ic.lookup("java:/PostgresDS");
                con = ds.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return con;
    }

    private DBHelper() {}
}
