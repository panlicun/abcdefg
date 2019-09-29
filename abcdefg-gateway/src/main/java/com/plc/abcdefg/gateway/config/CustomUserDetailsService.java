package com.plc.abcdefg.gateway.config;

import com.plc.abcdefg.gateway.common.util.Md5PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // TODO: 2019/9/25 从数据库获取用户的信息
//        Member member = memberDao.findByMemberName(memberName);
//        if (member == null) {
//            throw new UsernameNotFoundException(memberName);
//        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
//        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        String password = new Md5PasswordEncoder().encode("123456");
        System.out.println(password);
        User user = new User("admin", password, simpleGrantedAuthorities);
        return user;

    }
}
