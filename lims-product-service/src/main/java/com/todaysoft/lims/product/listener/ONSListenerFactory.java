package com.todaysoft.lims.product.listener;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.MessageListener;

@Component
public class ONSListenerFactory
{
    @Resource(name = "productConsumerListener")
    private MessageListener productConsumerListener;
    
    public MessageListener getProductConsumerListener()
    {
        return productConsumerListener;
    }
    
    @Resource(name = "diseaseConsumerListener")
    private MessageListener diseaseConsumerListener;
    
    public MessageListener getDiseaseConsumerListener()
    {
        return diseaseConsumerListener;
    }
    
    @Resource(name = "geneConsumerListener")
    private MessageListener geneConsumerListener;
    
    public MessageListener getGeneConsumerListener()
    {
        return geneConsumerListener;
    }
    
    @Resource(name = "phenotypeConsumerListener")
    private MessageListener phenotypeConsumerListener;
    
    public MessageListener getPhenotypeConsumerListener()
    {
        return phenotypeConsumerListener;
    }
    
}
