package com.fzd.security.sms.code;

import com.fzd.security.controller.ValidateController;
import com.fzd.security.validate.code.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
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

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if("/login/mobile".equalsIgnoreCase(request.getRequestURI())
                && HttpMethod.POST.name().equals(request.getMethod())){
            try {
                validateCode(new ServletWebRequest(request));
            }catch (ValidateCodeException e){
                failureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(ServletWebRequest request){
        String mobile = request.getParameter(SmsAuthenticationFilter.MOBILE_KEY);
        SmsCode smsCodeInSession = (SmsCode) sessionStrategy.getAttribute(request, ValidateController.SESSION_KEY_SMS_CODE + mobile);
        String codeInRequest = request.getParameter("smsCode");
        if(StringUtils.isEmpty(codeInRequest)){
            throw new ValidateCodeException("验证码不能为空");
        }
        if(smsCodeInSession.getCode() == null){
            throw new ValidateCodeException("验证码不存在");
        }
        if(smsCodeInSession.isExpired()){
            sessionStrategy.removeAttribute(request, ValidateController.SESSION_KEY_SMS_CODE + mobile);
            throw new ValidateCodeException("验证码已过期");
        }
        if(!smsCodeInSession.getCode().equalsIgnoreCase(codeInRequest)){
            throw new ValidateCodeException("验证码不正确");
        }
        sessionStrategy.removeAttribute(request, ValidateController.SESSION_KEY_SMS_CODE + mobile);
    }
}
