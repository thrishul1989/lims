package com.todaysoft.lims.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
@MapperScan("com.todaysoft.lims.sample.mybatis.mapper")
@ComponentScan(basePackages = {"com.todaysoft.lims"})
public class OrderServiceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}