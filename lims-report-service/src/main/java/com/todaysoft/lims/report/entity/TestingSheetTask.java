package com.todaysoft.lims.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SHEET_TASK")
public class TestingSheetTask extends UuidKeyEntity
{
    private static final long serialVersionUID = -8528231702266388091L;
    
    private TestingSheet testingSheet;//任务单ID
    
    private String testingTaskId;//检测任务ID
    
    private Integer resultType;//1正常2失败可重做3失败不可重做
    
    private String remark;//备注
    
    private String resultDetails;//结果详情
    
    @ManyToOne
    @JoinColumn(name = "SHEET_ID")
    @JsonIgnore
    public TestingSheet getTestingSheet()
    {
        return testingSheet;
    }
    
    public void setTestingSheet(TestingSheet testingSheet)
    {
        this.testingSheet = testingSheet;
    }
    
    @Column(name = "TESTING_TASK_ID")
    public String getTestingTaskId()
    {
        return testingTaskId;
    }
    
    public void setTestingTaskId(String testingTaskId)
    {
        this.testingTaskId = testingTaskId;
    }
    
    @Column(name = "RESULT_TYPE")
    public Integer getResultType()
    {
        return resultType;
    }
    
    public void setResultType(Integer resultType)
    {
        this.resultType = resultType;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "RESULT_DETAILS")
    public String getResultDetails()
    {
        return resultDetails;
    }
    
    public void setResultDetails(String resultDetails)
    {
        this.resultDetails = resultDetails;
    }
}
