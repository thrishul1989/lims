package com.todaysoft.lims.system.model.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.utils.excel.annotation.ExcelField;


@Builder(toBuilder = true)
@AllArgsConstructor
public class CouponApplyDetail 
{
    private String id;
    private CouponApply couponApply;
    private String userId;
    private String validStartDate;
    private String validEndDate;
    private Integer amount;
    private Integer couponCount;
    private Integer totalAmount;
    private String customerName;//额外字段
    private String customerMobile;//额外字段
    private String amount_toyuan;//分转元额外字段
    private String totalAmount_toyuan;//分转元额外字段
    private String businessName;//额外字段
    
   public  CouponApplyDetail(){
        
        
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

 

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @ExcelField(title = "有效期起", align = 2, sort = 30)
    public String getValidStartDate()
    {
        return validStartDate;
    }

    public void setValidStartDate(String validStartDate)
    {
        this.validStartDate = validStartDate;
    }

    @ExcelField(title = "有效期截止", align = 2, sort = 40)
    public String getValidEndDate()
    {
        return validEndDate;
    }

    public void setValidEndDate(String validEndDate)
    {
        this.validEndDate = validEndDate;
    }
  
    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    @ExcelField(title = "张数", align = 2, sort = 60)
    public Integer getCouponCount()
    {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount)
    {
        this.couponCount = couponCount;
    }

    public Integer getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public CouponApply getCouponApply()
    {
        return couponApply;
    }

    public void setCouponApply(CouponApply couponApply)
    {
        this.couponApply = couponApply;
    }

    @ExcelField(title = "姓名", align = 2, sort = 10)
    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

  

 

    public String getTotalAmount_toyuan()
    {
        return totalAmount_toyuan;
    }

    public void setTotalAmount_toyuan(String totalAmount_toyuan)
    {
        this.totalAmount_toyuan = totalAmount_toyuan;
    }
    @ExcelField(title = "面额(元)", align = 2, sort = 50)
    public String getAmount_toyuan()
    {
        return amount_toyuan;
    }

    public void setAmount_toyuan(String amount_toyuan)
    {
        this.amount_toyuan = amount_toyuan;
    }
    @ExcelField(title = "手机号", align = 2, sort = 20)
    public String getCustomerMobile()
    {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile)
    {
        this.customerMobile = customerMobile;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }
    
  
    
}
