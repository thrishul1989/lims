package com.todaysoft.lims.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.todaysoft.lims.system.model.vo.Coupon;
import com.todaysoft.lims.system.model.vo.CouponApply;
import com.todaysoft.lims.system.model.vo.CouponApplyDetail;
import com.todaysoft.lims.system.model.vo.Pagination;

public interface ICouponService
{
    Pagination<CouponApply> couponApplyPaging(CouponApply searcher, int pageNo, int pageSize);
    
    Pagination<Coupon> couponList(Coupon searcher, int pageNo, int pageSize);
    
    void deleteApply(String id);
    
    Integer applyAdd(CouponApply request);
    
 
    
    Integer getTotalAmountByApply(CouponApply couponApply);
    
    CouponApply getApply(String id);
    
    List<CouponApplyDetail> couponDetialByApply(CouponApply request);
    
    Integer reviewApply(CouponApply request);
    
    List<CouponApplyDetail> analyticalDetail(HttpServletRequest request, HttpServletResponse response);
}
