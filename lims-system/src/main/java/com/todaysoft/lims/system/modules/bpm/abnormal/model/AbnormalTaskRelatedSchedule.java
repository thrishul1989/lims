package com.todaysoft.lims.system.modules.bpm.abnormal.model;

import com.todaysoft.lims.system.model.vo.samplereceiving.ReceivedSample;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSample;


public class AbnormalTaskRelatedSchedule
{
    private String id;
    
    private String orderCode;
    
    private String marketingCenter;
    
    private String productName;
    
    private String testingMethod;
    
    private String chrLocation;
    
    private String geneSymbol;
    
    private ReceivedSample receivedSample;
    
    public ReceivedSample getReceivedSample()
    {
        return receivedSample;
    }
    
    public void setReceivedSample(ReceivedSample receivedSample)
    {
        this.receivedSample = receivedSample;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getTestingMethod()
    {
        return testingMethod;
    }
    
    public void setTestingMethod(String testingMethod)
    {
        this.testingMethod = testingMethod;
    }
    
    public String getChrLocation()
    {
        return chrLocation;
    }
    
    public void setChrLocation(String chrLocation)
    {
        this.chrLocation = chrLocation;
    }
    
    public String getGeneSymbol()
    {
        return geneSymbol;
    }
    
    public void setGeneSymbol(String geneSymbol)
    {
        this.geneSymbol = geneSymbol;
    }
}
