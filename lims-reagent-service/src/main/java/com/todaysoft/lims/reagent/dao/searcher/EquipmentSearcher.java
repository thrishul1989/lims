package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Equipment;

public class EquipmentSearcher implements ISearcher<Equipment>
{
    private String equipmentNo; // 设备编号
    
    private String name; // 设备名称
    
    private String vendor; // 生产厂商
    
    private Date acceptDateStart; // 接受日期
    
    private Date acceptDateEnd;
    
    private String principal; // 设备负责人
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Equipment e WHERE  e.deleted = false");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addEquipmentNoFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        addVendorFilter(hql, paramNames, parameters);
        addAcceptDateFilter(hql, paramNames, parameters);
        addPrincipalFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<Equipment> getEntityClass()
    {
        return Equipment.class;
    }
    
    private void addEquipmentNoFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != equipmentNo && !"".equals(equipmentNo))
        {
            hql.append(" AND e.equipmentNo LIKE :equipmentNo");
            paramNames.add("equipmentNo");
            parameters.add("%" + equipmentNo + "%");
        }
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != name && !"".equals(name))
        {
            hql.append(" AND e.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
    }
    
    private void addVendorFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != vendor && !"".equals(vendor))
        {
            hql.append(" AND e.vendor LIKE :vendor");
            paramNames.add("vendor");
            parameters.add("%" + vendor + "%");
        }
    }
    
    private void addAcceptDateFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != acceptDateStart && !"".equals(acceptDateStart))
        {
            hql.append(" AND e.acceptDate > :acceptDateStart");
            paramNames.add("acceptDateStart");
            parameters.add(acceptDateStart);
        }
        if (null != acceptDateEnd && !"".equals(acceptDateEnd))
        {
            hql.append(" AND e.acceptDate < :acceptDateEnd");
            paramNames.add("acceptDateEnd");
            parameters.add(acceptDateEnd);
        }
    }
    
    private void addPrincipalFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != principal && !"".equals(principal))
        {
            hql.append(" AND e.principal LIKE :principal");
            paramNames.add("principal");
            parameters.add("%" + principal + "%");
        }
        
        hql.append(" order by  e.createTime desc");
    }
    
    public String getEquipmentNo()
    {
        return equipmentNo;
    }
    
    public void setEquipmentNo(String equipmentNo)
    {
        this.equipmentNo = equipmentNo;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getVendor()
    {
        return vendor;
    }
    
    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }
    
    public Date getAcceptDateStart()
    {
        return acceptDateStart;
    }
    
    public void setAcceptDateStart(Date acceptDateStart)
    {
        this.acceptDateStart = acceptDateStart;
    }
    
    public Date getAcceptDateEnd()
    {
        return acceptDateEnd;
    }
    
    public void setAcceptDateEnd(Date acceptDateEnd)
    {
        this.acceptDateEnd = acceptDateEnd;
    }
    
    public String getPrincipal()
    {
        return principal;
    }
    
    public void setPrincipal(String principal)
    {
        this.principal = principal;
    }
    
}
