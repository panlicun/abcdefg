package com.plc.abcdefg.gateway.user.service.impl;

import com.plc.abcdefg.gateway.user.mapper.SysUserMapper;
import com.plc.abcdefg.gateway.user.model.SysUser;
import com.plc.abcdefg.gateway.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserByAccount(String account) {
        return sysUserMapper.getByAccount(account);
    }

    @Override
    public SysUser getUserById(int id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }
}
