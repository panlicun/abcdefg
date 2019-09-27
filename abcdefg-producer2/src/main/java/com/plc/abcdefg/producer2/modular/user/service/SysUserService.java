package com.plc.abcdefg.producer2.modular.user.service;

import com.plc.abcdefg.producer2.modular.user.model.SysUser;
import com.plc.producer2.modular.user.model.SysUser;


public interface SysUserService {


    SysUser getUserByAccount(String account);

    SysUser getUserById(int id);

    void saveSysUser(SysUser sysUser) throws Exception;


}
