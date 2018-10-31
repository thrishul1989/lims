package com.todaysoft.lims.smm.service.impl;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.AppsalemanSearcher;
import com.todaysoft.lims.smm.entity.APPSaleman;
import com.todaysoft.lims.smm.entity.BusinessInfo;
import com.todaysoft.lims.smm.entity.CustomerRelation;
import com.todaysoft.lims.smm.request.APPSalemanRequest;
import com.todaysoft.lims.smm.service.IAPPSalemanService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class APPSalemanService implements IAPPSalemanService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<BusinessInfo> paging(APPSalemanRequest request)
    {
        AppsalemanSearcher searcher = new AppsalemanSearcher();
        searcher.setName(request.getName());
        searcher.setPhone(request.getPhone());
        searcher.setUsername(request.getUsername());
        searcher.setTestingType(request.getTestingType());
        searcher.setRoleType(request.getRoleType());
        searcher.setProjectManager(request.getProjectManager());
        searcher.setStatus(request.getStatus());
        searcher.setUsername(request.getUsername());
        searcher.setBelongArea(request.getBelongArea());
        Pagination<BusinessInfo> p = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), BusinessInfo.class);
        return p;
    }
    
    @Override
    public List<String> list(APPSalemanRequest request)
    {
        
        List<String> s = baseDaoSupport.find(String.class, "select b.id FROM BusinessInfo b");
        return s;
    }
    
    @Override
    public List<BusinessInfo> listAll(APPSalemanRequest request)
    {
        AppsalemanSearcher searcher = new AppsalemanSearcher();
        searcher.setName(request.getName());
        searcher.setPhone(request.getPhone());
        searcher.setUsername(request.getUsername());
        searcher.setTestingType(request.getTestingType());
        searcher.setRoleType(request.getRoleType());
        searcher.setStatus(request.getStatus());
        searcher.setUsername(request.getUsername());
        return baseDaoSupport.find(searcher.toQuery(), BusinessInfo.class);
    }
    
    public void setBaseDaoSupport(BaseDaoSupport baseDaoSupport)
    {
        this.baseDaoSupport = baseDaoSupport;
    }
    
    @Override
    public APPSaleman get(String id)
    {
        APPSaleman entity = baseDaoSupport.get(APPSaleman.class, id);
        return entity;
    }
    
    @Override
    @Transactional
    public void modify(APPSaleman data)
    {
        data.setUodateTime(new Date());
        baseDaoSupport.update(data);
    }
    
    @Override
    @Transactional
    public void create(APPSaleman data)
    {
        data.setCreateTime(new Date());
        data.setIsFirstLogin("0");
        baseDaoSupport.insert(data);
    }
    
    @Override
    public BusinessInfo getBusinessInfo(String id)
    {
        return baseDaoSupport.get(BusinessInfo.class, id);
    }
    
    @Override
    public boolean getDataById(String userId)
    {
        String hql = "FROM BusinessInfo b where b.parentId = :userId";
        List<BusinessInfo> list = baseDaoSupport.findByNamedParam(BusinessInfo.class, hql, new String[] {"userId"}, new Object[] {userId});
        return Collections3.isNotEmpty(list) ? true : false;
    }
    
    @Override
    public boolean getCustomerByAppId(String appId)
    {
        String hql = "FROM CustomerRelation cr WHERE cr.business.id = :appId";
        List<CustomerRelation> list = baseDaoSupport.findByNamedParam(CustomerRelation.class, hql, new String[] {"appId"}, new Object[] {appId});
        return Collections3.isNotEmpty(list) ? false : true;
    }

    @Override
    public List<APPSaleman> getAppSaleByids(String ids)
    {
        List<APPSaleman> list = Lists.newArrayList();
        for (String id : ids.split(","))
        {
            list.add(get(id));
        }
        return list;
    }


}
