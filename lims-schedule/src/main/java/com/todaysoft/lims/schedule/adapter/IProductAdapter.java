package com.todaysoft.lims.schedule.adapter;

import java.util.List;

import com.todaysoft.lims.schedule.model.TestingNode;

public interface IProductAdapter
{

    List<TestingNode> getTestingNodes(String sourceSampleType,String targetSampleType);
}
