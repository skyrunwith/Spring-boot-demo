package com.fzd.oauth.web;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述:
 *
 * @author: FZD
 * @date: 2020/2/27
 */
@Slf4j
@RestController
public class TestEndpoints {

    @GetMapping("/product/{id}")
    public Authentication product(@PathVariable String id){
        log.debug(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("/order/{id}")
    public Authentication order(@PathVariable String id){
        log.debug(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("/index")
    public Object index(@AuthenticationPrincipal Authentication authentication , HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header,"bearer ");
        return Jwts.parser().setSigningKey("test_key".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
    }
}
