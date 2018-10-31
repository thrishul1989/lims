package com.todaysoft.lims.system.modules.bmm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.order.request.OrderSearchRequest;
import com.todaysoft.lims.order.response.OrderSearchResponse;
import com.todaysoft.lims.order.response.ScheduleMonitorOrder;
import com.todaysoft.lims.schedule.request.OrderPostponedDetailsBatchRequest;
import com.todaysoft.lims.schedule.response.OrderPostponedDetails;
import com.todaysoft.lims.schedule.response.OrderPostponedDetailsBatchResponse;
import com.todaysoft.lims.schedule.response.OrderScheduleMonitorPlanTask;
import com.todaysoft.lims.schedule.response.OrderScheduleMonitorResponse;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.OrderSchedulePlanTaskModel;
import com.todaysoft.lims.system.modules.bmm.service.IOrderScheduleMonitorService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class OrderScheduleMonitorService extends RestService implements IOrderScheduleMonitorService
{
    @Override
    public Pagination<ScheduleMonitorOrder> search(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/orders/schedule_monitor_search");
        ResponseEntity<OrderSearchResponse<ScheduleMonitorOrder>> exchange = template.exchange(url,
            HttpMethod.POST,
            new HttpEntity<OrderSearchRequest>(request),
            new ParameterizedTypeReference<OrderSearchResponse<ScheduleMonitorOrder>>()
            {
            });
        
        OrderSearchResponse<ScheduleMonitorOrder> response = exchange.getBody();
        
        Pagination<ScheduleMonitorOrder> pagination = new Pagination<ScheduleMonitorOrder>();
        pagination.setPageNo(response.getPageNo());
        pagination.setPageSize(response.getPageSize());
        pagination.setTotalCount(response.getTotalCount());
        pagination.setRecords(response.getRecords());
        return pagination;
    }
    
    @Override
    public OrderScheduleMonitorResponse getOrderScheduleMonitorDetails(String orderId)
    {
        String url = GatewayService.getServiceUrl("/schedule/orders/{id}");
        return template.getForObject(url, OrderScheduleMonitorResponse.class, orderId);
    }
    
    @Override
    public List<OrderSchedulePlanTaskModel> getGroupPlanTasks(String groupId)
    {
        String url = GatewayService.getServiceUrl("/schedule/groups/{id}");
        ResponseEntity<List<OrderScheduleMonitorPlanTask>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderScheduleMonitorPlanTask>>()
            {
            }, groupId);
        
        List<OrderScheduleMonitorPlanTask> records = exchange.getBody();
        
        List<OrderSchedulePlanTaskModel> tasks = new ArrayList<OrderSchedulePlanTaskModel>();
        
        if (!CollectionUtils.isEmpty(records))
        {
            OrderSchedulePlanTaskModel task;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            for (OrderScheduleMonitorPlanTask record : records)
            {
                task = new OrderSchedulePlanTaskModel();
                task.setId(record.getId());
                task.setPlannedStartDate(null == record.getPlannedStartDate() ? "-" : format.format(record.getPlannedStartDate()));
                task.setPlannedFinishDate(null == record.getPlannedFinishDate() ? "-" : format.format(record.getPlannedFinishDate()));
                task.setActualStartDate(null == record.getActualStartDate() ? "-" : format.format(record.getActualStartDate()));
                task.setActualFinishDate(null == record.getActualFinishDate() ? "-" : format.format(record.getActualFinishDate()));
                task.setName(record.getName());
                task.setActived(record.isActived());
                task.setStarted(record.isStarted());
                task.setFinished(record.isFinished());
                task.setCanceled(record.isCanceled());
                task.setGrouped(record.isGrouped());
                task.setPostponed(record.isPostponed());
                task.setPostponedDays(record.getPostponedDays());
                task.setAbnormalCount(record.getAbnormalCount());
                tasks.add(task);
            }
        }
        
        return tasks;
    }
    
    @Override
    public List<OrderPostponedDetails> getOrderPostponedDetails(String keys)
    {
        if (StringUtils.isEmpty(keys))
        {
            return Collections.emptyList();
        }
        
        OrderPostponedDetailsBatchRequest request = new OrderPostponedDetailsBatchRequest();
        request.setOrderIds(new HashSet<String>(Arrays.asList(keys.split(","))));
        
        String url = GatewayService.getServiceUrl("/schedule/orders/postponed/details");
        OrderPostponedDetailsBatchResponse response = template.postForObject(url, request, OrderPostponedDetailsBatchResponse.class);
        
        if (null == response)
        {
            return Collections.emptyList();
        }
        
        return CollectionUtils.isEmpty(response.getRecords()) ? Collections.emptyList() : response.getRecords();
    }
    
    @Override
    public void updateOrderUrgent(String orderId, Integer ifUrgent, String urgentName)
    {
        String url = GatewayService.getServiceUrl("/schedule/orderUrgent/{orderId}/{ifUrgent}/{urgentName}");
        template.getForObject(url, String.class, orderId, ifUrgent, urgentName);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> correct(String orderId)
    {
        String url = GatewayService.getServiceUrl("/schedule/correct/orders/{id}");
        Map<String, Object> rsp = template.getForObject(url, Map.class, orderId);
        return rsp;
    }
}
