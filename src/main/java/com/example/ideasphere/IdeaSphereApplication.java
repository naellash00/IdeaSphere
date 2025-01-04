package com.example.ideasphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IdeaSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdeaSphereApplication.class, args);
    }

}
