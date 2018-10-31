package com.todaysoft.lims.report.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.report.entity.system.ReportSystemContractInfo;
import com.todaysoft.lims.utils.StringUtils;

public class ContractReportSearcher implements ISearcher<ReportSystemContractInfo>
{

    private String taskId;

    private Integer pageNo;

    private Integer pageSize;
    
    private String status;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append(" FROM ReportSystemContractInfo c WHERE 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addContractStatusFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

    private void addContractStatusFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        hql.append(" AND c.taskId=:taskId ");
        paramNames.add("taskId");
        parameters.add(taskId);
        
        if (!StringUtils.isEmpty(status))
        {
            hql.append(" AND EXISTS(select cc.id from Contract cc where cc.id = c.contractId and cc.status = :status");
            if("2-1".equals(status))
            {
                hql.append(" and str_to_date(now(),'%Y-%m-%d') > cc.effectiveEnd ");
                status = "2";
                
            }else
            {
                if("2".equals(status))
                {
                    hql.append(" and cc.signDate <= str_to_date(now(),'%Y-%m-%d') and str_to_date(now(),'%Y-%m-%d') <= cc.effectiveEnd");
                }
            }
            hql.append(")");
            paramNames.add("status");
            parameters.add(status);
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public Class<ReportSystemContractInfo> getEntityClass()
    {
        return ReportSystemContractInfo.class;
    }
    
}
