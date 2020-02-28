package com.fzd.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 功能描述:
 *
 * @author: FZD
 * @date: 2020/2/27
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager inMemory = new InMemoryUserDetailsManager();
        inMemory.createUser(User.withUsername("admin").password(this.passwordEncoder().encode("123456")).authorities("Admin").build());
        inMemory.createUser(User.withUsername("test").password(this.passwordEncoder().encode("123456")).authorities("Test").build());
        return inMemory;
    }
}
