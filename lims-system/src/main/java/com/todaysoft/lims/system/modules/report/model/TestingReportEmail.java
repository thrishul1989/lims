package com.todaysoft.lims.system.modules.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.order.Order;


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

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getAssignId()
    {
        return assignId;
    }

    public void setAssignId(String assignId)
    {
        this.assignId = assignId;
    }

    public String getAssignName()
    {
        return assignName;
    }

    public void setAssignName(String assignName)
    {
        this.assignName = assignName;
    }

    public Date getAssignTime()
    {
        return assignTime;
    }

    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }

    public String getAssignedId()
    {
        return assignedId;
    }

    public void setAssignedId(String assignedId)
    {
        this.assignedId = assignedId;
    }

    public String getAssignedName()
    {
        return assignedName;
    }

    public void setAssignedName(String assignedName)
    {
        this.assignedName = assignedName;
    }

    public Date getEmailTime()
    {
        return emailTime;
    }

    public void setEmailTime(Date emailTime)
    {
        this.emailTime = emailTime;
    }

    public String getEmailNo()
    {
        return emailNo;
    }

    public void setEmailNo(String emailNo)
    {
        this.emailNo = emailNo;
    }

    public String getEmailType()
    {
        return emailType;
    }

    public void setEmailType(String emailType)
    {
        this.emailType = emailType;
    }

    public String getEmailName()
    {
        return emailName;
    }

    public void setEmailName(String emailName)
    {
        this.emailName = emailName;
    }

    public String getEmailPhone()
    {
        return emailPhone;
    }

    public void setEmailPhone(String emailPhone)
    {
        this.emailPhone = emailPhone;
    }

    public String getEmailContent()
    {
        return emailContent;
    }

    public void setEmailContent(String emailContent)
    {
        this.emailContent = emailContent;
    }
    
 
    
}
