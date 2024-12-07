package org.example.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:application.properties")
public class RedissonConfig {
    @Autowired
    RedisConfig redisConfig;
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
              .setAddress(redisConfig.getConnectURL())
              .setPassword(redisConfig.getPassword())
              .setDatabase(redisConfig.getDatabase());
        return Redisson.create(config);
    }
}
