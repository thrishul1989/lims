package com.todaysoft.lims.system.model.vo.contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class ContractSettleBill extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Contract contract;
    
    private String code;
    
    private Integer orderCount;
    
    private Integer orderAmount;
    
    private int status;
    
    private String createId;
    
    private String createName;
    
    private Date createTime;
    
    private List<ContractSettleBillDetail> settleBillDetail = new ArrayList<ContractSettleBillDetail>();
    
    private String contractId;
    
    private ContractContent contractContent;
    
    public ContractContent getContractContent()
    {
        return contractContent;
    }
    
    public void setContractContent(ContractContent contractContent)
    {
        this.contractContent = contractContent;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Integer getOrderCount()
    {
        return orderCount;
    }
    
    public void setOrderCount(Integer orderCount)
    {
        this.orderCount = orderCount;
    }
    
    public Integer getOrderAmount()
    {
        return orderAmount;
    }
    
    public void setOrderAmount(Integer orderAmount)
    {
        this.orderAmount = orderAmount;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public List<ContractSettleBillDetail> getSettleBillDetail()
    {
        return settleBillDetail;
    }
    
    public void setSettleBillDetail(List<ContractSettleBillDetail> settleBillDetail)
    {
        this.settleBillDetail = settleBillDetail;
    }
    
}
