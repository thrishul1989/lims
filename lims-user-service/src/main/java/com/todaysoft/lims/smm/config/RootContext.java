package com.todaysoft.lims.smm.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableConfigurationProperties(Configs.class)
@EnableTransactionManagement
public class RootContext implements ApplicationContextAware
{
    private static ApplicationContext context;
    
    @Autowired
    private Configs configs;
    
    @Bean
    public DataSource dataSource()
        throws Exception
    {
        DruidDataSource dataSource = new DruidDataSource();
        
        dataSource.setDriverClassName(configs.getJdbcDriver());
        dataSource.setUrl(configs.getJdbcUrl());
        dataSource.setUsername(configs.getJdbcUsername());
        dataSource.setPassword(configs.getJdbcPassword());
        dataSource.setMaxActive(configs.getMaxActive());
        dataSource.setInitialSize(configs.getInitialSize());
        
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
    public LocalSessionFactoryBean sessionFactory()
        throws Exception
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
    
    // redis配置
    @Bean
    public JedisConnectionFactory redisConnectionFactory()
    {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(configs.getRedisHost());
        factory.setPort(configs.getRedisPort());
        factory.setPassword(configs.getRedisPassword());
        factory.setTimeout(configs.getRedisTimeout());
        return factory;
    }
    
    @Bean(name = "redisTemplate")
    public StringRedisTemplate redisTemplate(RedisConnectionFactory factory)
    {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        return template;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext context)
        throws BeansException
    {
        RootContext.context = context;
    }
    
    public static <T> T getBean(Class<T> requiredType)
    {
        return context.getBean(requiredType);
    }
    
    @Bean(name = "characterEncodingFilter")
    public CharacterEncodingFilter getCharacterEncodingFilter()
    {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("utf-8");
        return filter;
    }
}
