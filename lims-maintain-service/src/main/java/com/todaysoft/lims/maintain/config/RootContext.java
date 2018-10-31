package com.todaysoft.lims.maintain.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
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

@Configuration
@EnableConfigurationProperties(Configs.class)
@EnableTransactionManagement
public class RootContext implements ApplicationContextAware
{
    private static Logger log = LoggerFactory.getLogger(RootContext.class);
    
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
        dataSource.setRemoveAbandonedTimeout(6000);
        
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
        properties.put("hibernate.show_sql", configs.isHibernateShowsql());
        properties.put("hibernate.format_sql", configs.isHibernateFormatSql());
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
    
    @Bean
    public Client client()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to initialize the elastic search client.");
        }
        
        String name = configs.getElasticsearchClusterName();
        String nodes = configs.getElasticsearchClusterNodes();
        
        if (log.isDebugEnabled())
        {
            log.debug("Client settings config as cluster name {}, cluster nodes {}", name, nodes);
        }
        
        if (StringUtils.isEmpty(nodes))
        {
            throw new IllegalStateException();
        }
        
        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", name).build();
        TransportClient client = new TransportClient(settings);
        
        String[] array;
        String[] transports = nodes.split(",");
        
        for (String transport : transports)
        {
            array = transport.split(":");
            client.addTransportAddress(new InetSocketTransportAddress(array[0], Integer.valueOf(array[1])));
            
            if (log.isDebugEnabled())
            {
                log.debug("Client add transport address {}:{}", array[0], array[1]);
            }
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Initialize the elastic search client successful.");
        }
        
        return client;
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
}
