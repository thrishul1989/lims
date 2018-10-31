package com.todaysoft.lims.product.model.request;

import java.math.BigDecimal;
import java.util.List;

import com.todaysoft.lims.product.entity.ProductDisease;
import com.todaysoft.lims.product.entity.ProductGene;
import com.todaysoft.lims.product.entity.ProductPrincipal;
import com.todaysoft.lims.product.entity.ProductProbe;
import com.todaysoft.lims.product.entity.ProductSample;
import com.todaysoft.lims.product.entity.ProductTestingMethod;
import com.todaysoft.lims.product.entity.TestingType;
import com.todaysoft.lims.product.entity.User;
import com.todaysoft.lims.product.entity.report.ReportTemplate;

/**
 * Created by HSHY-032 on 2016/6/8.
 */
public class ProductCreateRequest
{
    private String id;
    
    private String code; // 产品编号
    
    private String name; // 产品名称
    
    private String price; // 产品定价￥
    
    private String realPrice;//实际价格分转元
    
    private User productDuty;
    
    private ReportTemplate reportTemplate;
    
    private String productLeader;
    
    private TestingType testingType;// 检测类型
    
    private TestingType testingSubtype;// 检测类型
    
    private Integer testingDuration; // 产品周期 天
    
    private String testSample; // 实验样本
    
    private String description;// 产品描述
    
    private String probeArray;// 探针ids
    
    private List<ProductProbe> probeList;
    
    private String remark;//备注
    
    private Integer delFlag;
    
    private Integer ifMade;//是否定制 0 否 1是
    
    private String testInstitution;//检测机构：0 检验所 1麦诺基公司
    
    private String updateTime;
    
    private String testingStartSample;
    
    private String creater;// 创建人
    
    private String createTime;// 创建时间
    
    private Integer enabled;// 状态
    
    private List<ProductSample> productSampleList;
    
    private List<ProductTestingMethod> productTestingMethodList;
    
    private List<ProductProbeRequest> productProbeList;// 请求字段
    
    private List<ProductDisease> productDiseaseList;
    
    private List<ProductGene> productGeneList;
    
    private User createUSER;//冗余字段
    
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
    
    public String getRealPrice()
    {
        return BigDecimal.valueOf(Long.valueOf(price)).divide(new BigDecimal(100)).toString();
    }
    
    public void setRealPrice(String realPrice)
    {
        this.realPrice = realPrice;
    }
    
    public User getProductDuty()
    {
        return productDuty;
    }
    
    public void setProductDuty(User productDuty)
    {
        this.productDuty = productDuty;
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
    
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public String getTestingStartSample()
    {
        return testingStartSample;
    }
    
    public void setTestingStartSample(String testingStartSample)
    {
        this.testingStartSample = testingStartSample;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public List<ProductDisease> getProductDiseaseList()
    {
        return productDiseaseList;
    }
    
    public void setProductDiseaseList(List<ProductDisease> productDiseaseList)
    {
        this.productDiseaseList = productDiseaseList;
    }
    
    public List<ProductGene> getProductGeneList()
    {
        return productGeneList;
    }
    
    public void setProductGeneList(List<ProductGene> productGeneList)
    {
        this.productGeneList = productGeneList;
    }
    
    public String getSamplePurpose()
    {
        return samplePurpose;
    }
    
    public void setSamplePurpose(String samplePurpose)
    {
        this.samplePurpose = samplePurpose;
    }
    
    public String getCreater()
    {
        return creater;
    }
    
    public void setCreater(String creater)
    {
        this.creater = creater;
    }
    
    public User getCreateUSER()
    {
        return createUSER;
    }
    
    public void setCreateUSER(User createUSER)
    {
        this.createUSER = createUSER;
    }
    
    public List<ProductPrincipal> getProductPrincipalList()
    {
        return productPrincipalList;
    }
    
    public void setProductPrincipalList(List<ProductPrincipal> productPrincipalList)
    {
        this.productPrincipalList = productPrincipalList;
    }
    
    private List<ProductPrincipal> productPrincipalList;
    
    public List<ProductProbeRequest> getProductProbeList()
    {
        return productProbeList;
    }
    
    public void setProductProbeList(List<ProductProbeRequest> productProbeList)
    {
        this.productProbeList = productProbeList;
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
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public Integer getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    
    public List<ProductSample> getProductSampleList()
    {
        return productSampleList;
    }
    
    public void setProductSampleList(List<ProductSample> productSampleList)
    {
        this.productSampleList = productSampleList;
    }
    
    public List<ProductTestingMethod> getProductTestingMethodList()
    {
        return productTestingMethodList;
    }
    
    public void setProductTestingMethodList(List<ProductTestingMethod> productTestingMethodList)
    {
        this.productTestingMethodList = productTestingMethodList;
    }
    
    public List<ProductProbe> getProbeList()
    {
        return probeList;
    }
    
    public void setProbeList(List<ProductProbe> probeList)
    {
        this.probeList = probeList;
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
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
