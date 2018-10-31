package com.todaysoft.lims.sample.model.sampleReceiving;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleStoraging;
import com.todaysoft.lims.utils.StringUtils;

public class SampleStoragingSearcher extends IDataAuthoritySearcher implements ISearcher<SampleStoraging>
{
    
    private String id;
    
    private String code; //    '编号',
    
    private String operatorName; //    '操作者',
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String sampleCode;
    
    private Integer type; // '入库类型'
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select distinct s FROM SampleStoraging s left join  s.sampleStoragingDetail d  WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public NamedQueryer toAuthQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select distinct s FROM SampleStoraging s , SampleStoragingDetail d  WHERE 1=1 and s.id= d.sampleStoraging.id");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addAuthFilter(hql, paramNames, parameters, "operatorId");
        addFilter(hql, paramNames, parameters);
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        if (StringUtils.isNotEmpty(id))
        {
            hql.append(" AND s.id = :id");
            paramNames.add("id");
            parameters.add(id);
        }
        if (StringUtils.isNotEmpty(isOver))
        {
            hql.append(" AND s.status = :status");
            paramNames.add("status");
            if (isOver == 1)
            {
                parameters.add(false);
            }
            else
            {
                parameters.add(true);
            }
        }
        
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND d.sampleCode LIKE :sampleCode");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        
        if (StringUtils.isNotEmpty(operatorName))
        {
            hql.append(" AND s.operatorName LIKE :name");
            paramNames.add("name");
            parameters.add("%" + operatorName + "%");
        }
        if (null != type)
        {
            hql.append(" AND s.type = :type");
            paramNames.add("type");
            parameters.add(type);
        }
        hql.append(" order by s.operateTime desc ");
    }
    
    @Override
    public Class<SampleStoraging> getEntityClass()
    {
        return SampleStoraging.class;
    }
    
    private Integer isOver;
    
    public Integer getIsOver()
    {
        return isOver;
    }
    
    public void setIsOver(Integer isOver)
    {
        this.isOver = isOver;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getOperatorName()
    {
        return operatorName;
    }
    
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
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
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public NamedQueryer toDetailQuery()
    {
        //SampleStoraging
        StringBuffer hql = new StringBuffer(512);
        hql.append("select r FROM ReceivedSample r , SampleStoragingDetail s WHERE 1=1 and s.sampleCode = r.sampleCode");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addDetailFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addDetailFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(id))
        {
            hql.append(" AND s.sampleStoraging.id =:id");
            paramNames.add("id");
            parameters.add(id);
        }
        
        if (StringUtils.isNotEmpty(code))
        {
            hql.append(" AND s.sampleCode LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
}
