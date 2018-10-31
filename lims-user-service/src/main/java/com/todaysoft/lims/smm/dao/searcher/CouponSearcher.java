package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.Coupon;
import com.todaysoft.lims.utils.DateUtils;

public class CouponSearcher extends IDataAuthoritySearcher implements ISearcher<Coupon>
{
    
    private String code;//申请编号+客户id+流水号
    
    private CouponApplyDetailSearcher applyDetail;
    
    private String userId;
    
    private Integer amount;//面额分
    
    private String validStartDate;//开始时间
    
    private String validEndDate;//结束时间
    
    private Integer consumed;//消费状态
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private boolean undueSearch; //判断是否要过滤过期的
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public CouponApplyDetailSearcher getApplyDetail()
    {
        return applyDetail;
    }
    
    public void setApplyDetail(CouponApplyDetailSearcher applyDetail)
    {
        this.applyDetail = applyDetail;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
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
    
    public Integer getConsumed()
    {
        return consumed;
    }
    
    public void setConsumed(Integer consumed)
    {
        this.consumed = consumed;
    }
    
    public boolean isUndueSearch()
    {
        return undueSearch;
    }
    
    public void setUndueSearch(boolean undueSearch)
    {
        this.undueSearch = undueSearch;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM Coupon s where 1=1 ");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addFilter(hql, names, values);
        hql.append(" ORDER BY s.createTime DESC");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(code))
        {
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        
        if (!StringUtils.isEmpty(userId))
        {
            hql.append(" AND s.userId =:userId");
            paramNames.add("userId");
            parameters.add(userId);
        }
        
        if (undueSearch)
        {
            hql.append(" AND s.validStartDate <=:startDate AND s.validEndDate >=:endDate");
            paramNames.add("startDate");
            parameters.add(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
            paramNames.add("endDate");
            parameters.add(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
        }
        
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(validStartDate))
        {
            hql.append(" AND s.validStartDate > :startDate");
            paramNames.add("startDate");
            parameters.add(validStartDate);
        }
        
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(validEndDate))
        {
            hql.append(" AND s.validEndDate < :endDate");
            paramNames.add("endDate");
            parameters.add(validEndDate);
        }
        
        if (!StringUtils.isEmpty(consumed))
        {
            if (2 == consumed)
            {
                hql.append(" AND s.validEndDate < CURRENT_DATE AND s.consumed = 0");
            }
            else
            {
                hql.append(" AND s.consumed = :consumed");
                paramNames.add("consumed");
                parameters.add(consumed);
            }
            
        }
        
        if (null != applyDetail && null != applyDetail.getCouponApply())
        {
            if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(applyDetail.getCouponApply().getCreateName()))
            {
                hql.append(" AND s.userId in (select cr.customer.id from  CustomerRelation cr where cr.business.id in( select bi.id from BusinessInfo "
                    + "bi where bi.realName LIKE :createName))");
                paramNames.add("createName");
                parameters.add("%" + applyDetail.getCouponApply().getCreateName() + "%");
            }
            
        }
        if (null != applyDetail)
        {
            if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(applyDetail.getCustomerName()))
            {
                hql.append(" AND s.applyDetail.userId in (select c.id from Customer c where c.realName LIKE :customerName)");
                paramNames.add("customerName");
                parameters.add("%" + applyDetail.getCustomerName() + "%");
            }
            
        }
        
        if (null != applyDetail && null != applyDetail.getCouponApply())
        {
            if (!StringUtils.isEmpty(applyDetail.getCouponApply().getTestingType()))
            {
                hql.append(" AND s.applyDetail.couponApply.testingType = :testingType");
                paramNames.add("testingType");
                parameters.add(applyDetail.getCouponApply().getTestingType());
            }
            
        }
        
    }
    
    @Override
    public Class<Coupon> getEntityClass()
    {
        // TODO Auto-generated method stub
        return Coupon.class;
    }
    
    @Override
    public NamedQueryer toAuthQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM Coupon s where 1=1 ");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addAuthFilter(hql, names, values, "applyDetail.couponApply.userId");
        addFilter(hql, names, values);
        hql.append(" ORDER BY s.createTime DESC");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
}
