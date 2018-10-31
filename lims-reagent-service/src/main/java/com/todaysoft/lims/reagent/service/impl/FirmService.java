package com.todaysoft.lims.reagent.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.FirmSearcher;
import com.todaysoft.lims.reagent.entity.Firm;
import com.todaysoft.lims.reagent.exception.ServiceException;
import com.todaysoft.lims.reagent.service.IFirmService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class FirmService implements IFirmService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Firm> paging(FirmSearcher request)
    {
        
        return baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), Firm.class);
    }
    
    @Override
    public List<Firm> list(FirmSearcher request)
    {
        
        return baseDaoSupport.find(request);
    }
    
    @Override
    public Firm get(Integer id)
    {
        return baseDaoSupport.get(Firm.class, id);
    }
    
    @Override
    @Transactional
    public Integer create(FirmSearcher request)
    {
        if (StringUtils.isEmpty(request.getName()))
        {
            // TODO: 错误码设置
            throw new ServiceException("名称不能为空");
        }
        Firm entity = new Firm();
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void modify(FirmSearcher request)
    {
        Firm entity = get(request.getId());
        request.setState(entity.getState());
        BeanUtils.copyProperties(request, entity);
        
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void delete(Integer id)
    {
        Firm entity = get(id);
        baseDaoSupport.delete(entity);
    }
    
    @Override
    public boolean validate(FirmSearcher request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Firm.class, request, "code")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public Pagination<Firm> selectFirm(FirmSearcher request)
    {
        String hql = "FROM Firm u WHERE (u.name LIKE :name or u.otherName LIKE :names) and state='0'";
        NamedQueryer queryer = NamedQueryer.builder()
            .hql(hql)
            .names(Lists.newArrayList("name", "names"))
            .values(Lists.newArrayList((Object)("%" + request.getName() + "%"), (Object)("%" + request.getName() + "%")))
            .build();
        Pagination<Firm> pageFirm = baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), Firm.class);
        
        return pageFirm;
    }
    
    @Override
    @Transactional
    public void enableFirm(FirmSearcher request)
    {
        Firm entity = get(request.getId());
        if ("1".equals(request.getState()))
        {
            entity.setState("1");
            
        }
        else if ("0".equals(request.getState()))
        {
            entity.setState("0");
            
        }
        
        baseDaoSupport.update(entity);
        
    }
    
}
