package com.todaysoft.lims.maintain.testing.service;

import java.io.IOException;

import com.todaysoft.lims.maintain.indexes.model.PrimerSqlModel;
import com.todaysoft.lims.maintain.model.OrderExportInfomation;
import com.todaysoft.lims.maintain.model.OrderSearchRequest;
import com.todaysoft.lims.persist.Pagination;

public interface ITestingOptimizeService
{
    void generateRedundantFields(String semantics);
    
    Integer validateData();
    
    PrimerSqlModel handleSangerVerify();
    
    String handleReportName();
    
    void generateTask(String id);
    
    String findTestingReports();

    void updateTestingReportRedundant();

    void updateTechnicalRecordToMap();

    void updateDataAnalysisRecordToMap();

    void updateMethodTemplate();

    void updateScheduleNotgoinTech(String orderCode);

    Pagination<OrderExportInfomation> exportAllOrderInfomation(OrderSearchRequest request);

    void updateTestingReportTechnicalMan();

    void updateOrderProductStatu();
    
    void updateTestingTaskPlanFinishDate();

    void updateTestingReportProductDutyMan();
    
    void updateDataPicForMlpaVerify();
    
    void updateOrderStatusForSchedulePlanTask();

	void updateOldNgsSequecingTask();

    void updateNgsAndBioTask(String sequecingCode,Integer tag) throws IOException;
}
