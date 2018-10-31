package com.todaysoft.lims.report.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.report.entity.system.ReportSystemCycleInfo;
import com.todaysoft.lims.utils.StringUtils;

public class CycleReportSearcher implements ISearcher<ReportSystemCycleInfo>
{

    private String taskId;
    
    private Integer pageNo;

    private Integer pageSize;
    
    private String productName;
    
    private String testingMethod;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append(" FROM ReportSystemCycleInfo c WHERE 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        hql.append(" AND c.taskId=:taskId ");
        paramNames.add("taskId");
        parameters.add(taskId);
        if(StringUtils.isNotEmpty(productName))
        {
            hql.append(" AND EXISTS(select op.id from OrderProduct op where op.orderId=c.orderId and op.product.name like :productName");
            if(StringUtils.isNotEmpty(testingMethod))
            {
                hql.append(" AND EXISTS(select pt.id from ProductTestingMethod pt where pt.productId = op.product.id and pt.testingMethodId=:methodId)");
                paramNames.add("methodId");
                parameters.add(testingMethod);
            }
            hql.append(")");
            paramNames.add("productName");
            parameters.add("%"+productName+"%");
        }
        
    }
    
    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
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

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getTestingMethod()
    {
        return testingMethod;
    }

    public void setTestingMethod(String testingMethod)
    {
        this.testingMethod = testingMethod;
    }

    @Override
    public Class<ReportSystemCycleInfo> getEntityClass()
    {
        return ReportSystemCycleInfo.class;
    }
    
}
