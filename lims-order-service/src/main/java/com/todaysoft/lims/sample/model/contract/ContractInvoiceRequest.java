package com.todaysoft.lims.sample.model.contract;

import java.util.Date;
import java.util.List;

public class ContractInvoiceRequest
{
    private List<ContractInvoiceDetail> invoiceDetail;
    
    private String creatorId;//创建人ID
    
    private String creatorName;//创建人姓名
    
    private Date createTime;//创建时间
    
    private String remark;
    
    private Integer status;
    
    private String invoiceApplyId;
    
    private String contractId;
    
    public String getInvoiceApplyId()
    {
        return invoiceApplyId;
    }
    
    public void setInvoiceApplyId(String invoiceApplyId)
    {
        this.invoiceApplyId = invoiceApplyId;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public List<ContractInvoiceDetail> getInvoiceDetail()
    {
        return invoiceDetail;
    }
    
    public void setInvoiceDetail(List<ContractInvoiceDetail> invoiceDetail)
    {
        this.invoiceDetail = invoiceDetail;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
