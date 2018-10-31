package com.todaysoft.lims.reagent.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.ProducterManageSearcher;
import com.todaysoft.lims.reagent.entity.ProducterManage;
import com.todaysoft.lims.reagent.model.request.ProducterManageRequest;
import com.todaysoft.lims.reagent.service.IProducterManageService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class ProducterManageService implements IProducterManageService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<ProducterManage> paging(ProducterManageRequest request)
    {
        ProducterManageSearcher searcher = new ProducterManageSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), ProducterManage.class);
        
    }
    
    @Override
    public ProducterManage get(String id)
    {
        return baseDaoSupport.get(ProducterManage.class, id);
    }
    
    @Override
    @Transactional
    public String create(ProducterManage request)
    {
        baseDaoSupport.insert(request);
        return request.getId();
    }
    
    @Override
    @Transactional
    public void modify(ProducterManage request)
    {
        
        baseDaoSupport.update(request);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        ProducterManage entity = get(id);
        baseDaoSupport.delete(entity);
    }
    
    @Override
    public boolean validate(ProducterManage request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(ProducterManage.class, request, "producterNo")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    @Transactional
    public boolean deleteEmail(ProducterManage data)
    {
        ProducterManage entity = get(data.getId());
        String emails = entity.getContactEmails();
        String relEmail = null;
        int index = emails.indexOf(data.getEmailVal());
        if (index > 0 && emails.length() - data.getEmailVal().length() != index)
        {
            relEmail = emails.substring(0, index - 1) + emails.substring(index + data.getEmailVal().length());
        }
        else if (index == 0)
        {
            relEmail = emails.substring(index + data.getEmailVal().length() + 1);
        }
        else
        {
            relEmail = emails.substring(0, emails.length() - data.getEmailVal().length() - 1);
        }
        entity.setContactEmails(relEmail);
        modify(entity);
        return true;
    }
    
}
