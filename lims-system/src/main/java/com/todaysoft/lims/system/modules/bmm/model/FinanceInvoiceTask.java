package com.todaysoft.lims.system.modules.bmm.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Transient;

public class FinanceInvoiceTask
{
    public static final Integer STATUS_TODO = 1;
    
    public static final Integer STATUS_CANDO = 2;
    
    public static final Integer STATUS_ALREADY = 3;
    
    public static final String INSTITUTION_INSPECTION = "0";
    
    public static final String INSTITUTION_COMPANY = "1";

    public static final String INSTITUTION_CQINSPECTION = "3";

    public static final String INSTITUTION_CQCOMPANY = "2";
    
    private String category;//任务类型：1-默认开票 2-申请开票
    
    private String recordKey;//默认开票订单ID/申请开票记录ID
    
    private String institution;//开票机构
    
    private BigDecimal amount;//开票金额
    
    private Integer status;//状态：1-待开票 2-可开票 3-已开票
    
    private List<InvoiceInfo> infoList;//开票信息
    
    private String invoiceTitle;
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getRecordKey()
    {
        return recordKey;
    }
    
    public void setRecordKey(String recordKey)
    {
        this.recordKey = recordKey;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public BigDecimal getAmount()
    {
        return amount;
    }
    
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public List<InvoiceInfo> getInfoList()
    {
        return infoList;
    }
    
    public void setInfoList(List<InvoiceInfo> infoList)
    {
        this.infoList = infoList;
    }
    
    public static String getInstitutionInspection()
    {
        return INSTITUTION_INSPECTION;
    }
    
    public static String getInstitutionCompany()
    {
        return INSTITUTION_COMPANY;
    }
}
