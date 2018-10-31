package com.todaysoft.lims.schedule.action;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.schedule.request.OrderPostponedDetailsBatchRequest;
import com.todaysoft.lims.schedule.response.OrderPostponedDetails;
import com.todaysoft.lims.schedule.response.OrderPostponedDetailsBatchResponse;
import com.todaysoft.lims.schedule.response.OrderScheduleMonitorPlanTask;
import com.todaysoft.lims.schedule.response.OrderScheduleMonitorResponse;
import com.todaysoft.lims.schedule.service.IOrderScheduleMonitorService;

@RestController
@RequestMapping("/schedule")
public class OrderScheduleMonitorController
{
    @Autowired
    private IOrderScheduleMonitorService service;
    
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    public OrderScheduleMonitorResponse getOrderScheduleMonitorDetails(@PathVariable String id)
    {
        return service.getOrderScheduleMonitorDetails(id);
    }
    
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
    public List<OrderScheduleMonitorPlanTask> getGroupPlanTasks(@PathVariable String id)
    {
        return service.getGroupPlanTasks(id);
    }
    
    @RequestMapping("/orders/postponed/details")
    public OrderPostponedDetailsBatchResponse orderPostponedDetailsBatchRequest(@RequestBody OrderPostponedDetailsBatchRequest request)
    {
        List<OrderPostponedDetails> records;
        
        if (CollectionUtils.isEmpty(request.getOrderIds()))
        {
            records = Collections.emptyList();
        }
        else
        {
            records = service.getOrderPostponedDetails(request.getOrderIds());
        }
        
        OrderPostponedDetailsBatchResponse response = new OrderPostponedDetailsBatchResponse();
        response.setRecords(null == records ? Collections.emptyList() : records);
        return response;
    }
    
    @RequestMapping(value = "/orderUrgent/{orderId}/{ifUrgent}/{urgentName}", method = RequestMethod.GET)
    public void orderUrgent(@PathVariable String orderId, @PathVariable Integer ifUrgent, @PathVariable String urgentName)
    {
        service.updateOrderUrgent(orderId, ifUrgent, urgentName);
    }
}
