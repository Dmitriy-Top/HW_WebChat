package main.java.utils;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;


/**
 * Created by admin on 25.01.2017.
 */
public class msgDAO {
    private static DataSource ds = null;
    private static Connection con = null;
    private static Statement stmt = null;
    private static InitialContext ic;

    public static String getMsgLi() {
        String result="";
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/PostgresDS");
            con = ds.getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from chat_log");

            while (rs.next()) {
                result +="<li>"+rs.getTimestamp("msgdate") +" - "+ rs.getString("nickname")+": "+ rs.getString("msg")+"</li>";
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Exception thrown :/");
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
        return result;
    }


    public static void sendMsg(String msg, String nickname, java.util.Date msgDate) {
        PreparedStatement preparedStatement = null;
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/PostgresDS");
            con = ds.getConnection();
            preparedStatement = con.prepareStatement("INSERT INTO chat_log (msg,nickname,msgdate) values(?,?,?)");
            preparedStatement.setString(1,msg);
            preparedStatement.setString(2,nickname);
            preparedStatement.setTimestamp(3, new Timestamp(msgDate.getTime()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Exception thrown :/");
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
