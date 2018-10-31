package com.todaysoft.lims.report.dao.searcher;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.report.entity.system.ReprotSystemCustomerInfo;
import com.todaysoft.lims.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerReportSearcher implements ISearcher<ReprotSystemCustomerInfo>
{
    private String taskId;

    private Integer pageNo;

    private Integer pageSize;

    private String status;

    private String currentName;

    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append(" FROM ReprotSystemCustomerInfo c WHERE 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCustomerStatusFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

    private void addCustomerStatusFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        hql.append(" AND c.taskId=:taskId ");
        paramNames.add("taskId");
        parameters.add(taskId);
        if (!StringUtils.isEmpty(currentName)) {
            hql.append(" AND c.currentName LIKE :currentName ");
            paramNames.add("currentName");
            parameters.add('%'+currentName+'%');
        }
    }

    @Override
    public Class<ReprotSystemCustomerInfo> getEntityClass() {
        return ReprotSystemCustomerInfo.class;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
}
