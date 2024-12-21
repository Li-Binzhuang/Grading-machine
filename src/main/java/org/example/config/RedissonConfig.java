package org.example.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:application.properties")
public class RedissonConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    @Value("${redisson.threads}")
    private int threads;

    @Value("${redisson.connection_pool_size}")
    private int connectionPoolSize;

    @Value("${redisson.setConnectionMinimumIdleSize}")
    private int connectionMinimumIdleSize;

    @Value("${redisson.connectTimeout}")
    private int connectTimeout;

    @Autowired
    RedisConfig redisConfig;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
              .setAddress(redisConfig.getConnectURL())
              .setPassword(redisConfig.getPassword())
              .setDatabase(redisConfig.getDatabase())
              .setConnectionPoolSize(connectionPoolSize)
              .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
              .setConnectTimeout(connectTimeout);
        try{
            RedissonClient redissonClient=Redisson.create(config);
            logger.info("redis客户端初始化成功");
            return redissonClient;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
