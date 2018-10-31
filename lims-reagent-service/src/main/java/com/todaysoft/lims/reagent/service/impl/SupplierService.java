package com.todaysoft.lims.reagent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.SupplierSearcher;
import com.todaysoft.lims.reagent.entity.Supplier;
import com.todaysoft.lims.reagent.service.ISupplierService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class SupplierService implements ISupplierService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Supplier> paging(SupplierSearcher request)
    {
        
        return baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), Supplier.class);
        
    }
    
    //	@Override
    //	public List<Supplier> list(Supplier request) {
    //		
    //		return baseDaoSupport.find(request);
    //	}
    
    @Override
    public Supplier get(Integer id)
    {
        return baseDaoSupport.get(Supplier.class, id);
    }
    
    @Override
    @Transactional
    public Integer create(Supplier request)
    {
        
        baseDaoSupport.insert(request);
        return request.getId();
    }
    
    @Override
    @Transactional
    public void modify(Supplier request)
    {
        
        baseDaoSupport.update(request);
    }
    
    @Override
    @Transactional
    public void delete(Integer id)
    {
        Supplier entity = get(id);
        baseDaoSupport.delete(entity);
    }
    
    @Override
    public boolean validate(Supplier request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Supplier.class, request, "code")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public Pagination<Supplier> selectSupplier(SupplierSearcher request)
    {
        String hql = "FROM Supplier u WHERE u.name LIKE :name or u.code LIKE :code";
        NamedQueryer queryer = NamedQueryer.builder()
            .hql(hql)
            .names(Lists.newArrayList("name", "code"))
            .values(Lists.newArrayList((Object)("%" + request.getName() + "%"), (Object)("%" + request.getName() + "%")))
            .build();
        Pagination<Supplier> pageReagentKit = baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), Supplier.class);
        
        return pageReagentKit;
    }
    
}
