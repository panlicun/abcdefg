package com.plc.abcdefg.producer.modular.user.service.impl;

import com.plc.producer.modular.user.mapper.SysUserMapper;
import com.plc.producer.modular.user.model.SysUser;
import com.plc.producer.modular.user.service.SysUserService;
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
