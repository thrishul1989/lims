package com.todaysoft.lims.schedule.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.schedule.entity.TestingSchedule;

public class OrderSchedules
{
    private List<TestingSchedule> schedules;
    
    private List<String[]> productSamples = new ArrayList<String[]>();
    
    private Map<String, Set<String>> productSampleMethods = new HashMap<String, Set<String>>();
    
    private Map<String, TestingSchedule> productSampleMethodSchedules = new HashMap<String, TestingSchedule>();
    
    public OrderSchedules(List<TestingSchedule> schedules)
    {
        this.schedules = schedules;
        this.parse();
    }
    
    public List<String[]> groupAsProductSample()
    {
        return this.productSamples;
    }
    
    public List<String[]> groupAsProductSampleMethod(String productId, String sampleId)
    {
        String key = productId + "_" + sampleId;
        Set<String> methods = productSampleMethods.get(key);
        
        if (CollectionUtils.isEmpty(methods))
        {
            return Collections.emptyList();
        }
        
        List<String[]> psm = new ArrayList<String[]>();
        
        for (String method : methods)
        {
            psm.add(new String[] {productId, sampleId, method});
        }
        
        return psm;
    }
    
    public TestingSchedule getProductSampleMethodTestingSchedule(String productId, String sampleId, String methodId)
    {
        String key = productId + "_" + sampleId + "_" + methodId;
        return productSampleMethodSchedules.get(key);
    }
    
    private void parse()
    {
        if (CollectionUtils.isEmpty(schedules))
        {
            return;
        }
        
        String psKey;
        String psmKey;
        String[] productSample;
        Set<String> pss = new HashSet<String>();
        Set<String> psms = new HashSet<String>();
        
        for (TestingSchedule schedule : schedules)
        {
            psKey = schedule.getProductId() + "_" + schedule.getSampleId();
            psmKey = schedule.getProductId() + "_" + schedule.getSampleId() + "_" + schedule.getMethodId();
            
            if (!pss.contains(psKey))
            {
                productSample = new String[] {schedule.getProductId(), schedule.getSampleId()};
                productSamples.add(productSample);
                pss.add(psKey);
            }
            
            if (!psms.contains(psmKey))
            {
                Set<String> methods = productSampleMethods.get(psKey);
                
                if (null == methods)
                {
                    methods = new HashSet<String>();
                    productSampleMethods.put(psKey, methods);
                }
                
                methods.add(schedule.getMethodId());
                psms.add(psmKey);
            }
            
            if (null == schedule.getVerifyKey())
            {
                productSampleMethodSchedules.put(psmKey, schedule);
            }
        }
    }
}
