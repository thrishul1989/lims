package com.todaysoft.lims.system.modules.bmm.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.order.request.OrderSearchRequest;
import com.todaysoft.lims.order.request.OrderStatusScheduleRequest;
import com.todaysoft.lims.order.response.OrderStatusScheduleModel;
import com.todaysoft.lims.order.response.OrderSearchResponse;
import com.todaysoft.lims.order.response.TestingMonitorOrder;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bmm.service.IOrderTestingMonitorService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class OrderTestingMonitorService extends RestService implements IOrderTestingMonitorService
{
    @Override
    public Pagination<TestingMonitorOrder> search(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/orders/testing_monitor_search");
        ResponseEntity<OrderSearchResponse<TestingMonitorOrder>> exchange = template.exchange(url,
            HttpMethod.POST,
            new HttpEntity<OrderSearchRequest>(request),
            new ParameterizedTypeReference<OrderSearchResponse<TestingMonitorOrder>>()
            {
            });
        
        OrderSearchResponse<TestingMonitorOrder> response = exchange.getBody();
        
        Pagination<TestingMonitorOrder> pagination = new Pagination<TestingMonitorOrder>();
        pagination.setPageNo(response.getPageNo());
        pagination.setPageSize(response.getPageSize());
        pagination.setTotalCount(response.getTotalCount());
        pagination.setRecords(response.getRecords());
        return pagination;
    }

    @Override
    public Map<String, List<OrderStatusScheduleModel>> getScheduleListByOrderId(OrderStatusScheduleRequest request)
    {
        String url = GatewayService.getServiceUrl("/orders/testingOrderStatusByOrderId");
        ResponseEntity<Map<String, List<OrderStatusScheduleModel>>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderStatusScheduleRequest>(request), 
            new ParameterizedTypeReference<Map<String, List<OrderStatusScheduleModel>>>()
            {
            });
        return exchange.getBody();
    }
}
