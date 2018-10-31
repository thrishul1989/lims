package com.todaysoft.lims.ngs.utils;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpRequestAPI
{
    
    public static Logger log = LoggerFactory.getLogger(HttpRequestAPI.class);
    
    public static String HOST = "http://60.205.205.23:8080/";
    
    public static String NGS_SEQUECING = "mygeno/dmdmstate";// 上机测序查询
    
    public static String NGS_SEQUECING_TASK = "mygeno/dmdmtask";// 上机测序
    
    @Value("${mygeno.base.url}")
    public void set(String HOST)
    {
        this.HOST = HOST;
        System.out.println("HOST:" + HOST);
    }
    
    public static <T> T httpPost(String url, String request, Class<T> responseType)
    {
        
        HttpRequest httpRequest = new HttpRequest();
        HttpResponse response = httpRequest.post(HOST + url).contentType("application/json; charset=utf-8").charset("utf-8").bodyText(request).send();
        String body = response.body();
        
        T result = JsonUtils.fromJson(body, responseType);
        if (null == result)
        {
            
            throw new IllegalStateException();
        }
        return result;
        
    }
    
    public static <T> T httpGetByTaskId(String url, String taskId, Class<T> responseType)
    {
        log.info(" search task result ,taskId is：" + taskId);
        url = HOST + url + "?taskId=" + taskId;
        HttpRequest httpRequest = HttpRequest.get(url);
        HttpResponse response = httpRequest.send();
        String body = response.body();
        System.out.println(body);
        T model = null;
        if (StringUtils.isNotEmpty(body))
        {
            model = JsonUtils.fromJson(body, responseType);
        }
        else
        {
            log.warn("search result model is null ");
        }
        return model;
    }
    
}
