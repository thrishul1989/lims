package com.todaysoft.lims.testing.report.service;

import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.report.model.MetadataSample;

public interface IOrderService
{
    Order getOrderById(String id);

    MetadataSample get(String id);
}
