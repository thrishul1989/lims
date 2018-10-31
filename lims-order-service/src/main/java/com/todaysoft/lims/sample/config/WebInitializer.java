package com.todaysoft.lims.sample.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

public class WebInitializer implements WebApplicationInitializer
{
    private static final String CHARACTER_ENCODING_FILTER_BEAN = "characterEncodingFilter";

    private static final String CHARACTER_ENCODING_FILTER_NAME = "CharacterEncodingFilter";

    private static final String[] CHARACTER_ENCODING_FILTER_PATTERNS = new String[] {"/*"};

    private static final String OPENSESSIONINVIEW_FILTER_BEAN = "openSessionInViewFilter";

    private static final String OPENSESSIONINVIEW_FILTER_NAME = "OpenSessionInViewFilter";

    private static final String[] OPENSESSIONINVIEW_FILTER_PATTERNS = new String[] {"/*"};

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        registerCharacterEncodingFilter(servletContext);
        registerOpenSessionInViewFilter(servletContext);
    }

    private void registerCharacterEncodingFilter(ServletContext servletContext) throws ServletException
    {
        DelegatingFilterProxy filter = new DelegatingFilterProxy(CHARACTER_ENCODING_FILTER_BEAN);
        filter.setTargetFilterLifecycle(true);
        Dynamic registration = servletContext.addFilter(CHARACTER_ENCODING_FILTER_NAME, filter);
        registration.setAsyncSupported(true);
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        registration.addMappingForUrlPatterns(dispatcherTypes, false, CHARACTER_ENCODING_FILTER_PATTERNS);
    }

    private void registerOpenSessionInViewFilter(ServletContext servletContext) throws ServletException
    {
        DelegatingFilterProxy filter = new DelegatingFilterProxy(OPENSESSIONINVIEW_FILTER_BEAN);
        filter.setTargetFilterLifecycle(true);
        Dynamic registration = servletContext.addFilter(OPENSESSIONINVIEW_FILTER_NAME, filter);
        registration.setAsyncSupported(true);
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        registration.addMappingForUrlPatterns(dispatcherTypes, false, OPENSESSIONINVIEW_FILTER_PATTERNS);
    }
}
