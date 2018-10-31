package com.todaysoft.lims.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServiceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
    
    @Bean
    public PasswordEncoder getDefaultPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}