package com.fzd.oauth.validate.sms.code;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsAuthenticationProvider implements AuthenticationProvider {


    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) (authentication).getPrincipal());
        if(userDetails == null){
            throw new InternalAuthenticationServiceException("没有找到与该手机号对应的用户");
        }
        SmsAuthenticationToken result = new SmsAuthenticationToken(userDetails.getAuthorities(), userDetails.getUsername());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
