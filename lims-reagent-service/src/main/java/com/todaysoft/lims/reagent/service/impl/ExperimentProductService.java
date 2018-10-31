package com.todaysoft.lims.reagent.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.ExperimentProductSearcher;
import com.todaysoft.lims.reagent.entity.ExperimentProduct;
import com.todaysoft.lims.reagent.exception.ServiceException;
import com.todaysoft.lims.reagent.model.request.ExperimentProductCreateRequest;
import com.todaysoft.lims.reagent.model.request.ExperimentProductListRequest;
import com.todaysoft.lims.reagent.model.request.ExperimentProductModifyRequest;
import com.todaysoft.lims.reagent.model.request.ExperimentProductPagingRequest;
import com.todaysoft.lims.reagent.service.IExperimentProductService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class ExperimentProductService implements IExperimentProductService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<ExperimentProduct> paging(ExperimentProductPagingRequest request)
    {
        ExperimentProductSearcher searcher = new ExperimentProductSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), ExperimentProduct.class);
    }
    
    @Override
    public List<ExperimentProduct> list(ExperimentProductListRequest request)
    {
        ExperimentProductSearcher searcher = new ExperimentProductSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher);
    }
    
    @Override
    public ExperimentProduct getExperimentProduct(Integer id)
    {
        return baseDaoSupport.get(ExperimentProduct.class, id);
    }
    
    @Override
    @Transactional
    public Integer create(ExperimentProductCreateRequest request)
    {
        if (StringUtils.isEmpty(request.getName()))
        {
            // TODO: 错误码设置
            throw new ServiceException("产物名称不能为空");
        }
        ExperimentProduct entity = new ExperimentProduct();
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    @Override
    @Transactional
    public void modify(ExperimentProductModifyRequest request)
    {
        ExperimentProduct entity = getExperimentProduct(request.getId());
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void delete(Integer id)
    {
        ExperimentProduct entity = getExperimentProduct(id);
        baseDaoSupport.delete(entity);
    }
    
    @Override
    public boolean checkedName(ExperimentProductPagingRequest request)
    {
        
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(ExperimentProduct.class, request, "code")))
        {
            return false;
        }
        return true;
    }
    
}
