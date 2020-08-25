package com.plc.abcdefg.consumer.modular.user.dao.impl;

import com.plc.abcdefg.consumer.modular.user.dao.ProducerRemote;
import com.plc.abcdefg.kernel.model.User;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;


@Component
public class ProducerRemoteHystrix implements FallbackFactory<ProducerRemote> {

    private static Logger logger = LoggerFactory.getLogger(ProducerRemoteHystrix.class);


    @Override
    public ProducerRemote create(Throwable throwable) {
        return new ProducerRemote() {
            @Override
            public User getUser(int id) {
                logger.info("fallback;reason was:",throwable);
                return null;
            }

            @Override
            public User getUserByName(String name) {
                logger.info("fallback;reason was:",throwable);
                return null;
            }

            @Override
            public void saveUser(User sysUser) {

            }
        };
    }
}
