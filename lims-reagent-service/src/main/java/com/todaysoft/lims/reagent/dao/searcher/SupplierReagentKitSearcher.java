package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.ReagentKit;
import com.todaysoft.lims.reagent.entity.Supplier;
import com.todaysoft.lims.reagent.entity.SupplierReagentKit;

public class SupplierReagentKitSearcher implements ISearcher<SupplierReagentKit>
{
    
    private Integer id;
    
    private Supplier supplier;
    
    private ReagentKit reagentKit;
    
    private double price;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private Integer sort;
    
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
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
    
    public ReagentKit getReagentKit()
    {
        return reagentKit;
    }
    
    public void setReagentKit(ReagentKit reagentKit)
    {
        this.reagentKit = reagentKit;
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
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SupplierReagentKit p WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addSupplierIdFilter(hql, paramNames, parameters);
        addSortFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<SupplierReagentKit> getEntityClass()
    {
        return SupplierReagentKit.class;
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
