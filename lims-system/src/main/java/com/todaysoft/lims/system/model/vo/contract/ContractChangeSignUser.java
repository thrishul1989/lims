package com.todaysoft.lims.system.model.vo.contract;

import java.util.Date;

public class ContractChangeSignUser
{
    private String contractId;
    
    private String beforeSignUser;
    
    private String beforeSignUserName;
    
    private String afterSignUser;
    
    private String afterSignUserName;
    
    private String operateId;
    
    private String operateName;
    
    private Date operateTime;

    public String getContractId()
    {
        return contractId;
    }

    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }

    public String getBeforeSignUser()
    {
        return beforeSignUser;
    }

    public void setBeforeSignUser(String beforeSignUser)
    {
        this.beforeSignUser = beforeSignUser;
    }

    public String getAfterSignUser()
    {
        return afterSignUser;
    }

    public void setAfterSignUser(String afterSignUser)
    {
        this.afterSignUser = afterSignUser;
    }

    public String getOperateId()
    {
        return operateId;
    }

    public void setOperateId(String operateId)
    {
        this.operateId = operateId;
    }

    public String getOperateName()
    {
        return operateName;
    }

    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }

    public Date getOperateTime()
    {
        return operateTime;
    }

    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }

    public String getBeforeSignUserName()
    {
        return beforeSignUserName;
    }

    public void setBeforeSignUserName(String beforeSignUserName)
    {
        this.beforeSignUserName = beforeSignUserName;
    }

    public String getAfterSignUserName()
    {
        return afterSignUserName;
    }

    public void setAfterSignUserName(String afterSignUserName)
    {
        this.afterSignUserName = afterSignUserName;
    }
    
}
