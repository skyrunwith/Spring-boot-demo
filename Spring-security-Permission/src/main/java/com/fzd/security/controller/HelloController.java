package com.fzd.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Spring-Security-Authentication";
    }

    @GetMapping("/index")
    public Authentication authentication(Authentication authentication){
//        return SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("/auth/admin")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String authAdmin(){
        return "您拥有Admin权限，可以查看";
    }
}
