package com.todaysoft.lims.order.service;


import com.todaysoft.lims.order.model.OrderDetail;
import com.todaysoft.lims.order.model.OrderSyncSearchModel;

import java.util.List;

public interface IOrderSyncService {
    List<OrderDetail> patchSearch(OrderSyncSearchModel request);

    OrderDetail searchByOrderCode(OrderSyncSearchModel request);
}
