package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.sampleBack.SampleBackApply;
import com.todaysoft.lims.utils.StringUtils;

public class SampleBackApplySearcher implements ISearcher<SampleBackApply>
{
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String status;//返样状态
    
    private Date applyStartTime;//'申请时间起',
    
    private Date applyEndTime;//'申请时间止',
    
    private String applyName;//申请人
    
    private String backChannel;//返回途径
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SampleBackApply s  where 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" order by s.status,  s.applyDate DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<SampleBackApply> getEntityClass()
    {
        return SampleBackApply.class;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(applyName))
        {
            hql.append(" AND s.applyName LIKE:applyName");
            paramNames.add("applyName");
            parameters.add("%" + applyName + "%");
        }
        
        if (StringUtils.isNotEmpty(backChannel))
        {
            hql.append(" AND s.backChannel=:backChannel");
            paramNames.add("backChannel");
            parameters.add(backChannel);
        }
        
        if (StringUtils.isNotEmpty(status))
        {
            hql.append(" AND s.status=:status");
            paramNames.add("status");
            parameters.add(status);
        }
        if (StringUtils.isNotEmpty(applyStartTime))
        {
            
            hql.append(" AND s.applyDate >=:applyStartTime");
            paramNames.add("applyStartTime");
            parameters.add(applyStartTime);
            
        }
        
        if (StringUtils.isNotEmpty(applyEndTime))
        {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(applyEndTime);
            calendar.add(calendar.DATE, 1);
            hql.append(" AND s.applyDate <:applyEndTime");
            paramNames.add("applyEndTime");
            parameters.add(calendar.getTime());
        }
    }
    
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
    
    public Date getApplyStartTime()
    {
        return applyStartTime;
    }
    
    public void setApplyStartTime(Date applyStartTime)
    {
        this.applyStartTime = applyStartTime;
    }
    
    public Date getApplyEndTime()
    {
        return applyEndTime;
    }
    
    public void setApplyEndTime(Date applyEndTime)
    {
        this.applyEndTime = applyEndTime;
    }
    
    public String getApplyName()
    {
        return applyName;
    }
    
    public void setApplyName(String applyName)
    {
        this.applyName = applyName;
    }
    
    public String getBackChannel()
    {
        return backChannel;
    }
    
    public void setBackChannel(String backChannel)
    {
        this.backChannel = backChannel;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
}
