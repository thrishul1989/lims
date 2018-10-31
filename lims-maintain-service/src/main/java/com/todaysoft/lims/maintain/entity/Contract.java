package com.todaysoft.lims.maintain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_CONTRACT")
public class Contract implements Serializable
{
    private static final long serialVersionUID = 7185148291084720019L;
    
    private String id;
    
    private String code;
    
    private String name;
    
    private MarketingCenter marketingCenter;
    
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
    public MarketingCenter getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(MarketingCenter marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
}
