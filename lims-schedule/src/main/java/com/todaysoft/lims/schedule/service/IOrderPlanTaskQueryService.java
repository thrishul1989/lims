package com.todaysoft.lims.schedule.service;

import com.todaysoft.lims.schedule.entity.OrderPlanTask;

public interface IOrderPlanTaskQueryService
{
    /**
     * 查询订单下某个节点类型的计划任务，该计划任务在订单下唯一
     */
    OrderPlanTask queryForOrderSemanticRecord(String orderId, String semantic);
}
