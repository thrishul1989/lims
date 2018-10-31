package com.todaysoft.lims.biology.adapter.impl;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.todaysoft.lims.biology.model.api.BiologyDivisionStartReqModel;
import com.todaysoft.lims.biology.model.api.divisioncallback.BiologyDivisionCallBackModel;
import com.todaysoft.lims.biology.model.api.divisioncallback.BiologyDivisionTaskModel;
import com.todaysoft.lims.biology.util.JsonUtils;
import com.todaysoft.lims.utils.StringUtils;

@Component
public class HttpRequestAPI
{
    
    private static Logger log = LoggerFactory.getLogger(HttpRequestAPI.class);
    
    private static String HOST = "http://60.205.205.23:8080/";
    
    public static String START_DIVISION_URL = "mygeno/undertask";//启动返回taskId的接口
    
    public static String GET_RESULT_DIVISION_URL = "mygeno/understate";//下机数据拆分查询接口

    public static String START_ANNOTATION_URL = "mygeno/limsAnalysisCreate";//注释接口(新)

    public static String GET_RESULT_ANNOTATION_URL = "mygeno/limsAnalysisQuery";//注释接口查询(新)

    public static String START_BIOLOGY_URL = "mygeno/limsAnalysisTest";//注释接口（旧）

    public static String GET_RESULT_BIOLOGY_URL = "mygeno/limsAnalysisTestQuery";//注释接口查询（旧）
    
    public static String START_FAMILY_ANNOTATION_URL = "mygeno/family";//家系分析
    
    public static String GET_FAMILY_ANNOTATION_URL = "mygeno/familyQuery";//家系分析
    
    @Value("${mygeno.base.url}")
    public void set(String HOST)
    {
        this.HOST = HOST;
        System.out.println("HOST:" + HOST);
    }
    
    // 发送数据
    public static String startDivision(BiologyDivisionStartReqModel model)
    {
        String reqText = JSON.toJSONString(model);
        log.info(" start division request text is：" + reqText);
        HttpRequest request = new HttpRequest();
        HttpResponse response =
            request.post(HOST + START_DIVISION_URL).contentType("application/json; charset=utf-8").charset("utf-8").bodyText(reqText).send();
        String body = response.body();
        log.info("start division response is：" + body);
        BiologyDivisionTaskModel result = JSON.parseObject(body, BiologyDivisionTaskModel.class);
        if (null == result)
        {
            log.warn("start division API is not access!!!!");
            throw new IllegalStateException();
        }
        return result.getData().getTaskId();
    }
    
    // 定时查询
    public static BiologyDivisionCallBackModel getResultByTaskId(String taskId)
    {
        log.info(" search task result ,taskId is：" + taskId);
        String url = HOST + GET_RESULT_DIVISION_URL + "?taskId=" + taskId;
        HttpRequest httpRequest = HttpRequest.get(url);
        HttpResponse response = httpRequest.send();
        String body = response.body();
        System.out.println(body);
        BiologyDivisionCallBackModel model = null;
        if (StringUtils.isNotEmpty(body))
        {
            model = JSON.parseObject(body, BiologyDivisionCallBackModel.class);
        }
        else
        {
            System.err.println("search result model is null ");
        }
        return model;
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

    public static <T> T oldBiologyHttpPost(String url, String request, Class<T> responseType)
    {

        HttpRequest httpRequest = new HttpRequest();
        HttpResponse response = httpRequest.post("http://60.205.205.23:9090/" + url).contentType("application/json; charset=utf-8").charset("utf-8").bodyText(request).send();
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
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(body))
        {
            model = JsonUtils.fromJson(body, responseType);
        }
        else
        {
            log.warn("search result model is null ");
        }
        return model;
    }
    public static <T> T oldBiologyHttpGetByTaskId(String url, String taskId, Class<T> responseType)
    {
        log.info(" search task result ,taskId is：" + taskId);
        if(!"mygeno/familyQuery".equals(url))
        {
            url = "http://60.205.205.23:9090/" + url + "?taskId=" + taskId;
        }
        HttpRequest httpRequest = HttpRequest.get(url);
        HttpResponse response = httpRequest.send();
        String body = response.body();
        System.out.println(body);
        T model = null;
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(body))
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
