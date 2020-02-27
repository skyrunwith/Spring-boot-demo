package com.fzd.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

//@EnableAuthorizationServer
//@EnableResourceServer
@SpringBootApplication
public class SpringSecurityOauth2GuideApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityOauth2GuideApplication.class, args);
    }

}
