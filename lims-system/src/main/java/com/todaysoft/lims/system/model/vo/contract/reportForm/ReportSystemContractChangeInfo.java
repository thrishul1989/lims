package com.todaysoft.lims.system.model.vo.contract.reportForm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

public class ReportSystemContractChangeInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = -8959108329362647536L;
    private String taskId;
    private ReportSystemContractInfo contractInfo;
    private String beforeSignUser;//变更前业务员
    private String afterSignUser;//变更后业务员
    private String operateName;//操作人
    private String operateTime;//操作时间
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public ReportSystemContractInfo getContractInfo()
    {
        return contractInfo;
    }
    public void setContractInfo(ReportSystemContractInfo contractInfo)
    {
        this.contractInfo = contractInfo;
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
    
    public String getOperateName()
    {
        return operateName;
    }
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }
    
    public String getOperateTime()
    {
        return operateTime;
    }
    public void setOperateTime(String operateTime)
    {
        this.operateTime = operateTime;
    }
    
    
    
}
