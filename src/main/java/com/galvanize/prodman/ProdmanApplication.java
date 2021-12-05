package com.galvanize.prodman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
// @ComponentScan(basePackages = { "com.galvanize" })
@Configuration
public class ProdmanApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdmanApplication.class, args);
    }

}
