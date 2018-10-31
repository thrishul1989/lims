package com.todaysoft.lims.testing.base.service;

import java.util.List;

import com.todaysoft.lims.testing.base.model.TestingNode;
import com.todaysoft.lims.testing.base.model.TestingStartRecord;
import com.todaysoft.lims.testing.base.request.StartOrderTestingRequest;
import com.todaysoft.lims.testing.base.request.StartVerifyTestingRequest;

public interface ITestingResolveService
{
    /**
     * 根据订单解析，获取所有需要启动的检测：订单-产品-检测方法
     */
    List<TestingStartRecord> resolve(StartOrderTestingRequest request, int type);
    
    /**
     * 根据验证解析，获取所有需要启动的检测：订单-产品-检测方法
     */
    List<TestingStartRecord> resolve(StartVerifyTestingRequest request);
    
    /**
     * 根据原样本与目标样本解析，获取原样本转化为目标样本需要的任务节点
     */
    List<TestingNode> resolve(String sourceSampleType, String targetSampleType);
    
    List<TestingStartRecord> resolveAsSingleVeritySample(StartOrderTestingRequest startRequest, int i);
    
}
