package com.plc.abcdefg.producer3.modular.user.service;

import com.plc.producer3.modular.user.model.SysUser;


public interface SysUserService {


    SysUser getUserByAccount(String account);

    SysUser getUserById(int id);

    void saveSysUser(SysUser sysUser) throws Exception;


}
