package com.todaysoft.lims.product.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OrderBy;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.product.entity.report.ReportTemplate;

@Entity
@Table(name = "LIMS_PRODUCT")
public class Product extends UuidKeyEntity
{
    private static final long serialVersionUID = 8971472981680521730L;
    
    private String code; // 产品编号
    
    private String name; // 产品名称
    
    private Integer price; // 产品定价￥
    
    private TestingType testingType;// 检测类型以及分类
    
    private TestingType testingSubtype;//二级分类
    
    private Integer testingDuration; // 产品周期 天
    
    private ReportTemplate reportTemplate;
    
    private User productDuty;
    
    private String description;// 产品描述
    
    private Integer ifMade;//是否定制 0 否 1是
    
    private String testInstitution;//检测机构：0 检验所 1麦诺基公司
    
    private String updateTime;
    
    private Integer delFlag;
    
    private String creater;// 创建人
    
    private String createTime;// 创建时间
    
    private Integer enabled;// 状态
    
    private String remark;// 产品备注
    
    private List<ProductSample> productSampleList;
    
    private List<ProductTestingMethod> productTestingMethodList;
    
    private List<ProductPrincipal> productPrincipalList;
    
    private List<ProductDisease> productDiseaseList;
    
    private List<ProductGene> productGeneList;
    
    private String testingStartSample;//实验样本
    
    private String samplePurpose;
    
    private List<ProductAmountRule> productAmountRuleList;
    
    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy(clause = "count asc ")
    public List<ProductAmountRule> getProductAmountRuleList()
    {
        return productAmountRuleList;
    }
    
    public void setProductAmountRuleList(List<ProductAmountRule> productAmountRuleList)
    {
        this.productAmountRuleList = productAmountRuleList;
    }
    
    @Column(name = "SAMPLE_PURPOSE")
    public String getSamplePurpose()
    {
        return samplePurpose;
    }
    
    public void setSamplePurpose(String samplePurpose)
    {
        this.samplePurpose = samplePurpose;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_DUTY")
    @NotFound(action = NotFoundAction.IGNORE)
    public User getProductDuty()
    {
        return productDuty;
    }
    
    public void setProductDuty(User productDuty)
    {
        this.productDuty = productDuty;
    }
    
    @Column(name = "IF_MADE")
    public Integer getIfMade()
    {
        return ifMade;
    }
    
    public void setIfMade(Integer ifMade)
    {
        this.ifMade = ifMade;
    }
    
    @Column(name = "TEST_INSTITUTION")
    public String getTestInstitution()
    {
        return testInstitution;
    }
    
    public void setTestInstitution(String testInstitution)
    {
        this.testInstitution = testInstitution;
    }
    
    @Column(name = "UPDATE_TIME")
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @Column(name = "DEL_FLAG", columnDefinition = "tinyint default 0")
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TESTING_TYPE")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingType getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(TestingType testingType)
    {
        this.testingType = testingType;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TESTING_SUBTYPE")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingType getTestingSubtype()
    {
        return testingSubtype;
    }
    
    public void setTestingSubtype(TestingType testingSubtype)
    {
        this.testingSubtype = testingSubtype;
    }
    
    @Column(name = "TESTING_START_SAMPLE")
    public String getTestingStartSample()
    {
        return testingStartSample;
    }
    
    public void setTestingStartSample(String testingStartSample)
    {
        this.testingStartSample = testingStartSample;
    }
    
    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    // @Fetch(FetchMode.SUBSELECT)
    public List<ProductGene> getProductGeneList()
    {
        return productGeneList;
    }
    
    public void setProductGeneList(List<ProductGene> productGeneList)
    {
        this.productGeneList = productGeneList;
    }
    
    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    //@Fetch(FetchMode.SUBSELECT)
    public List<ProductDisease> getProductDiseaseList()
    {
        return productDiseaseList;
    }
    
    public void setProductDiseaseList(List<ProductDisease> productDiseaseList)
    {
        this.productDiseaseList = productDiseaseList;
    }
    
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ProductPrincipal> getProductPrincipalList()
    {
        return productPrincipalList;
    }
    
    public void setProductPrincipalList(List<ProductPrincipal> productPrincipalList)
    {
        this.productPrincipalList = productPrincipalList;
    }
    
    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ProductTestingMethod> getProductTestingMethodList()
    {
        return productTestingMethodList;
    }
    
    public void setProductTestingMethodList(List<ProductTestingMethod> productTestingMethodList)
    {
        this.productTestingMethodList = productTestingMethodList;
    }
    
    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy(clause = "sample.id asc ")
    public List<ProductSample> getProductSampleList()
    {
        return productSampleList;
    }
    
    public void setProductSampleList(List<ProductSample> productSampleList)
    {
        this.productSampleList = productSampleList;
    }
    
    @Column(name = "CREATE_TIME")
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    @Column(name = "ENABLED", columnDefinition = "tinyint default 0")
    public Integer getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "PRICE")
    public Integer getPrice()
    {
        return price;
    }
    
    public void setPrice(Integer price)
    {
        this.price = price;
    }
    
    @Column(name = "TESTING_DURATION")
    public Integer getTestingDuration()
    {
        return testingDuration;
    }
    
    public void setTestingDuration(Integer testingDuration)
    {
        this.testingDuration = testingDuration;
    }
    
    @Column(name = "DESCRIPTION")
    public String getDescription()
    {
        return description;
    }
    
    @Column(name = "CREATOR_ID")
    public String getCreater()
    {
        return creater;
    }
    
    public void setCreater(String creater)
    {
        this.creater = creater;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    @JoinColumn(name = "REPORT_TEMPLATE_ID")
    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    public ReportTemplate getReportTemplate()
    {
        return reportTemplate;
    }
    
    public void setReportTemplate(ReportTemplate reportTemplate)
    {
        this.reportTemplate = reportTemplate;
    }
    
    /*   @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
       @NotFound(action = NotFoundAction.IGNORE)
       @Fetch(FetchMode.SUBSELECT)
       public List<ProductTask> getProductTaskList()
       {
           return productTaskList;
       }
       
       public void setProductTaskList(List<ProductTask> productTaskList)
       {
           this.productTaskList = productTaskList;
       }
       */
}
