package com.todaysoft.lims.testing.base.adapter;

import java.util.List;

import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TestingNode;
import com.todaysoft.lims.testing.base.model.TestingProduct;

public interface IProductAdapter
{
    TaskConfig getTaskCOnfigBySemantic(String semantic);

    List<TestingNode> getTestingNodes(String sourceSampleType,String targetSampleType);
    
    String getAddressConfigByKey(String key);
}
