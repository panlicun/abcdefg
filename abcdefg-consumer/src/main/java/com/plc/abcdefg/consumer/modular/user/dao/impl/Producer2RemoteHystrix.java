package com.plc.abcdefg.consumer.modular.user.dao.impl;

import com.plc.abcdefg.consumer.modular.user.dao.Producer2Remote;
import com.plc.abcdefg.kernel.model.User;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class Producer2RemoteHystrix implements FallbackFactory<Producer2Remote> {

    private static Logger logger = LoggerFactory.getLogger(Producer2RemoteHystrix.class);


    @Override
    public Producer2Remote create(Throwable throwable) {
        return new Producer2Remote() {
            @Override
            public User test(int id) {
                logger.info("fallback;reason was:",throwable);
                return null;
            }

            @Override
            public void saveUser(User sysUser) {

            }
        };
    }
}
