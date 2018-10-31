package com.todaysoft.lims.report.dao.searcher;


import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.report.entity.ReportSheetSample;
import com.todaysoft.lims.report.entity.ReportSheetSampleSuccessRate;

import java.util.ArrayList;
import java.util.List;

public class SheetSampleSuccessRateSearcher implements ISearcher<ReportSheetSampleSuccessRate>
{
    private String reportTaskId;

    private Integer pageNo;

    private Integer pageSize;


    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append(" FROM ReportSheetSampleSuccessRate t WHERE t.reportTaskId=:reportTaskId ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        paramNames.add("reportTaskId");
        parameters.add(reportTaskId);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Class<ReportSheetSampleSuccessRate> getEntityClass()
    {
        return ReportSheetSampleSuccessRate.class;
    }

    public String getReportTaskId() {
        return reportTaskId;
    }

    public void setReportTaskId(String reportTaskId) {
        this.reportTaskId = reportTaskId;
    }
}
