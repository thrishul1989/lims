package com.todaysoft.lims.system.config;

import java.util.EventListener;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.todaysoft.lims.system.form.SystemMonitorInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.todaysoft.lims.system")
@EnableAspectJAutoProxy
public class DispatcherServletContext extends WebMvcConfigurerAdapter
{
    @Autowired
    private HandlerExceptionResolver exceptionResolver;
    
    @Resource(name = "formRepeatSubmitInterceptor")
    private HandlerInterceptorAdapter formRepeatSubmitInterceptor;
    
    @Autowired
    private SystemMonitorInterceptor systemMonitorInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(formRepeatSubmitInterceptor);
        registry.addInterceptor(systemMonitorInterceptor);
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addRedirectViewController("/login_success.do", "/index.do");
    }
    
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers)
    {
        exceptionResolvers.add(exceptionResolver);
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/templates/static/");
    }
    
    @Override
    public void addFormatters(FormatterRegistry registry)
    {
        registry.addFormatter(new DateFormatter());
    }
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver(ServletContext servletContext)
    {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(servletContext);
        resolver.setMaxUploadSize(104857600);
        resolver.setMaxInMemorySize(104857600);
        return resolver;
    }
    
    @Bean
    public ServletListenerRegistrationBean<EventListener> getDemoListener()
    {
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new HttpSessionEventPublisher());
        
        return registrationBean;
    }
    
}
