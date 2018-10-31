package com.todaysoft.lims.testing.ons.event;

import java.util.Map;

public class ReportVerifyEvent
{
    private Map<String,String> orderProductId;
    private String reviewResult;//审核结果

    public Map<String, String> getOrderProductId()
    {
        return orderProductId;
    }

    public void setOrderProductId(Map<String, String> orderProductId)
    {
        this.orderProductId = orderProductId;
    }

    public String getReviewResult()
    {
        return reviewResult;
    }

    public void setReviewResult(String reviewResult)
    {
        this.reviewResult = reviewResult;
    }

}
