package com.todaysoft.lims.report;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.todaysoft.lims"})
public class ExportServiceApplication implements CommandLineRunner
{
    public static void main(String[] args)
    {
        SpringApplication.run(ExportServiceApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {}
}