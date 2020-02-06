package com.fzd.security.validate.code;

import com.fzd.security.controller.ValidateController;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ValidateCodeFilter extends OncePerRequestFilter {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if("/login".equalsIgnoreCase(request.getRequestURI()) &&
                "post".equalsIgnoreCase(request.getMethod())){
            try {
                validateCode(request);
            }catch (ValidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(HttpServletRequest httpServletRequest){
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(new ServletWebRequest(httpServletRequest), ValidateController.SESSION_KEY_IMAGE_CODE);
        String codeInRequest = httpServletRequest.getParameter("imageCode");
        if(StringUtils.isEmpty(codeInRequest)){
            throw new ValidateCodeException("验证码不能为空");
        }
        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在");
        }
        if(codeInSession.isExpired()){
            throw new ValidateCodeException("验证码已过期");
        }
        if(!codeInRequest.equalsIgnoreCase(codeInSession.getCode())){
            throw new ValidateCodeException("验证码不正确");
        }
        sessionStrategy.removeAttribute(new ServletWebRequest(httpServletRequest), ValidateController.SESSION_KEY_IMAGE_CODE);
    }
}
