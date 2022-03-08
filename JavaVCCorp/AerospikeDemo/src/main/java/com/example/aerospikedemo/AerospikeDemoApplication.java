package com.example.aerospikedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AerospikeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AerospikeDemoApplication.class, args);
    }

}
