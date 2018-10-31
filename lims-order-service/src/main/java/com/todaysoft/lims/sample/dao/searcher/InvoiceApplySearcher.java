package com.todaysoft.lims.sample.dao.searcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.InvoiceApply;
import com.todaysoft.lims.sample.util.DateUtil;
import com.todaysoft.lims.utils.StringUtils;

public class InvoiceApplySearcher implements ISearcher<InvoiceApply>
{
    private Integer status;//状态：1-审核中，2-未通过，3-已开票，4-已寄送
    
    private String testingType;//营销中心
    
    private String creatorId;//申请人id
    
    private String customerId;//客户ID
    
    private String applyTimeStart;//申请时间
    
    private String applyTimeEnd;
    
    private int pageNo;
    
    private int pageSize;
    
    private String code;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM InvoiceApply ia WHERE 1 = 1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" ORDER BY ia.status, ia.applyTime");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND ia.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
            
        }
        if (StringUtils.isNotEmpty(status))
        {
            hql.append(" AND ia.status = :status");
            paramNames.add("status");
            parameters.add(status);
            
        }
        if (StringUtils.isNotEmpty(testingType))
        {
            hql.append(" AND ia.creatorId in (select a.userId from APPSaleman a where a.testingType = :testingType)");
            paramNames.add("testingType");
            parameters.add(testingType);
            
        }
        if (StringUtils.isNotEmpty(creatorId))
        {
            hql.append(" AND ia.creatorId in (select bi.id from BusinessInfo bi where bi.realName LIKE :creatorId)");
            paramNames.add("creatorId");
            parameters.add("%" + creatorId + "%");
            
        }
        if (StringUtils.isNotEmpty(customerId))
        {
            hql.append(" AND ia.customerId in (select c.id from Customer c where c.realName LIKE :customerId)");
            paramNames.add("customerId");
            parameters.add("%" + customerId + "%");
            
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            if (StringUtils.isNotEmpty(applyTimeStart))
            {
                hql.append(" AND ia.applyTime > :applyTimeStart");
                paramNames.add("applyTimeStart");
                parameters.add(DateUtil.toStartDate(sdf.parse(applyTimeStart)));
                
            }
            if (StringUtils.isNotEmpty(applyTimeEnd))
            {
                hql.append(" AND ia.applyTime < :applyTimeEnd");
                paramNames.add("applyTimeEnd");
                parameters.add(DateUtil.toEndDate(sdf.parse(applyTimeEnd)));
                
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public Class<InvoiceApply> getEntityClass()
    {
        return InvoiceApply.class;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public String getApplyTimeStart()
    {
        return applyTimeStart;
    }
    
    public void setApplyTimeStart(String applyTimeStart)
    {
        this.applyTimeStart = applyTimeStart;
    }
    
    public String getApplyTimeEnd()
    {
        return applyTimeEnd;
    }
    
    public void setApplyTimeEnd(String applyTimeEnd)
    {
        this.applyTimeEnd = applyTimeEnd;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
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
}
