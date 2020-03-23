package com.plc.abcdefg.oauth.authentication.mobile;


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
public class MobileAuthService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        // TODO: 2020/3/23 后期如果有需要可以调用其他服务模块获取用户信息进行验证
        //通过手机号获取用户
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        com.plc.abcdefg.kernel.model.User user = userMapper.getByAccount(mobile);
        if(null == user){
            throw new UsernameNotFoundException(mobile);
        }
        return new User(user.getAccount(), user.getPassword(), simpleGrantedAuthorities);

    }
}
