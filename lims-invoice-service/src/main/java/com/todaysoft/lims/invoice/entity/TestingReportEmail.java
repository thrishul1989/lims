package com.todaysoft.lims.invoice.entity;

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
    private Order order;
    
    private Product product;
    
    private Date createTime;
    
    private String status;//1待分配 2 已分配 3 已寄送 4不寄送
    
    private String assignId;
    
    private String assignName;
    
    private Date assignTime;
    
    private String assignedId;
    
    private String assignedName;
    
    private Date emailTime;
    
    private String emailNo;
    
    private String emailType;//快递类型快递类型 0 人工物流 1.。2.。。
    
    private String emailName;
    
    private String emailPhone;
    
    private String emailContent;
    
    @Column(name = "ASSIGNED_NAME")
    public String getAssignedName()
    {
        return assignedName;
    }
    
    public void setAssignedName(String assignedName)
    {
        this.assignedName = assignedName;
    }
    
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
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
    
    @Column(name = "STATUS")
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Column(name = "ASSIGN_ID")
    public String getAssignId()
    {
        return assignId;
    }
    
    public void setAssignId(String assignId)
    {
        this.assignId = assignId;
    }
    
    @Column(name = "ASSIGN_NAME")
    public String getAssignName()
    {
        return assignName;
    }
    
    public void setAssignName(String assignName)
    {
        this.assignName = assignName;
    }
    
    @Column(name = "ASSIGN_TIME")
    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }
    
    @Column(name = "ASSIGNED_ID")
    public String getAssignedId()
    {
        return assignedId;
    }
    
    public void setAssignedId(String assignedId)
    {
        this.assignedId = assignedId;
    }
    
    @Column(name = "EMAIL_TIME")
    public Date getEmailTime()
    {
        return emailTime;
    }
    
    public void setEmailTime(Date emailTime)
    {
        this.emailTime = emailTime;
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
    
    @Column(name = "EMAIL_TYPE")
    public String getEmailType()
    {
        return emailType;
    }
    
    public void setEmailType(String emailType)
    {
        this.emailType = emailType;
    }
    
    @Column(name = "EMAIL_NAME")
    public String getEmailName()
    {
        return emailName;
    }
    
    public void setEmailName(String emailName)
    {
        this.emailName = emailName;
    }
    
    @Column(name = "EMAIL_PHONE")
    public String getEmailPhone()
    {
        return emailPhone;
    }
    
    public void setEmailPhone(String emailPhone)
    {
        this.emailPhone = emailPhone;
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
    
}
