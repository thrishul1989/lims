/**
 * spring cloud simple service
 * @author lzhoumail@126.com 
 */
package com.todaysoft.lims.reagent;

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
public class ReagentServiceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ReagentServiceApplication.class, args);
    }
    
}