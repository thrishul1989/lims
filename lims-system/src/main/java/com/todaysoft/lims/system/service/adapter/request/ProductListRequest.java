package com.todaysoft.lims.system.service.adapter.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.todaysoft.lims.system.model.vo.ProductTask;

/**
 * @author admin
 *
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
    
    private List<ProductTask> productTaskList = new ArrayList<ProductTask>();
    
    private String experimentSamples;//实验样本
    
    private Set<String> productIds;
    
    public Set<String> getProductIds()
    {
        return productIds;
    }
    
    public void setProductIds(Set<String> productIds)
    {
        this.productIds = productIds;
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
    
    public List<ProductTask> getProductTaskList()
    {
        return productTaskList;
    }
    
    public void setProductTaskList(List<ProductTask> productTaskList)
    {
        this.productTaskList = productTaskList;
    }
}
