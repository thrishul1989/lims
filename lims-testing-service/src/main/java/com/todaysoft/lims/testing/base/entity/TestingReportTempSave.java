package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_REPORT_TEMP_SAVE")
public class TestingReportTempSave extends UuidKeyEntity implements Serializable
{

    private static final long serialVersionUID = -7855460569026410360L;
    
    private String reportId;
    
    private String semantic;
    
    private String recordId;
    
    private String clinicalJudgment;
    
    private String mutationSource;
    
    private Date createTime;

    @Column(name="REPORT_ID")
    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    @Column(name="SEMANTIC")
    public String getSemantic()
    {
        return semantic;
    }

    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }

    @Column(name="RECORD_ID")
    public String getRecordId()
    {
        return recordId;
    }

    public void setRecordId(String recordId)
    {
        this.recordId = recordId;
    }

    @Column(name="CLINICAL_JUDGMENT")
    public String getClinicalJudgment()
    {
        return clinicalJudgment;
    }

    public void setClinicalJudgment(String clinicalJudgment)
    {
        this.clinicalJudgment = clinicalJudgment;
    }

    @Column(name="MUTATION_SOURCE")
    public String getMutationSource()
    {
        return mutationSource;
    }

    public void setMutationSource(String mutationSource)
    {
        this.mutationSource = mutationSource;
    }

    @Column(name="CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    
}
