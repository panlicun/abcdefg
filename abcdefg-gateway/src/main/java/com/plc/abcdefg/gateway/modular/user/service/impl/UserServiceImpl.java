package com.plc.abcdefg.gateway.modular.user.service.impl;

import com.plc.abcdefg.gateway.common.util.Md5PasswordEncoder;
import com.plc.abcdefg.gateway.modular.user.mapper.UserMapper;
import com.plc.abcdefg.gateway.modular.user.service.UserService;
import com.plc.abcdefg.kernel.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User getUserByAccount(String account) {
        return userMapper.getByAccount(account);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public void saveSysUser() {
        User user = new User();
        user.setName("test");
        String password = new Md5PasswordEncoder().encode("123456");
        user.setPassword(password);
        user.setPhone("18888888888");
        userMapper.insert(user);
    }
}
