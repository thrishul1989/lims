package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Reagent;
import com.todaysoft.lims.reagent.entity.Supplier;
import com.todaysoft.lims.reagent.entity.SupplierReagent;
import com.todaysoft.lims.utils.StringUtils;

public class SupplierReagentSearcher implements ISearcher<SupplierReagent>
{
    
    private Integer id;
    
    private Supplier supplier;
    
    private Reagent reagent;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Supplier getSupplier()
    {
        return supplier;
    }
    
    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }
    
    public Reagent getReagent()
    {
        return reagent;
    }
    
    public void setReagent(Reagent reagent)
    {
        this.reagent = reagent;
    }
    
    public double getPrice()
    {
        return price;
    }
    
    public void setPrice(double price)
    {
        this.price = price;
    }
    
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
    
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    private double price;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private Integer sort;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SupplierReagent p WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addSupplierIdFilter(hql, paramNames, parameters);
        addKitIdFilter(hql, paramNames, parameters);
        addSortFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<SupplierReagent> getEntityClass()
    {
        return SupplierReagent.class;
    }
    
    private void addSupplierIdFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != supplier)
        {
            if (null != supplier.getId() && 0 != supplier.getId())
            {
                hql.append(" AND p.supplier.id =:supplierId");
                paramNames.add("supplierId");
                parameters.add(supplier.getId());
            }
        }
    }
    
    private void addKitIdFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != reagent)
        {
            if (StringUtils.isNotEmpty(reagent.getId()))
            {
                hql.append(" AND p.reagent.id =:reagentId");
                paramNames.add("reagentId");
                parameters.add(reagent.getId());
            }
        }
        
    }
    
    private void addSortFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != sort && 0 != sort)
        {
            if (1 == sort)
            {//升序
                hql.append(" order by p.price");
                
            }
            else if (2 == sort)
            {
                hql.append(" order by p.price desc");
            }
            else
            {
                
            }
            
        }
        
    }
    
}
