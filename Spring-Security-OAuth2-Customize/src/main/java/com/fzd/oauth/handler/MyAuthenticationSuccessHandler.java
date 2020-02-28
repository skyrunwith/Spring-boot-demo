package com.fzd.oauth.handler;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

/**
 * 功能描述:
 *
 * @author: FZD
 * @date: 2020/2/27
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || !authorizationHeader.startsWith("Basic ")){
            throw new UnapprovedClientAuthenticationException("请求头无Authorization信息");
        }
        String[] token = extractAndDecodeHeader(authorizationHeader, request);
        String clientId = token[0];
        String clientSecret = token[1];

        TokenRequest tokenRequest = null;

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if(clientDetails == null){
            throw new UnapprovedClientAuthenticationException("Invalid client id: " + clientId);
        }else if(!clientDetails.getClientSecret().equals(clientSecret)){
            throw new UnapprovedClientAuthenticationException("Invalid clientSecret: " + clientSecret);
        }else{
            tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "custom");
        }

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken auth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        log.info("登录成功");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(auth2AccessToken));
    }


    private String[] extractAndDecodeHeader(String authorizationHeader, HttpServletRequest request){
        byte[] base64Token = authorizationHeader.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decode;
        try {
            decode = Base64.getDecoder().decode(base64Token);
        }catch (IllegalArgumentException e){
            throw new BadCredentialsException("解析 authorization header 失败");
        }
        String token = new String(decode, StandardCharsets.UTF_8);
        int delim = token.indexOf(":");
        if(delim == -1){
            throw new BadCredentialsException("Invalid basic authorization header");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

}
