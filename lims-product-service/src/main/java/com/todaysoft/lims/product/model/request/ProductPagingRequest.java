package com.todaysoft.lims.product.model.request;

import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.product.entity.TestingType;
import com.todaysoft.lims.product.entity.User;

/**
 * Created by HSHY-032 on 2016/6/8.
 */
public class ProductPagingRequest
{
    
    private String id;
    
    private String code; //产品编号
    
    private String name; //产品名称
    
    private Double price; //产品定价￥
    
    private Integer ifMade;//是否定制 0 否 1是
    
    private String testInstitution;//检测机构：0 检验所 1麦诺基公司
    
    private String updateTime;
    
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
    
    private Integer enabled;
    
    public Integer getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    
    public Integer getIfMade()
    {
        return ifMade;
    }
    
    public void setIfMade(Integer ifMade)
    {
        this.ifMade = ifMade;
    }
    
    public String getTestInstitution()
    {
        return testInstitution;
    }
    
    public void setTestInstitution(String testInstitution)
    {
        this.testInstitution = testInstitution;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    private TestingType testingType;//检测类型
    
    public TestingType getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(TestingType testingType)
    {
        this.testingType = testingType;
    }
    
    public User getCreater()
    {
        return creater;
    }
    
    public void setCreater(User creater)
    {
        this.creater = creater;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public String getDiseaseCategory()
    {
        return diseaseCategory;
    }
    
    public void setDiseaseCategory(String diseaseCategory)
    {
        this.diseaseCategory = diseaseCategory;
    }
    
    public Integer getTestingGenes()
    {
        return testingGenes;
    }
    
    public void setTestingGenes(Integer testingGenes)
    {
        this.testingGenes = testingGenes;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    private Integer detectionClassfy; //检测分类
    
    private String testMethod; //检测方法
    
    private Integer cycle; //产品周期 天
    
    private String testSample; //实验样本
    
    private String description;//产品描述
    
    private String tasks; //实验配置，选择的任务
    
    private User creater;//创建人
    
    private String createTime;//创建时间
    
    private String state;//状态
    
    private String diseaseCategory;//疾病类型
    
    private Integer testingGenes;//基因数
    
    private String remark;//产品备注
    
    private int pageNo;
    
    private int pageSize;
    
    private String experimentSamples;//实验样本
    
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
    
    public Integer getCycle()
    {
        return cycle;
    }
    
    public void setCycle(Integer cycle)
    {
        this.cycle = cycle;
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
    
    public String getTestSample()
    {
        return testSample;
    }
    
    public void setTestSample(String testSample)
    {
        this.testSample = testSample;
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
}
