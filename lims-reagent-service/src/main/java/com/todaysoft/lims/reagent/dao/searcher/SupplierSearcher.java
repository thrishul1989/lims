package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Supplier;
import com.todaysoft.lims.reagent.entity.SupplierContacter;

public class SupplierSearcher implements ISearcher<Supplier>
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
    
    private String code;
    
    public List<SupplierContacter> getSupplierContacterList()
    {
        return supplierContacterList;
    }
    
    public void setSupplierContacterList(List<SupplierContacter> supplierContacterList)
    {
        this.supplierContacterList = supplierContacterList;
    }
    
    private String name;
    
    private String address;
    
    private List<SupplierContacter> supplierContacterList;
    
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
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Supplier p WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCodeFilter(hql, paramNames, parameters);
        addNameFilter(hql, paramNames, parameters);
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<Supplier> getEntityClass()
    {
        return Supplier.class;
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != code && !"".equals(code))
        {
            hql.append(" AND p.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
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
