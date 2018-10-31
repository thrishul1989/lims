package com.todaysoft.lims.testing.base.entity.rapid;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todaysoft.lims.testing.base.entity.TestingType;

@Entity
@Table(name = "LIMS_ORDER")
public class RapidOrder implements Serializable
{
    private static final long serialVersionUID = 2161585840633690043L;
    
    private String id;
    
    private String code;
    
    private TestingType marketingCenter;
    
    private String doctorAssist;
    
    private Date submitTime;
    
    private RapidContract contract;
    
    private RapidCustomer owner;
    
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
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
    public TestingType getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(TestingType marketingCenter)
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
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
    public RapidContract getContract()
    {
        return contract;
    }
    
    public void setContract(RapidContract contract)
    {
        this.contract = contract;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
    public RapidCustomer getOwner()
    {
        return owner;
    }
    
    public void setOwner(RapidCustomer owner)
    {
        this.owner = owner;
    }
}
