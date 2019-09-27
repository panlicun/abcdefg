package com.plc.abcdefg.consumer.modular.user.controller;

import com.plc.abcdefg.consumer.modular.user.dao.Producer2Remote;
import com.plc.abcdefg.consumer.modular.user.model.User;
import com.plc.abcdefg.consumer.modular.user.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Administrator on 2018/3/19 0019.
 */
@RestController
public class ConsumerController {
    private static Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    TestService testService;
    @Autowired
    Producer2Remote producer2Remote;

    @GetMapping("/aa/{id}")
    public ResponseEntity aa(HttpServletRequest request,@PathVariable(value = "id") int id) {
        logger.info("requeryType={} | uri={} | responseBody={}", request.getMethod(), request.getRequestURI());
        System.out.println("aaaaa");
        return ResponseEntity.ok("aaa");
    }

    @GetMapping("/test/{id}")
    public ResponseEntity test(HttpServletRequest request,@PathVariable(value = "id") int id) {
        logger.info("requeryType={} | uri={} | responseBody={}", request.getMethod(), request.getRequestURI());
        User user = producer2Remote.test(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/add")
    public ResponseEntity add() {
        testService.testService();
        return ResponseEntity.ok(null);
    }


}
