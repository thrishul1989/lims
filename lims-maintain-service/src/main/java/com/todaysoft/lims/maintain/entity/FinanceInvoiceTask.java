package com.todaysoft.lims.maintain.entity;

import java.math.BigDecimal;
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
@Table(name = "LIMS_FINANCE_INVOICE_TASK")
public class FinanceInvoiceTask extends UuidKeyEntity
{
    private static final long serialVersionUID = 3702383437102420472L;
    
    public static final String CATEGORY_AUTO = "1";
    
    public static final Integer STATUS_TODO = 1;
    
    public static final Integer STATUS_CANDO = 2;
    
    public static final Integer STATUS_ALREADY = 3;
    
    private String category;//任务类型：1-默认开票 2-申请开票
    
    private String recordKey;//默认开票订单ID/申请开票记录ID
    
    private String institution;//开票机构
    
    private BigDecimal amount;//开票金额
    
    private Integer status;//状态：1-待开票 2-可开票 3-已开票
    
    private Date updateTime;//处理时间
    
    @Column(name = "CATEGORY")
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    @Column(name = "RECORD_KEY")
    public String getRecordKey()
    {
        return recordKey;
    }
    
    public void setRecordKey(String recordKey)
    {
        this.recordKey = recordKey;
    }
    
    @Column(name = "INSTITUTION")
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    @Column(name = "AMOUNT")
    public BigDecimal getAmount()
    {
        return amount;
    }
    
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
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
    
}
