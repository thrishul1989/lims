package com.todaysoft.lims.testing.base.adapter;

import java.util.List;

import com.todaysoft.lims.testing.base.model.OriginalSample;
import com.todaysoft.lims.testing.base.model.TestingNode;
import com.todaysoft.lims.testing.base.model.TestingOrder;
import com.todaysoft.lims.testing.base.model.TestingProduct;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTaskVariables;

public interface IOrderAdapter
{
    TestingOrder getTestingOrder(String orderId);
    
    List<OriginalSample> getPrimaryTestingSamples(String orderId);
    
    List<OriginalSample> getSubsidiaryTestingSamples(String orderId, String sampleId);
    
    List<TestingNode> getTestingNodes(String sourceId, String targetId);
    
    List<OriginalSample> getResearchTestingSamples(String orderId);
    
    List<TestingProduct> getResearchTestingProducts(String sampleId);
    
    DNAExtractTaskVariables getReceivedSample(String sampleId);
    
    List<TestingProduct> getOrderProducts(String orderId);
}
