package com.todaysoft.lims.smm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.smm.entity.CouponApply.CouponApplyBuilder;

@Entity
@Table(name = "LIMS_COUPON_APPLY_DETAIL")
@Builder(toBuilder = true)
@AllArgsConstructor
public class CouponApplyDetail extends UuidKeyEntity
{
    
    private CouponApply couponApply;
    private String userId;
    private String validStartDate;
    private String validEndDate;
    private Integer amount;
    private Integer couponCount;
    private Integer totalAmount;
    private List<Coupon> couponList;
    
   public CouponApplyDetail(){
        
        
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
    @Column(name = "AMOUNT")
    public Integer getAmount()
    {
        return amount;
    }
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    @Column(name = "COUPON_COUNT")
    public Integer getCouponCount()
    {
        return couponCount;
    }
    public void setCouponCount(Integer couponCount)
    {
        this.couponCount = couponCount;
    }
    @Column(name = "TOTAL_AMOUNT")
    public Integer getTotalAmount()
    {
        return totalAmount;
    }
    public void setTotalAmount(Integer totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPON_APPLY_ID")
    @NotFound(action=NotFoundAction.IGNORE)
 
    public CouponApply getCouponApply()
    {
        return couponApply;
    }


    public void setCouponApply(CouponApply couponApply)
    {
        this.couponApply = couponApply;
    }


    @OneToMany(mappedBy = "applyDetail", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    public List<Coupon> getCouponList()
    {
        return couponList;
    }


    public void setCouponList(List<Coupon> couponList)
    {
        this.couponList = couponList;
    }
    
}
