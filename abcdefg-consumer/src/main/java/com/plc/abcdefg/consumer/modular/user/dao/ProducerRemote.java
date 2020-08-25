package com.plc.abcdefg.consumer.modular.user.dao;

import com.plc.abcdefg.consumer.config.FeignConfiguration;
import com.plc.abcdefg.consumer.modular.user.dao.impl.ProducerRemoteHystrix;
import com.plc.abcdefg.kernel.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/3/19 0019.
 */
@FeignClient(name="abcdefg-producer",configuration = FeignConfiguration.class,fallbackFactory = ProducerRemoteHystrix.class)
public interface ProducerRemote {

    @RequestMapping("/{id}")
    User getUser(@PathVariable(value = "id") int id);

    @GetMapping("/name/{name}")
    User getUserByName(@PathVariable(value = "name") String name);

    @PostMapping("/saveUser")
    void saveUser(@RequestBody User sysUser);
}
