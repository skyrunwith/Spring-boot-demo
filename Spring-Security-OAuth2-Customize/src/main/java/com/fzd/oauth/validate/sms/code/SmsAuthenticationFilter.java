package com.fzd.oauth.validate.sms.code;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public final static String MOBILE_KEY = "mobile";

    private String mobileParameter = MOBILE_KEY;

    private boolean postOnly = true;

    public SmsAuthenticationFilter(){
        super(new AntPathRequestMatcher("/login/mobile", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);

        if(mobile == null){
            mobile = "";
        }

        mobile = mobile.trim();

        SmsAuthenticationToken smsToken = new SmsAuthenticationToken(mobile);
        setDetails(request, smsToken);
        return this.getAuthenticationManager().authenticate(smsToken);
    }

    private String obtainMobile(HttpServletRequest request){
        return request.getParameter(this.mobileParameter);
    }

    protected void setDetails(HttpServletRequest request, SmsAuthenticationToken auRequest){
        auRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public String getMobileParameter() {
        return mobileParameter;
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
