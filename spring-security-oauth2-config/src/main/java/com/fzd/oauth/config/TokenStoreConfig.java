package com.fzd.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 功能描述:
 *
 * @author: FZD
 * @date: 2020/2/28
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

//    @Bean
//    public RedisTokenStore redisTokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
}
