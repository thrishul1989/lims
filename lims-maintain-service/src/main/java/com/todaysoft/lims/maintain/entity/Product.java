package com.todaysoft.lims.maintain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LIMS_PRODUCT")
public class Product implements Serializable
{
    private static final long serialVersionUID = 5603118117198465059L;
    
    private String id;
    
    private String code;
    
    private String name;
    
    private MarketingCenter category;
    
    private MarketingCenter subcategory;
    
    private String description;
    
    private Date createTime;
    
    private Integer testingDuration;
    
    private List<ProductTestingMethod> productTestingMethodList;
    
    private List<ProductPrincipal> productPrincipalList;
    
    private Integer ifMade;//是否定制 0 否 1是
    
    private boolean deleted;
    
    private User productDuty;
    
    private Integer enabled;// 状态
    
    @Column(name = "ENABLED")
    public Integer getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
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
    
    @Id
    @Column(name = "ID")
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TESTING_TYPE")
    @NotFound(action = NotFoundAction.IGNORE)
    public MarketingCenter getCategory()
    {
        return category;
    }
    
    public void setCategory(MarketingCenter category)
    {
        this.category = category;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TESTING_SUBTYPE")
    @NotFound(action = NotFoundAction.IGNORE)
    public MarketingCenter getSubcategory()
    {
        return subcategory;
    }
    
    public void setSubcategory(MarketingCenter subcategory)
    {
        this.subcategory = subcategory;
    }
    
    @Column(name = "DESCRIPTION")
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
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
    
}
