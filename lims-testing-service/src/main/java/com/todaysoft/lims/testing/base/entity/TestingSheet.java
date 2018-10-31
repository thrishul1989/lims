package com.todaysoft.lims.testing.base.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SHEET")
public class TestingSheet extends UuidKeyEntity
{
    private static final long serialVersionUID = -1514257866284288508L;
    
    private String semantic;//任务单类型标记
    
    private String taskName;//任务单类型名称
    
    private String code;//任务单编号
    
    private String description;//任务单描述
    
    private String testerId;//操作员ID
    
    private String testerName;//操作员姓名
    
    private String assignerId;//下单人ID
    
    private String assignerName;//下单人姓名
    
    private Date assignTime;//下单时间
    
    private String submiterId;//操作员ID
    
    private String submiterName;//操作员姓名
    
    private Date submitTime;//任务单完成提交时间
    
    private Date createTime;
    
    private String variables;//json变量
    
    private List<TestingSheetTask> testingSheetTaskList = Lists.newArrayList();
    
    private Integer technicalIfResubmit;// 冗余字段，技术分析任务是否可以补提
    
  
    
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
    
    @Column(name = "ASSIGNER_ID")
    public String getAssignerId()
    {
        return assignerId;
    }
    
    public void setAssignerId(String assignerId)
    {
        this.assignerId = assignerId;
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
    
    @Column(name = "SUBMITER_ID")
    public String getSubmiterId()
    {
        return submiterId;
    }
    
    public void setSubmiterId(String submiterId)
    {
        this.submiterId = submiterId;
    }
    
    @Column(name = "SUBMITER_NAME")
    public String getSubmiterName()
    {
        return submiterName;
    }
    
    public void setSubmiterName(String submiterName)
    {
        this.submiterName = submiterName;
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
    
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "VARIABLES")
    public String getVariables()
    {
        return variables;
    }
    
    public void setVariables(String variables)
    {
        this.variables = variables;
    }
    
    @OneToMany(mappedBy = "testingSheet", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<TestingSheetTask> getTestingSheetTaskList()
    {
        return testingSheetTaskList;
    }
    
    public void setTestingSheetTaskList(List<TestingSheetTask> testingSheetTaskList)
    {
        this.testingSheetTaskList = testingSheetTaskList;
    }
}
