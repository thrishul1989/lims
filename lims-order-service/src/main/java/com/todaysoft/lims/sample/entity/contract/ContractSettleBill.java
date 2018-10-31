package com.todaysoft.lims.sample.entity.contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT_SETTLE_BILL")
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
    
    @Transient
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTRACT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    @Column(name = "CREATE_ID")
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    @Column(name = "CREATE_NAME")
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
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
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "ORDER_COUNT")
    public Integer getOrderCount()
    {
        return orderCount;
    }
    
    public void setOrderCount(Integer orderCount)
    {
        this.orderCount = orderCount;
    }
    
    @Column(name = "ORDER_AMOUNT")
    public Integer getOrderAmount()
    {
        return orderAmount;
    }
    
    public void setOrderAmount(Integer orderAmount)
    {
        this.orderAmount = orderAmount;
    }
    
    @Column(name = "STATUS")
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    @OneToMany(mappedBy = "settleBill", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ContractSettleBillDetail> getSettleBillDetail()
    {
        return settleBillDetail;
    }
    
    public void setSettleBillDetail(List<ContractSettleBillDetail> settleBillDetail)
    {
        this.settleBillDetail = settleBillDetail;
    }
    
}
