package com.plc.abcdefg.producer3.modular.user.controller;

import com.plc.abcdefg.kernel.model.User;
import com.plc.abcdefg.producer3.modular.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    SysUserService sysUserService;


    @GetMapping("/{id}")
    public ResponseEntity hello(@PathVariable(value = "id") int id) {
        User sysUser = sysUserService.getUserById(id);
        return ResponseEntity.ok(sysUser);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity hello1(@PathVariable(value = "name") String name) {
        User sysUser = sysUserService.getUserByAccount(name);
        return ResponseEntity.ok(sysUser);
    }

    @GetMapping("/name2/{name}")
    public ResponseEntity hello2(@PathVariable(value = "name") String name) {
        User sysUser = sysUserService.getUserByAccount2(name);
        return ResponseEntity.ok(sysUser);
    }

    @PostMapping("/saveUser")
    public void saveUser(@RequestBody User sysUser) throws Exception{
        sysUserService.saveSysUser(sysUser);
    }

    @GetMapping("/queryAll")
    public ResponseEntity queryAll() throws Exception{
        List<User> userList = sysUserService.queryAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/updateUser")
    public ResponseEntity updateUser() throws Exception{
        User user = new User();
        user.setId(1);
        user.setPhone("13888888888");
        sysUserService.updateUser(user);
        return ResponseEntity.ok("OK");
    }

}
