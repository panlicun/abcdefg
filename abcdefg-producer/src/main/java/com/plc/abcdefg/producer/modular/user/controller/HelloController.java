package com.plc.abcdefg.producer.modular.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloController {


    @RequestMapping("/hello")
    public String from() {
        return "hello";
    }
}