package com.example.ingressbookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IngressBookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngressBookStoreApplication.class, args);
    }

}
