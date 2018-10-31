package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_REPORT_SEND_CALLBACK")
public class TestingReportSendCallBack extends UuidKeyEntity implements Serializable
{
    public static int SEND_STATUS_WAIT = 0;//待处理
    
    public static int SEND_STATUS_DOING = 1;//处理中
    
    public static int SEND_STATUS_SUCCESS = 2;//成功
    
    public static int SEND_STATUS_FAILED = 3;//失败
    
    private static final long serialVersionUID = -84876107787080557L;
    
    private String reportId;
    
    private Integer status;
    
    private Date createTime;
    
    private List<TestingReportSendDetails> sendDetails;
    
    private String reportTemplateType;

    @Column(name="REPORT_ID")
    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    @Column(name="STATUS")
    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
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

    @OneToMany(mappedBy = "sendCallBack", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<TestingReportSendDetails> getSendDetails()
    {
        return sendDetails;
    }

    public void setSendDetails(List<TestingReportSendDetails> sendDetails)
    {
        this.sendDetails = sendDetails;
    }

    @Column(name="REPORT_TEMPLATE_TYPE")
    public String getReportTemplateType()
    {
        return reportTemplateType;
    }

    public void setReportTemplateType(String reportTemplateType)
    {
        this.reportTemplateType = reportTemplateType;
    }
    
}
