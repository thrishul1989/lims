package com.todaysoft.lims.product.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.product.service.IDiseaseService;
import com.todaysoft.lims.product.service.adapter.KBMAdapter;
import com.todaysoft.lims.product.service.adapter.ProductCosumerEvent;
import com.todaysoft.lims.utils.StringUtils;

@Component("productConsumerListener")
public class ProductConsumerListener implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(ProductConsumerListener.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private KBMAdapter adapter;
    
    @Autowired
    private IDiseaseService disservice;
    
    @Override
    @Transactional
    public Action consume(Message message, ConsumeContext context)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to consume message, topic {}, tag {}, id {}, key {}.", message.getTopic(), message.getTag(), message.getMsgID(), message.getKey());
        }
        
        String json = new String(message.getBody());
        
        if (log.isDebugEnabled())
        {
            log.debug("Message body as json {}.", json);
        }
        
        ProductCosumerEvent event = new Gson().fromJson(json, ProductCosumerEvent.class);
        
        if (StringUtils.isEmpty(event.getId()))
        {
            return Action.CommitMessage;
        }
        // Product product = baseDaoSupport.get(Product.class, event.getId());
        
        if ("create".equals(event.getActionType()) || "modify".equals(event.getActionType()))
        {
            disservice.createRelationProduct(event.getId());
        }
        
        if ("delete".equals(event.getActionType()))
        {
            adapter.removeIndex(event.getId());
        }
        
        return Action.CommitMessage;
    }
    
}
