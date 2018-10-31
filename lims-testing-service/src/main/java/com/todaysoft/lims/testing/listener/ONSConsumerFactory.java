package com.todaysoft.lims.testing.listener;

import com.aliyun.openservices.ons.api.MessageListener;

public class ONSConsumerFactory
{
    public static MessageListener getSampleConsumer()
    {
        return new SampleConsumer();
    }
    
    public static MessageListener getOrderConsumerListener()
    {
        
        return new OrderAutoStartConsumer();
    }
    
    public static MessageListener getReportConsumerListener()
    {
        
        return new ReportConsumer();
    }
    
    public static MessageListener getBiologyAnalysisCreateConsumerListener()
    {
        
        return new BiologyAnalysisCreateConsumer();
    }
    
}
