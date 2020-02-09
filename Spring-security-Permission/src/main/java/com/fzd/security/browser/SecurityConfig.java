package com.fzd.security.browser;

import com.fzd.security.handler.MyAccessDeniedHandler;
import com.fzd.security.handler.MyAuthenticationFailureHandler;
import com.fzd.security.handler.MyAuthenticationSuccessHandler;
import com.fzd.security.sms.code.SmsAuthenticationConfig;
import com.fzd.security.sms.code.SmsCodeFilter;
import com.fzd.security.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private SmsCodeFilter smsCodeFilter;

    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;

    @Autowired
    private SessionInformationExpiredStrategy sessionStrategy;

    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
             //短信验证码filter
            .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .loginPage("/authentication/require")
            .loginProcessingUrl("/login")
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
            .and()
                .authorizeRequests()
                .antMatchers("/authentication/require", "/login.html", "/code/image", "/code/sms").permitAll()
                .anyRequest().authenticated()
            .and()
                .sessionManagement() // 添加 Session管理器
                // Session失效后跳转到这个链接
                .invalidSessionUrl("/session/invalid")
                .maximumSessions(1)
                .expiredSessionStrategy(sessionStrategy)
            .and().and()
                .csrf().disable()
                // 将短信验证码认证配置加到 Spring Security 中
            .apply(smsAuthenticationConfig)
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/**/*.css");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
