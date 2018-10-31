package com.todaysoft.lims.sample.entity.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_CONCLUDING_REPORT")
public class TestingConcludingReport extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String deliveryMode;
    
    private String deliveryResult;
    
    private Date createTime;
    
    private Date reportDate;
    
    private String updateName;
    
    private Date updateTime;
    
    private String concludingReportUrl;
    
    private String concludingReportCode;
    
    private String concludingReportName;
    
    private Integer statu;//0-未上传报告1-已上传报告
    
    @Column(name = "UPDATE_NAME")
    public String getUpdateName()
    {
        return updateName;
    }
    
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }
    
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @Column(name = "CONCLUDING_REPORT_URL")
    public String getConcludingReportUrl()
    {
        return concludingReportUrl;
    }
    
    public void setConcludingReportUrl(String concludingReportUrl)
    {
        this.concludingReportUrl = concludingReportUrl;
    }
    
    @Column(name = "CONCLUDING_REPORT_CODE")
    public String getConcludingReportCode()
    {
        return concludingReportCode;
    }
    
    public void setConcludingReportCode(String concludingReportCode)
    {
        this.concludingReportCode = concludingReportCode;
    }
    
    @Column(name = "CONCLUDING_REPORT_NAME")
    public String getConcludingReportName()
    {
        return concludingReportName;
    }
    
    public void setConcludingReportName(String concludingReportName)
    {
        this.concludingReportName = concludingReportName;
    }
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "DELIVERY_MODE")
    public String getDeliveryMode()
    {
        return deliveryMode;
    }
    
    public void setDeliveryMode(String deliveryMode)
    {
        this.deliveryMode = deliveryMode;
    }
    
    @Column(name = "DELIVERY_RESULT")
    public String getDeliveryResult()
    {
        return deliveryResult;
    }
    
    public void setDeliveryResult(String deliveryResult)
    {
        this.deliveryResult = deliveryResult;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "REPORT_DATE")
    public Date getReportDate()
    {
        return reportDate;
    }
    
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    
    @Column(name = "STATU")
    public Integer getStatu()
    {
        return statu;
    }
    
    public void setStatu(Integer statu)
    {
        this.statu = statu;
    }
    
}
