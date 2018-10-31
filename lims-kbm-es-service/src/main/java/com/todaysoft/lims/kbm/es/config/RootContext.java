package com.todaysoft.lims.kbm.es.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Configs.class)
public class RootContext implements ApplicationContextAware
{
    private static Logger log = LoggerFactory.getLogger(RootContext.class);
    
    private static ApplicationContext context;
    
    @Autowired
    private Configs configs;
    
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
