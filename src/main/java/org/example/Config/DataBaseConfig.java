package org.example.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
public class DataBaseConfig {
    @Value("${jdbc.url}")
    public  String url ;
    @Value("${jdbc.user}")
    public  String user;
    @Value("${jdbc.password}")
    public  String password;
    @Value("${jdbc.driver}")
    public  String driver;

    @Bean(value = "connection")
    public Connection getStatment(){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            log.info(url+" "+user+" "+password+" 连接成功");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
