package com.todaysoft.lims.schedule.service.core.event;

import java.util.List;
import java.util.Map;

public class ReportGenerateEvent
{
    private List<Map<String,String>> orderProductIds;

    public List<Map<String, String>> getOrderProductIds()
    {
        return orderProductIds;
    }

    public void setOrderProductIds(List<Map<String, String>> orderProductIds)
    {
        this.orderProductIds = orderProductIds;
    }

    
    
}
