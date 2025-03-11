package org.example.config;

import org.example.Repository.RedissonService;
import org.example.Repository.RepositoryOPS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public RepositoryOPS repositoryOPS(DataConfig dataConfig, RedissonService redissonService){
        return new RepositoryOPS(dataConfig,redissonService);
    }

}
