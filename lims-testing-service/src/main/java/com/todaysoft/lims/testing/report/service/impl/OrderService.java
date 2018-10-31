package com.todaysoft.lims.testing.report.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.report.model.MetadataSample;
import com.todaysoft.lims.testing.report.service.IOrderService;


@Service
public class OrderService implements IOrderService
{
    private static Logger log = LoggerFactory.getLogger(OrderService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;

    @Override
    public Order getOrderById(String id)
    {
        Order o = baseDaoSupport.get(Order.class, id);
        return o;
    }
    
    @Override
    public MetadataSample get(String id)
    {
        MetadataSample sample = baseDaoSupport.get(MetadataSample.class, id);
        return sample;
    }
    
}
