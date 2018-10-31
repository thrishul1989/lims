package com.todaysoft.lims.sample.service.storaging;

import com.todaysoft.lims.sample.model.SampleReceivingFormRequest;

public interface ISampleStoragingService
{
    void received(SampleReceivingFormRequest request);
    
    void storaged(String sampleCode, String storagingType);
}
