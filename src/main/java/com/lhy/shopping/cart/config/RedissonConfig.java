package com.lhy.shopping.cart.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class RedissonConfig {

    @Value("${redis.service.host}")
    private String redisLoginHost;
    @Value("${redis.service.port}")
    private Integer redisLoginPort;
    @Value("${redis.service.password}")
    private String redisLoginPassword;


    @Bean
    public RedissonClient redissonClient() {
        return createRedis(redisLoginHost, redisLoginPort, redisLoginPassword);
    }

    private RedissonClient createRedis(String redisHost, Integer redisPort, String redisPassword) {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + redisHost + ":" + redisPort + "");
        if (Objects.nonNull(redisPassword)) {
            singleServerConfig.setPassword(redisPassword);
        }
        config.setCodec(JsonJacksonCodec.INSTANCE);
        return Redisson.create(config);
    }
}