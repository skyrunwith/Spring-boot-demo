package com.fzd.oauth.config;

import com.fzd.oauth.handler.MyAuthenticationFailureHandler;
import com.fzd.oauth.handler.MyAuthenticationSuccessHandler;
import com.fzd.oauth.validate.sms.code.SmsAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 功能描述:
 *
 * @author: FZD
 * @date: 2020/2/27
 */
@Configuration
@EnableResourceServer
public class AuthorizationResourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private MyAuthenticationFailureHandler failureHandler;

    @Autowired
    private MyAuthenticationSuccessHandler successHandler;

    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/code/sms").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .apply(smsAuthenticationConfig);
    }
}
