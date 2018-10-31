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
import com.todaysoft.lims.product.model.response.GenePageModel;
import com.todaysoft.lims.product.model.response.SimpleDisease;
import com.todaysoft.lims.product.service.IDiseaseService;
import com.todaysoft.lims.product.service.IDocumentService;
import com.todaysoft.lims.product.service.adapter.GeneCosumerEvent;
import com.todaysoft.lims.product.service.adapter.KBMAdapter;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Component("geneConsumerListener")
public class GeneConsumerListener implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(GeneConsumerListener.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private KBMAdapter adapter;
    
    @Autowired
    private IDocumentService service;
    
    @Autowired
    private IDiseaseService disservice;
    
    @Autowired
    private IDocumentService docservice;
    
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
        
        GeneCosumerEvent event = new Gson().fromJson(json, GeneCosumerEvent.class);
        
        if (StringUtils.isEmpty(event.getId()))
        {
            return Action.CommitMessage;
        }
        // Gene gene = baseDaoSupport.get(Gene.class, event.getId());
        
        if ("create".equals(event.getActionType()) || "modify".equals(event.getActionType()))
        {
            
            disservice.createRelationGene(event.getId());
            
            if ("modify".equals(event.getActionType()))
            {
                GenePageModel genePageModel = disservice.getGeneById(event.getId());
                if (StringUtils.isNotEmpty(genePageModel))
                {
                    List<SimpleDisease> dis = genePageModel.getDiseaseGeneList();
                    if (Collections3.isNotEmpty(dis))
                    {
                        for (SimpleDisease s : dis)
                        {
                            disservice.createRelationDisease(s.getId());//关联基因-疾病索引
                            
                            List<String> proresult = disservice.getEnableAndNotModelProductIdDiease(s.getId());
                            for (String product : proresult)
                            {
                                disservice.createRelationProduct(product);//关联疾病-产品索引
                            }
                            
                        }
                    }
                }
                
            }
        }
        if ("delete".equals(event.getActionType()))
        {
            adapter.removeGeneIndex(event.getId());
        }
        
        return Action.CommitMessage;
    }
}