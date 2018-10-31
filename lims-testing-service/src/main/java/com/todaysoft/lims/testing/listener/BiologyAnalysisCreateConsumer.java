package com.todaysoft.lims.testing.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.todaysoft.lims.testing.base.config.RootContext;
import com.todaysoft.lims.testing.biologyanaly.service.IBiologyAnalyService;
import com.todaysoft.lims.testing.listener.model.BiologyAnalysisEvent;
import com.todaysoft.lims.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BiologyAnalysisCreateConsumer implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(BiologyAnalysisCreateConsumer.class);

    private IBiologyAnalyService biologyAnalyService = RootContext.getBean(IBiologyAnalyService.class);
    
    @Override
    public Action consume(Message message, ConsumeContext context)
    {
        log.info("Start to consume message BiologyAnalysisCreate ~~~~~~~~~");
        
        String json = new String(message.getBody());

        BiologyAnalysisEvent event = new Gson().fromJson(json, BiologyAnalysisEvent.class);
        
        String taskId = event.getTaskId();
        
        if (StringUtils.isNotEmpty(taskId))
        {

            biologyAnalyService.biologyTaskCreate(taskId);

            return Action.CommitMessage;
        }
        
        return Action.CommitMessage;
    }
}
