package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.Material;
import com.todaysoft.lims.utils.StringUtils;

public class MaterialSearcher implements ISearcher<Material>
{
    private String id;
    private Integer type;
    
    private String name;
    
    private String equalName;
    
    private String sortId;
    
    private int pageNo;
    
    private int pageSize;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM Material m WHERE m.delFlag = false");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addFilter(hql, names, values);
        hql.append(" ORDER BY m.state,m.createTime DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if(null != type)
        {
            hql.append(" AND m.type = :type");
            paramNames.add("type");
            parameters.add(type);
        }
        if(StringUtils.isNotEmpty(name))
        {
            hql.append(" AND m.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        if(StringUtils.isNotEmpty(equalName))
        {
            hql.append(" AND m.name = :equalName");
            paramNames.add("equalName");
            parameters.add(equalName);
        }
        if(StringUtils.isNotEmpty(sortId))
        {
            hql.append(" AND m.materialSort.id = :sortId");
            paramNames.add("sortId");
            parameters.add(sortId);
        }
    }

    @Override
    public Class<Material> getEntityClass()
    {
        return Material.class;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEqualName()
    {
        return equalName;
    }

    public void setEqualName(String equalName)
    {
        this.equalName = equalName;
    }

    public String getSortId()
    {
        return sortId;
    }

    public void setSortId(String sortId)
    {
        this.sortId = sortId;
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
