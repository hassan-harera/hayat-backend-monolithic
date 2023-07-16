package com.harera.hayat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@ComponentScan(basePackages = { "com.harera.hayat.*" })
@EnableRedisRepositories
@SpringBootApplication
public class Hayat {

    public static void main(String[] args) {
        SpringApplication.run(Hayat.class, args);
    }

}
