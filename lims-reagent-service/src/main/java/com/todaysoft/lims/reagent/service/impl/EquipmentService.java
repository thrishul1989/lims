package com.todaysoft.lims.reagent.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.EquipmentSearcher;
import com.todaysoft.lims.reagent.entity.Equipment;
import com.todaysoft.lims.reagent.model.request.EquipmentCreateRequest;
import com.todaysoft.lims.reagent.model.request.EquipmentModifyRequest;
import com.todaysoft.lims.reagent.model.request.EquipmentPagingRequest;
import com.todaysoft.lims.reagent.service.IEquipmentService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class EquipmentService implements IEquipmentService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Equipment> paging(EquipmentPagingRequest request)
    {
        EquipmentSearcher searcher = new EquipmentSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), Equipment.class);
    }
    
    @Override
    public Equipment getEquipment(String id)
    {
        return baseDaoSupport.get(Equipment.class, id);
    }
    
    @Override
    @Transactional
    public String create(EquipmentCreateRequest request)
    {
        Equipment entity = new Equipment();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        entity.setDeleted(false);
        // entity.setId(UUID.randomUUID().toString());
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void modify(EquipmentModifyRequest request)
    {
        Equipment entity = getEquipment(request.getId());
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        Equipment entity = getEquipment(id);
        entity.setDeleted(true);
        entity.setDeleteTime(new Date());
        baseDaoSupport.update(entity);
    }
    
    @Override
    public Boolean checkName(EquipmentCreateRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Equipment.class, request, "name")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public Boolean checkEquipmentNo(EquipmentCreateRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Equipment.class, request, "equipmentNo")))
        {
            return false;
        }
        return true;
    }
    
}
