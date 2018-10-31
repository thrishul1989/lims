package com.todaysoft.lims.product.listener;

import java.util.List;

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
import com.todaysoft.lims.product.model.response.PhenotypePageModel;
import com.todaysoft.lims.product.model.response.SimpleDisease;
import com.todaysoft.lims.product.service.IDiseaseService;
import com.todaysoft.lims.product.service.IPhenotypeService;
import com.todaysoft.lims.product.service.adapter.DiseaseCosumerEvent;
import com.todaysoft.lims.product.service.adapter.KBMAdapter;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Component("phenotypeConsumerListener")
public class PhenotypeConsumerListener implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(PhenotypeConsumerListener.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private KBMAdapter adapter;
    
    @Autowired
    private IPhenotypeService service;
    
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
        
        DiseaseCosumerEvent event = new Gson().fromJson(json, DiseaseCosumerEvent.class);
        
        if (StringUtils.isEmpty(event.getId()))
        {
            return Action.CommitMessage;
        }
        // Phenotype p = baseDaoSupport.get(Phenotype.class, event.getId());
        
        if ("create".equals(event.getActionType()) || "modify".equals(event.getActionType()))
        {
            disservice.createRelationPhenotype(event.getId());
            
            if ("modify".equals(event.getActionType())) //修改后 --关联疾病、产品
            {
                PhenotypePageModel pm = service.getPhenotypeById(event.getId());
                if (StringUtils.isNotEmpty(pm))
                {
                    List<SimpleDisease> dis = pm.getList();
                    if (Collections3.isNotEmpty(dis))
                    {
                        for (SimpleDisease s : dis)
                        {
                            
                            disservice.createRelationDisease(s.getId());
                            
                            List<String> proresult = disservice.getEnableAndNotModelProductIdDiease(s.getId());
                            for (String product : proresult)
                            {
                                disservice.createRelationProduct(product);
                            }
                        }
                        
                    }
                }
            }
            
        }
        
        if ("delete".equals(event.getActionType()))
        {
            adapter.removePhenoIndex(event.getId());
        }
        
        return Action.CommitMessage;
    }
}