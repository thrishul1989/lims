package com.todaysoft.lims.order.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.todaysoft.lims.order.mybatis.model.OrderScheduleModel;

public interface OrderScheduleMapper
{
    List<OrderScheduleModel> searchTestingOrderStatusByOrderId(@Param("orderId") String orderId, @Param("productId") String productId);
    
    List<OrderScheduleModel> searchTestingOrderStatusByTecOrderId(@Param("orderId") String orderId, @Param("productId") String productId);
    
    String searchCodeByScheduleId(String scheduleId);
}
