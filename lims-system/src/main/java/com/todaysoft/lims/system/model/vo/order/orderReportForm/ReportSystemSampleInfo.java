package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_SYSTEM_SAMPLE_INFO")
public class ReportSystemSampleInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = 2350158422777126650L;
    
    private String taskId;
    //private String orderId;
    private String sampleCode;
    private String sampleType;
    private String sampleSize;
    private Date samplingDate;
    private String ownerName;
    private String ownerAge;
    private String familyRelation;
    private String puprose;
    private String ownerPhenotype;
    private String samplePackageStatus;
    private Date packDate;
    private Date acceptSampleTime;
    private ReportSystemOrderInfo orderInfo;
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public ReportSystemOrderInfo getOrderInfo()
    {
        return orderInfo;
    }
    public void setOrderInfo(ReportSystemOrderInfo orderInfo)
    {
        this.orderInfo = orderInfo;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getSampleSize()
    {
        return sampleSize;
    }
    public void setSampleSize(String sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    public Date getSamplingDate()
    {
        return samplingDate;
    }
    public void setSamplingDate(Date samplingDate)
    {
        this.samplingDate = samplingDate;
    }
    
    public String getOwnerName()
    {
        return ownerName;
    }
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    public String getOwnerAge()
    {
        return ownerAge;
    }
    public void setOwnerAge(String ownerAge)
    {
        this.ownerAge = ownerAge;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public String getPuprose()
    {
        return puprose;
    }
    public void setPuprose(String puprose)
    {
        this.puprose = puprose;
    }
    
    public String getOwnerPhenotype()
    {
        return ownerPhenotype;
    }
    public void setOwnerPhenotype(String ownerPhenotype)
    {
        this.ownerPhenotype = ownerPhenotype;
    }
    
    public String getSamplePackageStatus()
    {
        return samplePackageStatus;
    }
    public void setSamplePackageStatus(String samplePackageStatus)
    {
        this.samplePackageStatus = samplePackageStatus;
    }
    
    public Date getPackDate()
    {
        return packDate;
    }
    public void setPackDate(Date packDate)
    {
        this.packDate = packDate;
    }
    
    public Date getAcceptSampleTime()
    {
        return acceptSampleTime;
    }
    public void setAcceptSampleTime(Date acceptSampleTime)
    {
        this.acceptSampleTime = acceptSampleTime;
    }
    
    
}
