package com.plc.abcdefg.gateway.modular.user.controller;

import com.plc.abcdefg.gateway.modular.user.service.UserService;
import com.plc.abcdefg.kernel.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/oauthLogin")
    public ResponseEntity login() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{id}")
    public ResponseEntity hello(@PathVariable(value = "id") int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity hello1(@PathVariable(value = "name") String name) {
        User user = userService.getUserByAccount(name);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/saveUser")
    public void saveUser() throws Exception{
        userService.saveSysUser();
    }

}
