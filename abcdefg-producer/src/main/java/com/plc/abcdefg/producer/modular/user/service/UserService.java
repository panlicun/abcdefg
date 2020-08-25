package com.plc.abcdefg.producer.modular.user.service;


import com.plc.abcdefg.kernel.model.User;

import java.util.List;

public interface UserService {


    User getUserByAccount(String account);

    User getUserByAccount2(String account);

    User getUserById(int id);

    void saveSysUser(User user) throws Exception;

    List<User> queryAll();

    void updateUser(User user);


}
