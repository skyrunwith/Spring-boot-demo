package com.fzd.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述:
 *
 * @author: FZD
 * @date: 2020/1/21
 */
@RestController
public class DemoController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring Security";
    }
}
