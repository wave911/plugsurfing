package com.plugsurfing.hometask.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.validation.constraints.NotNull;

@ConfigurationProperties("redis")
@Data
public class RedisProperties {
    @NotNull
    private String hostname;

    @NotNull
    private int port;

    @NotNull
    private String prefix;

    @NotNull
    private String password;

    @NotNull
    private boolean useSsl;
}
