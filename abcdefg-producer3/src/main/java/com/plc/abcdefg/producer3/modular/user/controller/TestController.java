package com.plc.abcdefg.producer3.modular.user.controller;

import com.plc.abcdefg.producer3.modular.user.model.SysUser;
import com.plc.abcdefg.producer3.modular.user.service.SysUserService;
import com.plc.producer3.modular.user.model.SysUser;
import com.plc.producer3.modular.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping("/{id}")
    public ResponseEntity hello(@PathVariable(value = "id") int id) {
        SysUser sysUser = sysUserService.getUserById(id);
        return ResponseEntity.ok(sysUser);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity hello1(@PathVariable(value = "name") String name) {
        SysUser sysUser = sysUserService.getUserByAccount(name);
        return ResponseEntity.ok(sysUser);
    }

    @PostMapping("/saveUser")
    public void saveUser(@RequestBody SysUser sysUser) throws Exception{
        sysUserService.saveSysUser(sysUser);
    }

}
