package com.todaysoft.lims.system.modules.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendFormModel;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendModel;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendSearcher;

public interface ITestingDataSendService
{

    Pagination<TestingDataSendModel> paging(TestingDataSendSearcher searcher);

    List<TestingDataSendFormModel> getDetail(String orderId);

    void send(String dataSendIds);
    
}
