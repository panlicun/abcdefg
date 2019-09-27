package com.plc.abcdefg.producer3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.plc.core","com.plc.producer3"})
public class Producer3Application {
    public static void main(String[] args) {
        SpringApplication.run(Producer3Application.class, args);
    }
}
