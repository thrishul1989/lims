package com.todaysoft.lims.testing.abnormal.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingAbnormalTaskView;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;

public class AbnormalTaskSearcher implements ISearcher<TestingAbnormalTaskView>
{
    private String marketingCenter;
    
    private String orderCode;
    
    private String taskName;
    
    private String productName;
    
    private String receivedSampleCode;
    
    private String receivedSampleName;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingTask t, TestingTaskResult r WHERE t.id = r.taskId AND r.result = :result");
        hql.append(" AND NOT EXISTS (SELECT sr.id FROM AbnormalSolveRecord sr WHERE sr.taskId = t.id)");
        hql.append(" AND EXISTS (SELECT shm.id FROM TestingScheduleHistoryMapping shm WHERE shm.taskId = t.id and shm.schedule.proccessState=0");
        
        if (!StringUtils.isEmpty(marketingCenter))
        {
            hql.append(" AND shm.order.type.id = :marketingCenter");
            names.add("marketingCenter");
            values.add(marketingCenter);
        }
        
        if (!StringUtils.isEmpty(taskName))
        {
            hql.append(" AND t.semantic = :taskName");
            names.add("taskName");
            values.add(taskName);
        }
        
        if (!StringUtils.isEmpty(orderCode))
        {
            hql.append(" AND shm.order.code LIKE :orderCode");
            names.add("orderCode");
            values.add("%" + orderCode + "%");
        }
        
        if (!StringUtils.isEmpty(productName))
        {
            hql.append(" AND shm.product.name LIKE :productName");
            names.add("productName");
            values.add("%" + productName + "%");
        }
        
        if (!StringUtils.isEmpty(receivedSampleCode))
        {
            hql.append(" AND t.inputSample.receivedSample.sampleCode LIKE :receivedSampleCode");
            names.add("receivedSampleCode");
            values.add("%" + receivedSampleCode + "%");
        }
        
        if (!StringUtils.isEmpty(receivedSampleName))
        {
            hql.append(" AND t.inputSample.receivedSample.sampleName LIKE :receivedSampleName");
            names.add("receivedSampleName");
            values.add("%" + receivedSampleName + "%");
        }
        
        hql.append(")");
        hql.append(" ORDER BY t.endTime");
        
        names.add("result");
        values.add(TestingTaskResult.RESULT_FAILURE_REPORT);
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    public NamedQueryer toViewQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingAbnormalTaskView t WHERE 1=1 ");
        
        if (!StringUtils.isEmpty(marketingCenter))
        {
            hql.append(" AND t.orderType = :marketingCenter");
            names.add("marketingCenter");
            values.add(marketingCenter);
        }
        
        if (!StringUtils.isEmpty(taskName))
        {
            hql.append(" AND t.semantic = :taskName");
            names.add("taskName");
            values.add(taskName);
        }
        
        if (!StringUtils.isEmpty(orderCode))
        {
            hql.append(" AND t.orderCode LIKE :orderCode");
            names.add("orderCode");
            values.add("%" + orderCode + "%");
        }
        
        if (!StringUtils.isEmpty(productName))
        {
            hql.append(" AND t.productName LIKE :productName");
            names.add("productName");
            values.add("%" + productName + "%");
        }
        
        if (!StringUtils.isEmpty(receivedSampleCode))
        {
            hql.append(" AND t.sampleCode LIKE :receivedSampleCode");
            names.add("receivedSampleCode");
            values.add("%" + receivedSampleCode + "%");
        }
        
        if (!StringUtils.isEmpty(receivedSampleName))
        {
            hql.append(" AND t.sampleName LIKE :receivedSampleName");
            names.add("receivedSampleName");
            values.add("%" + receivedSampleName + "%");
        }
        
        hql.append(" ORDER BY t.endTime");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    @Override
    public Class<TestingAbnormalTaskView> getEntityClass()
    {
        return TestingAbnormalTaskView.class;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }
    
    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
    }
    
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    
    public String getReceivedSampleName()
    {
        return receivedSampleName;
    }
    
    public void setReceivedSampleName(String receivedSampleName)
    {
        this.receivedSampleName = receivedSampleName;
    }
    
}
