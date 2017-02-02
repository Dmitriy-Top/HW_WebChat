package main.java.utils;

import java.sql.*;


/**
 * Created by admin on 25.01.2017.
 */
public class msgDAO {


    public static String getMsgLi() {
        String result="";
        try {
            Statement stmt = DBHelper.getInstanceConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from chat_log");

            while (rs.next()) {
                result +="<li>"+rs.getTimestamp("msgdate") +" - "+ rs.getString("nickname")+": "+ rs.getString("msg")+"</li>";
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void sendMsg(String msg, String nickname, java.util.Date msgDate) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBHelper.getInstanceConnection().prepareStatement("INSERT INTO chat_log (msg,nickname,msgdate) values(?,?,?)");
            preparedStatement.setString(1,msg);
            preparedStatement.setString(2,nickname);
            preparedStatement.setTimestamp(3, new Timestamp(msgDate.getTime()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
