package com.plc.abcdefg.consumer.modular.user.service.impl;

import com.plc.abcdefg.consumer.modular.user.dao.Producer2Remote;
import com.plc.abcdefg.consumer.modular.user.dao.ProducerRemote;
import com.plc.abcdefg.consumer.modular.user.service.TestService;
import com.plc.abcdefg.kernel.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServiceImpl
        implements TestService {

    @Resource
    ProducerRemote producerRemote;
    @Resource
    Producer2Remote producer2Remote;

    /**
     * 测试feign调用微服务
     */
    @Override
    public User getUser(int userId) {
        return producerRemote.getUser(userId);
    }

    public void testService(){
        //测试分布式事物
        saveUser1();
        saveUser2();
    }

    public void saveUser1(){
        User user = new User();
        user.setAccount("test1");
        producerRemote.saveUser(user);
    }


    public void saveUser2(){
        User user = new User();
        user.setAccount("test2");
        producer2Remote.saveUser(user);
    }
}
