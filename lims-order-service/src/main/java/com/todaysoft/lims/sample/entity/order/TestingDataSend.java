package com.todaysoft.lims.sample.entity.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_DATA_SEND")
public class TestingDataSend extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private Date createTime;
    
    private Date reportDate;
    
    private String sendName;
    
    private Date sendTime;
    
    private Integer statu;//0-未发送 1-已发送
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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
    
    @Column(name = "SEND_NAME")
    public String getSendName()
    {
        return sendName;
    }
    
    public void setSendName(String sendName)
    {
        this.sendName = sendName;
    }
    
    @Column(name = "SEND_TIME")
    public Date getSendTime()
    {
        return sendTime;
    }
    
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
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
