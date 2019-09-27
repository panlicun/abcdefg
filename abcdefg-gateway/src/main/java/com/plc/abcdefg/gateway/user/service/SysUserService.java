package com.plc.abcdefg.gateway.user.service;


import com.plc.abcdefg.gateway.user.model.SysUser;

public interface SysUserService {


    SysUser getUserByAccount(String account);

    SysUser getUserById(int id);

    void saveSysUser(SysUser sysUser) throws Exception;


}
