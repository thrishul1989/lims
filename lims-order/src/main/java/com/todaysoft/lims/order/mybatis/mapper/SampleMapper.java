package com.todaysoft.lims.order.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.order.model.BasicSampleModel;
import com.todaysoft.lims.order.model.BasicSampleRequest;
import com.todaysoft.lims.order.model.CallBackSampleModel;
import com.todaysoft.lims.order.mybatis.model.PrimarySample;
import com.todaysoft.lims.order.request.SampleSynchronizeRequest;
import com.todaysoft.lims.order.response.GanalysisSampleInfo;
import com.todaysoft.lims.order.response.SampleKeyword;

public interface SampleMapper
{
    
    int countForSample(SampleSynchronizeRequest request);
    
    List<GanalysisSampleInfo> searchSampleList(SampleSynchronizeRequest searcher);
    
    void updateLimsSampleStatus3(CallBackSampleModel request);
    
    void updateLimsSampleStatus2(CallBackSampleModel request);
    
    void updateLimsSampleStatus1(CallBackSampleModel request);
    
    List<SampleKeyword> searchDisease(SampleSynchronizeRequest searcher);
    
    List<SampleKeyword> searchGene(SampleSynchronizeRequest orderId);
    
    List<SampleKeyword> searchPhenotype(SampleSynchronizeRequest orderId);
    
    List<PrimarySample> searchPrimarySample(SampleSynchronizeRequest searcher);
    
    List<BasicSampleModel> seacherBasicSample(BasicSampleRequest request);
}
