package com.todaysoft.lims.technical.utils;

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
    
    public static String HOST = "http://60.205.205.23:8080/";//正式环境
    
    public static Logger log = LoggerFactory.getLogger(HttpRequestAPI.class);
    
    public String NGS_SEQUECING = "mygeno/dmdmstate";// 上机测序查询
    
    public static String NGS_SEQUECING_TASK = "mygeno/dmdmtask";// 上机测序
    
    public static String PHENOTYPE_POINT = "mygeno/hpotask";// HPO表型打分

    public static String PHENOTYPE_POINT_STATE = "mygeno/hpostate";// hpo表型打分查询
    
    public static String DISEASE_LEVEL = "mygeno/hgmd";// 致病分级
    
    public static String RE_ANALYSIS_CNV_SEARCH_URL = "mygeno/cnvanalysestate";// CNV重新分析结果查询
    
    public static String DRAW_PICTURE_URL =  "mygeno/capcnv";// 画图接口
    
    public static String RE_ANALYSIS_CNV_TASKID_URL = "mygeno/cnvanalysetask";// CNV重新分析返回taskId
    
    public static String CNV_HOST = "60.205.205.23";//正式环境
    
    @Value("${mygeno.base.url}")
    public void set(String HOST)
    {
        this.HOST = HOST;
        System.out.println("HOST:" + HOST);
    }
    
    
    @Value("${mygeno.base.cnvurl}")
    public void setCnvHost(String HOST)
    {
        this.CNV_HOST = HOST;
        System.out.println("CNV_HOST:" + HOST);
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
