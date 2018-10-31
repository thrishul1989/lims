package com.todaysoft.lims.gateway.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaysoft.lims.exception.ServiceException;
import com.todaysoft.lims.gateway.service.IDistributeService;

@Service
public class DistributeService implements IDistributeService, ApplicationContextAware
{
    private Map<String, String> matches = new HashMap<String, String>();
    
    private Map<String, String> patterns = new HashMap<String, String>();
    
    @Autowired
    protected RestTemplate template;
    
    @PostConstruct
    protected void setRestTemplateErrorHandler()
    {
        template.setErrorHandler(new RestfulResponseErrorHandler(template.getMessageConverters()));
    }
    
    @Override
    public String distributeGetRequest(HttpServletRequest request) throws UnsupportedDistributeUrlException
    {
        String uri = request.getRequestURI();
        
        String service = getDistributeServiceName(uri);
        
        if (null == service)
        {
            throw new UnsupportedDistributeUrlException();
        }
        
        String query = null;
        try
        {
            query = URLDecoder.decode(request.getQueryString() == null ? "" : request.getQueryString(), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        query = StringUtils.isEmpty(query) ? "" : ("?" + query);
        String distributeUrl = String.format("http://%1$s%2$s%3$s", service, uri, query);
        DistributeRequestInterceptor interceptor = new DistributeRequestInterceptor(request);
        template.setInterceptors(Collections.singletonList(interceptor));
        String response = template.getForObject(distributeUrl, String.class);
        return response;
    }
    
    @Override
    public String distributePostRequest(HttpServletRequest request, String body) throws UnsupportedDistributeUrlException
    {
        String uri = request.getRequestURI();
        
        String service = getDistributeServiceName(uri);
        
        if (null == service)
        {
            throw new UnsupportedDistributeUrlException();
        }
        
        String query = request.getQueryString();
        query = StringUtils.isEmpty(query) ? "" : ("?" + query);
        String distributeUrl = String.format("http://%1$s%2$s%3$s", service, uri, query);
        DistributeRequestInterceptor interceptor = new DistributeRequestInterceptor(request);
        template.setInterceptors(Collections.singletonList(interceptor));
        ObjectMapper mapper = new ObjectMapper();
        
        try
        {
            Map<?, ?> data = StringUtils.isEmpty(body) ? null : mapper.readValue(body, Map.class);
            String response = template.postForObject(distributeUrl, StringUtils.isEmpty(body) ? null : data, String.class);
            return response;
        }
        catch (IOException e)
        {
            throw new ServiceException(ErrorCode.UNDEFINED_ERROR);
        }
        catch (ServiceException e)
        {
            throw new ServiceException(e.getErrorCode());
        }
        
    }
    
    @Override
    public void distributeDeleteRequest(HttpServletRequest request) throws UnsupportedDistributeUrlException
    {
        String uri = request.getRequestURI();
        
        String service = getDistributeServiceName(uri);
        
        if (null == service)
        {
            throw new UnsupportedDistributeUrlException();
        }
        
        String query = request.getQueryString();
        query = StringUtils.isEmpty(query) ? "" : ("?" + query);
        String distributeUrl = String.format("http://%1$s%2$s%3$s", service, uri, query);
        DistributeRequestInterceptor interceptor = new DistributeRequestInterceptor(request);
        template.setInterceptors(Collections.singletonList(interceptor));
        template.delete(distributeUrl);
    }
    
    private String getDistributeServiceName(String uri)
    {
        String name = matches.get(uri);
        
        if (!StringUtils.isEmpty(name))
        {
            return name;
        }
        
        AntPathMatcher matcher = new AntPathMatcher();
        
        for (String pattern : patterns.keySet())
        {
            if (matcher.match(pattern, uri))
            {
                name = patterns.get(pattern);
                matches.put(uri, name);
                return name;
            }
        }
        
        return null;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        Map<String, DistributeConfigProvider> providers = applicationContext.getBeansOfType(DistributeConfigProvider.class);
        
        if (CollectionUtils.isEmpty(providers))
        {
            return;
        }
        
        String service;
        Set<String> ptns;
        Set<DistributeConfig> configs;
        
        for (DistributeConfigProvider provider : providers.values())
        {
            configs = provider.getDistributeConfigs();
            
            if (CollectionUtils.isEmpty(configs))
            {
                continue;
            }
            
            for (DistributeConfig config : configs)
            {
                ptns = config.getPatterns();
                
                if (CollectionUtils.isEmpty(ptns))
                {
                    continue;
                }
                
                service = config.getServiceName();
                
                for (String pattern : ptns)
                {
                    patterns.put(pattern, service);
                }
            }
        }
    }
}
