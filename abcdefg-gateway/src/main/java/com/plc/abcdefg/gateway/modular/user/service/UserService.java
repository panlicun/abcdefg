package com.plc.abcdefg.gateway.modular.user.service;


import com.plc.abcdefg.kernel.model.User;

public interface UserService {


    User getUserByAccount(String account);

    User getUserById(int id);

    void saveSysUser() throws Exception;


}
