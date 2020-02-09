package com.fzd.security.browser;

import com.fzd.security.domain.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(passwordEncoder.encode("123456"));
        System.out.println(myUser.getPassword());
        List<GrantedAuthority> authorities;
        if("fzd".equals(username)){
            authorities = AuthorityUtils.createAuthorityList("admin");
        }else{
            authorities = AuthorityUtils.createAuthorityList("test");
        }
        return  new User(myUser.getUsername(), myUser.getPassword(), myUser.isEnabled(),
                myUser.isAccountNonExpired(), myUser.isCredentialsNonExpired(),
                myUser.isAccountNonLocked(), authorities);
    }
}
