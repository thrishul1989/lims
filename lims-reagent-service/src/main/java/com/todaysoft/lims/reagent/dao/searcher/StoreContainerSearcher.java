package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.StoreContainer;

public class StoreContainerSearcher implements ISearcher<StoreContainer>
{
    private String code; //容器编号
    
    private String name; //容器名称
    
    private String deviceType; //容器类型
    
    private String storageType; //存储类别
    
    private String sampleType;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM StoreContainer s WHERE 1=1  and s.deleted = false");
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
    public Class<StoreContainer> getEntityClass()
    {
        return StoreContainer.class;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != name && !"".equals(name))
        {
            hql.append(" AND s.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        
        if (null != code && !"".equals(code))
        {
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        if (null != deviceType && !"".equals(deviceType))
        {
            hql.append(" AND s.deviceType = :deviceType");
            paramNames.add("deviceType");
            parameters.add(deviceType);
        }
        if (null != storageType && !"".equals(storageType))
        {
            hql.append(" AND s.storageType = :storageType");
            paramNames.add("storageType");
            parameters.add(storageType);
        }
        
        if (null != sampleType && !"".equals(sampleType))
        {
            hql.append(" AND s.sampleType = :sampleType");
            paramNames.add("sampleType");
            parameters.add(sampleType);
        }
        hql.append(" order by s.createTime desc ");
        
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDeviceType()
    {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }
    
    public String getStorageType()
    {
        return storageType;
    }
    
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
}
