package com.plc.abcdefg.consumer.modular.user.dao;

import com.plc.abcdefg.consumer.modular.user.model.SysUser;
import com.plc.abcdefg.consumer.modular.user.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/3/19 0019.
 */
@FeignClient(name="producer")
public interface ProducerRemote {

    @RequestMapping("/{id}")
    User test(@PathVariable(value = "id") int id);

    @PostMapping("/saveUser")
    void saveUser(@RequestBody SysUser sysUser);
}
