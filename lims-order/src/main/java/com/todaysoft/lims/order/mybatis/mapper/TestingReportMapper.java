package com.todaysoft.lims.order.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.order.model.CallBackSampleModel;
import com.todaysoft.lims.order.mybatis.model.TestingReport;
import com.todaysoft.lims.order.request.MaintainReportRequest;

public interface TestingReportMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(TestingReport record);
    
    int insertSelective(TestingReport record);
    
    TestingReport selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(TestingReport record);
    
    int updateByPrimaryKeyWithBLOBs(TestingReport record);
    
    int updateByPrimaryKey(TestingReport record);
    
    List<TestingReport> search(MaintainReportRequest request);
    
    void updateLimsReportStatus(CallBackSampleModel request);
}