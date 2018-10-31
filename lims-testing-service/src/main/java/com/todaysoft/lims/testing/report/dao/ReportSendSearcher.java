package com.todaysoft.lims.testing.report.dao;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingReportSendCallBack;
import com.todaysoft.lims.utils.StringUtils;

public class ReportSendSearcher implements ISearcher<TestingReportSendCallBack>
{

    private String reportCode;
    
    private Integer status; 
    
    private int pageNo;
    
    private int pageSize;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingReportSendCallBack sc WHERE 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" ORDER BY sc.createTime DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(reportCode))
        {
            hql.append(" AND EXISTS(SELECT tr.id FROM TestingReport tr WHERE tr.id = sc.reportId AND tr.reportCode = :reportCode)");
            paramNames.add("reportCode");
            parameters.add(reportCode);
        }
        if(null != status)
        {
            hql.append(" AND sc.status = :status");
            paramNames.add("status");
            parameters.add(status);
        }
    }
    
    @Override
    public Class<TestingReportSendCallBack> getEntityClass()
    {
        return TestingReportSendCallBack.class;
    }

    public String getReportCode()
    {
        return reportCode;
    }

    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
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
    
}
