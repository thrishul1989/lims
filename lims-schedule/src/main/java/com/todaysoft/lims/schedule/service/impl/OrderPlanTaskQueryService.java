package com.todaysoft.lims.schedule.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.service.IOrderPlanTaskQueryService;

@Service
public class OrderPlanTaskQueryService implements IOrderPlanTaskQueryService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public OrderPlanTask queryForOrderSemanticRecord(String orderId, String semantic)
    {
        OrderPlanTaskQueryBuilder builder = new OrderPlanTaskQueryBuilder();
        builder.setOrderId(orderId);
        builder.setSemantics(Collections.singleton(semantic));
        List<OrderPlanTask> records = baseDaoSupport.find(builder.build(), OrderPlanTask.class);
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
}
