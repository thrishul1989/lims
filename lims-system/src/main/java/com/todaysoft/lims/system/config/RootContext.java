package com.todaysoft.lims.system.config;

import java.util.EventListener;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableAspectJAutoProxy
public class RootContext   implements ApplicationContextAware
{
    private static ApplicationContext context;
    
    /*   static String host = ConfigManage.getkey("app.redis.host");
       
       static Integer port = Integer.parseInt(ConfigManage.getkey("app.redis.port"));
       
       static String password = ConfigManage.getkey("app.redis.password");
       
       @Bean(name = "redisTemplate")
       public StringRedisTemplate getStringRedisTemplate()
       {
           StringRedisTemplate redisTemplate = new StringRedisTemplate();
           JedisConnectionFactory factory = new JedisConnectionFactory();
           factory.setHostName(host);
           factory.setPort(port);
           factory.setPassword(password);
           
           redisTemplate.setConnectionFactory(factory);
           // redisTemplate.afterPropertiesSet();
           return redisTemplate;
           
       }*/
    
    @Bean(name = "characterEncodingFilter")
    public CharacterEncodingFilter getCharacterEncodingFilter()
    {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("utf-8");
        return filter;
    }
    
  
    
    @Bean(name = "messageSource")
    public MessageSource getMessageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages/errors");
        
        return messageSource;
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
