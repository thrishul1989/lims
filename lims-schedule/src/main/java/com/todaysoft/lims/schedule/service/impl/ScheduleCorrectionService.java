package com.todaysoft.lims.schedule.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.schedule.service.IScheduleCorrectionService;
import com.todaysoft.lims.schedule.service.correction.OrderPrimaryPlanMissingCorrectionHandler;
import com.todaysoft.lims.schedule.service.correction.PaymentConfirmCorrectionHandler;
import com.todaysoft.lims.schedule.service.correction.SampleReceiveCorrectionHandler;
import com.todaysoft.lims.schedule.service.correction.ScheduleKeyHolder;

@Service
public class ScheduleCorrectionService implements IScheduleCorrectionService
{
    @Autowired
    private OrderPrimaryPlanMissingCorrectionHandler orderPrimaryPlanMissingCorrectionHandler;
    
    @Autowired
    private PaymentConfirmCorrectionHandler paymentConfirmCorrectionHandler;
    
    @Autowired
    private SampleReceiveCorrectionHandler sampleReceiveCorrectionHandler;
    
    @Override
    @Transactional
    public void correct(String orderId)
    {
        ScheduleKeyHolder keyHolder = new ScheduleKeyHolder();
        keyHolder.setOrderId(orderId);
        
        orderPrimaryPlanMissingCorrectionHandler.correct(keyHolder);
        paymentConfirmCorrectionHandler.correct(keyHolder);
        sampleReceiveCorrectionHandler.correct(keyHolder);
    }
}
