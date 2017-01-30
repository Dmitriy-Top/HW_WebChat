package main.java.utils;

import javax.naming.InitialContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Created by admin on 30.01.2017.
 */
public class LoginDAO {
    private static DataSource ds = null;
    private static Connection con = null;
    private static Statement stmt = null;
    private static InitialContext ic;


    public static boolean isAuthenticated(String jsessionid) {
        boolean isAuth = false;
        if (jsessionid == null) return false;
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/PostgresDS");
            con = ds.getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from sessions");
            while (rs.next()) {
                if (rs.getString("session").equals(jsessionid)) {
                    isAuth = true;
                    break;
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return isAuth;
    }

    public static boolean auth(String login, String pass) {
        boolean isAuth = false;
        if (login == null | pass == null) return false;
        String md5hash = MD5hash(pass);
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/PostgresDS");
            con = ds.getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                if (rs.getString("user").equals(login) && rs.getString("pass").equals(md5hash)) {
                    isAuth = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isAuth;
    }

    public static void addAuthSession(HttpServletRequest req) {
        String jsessionid=null;
        Cookie[] cookies = req.getCookies();
        if (cookies!=null)for (Cookie cookie:cookies){
            if ("JSESSIONID".equals(cookie.getName())){
                jsessionid = cookie.getValue();
                break;
            }
        }
        if (jsessionid!=null){
            try {
                ic = new InitialContext();
                ds = (DataSource) ic.lookup("java:/PostgresDS");
                con = ds.getConnection();
                PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO sessions (session) values(?)");
                preparedStatement.setString(1,jsessionid);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private static String MD5hash(String str) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1)); //хз что это и как это
                result = sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;

    }
}
