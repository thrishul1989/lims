package com.todaysoft.lims.smm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.CouponApplySearcher;
import com.todaysoft.lims.smm.dao.searcher.CouponSearcher;
import com.todaysoft.lims.smm.entity.Coupon;
import com.todaysoft.lims.smm.entity.CouponApply;
import com.todaysoft.lims.smm.entity.CouponApplyDetail;
import com.todaysoft.lims.smm.service.ICouponService;

@RestController
@RequestMapping("/smm/coupon")
public class CouponController
{
    
    
    @Autowired
    private ICouponService couponService;
    
    @RequestMapping(value = "/couponApplyPaging")
    public Pagination<CouponApply> couponApplyPaging(@RequestBody CouponApplySearcher request)
    {
        return couponService.couponApplyPaging(request);
    }
    
    @RequestMapping(value = "/couponList")
    public Pagination<Coupon> couponList(@RequestBody CouponSearcher request)
    {
        return couponService.couponList(request);
    }
    
    @RequestMapping(value = "/couponDetialByApply")
    public List<CouponApplyDetail> couponDetialByApply(@RequestBody CouponApplySearcher request)
    {
        return couponService.couponDetialByApply(request);
    }
    
    
    
    
    
    
    @RequestMapping(value = "/getTotalAmountByApply")
    public Integer getTotalAmountByApply(@RequestBody CouponApplySearcher request)
    {
        return couponService.getTotalAmountByApply(request);
    }
    
    
    @RequestMapping(value = "/applyAdd",method = RequestMethod.POST)
    public Integer applyAdd(@RequestBody CouponApplySearcher request)
    {
        return couponService.applyAdd(request);
    }
    
    
    @RequestMapping(value = "/getApply/{id}", method = RequestMethod.GET)
    public CouponApply getApply(@PathVariable String id)
    {
        CouponApply c = couponService.getApply(id);
        return c;
    }
    
  
    
    
    
    
    
    
    @RequestMapping(value = "/reviewApply")
    public Integer reviewApply(@RequestBody CouponApply request)
    {
        return couponService.reviewApply(request);
    }
    
    @RequestMapping(value = "/deleteApply/{id}", method = RequestMethod.DELETE)
    public void deleteApply(@PathVariable String id)
    {
        couponService.deleteApply(id);
    }
    
    
    
}
