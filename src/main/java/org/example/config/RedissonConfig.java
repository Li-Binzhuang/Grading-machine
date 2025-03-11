package org.example.config;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.Repository.RedissonService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@Slf4j
@PropertySource("classpath:application.properties")
public class RedissonConfig {

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

    @Value("${redisson.threads}")
    private int threads;

    @Value("${redisson.connection_pool_size}")
    private int connectionPoolSize;

    @Value("${redisson.setConnectionMinimumIdleSize}")
    private int connectionMinimumIdleSize;

    @Value("${redisson.connectTimeout}")
    private int connectTimeout;


    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(getConnectURL())
                .setPassword(password)
                .setDatabase(database)
                .setConnectionPoolSize(connectionPoolSize)
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setConnectTimeout(connectTimeout);
        try{
            RedissonClient redissonClient=Redisson.create(config);
            log.info("redis客户端初始化成功");
            return redissonClient;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    @Bean
    public RedissonService redissonService(){
        return new RedissonService(redissonClient());
    }
}
