package com.todaysoft.lims.reagent.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.SupplierReagentSearcher;
import com.todaysoft.lims.reagent.entity.SupplierReagent;
import com.todaysoft.lims.reagent.entity.SupplierReagentKit;
import com.todaysoft.lims.reagent.service.ISupplierReagentService;
import com.todaysoft.lims.utils.Collections3;

@Service
@Transactional(readOnly = false, rollbackFor = {Exception.class})
public class SupplierReagentService implements ISupplierReagentService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<SupplierReagent> paging(SupplierReagentSearcher request)
    {
        
        return baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), SupplierReagent.class);
    }
    
    @Override
    @Transactional
    public Integer create(SupplierReagentSearcher request)
    {
        SupplierReagent entity = new SupplierReagent();
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void delete(Integer id)
    {
        SupplierReagent entity = get(id);
        baseDaoSupport.delete(entity);
        
    }
    
    @Override
    public SupplierReagent get(Integer id)
    {
        return baseDaoSupport.get(SupplierReagent.class, id);
    }
    
    @Override
    @Transactional
    public void updateReagentPrice(SupplierReagent supplierReagent)
    {
        SupplierReagent entity = get(supplierReagent.getId());
        entity.setPrice(supplierReagent.getPrice());
        baseDaoSupport.update(entity);
        
    }
    
    @Override
    public boolean validate(SupplierReagent supplierReagent)
    {
        
        if (supplierReagent.getSupplierId() != null)
        {
            String sql = "select id from LIMS_SUPPLIER_REAGENT where supplier_id = " + supplierReagent.getSupplierId();
            return Collections3.isEmpty(baseDaoSupport.find(sql));
        }
        if (supplierReagent.getReagentId() != null)
        {
            
            String sql = "select id from LIMS_SUPPLIER_REAGENT where reagent_id = " + supplierReagent.getReagentId();
            return Collections3.isEmpty(baseDaoSupport.find(sql));
        }
        return true;
    }
    
    @Override
    public boolean validate(SupplierReagentKit supplierReagent)
    {
        
        if (supplierReagent.getSupplierId() != null)
        {
            String sql = "select id from LIMS_SUPPLIER_REAGENT_KIT where supplier_id = " + supplierReagent.getSupplierId();
            return Collections3.isEmpty(baseDaoSupport.find(sql));
        }
        if (supplierReagent.getReagentId() != null)
        {
            
            String sql = "select id from LIMS_SUPPLIER_REAGENT_KIT where reagent_kit_id = " + supplierReagent.getReagentId();
            return Collections3.isEmpty(baseDaoSupport.find(sql));
        }
        return true;
    }
    
}
