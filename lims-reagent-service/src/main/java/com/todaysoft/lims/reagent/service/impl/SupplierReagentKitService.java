package com.todaysoft.lims.reagent.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.SupplierReagentKitSearcher;
import com.todaysoft.lims.reagent.entity.SupplierReagentKit;
import com.todaysoft.lims.reagent.service.ISupplierReagentKitService;

@Service
public class SupplierReagentKitService implements ISupplierReagentKitService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<SupplierReagentKit> paging(SupplierReagentKitSearcher request)
    {
        
        return baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), SupplierReagentKit.class);
    }
    
    @Override
    @Transactional
    public Integer createKit(SupplierReagentKitSearcher request)
    {
        SupplierReagentKit entity = new SupplierReagentKit();
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void delete(Integer id)
    {
        SupplierReagentKit entity = get(id);
        baseDaoSupport.delete(entity);
        
    }
    
    @Override
    public SupplierReagentKit get(Integer id)
    {
        return baseDaoSupport.get(SupplierReagentKit.class, id);
    }
    
    @Override
    @Transactional
    public void updateKitPrice(SupplierReagentKit supplierReagentKit)
    {
        SupplierReagentKit entity = get(supplierReagentKit.getId());
        entity.setPrice(supplierReagentKit.getPrice());
        baseDaoSupport.update(entity);
        
    }
    
}
