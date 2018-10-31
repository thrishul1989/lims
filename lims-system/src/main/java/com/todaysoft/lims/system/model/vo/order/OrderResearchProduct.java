package com.todaysoft.lims.system.model.vo.order;

import java.util.List;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.system.model.vo.Product;

/**
 * 订单-主样本-产品
 * @author admin
 *
 */

public class OrderResearchProduct extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private OrderResearchSample orderResearchSample;
    
    private Product product;
    
    public OrderResearchProduct()
    {
        super();
    }
    
    public OrderResearchSample getOrderResearchSample()
    {
        return orderResearchSample;
    }
    
    public void setOrderResearchSample(OrderResearchSample orderResearchSample)
    {
        this.orderResearchSample = orderResearchSample;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    private List<TemporarySample> testingsamples;
    
    private List<TemporarySample> verificationSamples;
    
    public List<TemporarySample> getTestingsamples()
    {
        return testingsamples;
    }
    
    public void setTestingsamples(List<TemporarySample> testingsamples)
    {
        this.testingsamples = testingsamples;
    }
    
    public List<TemporarySample> getVerificationSamples()
    {
        return verificationSamples;
    }
    
    public void setVerificationSamples(List<TemporarySample> verificationSamples)
    {
        this.verificationSamples = verificationSamples;
    }
    
}
