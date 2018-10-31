package com.todaysoft.lims.smm.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.CouponApplySearcher;
import com.todaysoft.lims.smm.dao.searcher.CouponSearcher;
import com.todaysoft.lims.smm.entity.Coupon;
import com.todaysoft.lims.smm.entity.CouponApply;
import com.todaysoft.lims.smm.entity.CouponApplyDetail;

public interface ICouponService
{
    
    Pagination<CouponApply> couponApplyPaging(CouponApplySearcher request);
    
    Pagination<Coupon> couponList(CouponSearcher request);
    
    List<CouponApplyDetail> couponDetialByApply(CouponApplySearcher request);
    
    
    Integer getTotalAmountByApply(CouponApplySearcher request);
    Integer applyAdd(CouponApplySearcher request);
    
    CouponApply getApply(String id);
    
  
    Integer reviewApply(CouponApply request);
    
    void deleteApply(String id);
    
   
    
    
}
