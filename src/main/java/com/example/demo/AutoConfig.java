package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfig {
    @Bean
    public Storage createStorage(){
        System.out.println("createStorage called");
        return new ListStorage();
    }

}
