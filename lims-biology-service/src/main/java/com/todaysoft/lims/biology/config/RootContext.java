package com.todaysoft.lims.biology.config;

import java.util.Collections;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.druid.pool.DruidDataSource;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@EnableConfigurationProperties(Configs.class)
@EnableScheduling
public class RootContext implements ApplicationContextAware
{
    private static ApplicationContext context;
    
    @Autowired
    private Configs configs;
    
    @Autowired
    private Environment environment;
    
    private static Logger log = LoggerFactory.getLogger(RootContext.class);
    
    @Bean
    public DataSource dataSource() throws Exception
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
        dataSource.setRemoveAbandonedTimeout(1800);//1800秒=30分钟
        
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
    
    @Bean
    public MongoClient getMongo() throws Exception
    {
        // 单实例
        ServerAddress address = new ServerAddress(configs.getMongodbHost(), configs.getMongodbPort());
        
        String username = configs.getMongodbUsername();
        
        if (StringUtils.isEmpty(username))
        {
            return new MongoClient(address);
        }
        else
        {
            String password = configs.getMongodbPassword();
            String database = configs.getMongodbDatabase();
            MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
            return new MongoClient(address, Collections.singletonList(credential));
        }
    }
    
    @Bean
    public MongoDbFactory getMongoDbFactory() throws Exception
    {
        MongoDbFactory factory = new SimpleMongoDbFactory(getMongo(), configs.getMongodbDatabase());
        return factory;
    }
    
    @Bean
    public MongoTemplate getMongoTemplate() throws Exception
    {
        MongoTemplate template = new MongoTemplate(getMongoDbFactory());
        return template;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        RootContext.context = context;
    }
    
    public static <T> T getBean(Class<T> requiredType)
    {
        return context.getBean(requiredType);
    }
}
