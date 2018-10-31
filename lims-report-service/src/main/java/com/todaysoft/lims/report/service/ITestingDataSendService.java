package com.todaysoft.lims.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.TestingDataSendSearcher;
import com.todaysoft.lims.report.model.TestingDataSendFormModel;
import com.todaysoft.lims.report.model.TestingDataSendModel;

public interface ITestingDataSendService
{
    
    public void createDataSend(String orderId);
    
    public Pagination<TestingDataSendModel> paging(TestingDataSendSearcher request);
    
    public List<TestingDataSendFormModel> getDetail(String orderId);
    
    public List<TestingDataSendModel> send(String sendDataIds, String token);
    
    String getSequenceCode(String scheduleId);
}
