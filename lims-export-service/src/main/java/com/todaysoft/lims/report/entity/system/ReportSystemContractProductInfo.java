package com.todaysoft.lims.report.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_SYSTEM_CONTRACT_PRODUCT_INFO")
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
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    @JsonIgnore
    public ReportSystemContractInfo getContractInfo()
    {
        return contractInfo;
    }
    public void setContractInfo(ReportSystemContractInfo contractInfo)
    {
        this.contractInfo = contractInfo;
    }
    
    @Column(name = "PRODUCT_NAME")
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    @Column(name = "CONTRACT_PRICE")
    public String getPrice()
    {
        return price;
    }
    public void setPrice(String price)
    {
        this.price = price;
    }
    
    @Column(name = "SIGN_COUNT")
    public String getCount()
    {
        return count;
    }
    public void setCount(String count)
    {
        this.count = count;
    }
    
    @Column(name = "SIGN_AMOUNT")
    public String getAmount()
    {
        return amount;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "REQUIREMENT")
    public String getRequirement()
    {
        return requirement;
    }
    public void setRequirement(String requirement)
    {
        this.requirement = requirement;
    }
    
}
