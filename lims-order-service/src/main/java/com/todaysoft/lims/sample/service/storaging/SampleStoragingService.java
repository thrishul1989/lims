package com.todaysoft.lims.sample.service.storaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.aliyun.openservices.ons.api.SendResult;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.google.gson.Gson;
import com.todaysoft.lims.sample.config.Configs;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleReceivingDetail;
import com.todaysoft.lims.sample.model.SampleReceivingFormRequest;
import com.todaysoft.lims.sample.service.adapter.bpm.SampleReceivedDetails;
import com.todaysoft.lims.sample.service.adapter.bpm.SampleReceivedEvent;
import com.todaysoft.lims.sample.service.adapter.bpm.SampleStoragedRequest;
import com.todaysoft.lims.sample.util.Constant;

@Service
public class SampleStoragingService implements ISampleStoragingService
{
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;

    private static Logger log = LoggerFactory.getLogger(SampleStoragingService.class);

    
    @Override
    public void received(SampleReceivingFormRequest request)
    {
        if (CollectionUtils.isEmpty(request.getSampleReceivingDetail()))
        {
            return;
        }
        
        SampleReceivedDetails sample;
        Set<String> validTypes = new HashSet<String>(Arrays.asList("1", "2", "3"));
        List<SampleReceivedDetails> samples = new ArrayList<SampleReceivedDetails>();
        
        for (SampleReceivingDetail detail : request.getSampleReceivingDetail())
        {
            if (!validTypes.contains(String.valueOf(detail.getBtype())))
            {
                continue;
            }
            
            sample = new SampleReceivedDetails();
            sample.setSampleId(detail.getId());
            sample.setQualified(Constant.SAMPLE_RECEIVING_SUCCESS == detail.getQcStatus());
            samples.add(sample);
        }
        
        if (CollectionUtils.isEmpty(samples))
        {
            return;
        }
        
        SampleReceivedEvent event = new SampleReceivedEvent();
        event.setDetails(samples);
        
        Message msg = new Message(configs.getAliyunONSTopic(), "SAMPLE_RECEIVED", new Gson().toJson(event).getBytes());
        producer.send(msg);
    }
    
    @Override
    public void storaged(String sampleCode, String storagingType)
    {
        SampleStoragedRequest request = new SampleStoragedRequest();
        request.setSampleCode(sampleCode);
        request.setStoragingType(storagingType);
        Message msg = new Message(configs.getAliyunONSTopic(), "SAMPLE_STORAGED", new Gson().toJson(request).getBytes());
        msg.setKey("storagedKey"+sampleCode+"type"+storagingType);
        SendResult sendResult = producer.send(msg);
        log.info("sampleStorageIn log:"+"storagedKey"+sampleCode+"type"+storagingType+"--MessageId:"+sendResult.getMessageId());
    }
}
