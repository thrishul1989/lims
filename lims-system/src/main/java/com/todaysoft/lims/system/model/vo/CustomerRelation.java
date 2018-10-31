package com.todaysoft.lims.system.model.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

public class CustomerRelation 

{
    private String id;
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public Customer getCustomer()
    {
        return customer;
    }
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    public BusinessInfo getBusiness()
    {
        return business;
    }
    public void setBusiness(BusinessInfo business)
    {
        this.business = business;
    }
    public String getCreateDate()
    {
        return createDate;
    }
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }
    public String getUpdateDate()
    {
        return updateDate;
    }
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }
    private Customer customer;
    
    private BusinessInfo business;
    private String createDate;
    private String updateDate;
    
   
    
}
