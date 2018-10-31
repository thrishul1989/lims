package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Supplier;
import com.todaysoft.lims.reagent.entity.SupplierContacter;

public class SupplierContacterSearcher implements ISearcher<SupplierContacter>
{
    
    private Integer id;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    private String name;
    
    private String mobile;
    
    private String duty;
    
    private Supplier supplier;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getMobile()
    {
        return mobile;
    }
    
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    
    public String getDuty()
    {
        return duty;
    }
    
    public void setDuty(String duty)
    {
        this.duty = duty;
    }
    
    public Supplier getSupplier()
    {
        return supplier;
    }
    
    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SupplierContacter p WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addMobileFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        addSupplierFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<SupplierContacter> getEntityClass()
    {
        return SupplierContacter.class;
    }
    
    private void addSupplierFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != supplier)
        {
            if (null != supplier.getId() && 0 != supplier.getId())
            {
                hql.append(" AND p.supplier.id = :supplierId");
                paramNames.add("supplierId");
                parameters.add(supplier.getId());
            }
            
        }
    }
    
    private void addMobileFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != mobile && !"".equals(mobile))
        {
            hql.append(" AND p.mobile LIKE :mobile");
            paramNames.add("mobile");
            parameters.add("%" + mobile + "%");
        }
    }
    
    private void addNameFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != name && !"".equals(name))
        {
            hql.append(" AND p.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
    }
    
}
