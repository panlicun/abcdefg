package com.plc.abcdefg.producer.modular.user.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.plc.abcdefg.kernel.datasource.annotation.DataSource;
import com.plc.abcdefg.kernel.model.User;
import com.plc.abcdefg.producer.modular.user.mapper.UserMapper;
import com.plc.abcdefg.producer.modular.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User getUserByAccount(String account) {
        return userMapper.getByAccount(account);
    }

    @Override
    public User getUserByAccount2(String account) {
        return userMapper.getByAccount(account);
    }



    @Override
    public User getUserById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public void saveSysUser(User user) throws Exception {

    }

    @Override
    public List<User> queryAll() {
        Page userPage = new Page(1,10);
        List<User> userList = userMapper.selectPage(userPage,null);
        int count = userPage.getTotal();
        System.out.println(count);
        return userList;
    }

    @Override
    public void updateUser(User user) {
        User u = userMapper.selectById(user.getId());
        user.setVersion(u.getVersion());
        userMapper.updateById(user);
    }

}
