package com.plc.abcdefg.oauth.auth.service;


import com.plc.abcdefg.oauth.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        com.plc.abcdefg.kernel.model.User user = userMapper.getByAccount(name);
        if(null == user){
            throw new UsernameNotFoundException(name);
        }
        return new User(user.getAccount(), user.getPassword(), simpleGrantedAuthorities);

    }
}
