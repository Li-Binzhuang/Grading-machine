package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
public class DataConfig {
    @Value("${jdbc.url}")
    public  String url ;
    @Value("${jdbc.user}")
    public  String user;
    @Value("${jdbc.password}")
    public  String password;
    @Value("${jdbc.driver}")
    public  String driver;
}
