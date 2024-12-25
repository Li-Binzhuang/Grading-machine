package org.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class TestJdbc {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://192.168.31.221:3306/OJTest?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "mysql_cDhMBC";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // 使用连接
            Object stmt =  conn.createStatement();
            System.out.println("连接成功");
            String sql = "select * from questions";
            ResultSet rs = ((java.sql.Statement) stmt).executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("question_id") + " " + rs.getString("question_text") + " " + rs.getString("created_at") + " " + rs.getString("difficulty_level"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
