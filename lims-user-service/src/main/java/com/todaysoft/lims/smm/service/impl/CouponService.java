package com.todaysoft.lims.smm.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.CouponApplyDetailSearcher;
import com.todaysoft.lims.smm.dao.searcher.CouponApplySearcher;
import com.todaysoft.lims.smm.dao.searcher.CouponSearcher;
import com.todaysoft.lims.smm.entity.Coupon;
import com.todaysoft.lims.smm.entity.CouponApply;
import com.todaysoft.lims.smm.entity.CouponApplyDetail;
import com.todaysoft.lims.smm.service.ICouponService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class CouponService implements ICouponService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<CouponApply> couponApplyPaging(CouponApplySearcher request)
    {
        
        return baseDaoSupport.find(request.toAuthQuery(), request.getPageNo(), request.getPageSize(), CouponApply.class);
    }
    
    @Override
    public Integer getTotalAmountByApply(CouponApplySearcher request)
    {
        
        List list = baseDaoSupport.find(List.class, "select sum(s.totalAmount) from CouponApplyDetail s where s.couponApply.id='" + request.getId() + "'");
        if (null != list && Collections3.isNotEmpty(list) && null != list.get(0))
        {
            
            return Integer.parseInt(list.get(0).toString());
            
        }
        
        return 0;
    }
    
    @Override
    public CouponApply getApply(String id)
    {
        // TODO Auto-generated method stub
        return baseDaoSupport.get(CouponApply.class, id);
    }
    
    @Override
    @Transactional
    public Integer reviewApply(CouponApply request)
    {
        int code = 00000001;//流水号
        CouponApply apply = getApply(request.getId());
        if ("2".equals(request.getState()))
        {//不通过
            apply.setState("2");
            apply.setReviewUserId(request.getReviewUserId());
            apply.setReviewTime(request.getReviewTime());
            baseDaoSupport.update(apply);
            return 1;
        }
        else if ("1".equals(request.getState()))
        {//通过
            apply.setState("1");
            
            apply.setReviewUserId(request.getReviewUserId());
            apply.setReviewTime(request.getReviewTime());
            baseDaoSupport.update(apply);
            //同时生成抵用券
            for (CouponApplyDetail detail : apply.getApplyDetailList())
            {
                for (int i = 0; i <= detail.getCouponCount() - 1; i++)
                {
                    Coupon coupon =
                        new Coupon().builder()
                            .code(apply.getCode() + detail.getUserId() + (code++))
                            .applyDetail(detail)
                            .userId(detail.getUserId())
                            .amount(detail.getAmount())
                            .validStartDate(detail.getValidStartDate())
                            .validEndDate(detail.getValidEndDate())
                            .consumed(0)
                            .createTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                            .build();
                    baseDaoSupport.insert(coupon);
                    
                }
                
            }
            
            return 1;
        }
        return 0;
    }
    
    @Override
    @Transactional
    public Integer applyAdd(CouponApplySearcher request)
    {
        CouponApply apply = new CouponApply();
        BeanUtils.copyProperties(request, apply);
        //先插入申请
        baseDaoSupport.insert(apply);
        switch (request.getTestingType())
        {
            case "1":
                apply.setCode("G" + apply.getId());
                break;
            case "2":
                apply.setCode("O" + apply.getId());
                break;
            case "3":
                apply.setCode("H" + apply.getId());
                break;
            case "4":
                apply.setCode("R" + apply.getId());
                break;
        
        }
        baseDaoSupport.update(apply);
        //插入申请明细
        for (CouponApplyDetailSearcher detail : request.getApplyDetailList())
        {
            CouponApplyDetail applydetail = new CouponApplyDetail();
            BeanUtils.copyProperties(detail, applydetail);
            applydetail.setCouponApply(apply);
            baseDaoSupport.insert(applydetail);
            
        }
        return 1;
    }
    
    @Override
    public Pagination<Coupon> couponList(CouponSearcher request)
    {
        return baseDaoSupport.find(request.toAuthQuery(), request.getPageNo(), request.getPageSize(), Coupon.class);
    }
    
    @Override
    @Transactional
    public void deleteApply(String id)
    {
        baseDaoSupport.execute("update CouponApply ca set ca.delFlag = 1 where id='" + id + "'");
        
    }
    
    @Override
    public List<CouponApplyDetail> couponDetialByApply(CouponApplySearcher request)
    {
        return baseDaoSupport.find(CouponApplyDetail.class, "from CouponApplyDetail cad where cad.couponApply.id='" + request.getId() + "'");
    }
    

    
}
