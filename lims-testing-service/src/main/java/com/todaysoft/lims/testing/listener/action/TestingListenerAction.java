package com.todaysoft.lims.testing.listener.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.listener.model.SampleAbnormalSolvedEvent;
import com.todaysoft.lims.testing.listener.model.SampleStoragedEvent;
import com.todaysoft.lims.testing.listener.service.ISampleEventService;

@RestController
@RequestMapping("/bpm/testing/listener")
public class TestingListenerAction
{
    private static Logger log = LoggerFactory.getLogger(TestingListenerAction.class);
    
    @Autowired
    private ISampleEventService sampleEventService;
    
    @RequestMapping(value = "/samples/storaged", method = RequestMethod.POST)
    public void sampleStoraged(@RequestBody SampleStoragedEvent event)
    {
        sampleEventService.storaged(event);
    }
    
    @RequestMapping(value = "/samples/abnormal/solved", method = RequestMethod.POST)
    public void sampleAbnormalSolved(@RequestBody SampleAbnormalSolvedEvent event)
    {
        log.info("Fire sample abnormal solved event. abnormal sample {}, strategy {}, resend sample {}",
            event.getAbnormalSampleId(),
            event.getSolvedStrategy(),
            event.getResendSampleId());
        sampleEventService.abnormalSolved(event);
    }
}
