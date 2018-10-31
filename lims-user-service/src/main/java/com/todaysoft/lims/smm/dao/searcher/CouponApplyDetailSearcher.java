package com.todaysoft.lims.smm.dao.searcher;

import java.util.List;

import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.Coupon;
import com.todaysoft.lims.smm.entity.CouponApply;
import com.todaysoft.lims.smm.entity.CouponApplyDetail;

public class CouponApplyDetailSearcher extends IDataAuthoritySearcher implements ISearcher<CouponApplyDetail>
{
    
    private CouponApplySearcher couponApply;
    public CouponApplySearcher getCouponApply()
    {
        return couponApply;
    }
    public void setCouponApply(CouponApplySearcher couponApply)
    {
        this.couponApply = couponApply;
    }
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public String getValidStartDate()
    {
        return validStartDate;
    }
    public void setValidStartDate(String validStartDate)
    {
        this.validStartDate = validStartDate;
    }
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
    public List<Coupon> getCouponList()
    {
        return couponList;
    }
    public void setCouponList(List<Coupon> couponList)
    {
        this.couponList = couponList;
    }
    private String userId;
    private String validStartDate;
    private String validEndDate;
    private Integer amount;
    private Integer couponCount;
    private Integer totalAmount;
    private List<Coupon> couponList;
    private String customerName;//额外字段
    private String customerMobile;//额外字段
    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    public String getCustomerMobile()
    {
        return customerMobile;
    }
    public void setCustomerMobile(String customerMobile)
    {
        this.customerMobile = customerMobile;
    }
    @Override
    public NamedQueryer toQuery()
    {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Class<CouponApplyDetail> getEntityClass()
    {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public NamedQueryer toAuthQuery()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
