package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.materialsApply.MaterialsApply;
import com.todaysoft.lims.sample.model.enums.MaterialsStatus;
import com.todaysoft.lims.utils.StringUtils;

public class MaterialsApplySearcher implements ISearcher<MaterialsApply>
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private MaterialsStatus status;
    
    private String testingType;
    
    private String proposer;
    
    private Date start;
    
    private Date end;
    
    private String code;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select m FROM MaterialsApply m,BusinessInfo b WHERE b.id = m.creatorId ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" order by  m.status,m.applyTime");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(testingType))
        {
            hql.append(" AND b.testingType = :testingType");
            paramNames.add("testingType");
            parameters.add(testingType);
        }
        if (StringUtils.isNotEmpty(proposer))
        {
            hql.append(" AND b.realName like :proposer");
            paramNames.add("proposer");
            parameters.add('%' + proposer + '%');
        }
        if (StringUtils.isNotEmpty(status))
        {
            hql.append(" AND m.status = :status");
            paramNames.add("status");
            parameters.add(status);
        }
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND m.code LIKE :code");
            paramNames.add("code");
            parameters.add('%' + code + '%');
        }
        if (null != start)
        {
            hql.append(" AND m.applyTime > :start");
            paramNames.add("start");
            parameters.add(start);
        }
        if (null != end)
        {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(end);
            calendar.add(calendar.DATE, 1);
            hql.append(" AND m.applyTime < :end");
            paramNames.add("end");
            parameters.add(calendar.getTime());
        }
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    public MaterialsStatus getStatus()
    {
        return status;
    }
    
    public void setStatus(MaterialsStatus status)
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
    
    public String getProposer()
    {
        return proposer;
    }
    
    public void setProposer(String proposer)
    {
        this.proposer = proposer;
    }
    
    public Date getStart()
    {
        return start;
    }
    
    public void setStart(Date start)
    {
        this.start = start;
    }
    
    public Date getEnd()
    {
        return end;
    }
    
    public void setEnd(Date end)
    {
        this.end = end;
    }
    
    @Override
    public Class<MaterialsApply> getEntityClass()
    {
        return MaterialsApply.class;
    }
    
}
