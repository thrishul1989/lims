package com.todaysoft.lims.system.service.adapter.request;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.model.vo.ProductTask;
import com.todaysoft.lims.system.model.vo.TestingType;

/**
 * @author admin
 *
 */
public class ProductPagingRequest
{
    
    private String id;
    
    private String code; //产品编号
    
    private String name; //产品名称
    
    private Double price; //产品定价￥
    
    private String testInstitution;//检测机构：0 检验所 1麦诺基公司
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限
    
    private String tType;
    
    private String productDutyName;
    
    private String testingSubtype;//二级分类
    
    private String principalName;//技术负责人
    
    private String productProbe;
    
    public String getProductProbe()
    {
        return productProbe;
    }
    
    public void setProductProbe(String productProbe)
    {
        this.productProbe = productProbe;
    }
    
    public String getTestingSubtype()
    {
        return testingSubtype;
    }
    
    public void setTestingSubtype(String testingSubtype)
    {
        this.testingSubtype = testingSubtype;
    }
    
    public String getPrincipalName()
    {
        return principalName;
    }
    
    public void setPrincipalName(String principalName)
    {
        this.principalName = principalName;
    }
    
    public String getProductDutyName()
    {
        return productDutyName;
    }
    
    public void setProductDutyName(String productDutyName)
    {
        this.productDutyName = productDutyName;
    }
    
    public String gettType()
    {
        return tType;
    }
    
    public void settType(String tType)
    {
        this.tType = tType;
    }
    
    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    public String getTestInstitution()
    {
        return testInstitution;
    }
    
    public void setTestInstitution(String testInstitution)
    {
        this.testInstitution = testInstitution;
    }
    
    private Integer enabled;
    
    public Integer getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    
    private Integer detectionClassfy; //检测分类
    
    private String testMethod; //检测方法
    
    private Integer cycle; //产品周期 天
    
    private String testSample; //实验样本
    
    private String enSample; //推荐样本
    
    private String description;//产品描述
    
    private List<ProductTask> productTaskList = new ArrayList<ProductTask>();
    
    private int pageNo;
    
    private int pageSize;
    
    private String experimentSamples;//实验样本
    
    private TestingType testingType;
    
    public TestingType getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(TestingType testingType)
    {
        this.testingType = testingType;
    }
    
    public String getExperimentSamples()
    {
        return experimentSamples;
    }
    
    public void setExperimentSamples(String experimentSamples)
    {
        this.experimentSamples = experimentSamples;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
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
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
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
