package com.plc.abcdefg.gateway.auth.service;

import com.plc.abcdefg.gateway.auth.mapper.UserMapper;
import com.plc.abcdefg.gateway.common.util.Md5PasswordEncoder;
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
        String str = new Md5PasswordEncoder().encode("android");
        System.out.println(str);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        com.plc.abcdefg.kernel.model.User user = userMapper.getByAccount(name);
        if(null == user){
            throw new UsernameNotFoundException(name);
        }
        return new User(user.getAccount(), user.getPassword(), simpleGrantedAuthorities);

    }
}
