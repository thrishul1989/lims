package com.todaysoft.lims.biology;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.todaysoft.lims.biology.mybatis.mapper")
@EnableScheduling
public class BiologyApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BiologyApplication.class, args);
    }
}