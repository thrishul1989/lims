package com.todaysoft.lims.sample.entity.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_COUPON")
@Builder(toBuilder = true)
@AllArgsConstructor
public class Coupon extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code;//申请编号+客户id+流水号
    
    private String userId;
    
    private Integer amount;//面额分
    
    private String validStartDate;//开始时间
    
    private String validEndDate;//结束时间
    
    private Integer consumed;//消费状态
    
    public Coupon()
    {
        
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
    
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "AMOUNT")
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "VALID_START_DATE")
    public String getValidStartDate()
    {
        return validStartDate;
    }
    
    public void setValidStartDate(String validStartDate)
    {
        this.validStartDate = validStartDate;
    }
    
    @Column(name = "VALID_END_DATE")
    public String getValidEndDate()
    {
        return validEndDate;
    }
    
    public void setValidEndDate(String validEndDate)
    {
        this.validEndDate = validEndDate;
    }
    
    @Column(name = "CONSUMED")
    public Integer getConsumed()
    {
        return consumed;
    }
    
    public void setConsumed(Integer consumed)
    {
        this.consumed = consumed;
    }
    
}
