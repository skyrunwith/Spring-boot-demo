package com.fzd.oauth.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 功能描述:
 *
 * @author: FZD
 * @date: 2020/2/28
 */
@RestController
public class UserController {

    @GetMapping("user")
    public Principal principal(Principal principal){
        return principal;
    }

    @GetMapping("auth1")
    @PreAuthorize("hasAuthority('user:add')")
    public String auth1(){
        return "你拥有 'user:add '权限";
    }

    @GetMapping("auth2")
    @PreAuthorize("hasAuthority('user:update')")
    public String auth2(){
        return "你拥有 'user:update '权限";
    }
}
