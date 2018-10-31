package com.todaysoft.lims.testing.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.TestingReport;
import com.todaysoft.lims.testing.base.entity.User;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.report.dao.ReportSendSearcher;
import com.todaysoft.lims.testing.report.dao.ReportTaskSearcher;
import com.todaysoft.lims.testing.report.model.*;
import com.todaysoft.lims.testing.report.request.UpdateMutationSourceRequest;

public interface IReportService
{
    Pagination<TestingReport> paging(ReportTaskSearcher searcher, int pageNo, int pageSize);
    
    Pagination<TestingReport> assignPaging(ReportTaskSearcher searcher, int pageNo, int pageSize);
    
    void saveOrUpdateReport(VariableModel model);
    
    ReportTaskDetail getDetails(String id);
    
    ReturnModel batchUpload(ReportUploadRequest request);
    
    ReportDataModel getModelByReportId(TestingReport request);
    
    String dealReport(TestingReportPagingRequest request);
    
    ReturnModel updateTestingReport(TestingReportPagingRequest request);
    
    TestingReport get(String id);
    
    List<TestingReport> getReportListByOrderId(String orderId);
    
    ReportHandleModel getHandleModel(String id);
    
    ReportProcessModel getProcessModel(ReportProcessModelArgs args);
    
    String reportEdit(TestingReport report);
    
    void saveTestingResult(ReportProcessModel request);
    
    void updataOrderProductForFile(ReportRequest request);
    
    void updataReportForFile(ReportRequest request);
    
    String getReportByOrderAndProduct(TestingReport report);
    
    String savePictures(TestingReport report);
    
    void saveTestingSample(ReportProcessModel request);
    
    void saveVerifySample(ReportProcessModel request);
    
    ReturnModel solve(TestingReport request);
    
    ResponseModel generateCallback(ReportCallbackModel request);
    
    void send(SendDataRequest request);
    
    void remark(TestingReport request);
    
    Pagination<ReportSendCallBackModel> reportSendPaging(ReportSendSearcher searcher);
    
    ReportSendDataModel getSendDataForView(String id);
    
    List<TestingReport> getUpdateList();
    
    void assign(TestingReportAssignModel request, String token);
    
    Pagination<TestingReport> assignedPaging(ReportTaskSearcher searcher, int pageNo, int pageSize);
    
    List<User> getReportTaskUsers();
    
    List<ReportAndVerifyModel> getReportAndVerifyModelByOrderId(String orderId);
    
    DataColAndMutationDataModel searchMutationListByAnalsysiSampleId(String analysisSampleId);
    
    List<CollectionCnvAnalysisPicResultList> collectionCapcnvData(String analysisSampleId);

    void insertTestingReportUploadRecord(ReportUploadRecordRequest request);

    void updateMutationSource(UpdateMutationSourceRequest mutationSourceRequest);


}
