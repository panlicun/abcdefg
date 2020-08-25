package com.plc.abcdefg.consumer.modular.user.service;


import com.plc.abcdefg.kernel.model.User;

public interface TestService{

    void testService();

    /**
     * 测试feign调用微服务
     */
    User getUser(int userId);
}
