package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PRODUCT")
public class Product extends UuidKeyEntity implements Serializable
{
    private static final long serialVersionUID = 1619101351976500555L;
    
    private String name;
    
    private String code;
    
    private List<ProductTestingMethod> productTestingMethodList;
    
    private TestingType testingType;
    
    private String testingStartSample;//实验样本
    
    private Integer testingDuration;
    
    private List<ProductPrincipal> productPrincipalList;
    
    private Integer delFlag;
    
    private User productDuty;
    
    @Column(name = "DEL_FLAG", columnDefinition = "tinyint default 0")
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    @ManyToOne
    @JoinColumn(name = "TESTING_TYPE")
    public TestingType getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(TestingType testingType)
    {
        this.testingType = testingType;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
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
    
    @Column(name = "TESTING_START_SAMPLE")
    public String getTestingStartSample()
    {
        return testingStartSample;
    }
    
    public void setTestingStartSample(String testingStartSample)
    {
        this.testingStartSample = testingStartSample;
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
    
    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
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
}
