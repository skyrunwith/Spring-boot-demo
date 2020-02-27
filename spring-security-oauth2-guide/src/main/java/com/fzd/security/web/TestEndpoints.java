package com.fzd.security.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
