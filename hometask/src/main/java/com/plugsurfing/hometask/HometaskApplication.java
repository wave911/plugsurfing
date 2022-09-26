package com.plugsurfing.hometask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(exclude = { RedisRepositoriesAutoConfiguration.class })
@EnableConfigurationProperties
@EnableRedisRepositories(basePackages = {"com.plugsurfing.hometask.repository"})
@EnableFeignClients(basePackages = {"com.plugsurfing.hometask.feign"})
public class HometaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(HometaskApplication.class, args);
    }

}
