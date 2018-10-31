package com.todaysoft.lims.sample.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.todaysoft.lims.sample.ons.OrderStatusConsumer;

@Configuration
@EnableConfigurationProperties(Configs.class)
@EnableTransactionManagement
public class RootContext implements ApplicationContextAware
{
    private static ApplicationContext context;
    
    @Autowired
    private Configs configs;
    
    @Autowired
    private LocalStatLogger localStatLogger;
    
    private static Logger log = LoggerFactory.getLogger(RootContext.class);
    
    @Bean
    public DataSource dataSource() throws Exception
    {
        DruidDataSource dataSource = new DruidDataSource();
        
        dataSource.setDriverClassName(configs.getJdbcDriver());
        dataSource.setUrl(configs.getJdbcUrl());
        dataSource.setUsername(configs.getJdbcUsername());
        dataSource.setPassword(configs.getJdbcPassword());
        dataSource.setMaxActive(configs.getMaxActive());
        dataSource.setInitialSize(configs.getInitialSize());
        
        dataSource.setMaxWait(600000);
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
        //输出到日志
        dataSource.setTimeBetweenLogStatsMillis(60000);
        dataSource.setStatLogger(localStatLogger);
        Properties properties = new Properties();
        properties.setProperty("config.decrypt", "true");
        properties.setProperty("config.decrypt.key", configs.getJdbcPublicKey());
        dataSource.setConnectProperties(properties);
        
        return dataSource;
    }
    
    private Properties hibernateProperties()
    {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", configs.getHibernateDialect());
        properties.put("hibernate.show_sql", configs.getHibernateShowsql());
        properties.put("hibernate.format_sql", configs.getHibernateFormatSql());
        return properties;
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() throws Exception
    {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] {"com.todaysoft.lims"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory s)
    {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }
    
    @Bean(name = "localStatLogger")
    public LocalStatLogger druidLog()
    {
        LocalStatLogger log = new LocalStatLogger();
        return log;
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
        return producer;
    }
    
    @Bean(name = "orderStausConsumer")
    public Consumer orderStausConsumer()
    {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, configs.getAliyunONSAccessKey());
        properties.put(PropertyKeyConst.SecretKey, configs.getAliyunONSSecretKey());
        properties.put(PropertyKeyConst.ConsumerId, configs.getAliyunONSOrderStatusConsumer());
        Consumer consumer = ONSFactory.createConsumer(properties);
        String expression = "ORDER_STATUS_FINISH||ORDER_STATUS_CANCEL";
        consumer.subscribe(configs.getAliyunONSTopic(), expression, new OrderStatusConsumer());
        consumer.start();
        if (log.isDebugEnabled())
        {
            log.debug("Start aliyun ons consumer success, subscribe topic {}, expression {}, consumer id {}",
                configs.getAliyunONSTopic(),
                expression,
                configs.getAliyunONSOrderStatusConsumer());
        }
        return consumer;
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
