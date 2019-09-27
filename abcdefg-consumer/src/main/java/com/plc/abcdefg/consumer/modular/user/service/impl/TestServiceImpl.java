package com.plc.abcdefg.consumer.modular.user.service.impl;

import com.plc.abcdefg.consumer.modular.user.dao.Producer2Remote;
import com.plc.abcdefg.consumer.modular.user.dao.ProducerRemote;
import com.plc.abcdefg.consumer.modular.user.model.SysUser;
import com.plc.abcdefg.consumer.modular.user.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    ProducerRemote producerRemote;
    @Resource
    Producer2Remote producer2Remote;

    public void testService(){
        //测试分布式事物
        saveUser1();
        saveUser2();
    }

    public void saveUser1(){
        SysUser sysUser = new SysUser();
        sysUser.setAccount("test1");
        producerRemote.saveUser(sysUser);
    }


    public void saveUser2(){
        SysUser sysUser2 = new SysUser();
        sysUser2.setAccount("test2");
        producer2Remote.saveUser(sysUser2);
    }
}
