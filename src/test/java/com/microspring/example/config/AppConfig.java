package com.microspring.example.config;

import com.microspring.example.entity.MyService;
import com.microspring.ioc.bean.annotation.Bean;
import com.microspring.ioc.bean.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyService();
    }
}

