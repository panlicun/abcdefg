package com.plc.abcdefg.producer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.plc.core","com.plc.producer2"})
public class Producer2Application {
    public static void main(String[] args) {
        SpringApplication.run(Producer2Application.class, args);
    }
}
