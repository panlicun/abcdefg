package com.plc.abcdefg.producer.modular.user.controller;

import com.plc.producer.common.annotion.BussinessLog;
import com.plc.producer.modular.user.model.SysUser;
import com.plc.producer.modular.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: 潘立存
 * @Description：
 * @Date： Created in 下午 3:37 on 2018/3/14 0014.
 */
@RestController
public class testController {

    @Autowired
    SysUserService sysUserService;

    @BussinessLog(value="hello world")
    @RequestMapping("/{id}")
    public ResponseEntity hello(@PathVariable(value = "id") int id) {
        SysUser sysUser = sysUserService.getUserById(id);
        return ResponseEntity.ok(sysUser);
    }

    @Value("${hello:apollo}")
    private String hello;

    @RequestMapping("/helloApollo")
    @ResponseBody
    public String sayApollo(){
        return "hello "+hello;
    }

    @RequestMapping("/test/{name}")
    public SysUser test(@PathVariable(value = "name") String name) {
        SysUser user = new SysUser();
        user.setName(name);
        return user;
    }

    @PostMapping("/saveUser")
    public void saveUser(@RequestBody SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
    }

}
