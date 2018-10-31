package com.todaysoft.lims.reagent.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.SupplierContacterSearcher;
import com.todaysoft.lims.reagent.entity.SupplierContacter;
import com.todaysoft.lims.reagent.service.ISupplierContacterService;

@Service
public class SupplierContacterService implements ISupplierContacterService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<SupplierContacter> paging(SupplierContacterSearcher request)
    {
        return baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), SupplierContacter.class);
        
    }
    
    @Override
    @Transactional
    public Integer create(SupplierContacterSearcher request)
    {
        SupplierContacter enty = new SupplierContacter();
        BeanUtils.copyProperties(request, enty);
        baseDaoSupport.insert(enty);
        return enty.getId();
    }
    
    @Override
    @Transactional
    public void modify(SupplierContacterSearcher request)
    {
        SupplierContacter entity = get(request.getId());
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.update(entity);
        
    }
    
    @Override
    public SupplierContacter get(Integer id)
    {
        return baseDaoSupport.get(SupplierContacter.class, id);
    }
    
    @Override
    @Transactional
    public void delete(Integer id)
    {
        System.out.println(123213);
        SupplierContacter entity = get(id);
        baseDaoSupport.delete(entity);
        
    }
    
}
