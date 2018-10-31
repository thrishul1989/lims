package com.todaysoft.lims.sample.entity.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_REPORT_EMAIL")

public class TestingReportEmail extends UuidKeyEntity
{
    
    private static final long serialVersionUID = 3749532968297527535L;
    private String orderId;
    
    private String productId;
    
    private Date emailTime;
    
    private String status;//1待分配 2 已分配 3 已寄送 4不寄送 5流程被取消
    
    private String emailContent;
    
    private String emailType;//快递类型快递类型 0 人工物流 1.。2.。。
    
    private String emailNo;
    
    private String assignedName;
    
    @Column(name="ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    @Column(name="PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    @Column(name="EMAIL_TIME")
    public Date getEmailTime()
    {
        return emailTime;
    }
    public void setEmailTime(Date emailTime)
    {
        this.emailTime = emailTime;
    }
    
    @Column(name = "STATUS")
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Column(name = "EMAIL_CONTENT")
    public String getEmailContent()
    {
        return emailContent;
    }
    
    public void setEmailContent(String emailContent)
    {
        this.emailContent = emailContent;
    }
    
    @Column(name = "EMAIL_TYPE")
    public String getEmailType()
    {
        return emailType;
    }
    
    public void setEmailType(String emailType)
    {
        this.emailType = emailType;
    }
    
    @Column(name = "EMAIL_NO")
    public String getEmailNo()
    {
        return emailNo;
    }
    
    public void setEmailNo(String emailNo)
    {
        this.emailNo = emailNo;
    }
    
    @Column(name = "ASSIGNED_NAME")
    public String getAssignedName()
    {
        return assignedName;
    }
    
    public void setAssignedName(String assignedName)
    {
        this.assignedName = assignedName;
    }
}
