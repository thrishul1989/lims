package com.todaysoft.lims.testing.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SHEET_VIEW")
public class TestingSheetView extends UuidKeyEntity
{
    private static final long serialVersionUID = -1514257866284288508L;
    
    private String semantic;// 任务单类型标记
    
    private String taskName;// 任务单类型名称
    
    private String code;// 任务单编号
    
    private String description;// 任务单描述
    
    private String testerId;// 操作员ID
    
    private String testerName;// 操作员姓名
    
    private String assignerName;// 下单人姓名
    
    private Date assignTime;// 下单时间
    
    private Date submitTime;// 任务单完成提交时间
    
    private Integer technicalIfResubmit;
    @Transient
    public Integer getTechnicalIfResubmit()
    {
        return technicalIfResubmit;
    }
    
    public void setTechnicalIfResubmit(Integer technicalIfResubmit)
    {
        this.technicalIfResubmit = technicalIfResubmit;
    }
    
    @Column(name = "SEMANTIC")
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    @Column(name = "TASK_NAME")
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
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
    
    @Column(name = "DESCRIPTION")
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    @Column(name = "TESTER_ID")
    public String getTesterId()
    {
        return testerId;
    }
    
    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }
    
    @Column(name = "TESTER_NAME")
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    @Column(name = "ASSIGNER_NAME")
    public String getAssignerName()
    {
        return assignerName;
    }
    
    public void setAssignerName(String assignerName)
    {
        this.assignerName = assignerName;
    }
    
    @Column(name = "ASSIGN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
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
    
}
