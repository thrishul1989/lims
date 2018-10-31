package com.todaysoft.lims.system.service.impl;

import static com.todaysoft.lims.system.service.impl.GatewayService.getServiceUrl;

import java.util.Collections;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.system.model.vo.Pagination;

public abstract class RestService
{
    @Autowired
    protected RestTemplate template;
    
    @PostConstruct
    protected void setRestTemplateErrorHandler()
    {
        template.setInterceptors(Collections.singletonList((ClientHttpRequestInterceptor)new DefaultRequestInterceptor()));
        template.setErrorHandler(new RestfulResponseErrorHandler(template.getMessageConverters()));
        
    }
    
    /**
     * POST封装
     * @param url 地址
     * @param obj 实体
     * @param clz 返回实体class
     * @param <T>
     * @return
     */
    public <T> T post(String url, Object obj, Class<T> clz)
    {
        
        return template.postForObject(getServiceUrl(url), obj, clz);
    }
    
    /**
     * Get实体
     * @param url 地址
     * @param clz 返回class
     * @param var 可变参数
     * @param <T>
     * @return
     */
    public <T> T get(String url, Class<T> clz, Object... var)
    {
        return template.getForObject(getServiceUrl(url), clz, var);
    }
    
    public <T> T getById(String url, Class<T> clz, Object id)
    {
        return get(url, clz, Collections.singletonMap("id", id));
    }
    
    /**
     * Get实体
     * @param url 地址
     * @param clz 返回class
     * @param map map参数
     * @param <T>
     * @return
     */
    public <T> T get(String url, Class<T> clz, Map<String, Object> map)
    {
        
        return template.getForObject(getServiceUrl(url), clz, map);
    }
    
    public <T> Pagination<T> page(String url, Object obj)
    {
        
        return template.exchange(getServiceUrl(url), HttpMethod.POST, new HttpEntity(obj), new ParameterizedTypeReference<Pagination<T>>()
        {
        }).getBody();
    }
    
    public <T> T exchange(String url, Object obj, ParameterizedTypeReference<T> ref)
    {
        return template.exchange(getServiceUrl(url), HttpMethod.POST, new HttpEntity(obj), ref).getBody();
    }
    
    public void delete(String url, Integer id)
    {
        template.delete(getServiceUrl(url), Collections.singletonMap("id", id));
    }
}
