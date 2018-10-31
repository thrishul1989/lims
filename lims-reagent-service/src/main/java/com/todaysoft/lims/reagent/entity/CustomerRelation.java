package com.todaysoft.lims.reagent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
@Entity
@Table(name = "APP_USER_RELATION")
public class CustomerRelation extends UuidKeyEntity

{
    
    private Customer customer;
    
    private BusinessInfo business;
    private String createDate;
    private String updateDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    @NotFound(action=NotFoundAction.IGNORE)
    public Customer getCustomer()
    {
        return customer;
    }
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BUSINESS_USER_ID")
    @NotFound(action=NotFoundAction.IGNORE)
   
    public BusinessInfo getBusiness()
    {
        return business;
    }
    public void setBusiness(BusinessInfo business)
    {
        this.business = business;
    }
    @Column(name = "CREATE_DATE")
    public String getCreateDate()
    {
        return createDate;
    }
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }
    @Column(name = "UPDATE_DATE")
    public String getUpdateDate()
    {
        return updateDate;
    }
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }
    
}
