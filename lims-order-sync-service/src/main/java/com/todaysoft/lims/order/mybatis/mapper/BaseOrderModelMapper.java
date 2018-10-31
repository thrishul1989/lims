package com.todaysoft.lims.order.mybatis.mapper;

import com.todaysoft.lims.order.model.OrderSyncSearchModel;
import com.todaysoft.lims.order.mybatis.mapper.entity.*;

import java.util.List;

public interface BaseOrderModelMapper {

    List<BaseOrderModel> getOrderByModel(OrderSyncSearchModel model);

    List<OrderExamineeModel> getOrderExamineeDiseaseByOrderId(String orderId);

    List<OrderExamineeModel> getOrderExamineeGeneByOrderId(String orderId);

    List<OrderExamineeModel> getOrderExamineePhenotypeByOrderId(String orderId);

    List<OrderSampleModel> getOrderPrimarySampleModelByOrderId(String orderId);

    List<OrderSampleModel> getOrderSubSampleModelByOrderId(String orderId);

    List<OrderProductModel> getOrderProductModelByOrderId(String orderId);

    List<ProductMethodModel> getProductMethodModelByProductId(String productId);

    List<TestingDataModel> getTestingDataModelByOrderId(String orderId);

    List<ScheduleTaskModel> getTaskListBySchedule(String scheduleId);

    List<ScheduleTaskModel> getNgsTaskListBySchedule(String scheduleId);

    List<ScheduleTaskModel> getSiteAndGeneByScheduleAndSangerOrPcrNgs(String scheduleId);

    List<ScheduleTaskModel> getSiteAndGeneByScheduleAndMlpa(String scheduleId);

    List<ScheduleTaskModel> getSiteAndGeneByScheduleAndQpcr(String scheduleId);
}