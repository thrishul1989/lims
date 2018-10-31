package com.todaysoft.lims.report.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_SYSTEM_CONTRACT_CHANGE_INFO")
public class ReportSystemContractChangeInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = -8959108329362647536L;
    private String taskId;
    private ReportSystemContractInfo contractInfo;
    private String beforeSignUser;//变更前业务员
    private String afterSignUser;//变更后业务员
    private String operateName;//操作人
    private String operateTime;//操作时间
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    @JsonIgnore
    public ReportSystemContractInfo getContractInfo()
    {
        return contractInfo;
    }
    public void setContractInfo(ReportSystemContractInfo contractInfo)
    {
        this.contractInfo = contractInfo;
    }
    
    @Column(name = "BEFORE_SIGN_USER")
    public String getBeforeSignUser()
    {
        return beforeSignUser;
    }
    public void setBeforeSignUser(String beforeSignUser)
    {
        this.beforeSignUser = beforeSignUser;
    }
    
    @Column(name = "AFTER_SIGN_USER")
    public String getAfterSignUser()
    {
        return afterSignUser;
    }
    public void setAfterSignUser(String afterSignUser)
    {
        this.afterSignUser = afterSignUser;
    }
    
    @Column(name = "OPERATE_NAME")
    public String getOperateName()
    {
        return operateName;
    }
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }
    
    @Column(name = "OPERATE_TIME")
    public String getOperateTime()
    {
        return operateTime;
    }
    public void setOperateTime(String operateTime)
    {
        this.operateTime = operateTime;
    }
    
    
    
}
