package com.plugsurfing.hometask.config;

import java.time.Duration;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
@Data
@Profile({"!test & !local"})
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {

    @Bean
    JedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getHostname(), redisProperties.getPort());
        redisStandaloneConfiguration.setPassword(redisProperties.getPassword());

        JedisClientConfiguration jedisClientConfiguration;
        if (redisProperties.isUseSsl()){
            jedisClientConfiguration = JedisClientConfiguration.builder().useSsl().build();
        } else {
            jedisClientConfiguration = JedisClientConfiguration.builder().build();
        }

        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
        return factory;
    }

    @Bean(value = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Primary
    @Bean(name = "cacheManager1Hour")
    public CacheManager cacheManager1Hour(RedisConnectionFactory redisConnectionFactory,
                                          RedisProperties redisProperties) {
        return buildCacheManager(redisConnectionFactory, redisProperties, Duration.ofMinutes(60L));
    }

    private CacheManager buildCacheManager(RedisConnectionFactory redisConnectionFactory,
                                           RedisProperties redisProperties, Duration expiration) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                        .computePrefixWith((cacheName) -> redisProperties.getPrefix() + "::" + cacheName + "::")
                        .entryTtl(expiration)).build();
    }
}
