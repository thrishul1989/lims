package com.todaysoft.lims.testing.biologyanaly.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.service.adapter.bmm.OrderSimpleModel;
import com.todaysoft.lims.utils.Collections3;

public class SampleRelationContext
{
    private Map<String, String> orderPrimarySample = new HashMap<String, String>();
    
    private Map<String, String> orderReferenceSample = new HashMap<String, String>();
    
    private Map<String, OrderSimpleModel> orderSimpleModel = new HashMap<String, OrderSimpleModel>();
    
    public Map<String, OrderSimpleModel> getOrderSimpleModel()
    {
        return orderSimpleModel;
    }
    
    public void setOrderSimpleModel(Map<String, OrderSimpleModel> orderSimpleModel)
    {
        this.orderSimpleModel = orderSimpleModel;
    }
    
    public void addSample(String orderId, ReceivedSample sample)
    {
        boolean isPrimarySample = "1".equals(sample.getBusinessType());
        
        if (isPrimarySample)
        {
            String primarySample = orderPrimarySample.get(orderId);
            
            if (null == primarySample)
            {
                primarySample = sample.getSampleCode();
                orderPrimarySample.put(orderId, primarySample);
            }
        }
        else
        {
            boolean isReferenceSample = "3".equals(sample.getPurpose());
            
            if (isReferenceSample)
            {
                String referenceSample = orderReferenceSample.get(orderId);
                
                if (null == referenceSample)
                {
                    referenceSample = sample.getSampleCode();
                    orderReferenceSample.put(orderId, referenceSample);
                }
            }
        }
    }
    
    public void addSample2(String orderId, ReceivedSample sample)
    {
        
            boolean isReferenceSample = "3".equals(sample.getPurpose());
            
            if (isReferenceSample)
            {
                String referenceSample = orderReferenceSample.get(orderId);
                
                if (null == referenceSample)
                {
                    referenceSample = sample.getSampleCode();
                    orderReferenceSample.put(orderId, referenceSample);
                }
            }
            else
            {
                String order = orderPrimarySample.get(sample.getSampleCode());
                
                if (null == order)
                {
                    orderPrimarySample.put(sample.getSampleCode(), orderId);
                }
            }
    }
    
    public Map<String, String> getRelationAsPrimaryKey()
    {
        String orderId;
        Map<String, String> mapping = new HashMap<String, String>();
        
        for (Map.Entry<String, String> entry : orderPrimarySample.entrySet())
        {
            orderId = entry.getKey();
            
            if (orderReferenceSample.containsKey(orderId))
            {
                mapping.put(entry.getValue(), orderReferenceSample.get(orderId));
            }
        }
        
        return mapping;
    }
    
    public Map<String, String> getRelationAsPrimaryKey2()
    {
        String sampleCode;
        Map<String, String> mapping = new HashMap<String, String>();
        
        for (Map.Entry<String, String> entry : orderPrimarySample.entrySet())
        {
            sampleCode = entry.getKey();
            String orderId = entry.getValue();
            if (orderReferenceSample.containsKey(orderId))
            {
                mapping.put(sampleCode, orderReferenceSample.get(orderId));
            }
        }
        
        return mapping;
    }
    
    public Map<String, String> getRelationAsReferenceKey()
    {
        String orderId;
        Map<String, String> mapping = new HashMap<String, String>();
        
        for (Map.Entry<String, String> entry : orderReferenceSample.entrySet())
        {
            orderId = entry.getKey();
            
            if (orderPrimarySample.containsKey(orderId))
            {
                mapping.put(entry.getValue(), orderPrimarySample.get(orderId));
            }
        }
        
        return mapping;
    }
    
    public Map<String, List<String>> getRelationAsReferenceKey2()
    {
        String orderId;
        Map<String, List<String>> mapping = new HashMap<String, List<String>>();
        
        for (Map.Entry<String, String> entry : orderReferenceSample.entrySet())
        {
            orderId = entry.getKey();
            List<String> codeList = new ArrayList<>();
            for (Map.Entry<String, String> orentry : orderPrimarySample.entrySet())
            {
                if (orderPrimarySample.containsValue(orderId))
                {
                    codeList.add(orentry.getKey());
                }
            }
            mapping.put(entry.getValue(), codeList);
        }
        
        return mapping;
    }
}
