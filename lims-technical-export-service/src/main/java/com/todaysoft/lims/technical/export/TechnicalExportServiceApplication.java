package com.todaysoft.lims.technical.export;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.todaysoft.lims"})
@MapperScan("com.todaysoft.lims.technical.export.mybatis.mapper")
@EnableTransactionManagement
public class TechnicalExportServiceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TechnicalExportServiceApplication.class, args);
    }
}