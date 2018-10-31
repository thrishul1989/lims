package com.todaysoft.lims.system.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.todaysoft.lims.system.modules.report.model.ReportTemplate;
import com.todaysoft.lims.system.service.adapter.request.ProductAmountRule;
import com.todaysoft.lims.system.service.adapter.request.ProductProbeRequest;

public class Product
{
    private String id;
    
    private String code; // 产品编号
    
    private String name; // 产品名称
    
    private String price; // 产品定价￥
    
    private String realPrice;//真是价格分转元
    
    private User productDuty;//产品负责人
    
    private Integer testingDuration; // 产品周期 天
    
    private String description;// 产品描述
    
    private String probeArray;// 探针ids
    
    private List<Probe> probeList; // 探针
    
    private TestingType testingType;// 检测类型以及分类
    
    private TestingType testingSubtype;//二级分类
    
    private String creater;// 创建人
    
    private String createTime;// 创建时间
    
    private String enabled;// 状态
    
    private String delFlag;//
    
    private Integer testingGenes;// 基因数
    
    private String remark;// 产品备注
    
    private Integer ifMade;//是否定制 0 否 1是
    
    private String testInstitution;//检测机构：0 检验所 1麦诺基公司
    
    private String updateTime;
    
    private ReportTemplate reportTemplate;
    
    private List<ProductSample> productSampleList;
    
    private List<ProductTestingMethod> productTestingMethodList;
    
    private List<ProductPrincipal> productPrincipalList;
    
    private List<ProductProbeRequest> productProbeList;// 请求字段
    
    private List<ProductDisease> productDiseaseList;
    
    private List<ProductGene> productGeneList;
    
    private String testingStartSample;//实验样本
    
    private User createUSER;//冗余字段
    
    private String productLeader;//产品负责人
    
    private String samplePurpose;
    
    private List<ProductAmountRule> productAmountRuleList;
    
    public List<ProductAmountRule> getProductAmountRuleList()
    {
        return productAmountRuleList;
    }
    
    public void setProductAmountRuleList(List<ProductAmountRule> productAmountRuleList)
    {
        this.productAmountRuleList = productAmountRuleList;
    }
    
    public String getSamplePurpose()
    {
        return samplePurpose;
    }
    
    public void setSamplePurpose(String samplePurpose)
    {
        this.samplePurpose = samplePurpose;
    }
    
    public User getProductDuty()
    {
        return productDuty;
    }
    
    public void setProductDuty(User productDuty)
    {
        this.productDuty = productDuty;
    }
    
    public String getRealPrice()
    {
        if (null != price)
        {
            return BigDecimal.valueOf(Double.valueOf(price)).divide(new BigDecimal(100)).toString();
        }
        else
        {
            return "0";
        }
        
    }
    
    public void setRealPrice(String realPrice)
    {
        this.realPrice = realPrice;
    }
    
    public TestingType getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(TestingType testingType)
    {
        this.testingType = testingType;
    }
    
    public TestingType getTestingSubtype()
    {
        return testingSubtype;
    }
    
    public void setTestingSubtype(TestingType testingSubtype)
    {
        this.testingSubtype = testingSubtype;
    }
    
    public String getCreater()
    {
        return creater;
    }
    
    public void setCreater(String creater)
    {
        this.creater = creater;
    }
    
    public String getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
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
    
    public User getCreateUSER()
    {
        return createUSER;
    }
    
    public String getTestingStartSample()
    {
        return testingStartSample;
    }
    
    public void setTestingStartSample(String testingStartSample)
    {
        this.testingStartSample = testingStartSample;
    }
    
    public void setCreateUSER(User createUSER)
    {
        this.createUSER = createUSER;
    }
    
    public List<ProductGene> getProductGeneList()
    {
        return productGeneList;
    }
    
    public void setProductGeneList(List<ProductGene> productGeneList)
    {
        this.productGeneList = productGeneList;
    }
    
    public List<ProductDisease> getProductDiseaseList()
    {
        return productDiseaseList;
    }
    
    public void setProductDiseaseList(List<ProductDisease> productDiseaseList)
    {
        this.productDiseaseList = productDiseaseList;
    }
    
    public List<ProductProbeRequest> getProductProbeList()
    {
        return productProbeList;
    }
    
    public void setProductProbeList(List<ProductProbeRequest> productProbeList)
    {
        this.productProbeList = productProbeList;
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
    
    public List<ProductPrincipal> getProductPrincipalList()
    {
        return productPrincipalList;
    }
    
    public void setProductPrincipalList(List<ProductPrincipal> productPrincipalList)
    {
        this.productPrincipalList = productPrincipalList;
    }
    
    public List<ProductTestingMethod> getProductTestingMethodList()
    {
        return productTestingMethodList;
    }
    
    public void setProductTestingMethodList(List<ProductTestingMethod> productTestingMethodList)
    {
        this.productTestingMethodList = productTestingMethodList;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public List<ProductSample> getProductSampleList()
    {
        return productSampleList;
    }
    
    public String getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(String enabled)
    {
        this.enabled = enabled;
    }
    
    public void setProductSampleList(List<ProductSample> productSampleList)
    {
        this.productSampleList = productSampleList;
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
    
    public String getPrice()
    {
        return price;
    }
    
    public void setPrice(String price)
    {
        this.price = price;
        
    }
    
    public Integer getTestingDuration()
    {
        return testingDuration;
    }
    
    public void setTestingDuration(Integer testingDuration)
    {
        this.testingDuration = testingDuration;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getProbeArray()
    {
        return probeArray;
    }
    
    public void setProbeArray(String probeArray)
    {
        this.probeArray = probeArray;
    }
    
    public List<Probe> getProbeList()
    {
        return probeList;
    }
    
    public void setProbeList(List<Probe> probeList)
    {
        this.probeList = probeList;
    }
    
    public ReportTemplate getReportTemplate()
    {
        return reportTemplate;
    }
    
    public void setReportTemplate(ReportTemplate reportTemplate)
    {
        this.reportTemplate = reportTemplate;
    }
    
    public String getProductLeader()
    {
        return productLeader;
    }
    
    public void setProductLeader(String productLeader)
    {
        this.productLeader = productLeader;
    }
}
