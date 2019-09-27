package com.plc.abcdefg.producer.modular.user.service;


import com.plc.producer.modular.user.model.SysUser;

public interface SysUserService {


    SysUser getUserByAccount(String account);

    SysUser getUserById(int id);

    void saveSysUser(SysUser sysUser);


}
