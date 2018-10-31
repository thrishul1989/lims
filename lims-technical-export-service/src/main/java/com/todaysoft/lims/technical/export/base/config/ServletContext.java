package com.todaysoft.lims.technical.export.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

@Configuration
public class ServletContext
{
    @Bean(name = "openSessionInViewFilter")
    public OpenSessionInViewFilter getOpenSessionInViewFilter()
    {
        OpenSessionInViewFilter filter = new OpenSessionInViewFilter();
        return filter;
    }
}
