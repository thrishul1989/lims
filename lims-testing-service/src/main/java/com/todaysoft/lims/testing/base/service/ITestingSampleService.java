package com.todaysoft.lims.testing.base.service;

import com.todaysoft.lims.testing.base.entity.TestingSample;

public interface ITestingSampleService
{
    TestingSample getTestingSample(String id);
    
    TestingSample getTestingSampleByCode(String code);
    
    TestingSample getTestingSampleByReceivedSample(String receivedSampleId);
    
    int getDerivedSampleCount(String id);

    String getSexByAndSampleCode(String sampleCode);
}
