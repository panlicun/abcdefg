package com.plc.abcdefg.producer3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.plc.abcdefg.kernel","com.plc.abcdefg.producer3"})
public class Producer3Application {
    public static void main(String[] args) {
        SpringApplication.run(Producer3Application.class, args);
    }
}
