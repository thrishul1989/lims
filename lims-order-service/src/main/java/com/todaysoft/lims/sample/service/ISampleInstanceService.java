package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.sample.entity.Sample;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;

public interface ISampleInstanceService
{
    SampleReceiveDetail getMetadata(String sampleInstanceId);
}
