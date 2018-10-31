package com.todaysoft.lims.product.model.request;

import java.util.Set;

/**
 * Created by HSHY-032 on 2016/6/8.
 */
public class ProductListRequest
{
    
    private Integer id;
    
    private String code; //产品编号
    
    private String name; //产品名称
    
    private Double price; //产品定价￥
    
    private Integer detectionClassfy; //检测分类
    
    private String testMethod; //检测方法
    
    private Integer cycle; //产品周期 天
    
    private String testSample; //实验样本
    
    private String enSample; //推荐样本
    
    private String description;//产品描述
    
    private String tasks; //实验配置，选择的任务
    
    private String experimentSamples;//实验样本
    
    private boolean likeSearch; //支持 精确匹配   默认牧模糊
    
    private Set<String> productIds;
    
    public Set<String> getProductIds()
    {
        return productIds;
    }
    
    public void setProductIds(Set<String> productIds)
    {
        this.productIds = productIds;
    }
    
    public boolean isLikeSearch()
    {
        return likeSearch;
    }
    
    public void setLikeSearch(boolean likeSearch)
    {
        this.likeSearch = likeSearch;
    }
    
    public String getExperimentSamples()
    {
        return experimentSamples;
    }
    
    public void setExperimentSamples(String experimentSamples)
    {
        this.experimentSamples = experimentSamples;
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Double getPrice()
    {
        return price;
    }
    
    public void setPrice(Double price)
    {
        this.price = price;
    }
    
    public Integer getDetectionClassfy()
    {
        return detectionClassfy;
    }
    
    public void setDetectionClassfy(Integer detectionClassfy)
    {
        this.detectionClassfy = detectionClassfy;
    }
    
    public String getTestMethod()
    {
        return testMethod;
    }
    
    public void setTestMethod(String testMethod)
    {
        this.testMethod = testMethod;
    }
    
    public Integer getCycle()
    {
        return cycle;
    }
    
    public void setCycle(Integer cycle)
    {
        this.cycle = cycle;
    }
    
    public String getTestSample()
    {
        return testSample;
    }
    
    public void setTestSample(String testSample)
    {
        this.testSample = testSample;
    }
    
    public String getEnSample()
    {
        return enSample;
    }
    
    public void setEnSample(String enSample)
    {
        this.enSample = enSample;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getTasks()
    {
        return tasks;
    }
    
    public void setTasks(String tasks)
    {
        this.tasks = tasks;
    }
}
