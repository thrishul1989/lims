package com.todaysoft.lims.ngs.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.druid.pool.DruidDataSource;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;


@Configuration
@EnableConfigurationProperties(Configs.class)
@EnableScheduling
public class RootContext implements ApplicationContextAware
{
    private static ApplicationContext context;
    
    @Autowired
    private Configs configs;
    
    private static Logger log = LoggerFactory.getLogger(RootContext.class);
    
    
    @Bean
    public DataSource dataSource()
        throws Exception
    {
        DruidDataSource dataSource = new DruidDataSource();
        
        dataSource.setDriverClassName(configs.getDataSourceDriverClassName());
        dataSource.setUrl(configs.getDataSourceUrl());
        dataSource.setUsername(configs.getDataSourceUsername());
        dataSource.setPassword(configs.getDataSourcePassword());
        dataSource.setMaxActive(configs.getDataSourceMaxActive());
        dataSource.setInitialSize(configs.getDataSourceInitialSize());
        
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(1);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(30000);
        
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(180);
        
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestOnReturn(false);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestWhileIdle(true);
        /** 开启Druid的监控统计功能*/
        dataSource.setFilters("stat,config");
        dataSource.setLogAbandoned(true);
        
        Properties properties = new Properties();
        properties.setProperty("config.decrypt", "true");
        properties.setProperty("config.decrypt.key", configs.getDataSourcePublicKey());
        dataSource.setConnectProperties(properties);
        
        return dataSource;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext context)
        throws BeansException
    {
        RootContext.context = context;
    }
    
    
    @Bean(name = "producer")
    public Producer producer()
    {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, configs.getAliyunONSAccessKey());
        properties.put(PropertyKeyConst.SecretKey, configs.getAliyunONSSecretKey());
        properties.put(PropertyKeyConst.ProducerId, configs.getAliyunONSProducer());
        properties.put(PropertyKeyConst.ONSAddr, configs.getAliyunONSUrl());
        Producer producer = ONSFactory.createProducer(properties);
        producer.start();
        
        if (log.isDebugEnabled())
        {
            log.debug("Start aliyun ons producer success, producer id {}", configs.getAliyunONSProducer());
        }
        
        return producer;
    }
    
    public static <T> T getBean(Class<T> requiredType)
    {
        return context.getBean(requiredType);
    }
}
