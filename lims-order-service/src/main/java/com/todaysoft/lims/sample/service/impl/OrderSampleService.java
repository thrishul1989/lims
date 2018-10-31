package com.todaysoft.lims.sample.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.sample.dao.searcher.OrderSampleSearcher;
import com.todaysoft.lims.sample.entity.OrderSample;
import com.todaysoft.lims.sample.model.order.OrderSimpleModel;
import com.todaysoft.lims.sample.model.request.OrderSampleCreateRequest;
import com.todaysoft.lims.sample.model.request.OrderSampleListRequest;
import com.todaysoft.lims.sample.service.IOrderSampleService;

@Service
public class OrderSampleService implements IOrderSampleService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public List<OrderSample> list(OrderSampleListRequest request)
    {
        OrderSampleSearcher searcher = new OrderSampleSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher);
    }
    
    @Override
    @Transactional
    public Integer create(OrderSampleCreateRequest request)
    {
        OrderSample entity = new OrderSample();
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    @Override
    public OrderSample get(Integer id)
    {
        return baseDaoSupport.get(OrderSample.class, id);
    }
    
    @Override
    public OrderSimpleModel getSampleOrder(String id)
    {
        String hql = "SELECT osv.orderId FROM OrderSampleView osv WHERE osv.sampleId = :id";
        List<String> records = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"id"}, new Object[] {id});
        
        if (CollectionUtils.isEmpty(records))
        {
            return null;
        }
        
        OrderSimpleModel model = new OrderSimpleModel();
        model.setId(records.get(0));
        return model;
    }
}
