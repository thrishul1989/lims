package com.todaysoft.lims.testing.base.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.entity.rapid.RapidOrder;
import com.todaysoft.lims.testing.base.entity.rapid.RapidUserArchive;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.utils.StringUtils;

public class BatchWrapTestingTaskContext
{
    private Map<String, List<RapidOrder>> orders;
    
    private Map<String, List<Product>> products;
    
    private Map<String, List<RapidUserArchive>> productPrincipals;
    
    private Map<String, List<TestingMethod>> methods;
    
    private Map<String, TestingTaskRunVariable> variables;
    
    private Map<String, TestingSample> testingSamples;
    
    private Map<String, ReceivedSample> receivedSamples;
    
    private Map<String, List<SangerVerifyRecord>> sangerVerifyRecords;

    private Map<String, String> relations = Maps.newHashMap();
    
    public RapidOrder getOrder(String taskId)
    {
        if (CollectionUtils.isEmpty(orders))
        {
            return null;
        }
        
        List<RapidOrder> records = orders.get(taskId);
        
        if (CollectionUtils.isEmpty(records))
        {
            return null;
        }
        
        return records.get(0);
    }

    public String getRelation(String analRecordId)
    {
        String relationResult = relations.get(analRecordId);

        return StringUtils.isEmpty(relationResult) ? "" : relationResult;
    }
    
    public List<Product> getProducts(String taskId)
    {
        if (CollectionUtils.isEmpty(products))
        {
            return Collections.emptyList();
        }
        
        List<Product> records = products.get(taskId);
        
        return null == records ? Collections.emptyList() : records;
    }
    
    public List<RapidUserArchive> getProductPrincipals(String taskId)
    {
        if (CollectionUtils.isEmpty(productPrincipals))
        {
            return Collections.emptyList();
        }
        
        List<RapidUserArchive> records = productPrincipals.get(taskId);
        
        return null == records ? Collections.emptyList() : records;
    }
    
    public List<TestingMethod> getMethods(String taskId)
    {
        if (CollectionUtils.isEmpty(methods))
        {
            return Collections.emptyList();
        }
        
        List<TestingMethod> records = methods.get(taskId);
        
        return null == records ? Collections.emptyList() : records;
    }
    
    public TestingSample getTestingSample(String taskId)
    {
        if (CollectionUtils.isEmpty(testingSamples))
        {
            return null;
        }
        
        return testingSamples.get(taskId);
    }
    
    public ReceivedSample getReceivedSample(String taskId)
    {
        if (CollectionUtils.isEmpty(receivedSamples))
        {
            return null;
        }
        
        return receivedSamples.get(taskId);
    }
    
    public <T> T getVariables(String taskId, Class<T> clazz)
    {
        if (CollectionUtils.isEmpty(variables))
        {
            return null;
        }
        
        TestingTaskRunVariable record = variables.get(taskId);
        
        if (null == record || StringUtils.isEmpty(record.getText()))
        {
            return null;
        }
        
        return JsonUtils.asObject(record.getText(), clazz);
    }
    
    public List<SangerVerifyRecord> getSangerVerifyRecords(String taskId)
    {
        if (CollectionUtils.isEmpty(sangerVerifyRecords))
        {
            return Collections.emptyList();
        }
        
        List<SangerVerifyRecord> records = sangerVerifyRecords.get(taskId);
        
        return null == records ? Collections.emptyList() : records;
    }
    
    public void setOrders(Map<String, List<RapidOrder>> orders)
    {
        this.orders = orders;
    }
    
    public void setProducts(Map<String, List<Product>> products)
    {
        this.products = products;
    }
    
    public void setMethods(Map<String, List<TestingMethod>> methods)
    {
        this.methods = methods;
    }
    
    public void setVariables(Map<String, TestingTaskRunVariable> variables)
    {
        this.variables = variables;
    }
    
    public void setTestingSamples(Map<String, TestingSample> testingSamples)
    {
        this.testingSamples = testingSamples;
    }
    
    public void setReceivedSamples(Map<String, ReceivedSample> receivedSamples)
    {
        this.receivedSamples = receivedSamples;
    }
    
    public void setProductPrincipals(Map<String, List<RapidUserArchive>> productPrincipals)
    {
        this.productPrincipals = productPrincipals;
    }
    
    public void setSangerVerifyRecords(Map<String, List<SangerVerifyRecord>> sangerVerifyRecords)
    {
        this.sangerVerifyRecords = sangerVerifyRecords;
    }

    public Map<String, String> getRelations() {
        return relations;
    }

    public void setRelations(Map<String, String> relations) {
        this.relations = relations;
    }
}
