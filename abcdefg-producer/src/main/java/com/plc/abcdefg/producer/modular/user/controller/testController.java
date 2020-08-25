package com.plc.abcdefg.producer.modular.user.controller;

import com.plc.abcdefg.kernel.model.User;
import com.plc.abcdefg.producer.modular.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    UserService userService;


    @GetMapping("/{id}")
    public User getUser(@PathVariable(value = "id") int id) throws Exception {
        User user = userService.getUserById(id);
        throw new RuntimeException("哈哈，出错了");
//        return user;
    }

    @GetMapping("/name/{name}")
    public User getUserByName(@PathVariable(value = "name") String name) {
        User user = userService.getUserByAccount(name);
        return user;
    }

    @GetMapping("/name2/{name}")
    public ResponseEntity hello2(@PathVariable(value = "name") String name) {
        User user = userService.getUserByAccount2(name);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/saveUser")
    public void saveUser(@RequestBody User user) throws Exception{
        userService.saveSysUser(user);
    }

    @GetMapping("/queryAll")
    public ResponseEntity queryAll() throws Exception{
        List<User> userList = userService.queryAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/updateUser")
    public ResponseEntity updateUser() throws Exception{
        User user = new User();
        user.setId(1);
        user.setPhone("13888888888");
        userService.updateUser(user);
        return ResponseEntity.ok("OK");
    }

}
