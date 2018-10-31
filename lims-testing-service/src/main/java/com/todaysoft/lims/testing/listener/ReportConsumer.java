package com.todaysoft.lims.testing.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.todaysoft.lims.testing.base.config.RootContext;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.listener.model.ReportEvent;
import com.todaysoft.lims.testing.report.service.IReportService;
import com.todaysoft.lims.testing.reportemail.service.IReportEmailService;
import com.todaysoft.lims.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReportConsumer implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(ReportConsumer.class);
    
    private IReportService reportService = RootContext.getBean(IReportService.class);
    
    private IReportEmailService reportEmailService = RootContext.getBean(IReportEmailService.class);
    
    @Override
    public Action consume(Message message, ConsumeContext context)
    {
        log.info("Start to consume message report ~~~~~~~~~");
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to consume message, topic {}, tag {}, id {}, key {}.", message.getTopic(), message.getTag(), message.getMsgID(), message.getKey());
        }
        
        String json = new String(message.getBody());
        
        if (log.isDebugEnabled())
        {
            log.debug("Message body as json {}.", json);
        }
        
        ReportEvent event = new Gson().fromJson(json, ReportEvent.class);
        
        VariableModel model = event.getModel();
        
        if (null != model)
        {
            //执行到
            log.info("do report ~~~~~~~~~");
            //执行保存报告任务表逻辑
            reportService.saveOrUpdateReport(model);
            log.debug("model is null");
            
            return Action.CommitMessage;
        }
        else
        {
            //判断产品完成状态消息
            reportEmailService.sendEmailcallBackOrderStatus(event.getOrderId(), event.getProductId(), event.getTag());
        }
        
        return Action.CommitMessage;
    }
}
