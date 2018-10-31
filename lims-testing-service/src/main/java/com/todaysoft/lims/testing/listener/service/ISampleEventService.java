package com.todaysoft.lims.testing.listener.service;

import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.TestingResamplingRecord;
import com.todaysoft.lims.testing.listener.model.SampleAbnormalSolvedEvent;
import com.todaysoft.lims.testing.listener.model.SampleReceivedEvent;
import com.todaysoft.lims.testing.listener.model.SampleStoragedEvent;

public interface ISampleEventService
{
    void received(SampleReceivedEvent event);
    
    void storaged(SampleStoragedEvent event);
    
    void abnormalSolved(SampleAbnormalSolvedEvent event);
    
    Order getOrderByReceivedSampleCode(String code);
    
    TestingResamplingRecord getRecordByReceivedResendSampleId(String sampleId);
}
