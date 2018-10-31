package com.todaysoft.lims.smm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.InvoiceUserSearcher;
import com.todaysoft.lims.smm.entity.InvoiceUser;
import com.todaysoft.lims.smm.entity.InvoiceUserModel;
import com.todaysoft.lims.smm.service.IInvoiceUserService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class InvoiceUserService implements IInvoiceUserService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<InvoiceUser> paging(InvoiceUserSearcher searcher)
    {
        return baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), InvoiceUser.class);
    }
    
    @Override
    public InvoiceUser get(String id)
    {
        return baseDaoSupport.get(InvoiceUser.class, id);
    }
    
    @Override
    @Transactional
    public void create(InvoiceUser request)
    {
        baseDaoSupport.insert(request);
        for (InvoiceUserModel model : request.getUserList())
        {
            model.setInvoiceUser(request);
            baseDaoSupport.insert(model);
        }
    }
    
    @Override
    @Transactional
    public void modify(InvoiceUser request)
    {
        InvoiceUser entity = get(request.getId());
        for (InvoiceUserModel model : entity.getUserList())
        {
            baseDaoSupport.delete(model);
        }
        for (InvoiceUserModel model : request.getUserList())
        {
            model.setInvoiceUser(entity);
            baseDaoSupport.insert(model);
        }
        baseDaoSupport.merge(request);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        InvoiceUser entity = get(id);
        entity.setDelFlag(true);
        baseDaoSupport.update(entity);
    }
    
    @Override
    public boolean validateUser(InvoiceUser request)
    {
        List<String> userIds = Lists.newArrayList();
        for (InvoiceUserModel model : request.getUserList())
        {
            userIds.add(model.getId());
        }
        InvoiceUserSearcher searcher = new InvoiceUserSearcher();
        List<InvoiceUser> invoiceUserList = baseDaoSupport.find(searcher.toQuery(), InvoiceUser.class);
        for (InvoiceUser invoice : invoiceUserList)
        {
            if (!invoice.getId().equals(request.getId()))
            {
                for (InvoiceUserModel userModel : invoice.getUserList())
                {
                    if (userIds.contains(userModel.getId()))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean validateInstitution(String testInstitution)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM InvoiceUser iu WHERE iu.delFlag = false AND iu.testInstitution = :testInstitution")
                .names(Lists.newArrayList("testInstitution"))
                .values(Lists.newArrayList(testInstitution))
                .build();
        List<InvoiceUser> users = baseDaoSupport.find(queryer, InvoiceUser.class);
        if (Collections3.isNotEmpty(users))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public InvoiceUser getByInstitution(String testInstitution)
    {
        InvoiceUserSearcher searcher = new InvoiceUserSearcher();
        searcher.setTestInstitution(testInstitution);
        List<InvoiceUser> list = baseDaoSupport.find(searcher.toQuery(), InvoiceUser.class);
        if (Collections3.isNotEmpty(list))
        {
            return Collections3.getFirst(list);
        }
        return null;
    }
    
    @Override
    public List<InvoiceUserModel> findUserByName(InvoiceUserModel searcher)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM InvoiceUserModel u WHERE u.name = :name")
                .names(Lists.newArrayList("name"))
                .values(Lists.newArrayList(searcher.getName()))
                .build();
        return baseDaoSupport.find(queryer, InvoiceUserModel.class);
    }
    
    @Override
    public InvoiceUserModel getUser(String id)
    {
        return baseDaoSupport.get(InvoiceUserModel.class, id);
    }
    
    @Override
    public InvoiceUser getInvoiceUserByUserId(String id)
    {
        
        String hql = "select u from InvoiceUser u left join u.userList d where d.user.id =:id ";
        
        List<InvoiceUser> list = baseDaoSupport.findByNamedParam(InvoiceUser.class, hql, new String[] {"id"}, new Object[] {id});
        if (Collections3.isNotEmpty(list))
        {
            return Collections3.getFirst(list);
        }
        return null;
    }
}
