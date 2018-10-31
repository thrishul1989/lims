package com.todaysoft.lims.sample.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OrderBy;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PRODUCT")
public class Product extends UuidKeyEntity
{
    private static final long serialVersionUID = 8971472981680521730L;
    
    private String code; // 产品编号
    
    private String name; // 产品名称
    
    private Integer price; // 产品定价￥
    
    private Integer testingDuration; // 产品周期 天
    
    private String testInstitution;//检测机构：0 检验所 1麦诺基公司
    
    private String samplePurpose;
    
    private Integer delFlag;
    
    private Integer enabled;// 状态
    
    private List<ProductSample> productSampleList;
    
    private List<ProductTestingMethod> productTestingMethodList;
    
    private List<ProductAmountRule> productAmountRuleList;
    
    private String testingStartSample;//实验样本
    
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
    
    @Column(name = "TEST_INSTITUTION")
    public String getTestInstitution()
    {
        return testInstitution;
    }
    
    public void setTestInstitution(String testInstitution)
    {
        this.testInstitution = testInstitution;
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
    
    @Column(name = "TESTING_START_SAMPLE")
    public String getTestingStartSample()
    {
        return testingStartSample;
    }
    
    public void setTestingStartSample(String testingStartSample)
    {
        this.testingStartSample = testingStartSample;
    }
    
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
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
    
    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    public List<ProductSample> getProductSampleList()
    {
        return productSampleList;
    }
    
    public void setProductSampleList(List<ProductSample> productSampleList)
    {
        this.productSampleList = productSampleList;
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
    
}
