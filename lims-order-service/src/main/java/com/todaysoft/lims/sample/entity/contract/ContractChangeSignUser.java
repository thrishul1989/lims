package com.todaysoft.lims.sample.entity.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT_CHANGE_SIGN_USER")
public class ContractChangeSignUser extends UuidKeyEntity
{

    private static final long serialVersionUID = 4878159854677903573L;
    
    private String contractId;
    
    private String beforeSignUser;
    
    private String afterSignUser;
    
    private String operateId;
    
    private String operateName;
    
    private Date operateTime;

    @Column(name="CONTRACT_ID")
    public String getContractId()
    {
        return contractId;
    }

    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }

    @Column(name="BEFORE_SIGN_USER")
    public String getBeforeSignUser()
    {
        return beforeSignUser;
    }

    public void setBeforeSignUser(String beforeSignUser)
    {
        this.beforeSignUser = beforeSignUser;
    }

    @Column(name="AFTER_SIGN_USER")
    public String getAfterSignUser()
    {
        return afterSignUser;
    }

    public void setAfterSignUser(String afterSignUser)
    {
        this.afterSignUser = afterSignUser;
    }

    @Column(name="OPERATE_ID")
    public String getOperateId()
    {
        return operateId;
    }

    public void setOperateId(String operateId)
    {
        this.operateId = operateId;
    }

    @Column(name="OPERATE_NAME")
    public String getOperateName()
    {
        return operateName;
    }

    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }

    @Column(name="OPERATE_TIME") 
    public Date getOperateTime()
    {
        return operateTime;
    }

    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    
    
}
