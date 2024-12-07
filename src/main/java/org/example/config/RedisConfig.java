package org.example.config;
//获取redis连接信息
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class RedisConfig {
        @Getter
        @Value("${redis.password}")
        private String password;
        @Value("${redis.host}")
        private String host;
        @Value("${redis.port}")
        private String port;
        @Value("${redis.database}")
        @Getter
        private Integer database;
        public String getConnectURL(){
            return  "redis://"+host + ":" + port;
        }
}
