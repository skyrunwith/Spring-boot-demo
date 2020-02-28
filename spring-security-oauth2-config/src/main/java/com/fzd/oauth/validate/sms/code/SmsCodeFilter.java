package com.fzd.oauth.validate.sms.code;

import com.fzd.oauth.service.RedisCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SmsCodeFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCodeService redisCodeService;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if("/login/mobile".equalsIgnoreCase(request.getRequestURI())
                && HttpMethod.POST.name().equals(request.getMethod())){
            try {
                validateCode(new ServletWebRequest(request));
            }catch (Exception e){
                failureHandler.onAuthenticationFailure(request, response, new AuthenticationServiceException(e.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(ServletWebRequest request) throws Exception {
        String mobile = request.getParameter(SmsAuthenticationFilter.MOBILE_KEY);
        String codeInRedis = redisCodeService.get(request, mobile);
        String codeInRequest = request.getParameter("smsCode");

        if(StringUtils.isEmpty(codeInRequest)){
            throw new Exception("验证码不能为空");
        }
        if(codeInRedis == null){
            throw new Exception("验证码已过期");
        }
        if(!codeInRedis.equalsIgnoreCase(codeInRequest)){
            throw new Exception("验证码不正确");
        }
        redisCodeService.remove(request, mobile);
    }
}
