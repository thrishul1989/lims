package com.todaysoft.lims.system.model.vo.contract.reportForm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

public class ReportSystemContractProductInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = -3046861437857594596L;
    private String taskId;
    private ReportSystemContractInfo contractInfo;
    private String productName;//产品名称
    private String price;//单价
    private String count;//数据
    private String amount;//价格
    private String requirement;//服务要求
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public ReportSystemContractInfo getContractInfo()
    {
        return contractInfo;
    }
    public void setContractInfo(ReportSystemContractInfo contractInfo)
    {
        this.contractInfo = contractInfo;
    }
    
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getPrice()
    {
        return price;
    }
    public void setPrice(String price)
    {
        this.price = price;
    }
    
    public String getCount()
    {
        return count;
    }
    public void setCount(String count)
    {
        this.count = count;
    }
    
    public String getAmount()
    {
        return amount;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    public String getRequirement()
    {
        return requirement;
    }
    public void setRequirement(String requirement)
    {
        this.requirement = requirement;
    }
    
}
