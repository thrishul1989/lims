package com.todaysoft.lims.order.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;

import com.todaysoft.lims.order.mybatis.parameter.BaseOrderSearcher;
import com.todaysoft.lims.order.mybatis.parameter.ScheduleMonitorOrderSearcher;
import com.todaysoft.lims.order.mybatis.parameter.TestingMonitorOrderSearcher;
import com.todaysoft.lims.order.request.OrderSearchRequest;

public class OrderSearchRequestAdapter
{
    private OrderSearchRequest request;
    
    public OrderSearchRequestAdapter(OrderSearchRequest request)
    {
        if (null == request)
        {
            throw new IllegalArgumentException();
        }
        
        this.request = request;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T adapt(Class<T> clazz)
    {
        if (clazz.equals(TestingMonitorOrderSearcher.class))
        {
            TestingMonitorOrderSearcher searcher = new TestingMonitorOrderSearcher();
            requestForBaseOrderSearcher(searcher);
            searcher.setSampleCode(request.getSampleCode());
            return (T)searcher;
        }
        else if (clazz.equals(ScheduleMonitorOrderSearcher.class))
        {
            ScheduleMonitorOrderSearcher searcher = new ScheduleMonitorOrderSearcher();
            requestForBaseOrderSearcher(searcher);
            searcher.setSampleCode(request.getSampleCode());
            searcher.setPostponedNode(request.getPostponedNode());
            searcher.setProjectManager(request.getProjectManager());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                if(null != request.getPlannedFinishStartDate() && "" != request.getPlannedFinishStartDate())
                {
                    searcher.setPlannedFinishStartDate(sdf.parse(request.getPlannedFinishStartDate()));
                }
                if(null != request.getPlannedFinishEndDate() && "" != request.getPlannedFinishEndDate())
                {
                    searcher.setPlannedFinishEndDate(sdf.parse(request.getPlannedFinishEndDate()));
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            
            Date plannedFinishStartDate = searcher.getPlannedFinishStartDate();
            Date plannedFinishEndDate = searcher.getPlannedFinishEndDate();
            
            if (null != plannedFinishStartDate)
            {
                searcher.setPlannedFinishStartDate(DateUtils.truncate(plannedFinishStartDate, Calendar.DATE));
            }
            
            if (null != plannedFinishEndDate)
            {
                plannedFinishEndDate = DateUtils.truncate(plannedFinishEndDate, Calendar.DATE);
                plannedFinishEndDate = DateUtils.addDays(plannedFinishEndDate, 1);
                searcher.setPlannedFinishEndDate(plannedFinishEndDate);
            }
            
            return (T)searcher;
        }
        
        throw new UnsupportedOperationException();
    }
    
    private void requestForBaseOrderSearcher(BaseOrderSearcher searcher)
    {
        BeanUtils.copyProperties(request, searcher);
        
        if (null != request.getPageNo() && null != request.getPageSize())
        {
            int offset = (request.getPageNo() - 1) * request.getPageSize();
            searcher.setOffset(offset);
            searcher.setLimit(request.getPageSize());
        }
        
        Date submitStartDate = searcher.getSubmitStartDate();
        Date submitEndDate = searcher.getSubmitEndDate();
        
        if (null != submitStartDate)
        {
            searcher.setSubmitStartDate(DateUtils.truncate(submitStartDate, Calendar.DATE));
        }
        
        if (null != submitEndDate)
        {
            submitEndDate = DateUtils.truncate(submitEndDate, Calendar.DATE);
            submitEndDate = DateUtils.addDays(submitEndDate, 1);
            searcher.setSubmitEndDate(submitEndDate);
        }
        
        searcher.setFilter(request.getFilter());
    }
}
