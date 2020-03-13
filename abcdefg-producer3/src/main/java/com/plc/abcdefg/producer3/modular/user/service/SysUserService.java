package com.plc.abcdefg.producer3.modular.user.service;


import com.plc.abcdefg.kernel.model.User;

public interface SysUserService {


    User getUserByAccount(String account);

    User getUserByAccount2(String account);

    User getUserById(int id);

    void saveSysUser(User sysUser) throws Exception;



}
