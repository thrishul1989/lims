package com.todaysoft.lims.maintain.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LIMS_ORDER")
public class Order implements Serializable
{
    private static final long serialVersionUID = 2161585840633690043L;
    
    private String id;
    
    private String code;
    
    private MarketingCenter marketingCenter;
    
    private String doctorAssist;
    
    private Date submitTime;
    
    private Contract contract;

    private Customer owner;
    
    private String salesmanName;

    private String recipientName;//收件人姓名

    private String recipientPhone;//收件人电话

    private String recipientAddress;//收件人地址
    
    private Integer testingStatus;
    
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_TYPE")
    public MarketingCenter getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(MarketingCenter marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    @Column(name = "DOCTOR_ASSIST")
    public String getDoctorAssist()
    {
        return doctorAssist;
    }
    
    public void setDoctorAssist(String doctorAssist)
    {
        this.doctorAssist = doctorAssist;
    }
    
    @Column(name = "SUBMIT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    public Customer getOwner()
    {
        return owner;
    }
    
    public void setOwner(Customer owner)
    {
        this.owner = owner;
    }
    
    @Column(name = "CREATOR_NAME")
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
    }

    @Column(name = "RECIPIENT_NAME")
    public String getRecipientName()
    {
        return recipientName;
    }

    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }

    @Column(name = "RECIPIENT_PHONE")
    public String getRecipientPhone()
    {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }

    @Column(name = "RECIPIENT_ADDRESS")
    public String getRecipientAddress()
    {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }
    
    @Column(name = "TESTING_STATUS")
    public Integer getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(Integer testingStatus)
    {
        this.testingStatus = testingStatus;
    }
}
