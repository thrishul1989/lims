package com.todaysoft.lims.testing.base.entity.rapid;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todaysoft.lims.testing.base.entity.TestingType;

@Entity
@Table(name = "LIMS_CONTRACT")
public class RapidContract implements Serializable
{
    private static final long serialVersionUID = 7185148291084720019L;
    
    private String id;
    
    private String code;
    
    private String name;
    
    private TestingType marketingCenter;
    
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKETING_CENTER")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
    public TestingType getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(TestingType marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
}
