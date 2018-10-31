package com.todaysoft.lims.sample.config;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;



@Configuration
public class ServletContext
{
    
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new DruidStatViewServlet(), "/druid/*");// ServletName默认值为首字母小写，即myServlet
    }
    
    
    @Bean(name = "openSessionInViewFilter")
    public OpenSessionInViewFilter getOpenSessionInViewFilter()
    {
        OpenSessionInViewFilter filter = new OpenSessionInViewFilter();
        return filter;
    }
}
