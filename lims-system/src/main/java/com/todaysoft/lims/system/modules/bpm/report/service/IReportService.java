package com.todaysoft.lims.system.modules.bpm.report.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportAndVerifyModel;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportCallbackModel;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportDataModel;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportHandleModel;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportProcessModel;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportProcessModelArgs;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportRequest;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportSendCallBackModel;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportSendDataModel;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportTaskDetail;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportTaskSearch;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportUploadRecordRequest;
import com.todaysoft.lims.system.modules.bpm.report.model.ResponseModel;
import com.todaysoft.lims.system.modules.bpm.report.model.ReturnModel;
import com.todaysoft.lims.system.modules.bpm.report.model.TestingReport;
import com.todaysoft.lims.system.modules.bpm.report.model.TestingReportAssignModel;
import com.todaysoft.lims.system.modules.bpm.report.service.request.GenerateReportRequest;
import com.todaysoft.lims.system.modules.bpm.report.service.request.ReportSendSearcher;
import com.todaysoft.lims.system.modules.bpm.report.service.request.SendDataRequest;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;

public interface IReportService
{
    Pagination<TestingReport> paging(ReportTaskSearch searcher, int pageNo, int pageSize);
    
    ReportTaskDetail getDetails(String id);
    
    ReturnModel unZip(MultipartFile uploadData);
    
    //
    //    void handle(ExtraSampleTaskHandleRequest request);
    String downloadData(InputStream is, ReportDataModel model, String reportId);
    
    ReportDataModel getReportDataByIds(String reportId);
    
    String dealReport(String path, String wordPath, String id);
    
    ReturnModel unZip0(MultipartFile uploadData);
    
    Integer validateData();
    
    void updateTestingReport(TestingReport request);
    
    String findTestingReports();
    
    void handleSangerVerify();
    
    TestingReport get(String id);
    
    void handleReportName();
    
    List<com.todaysoft.lims.system.modules.report.model.TestingReport> getReportListByOrderId(String orderId);
    
    ReportHandleModel getHandleModel(String id);
    
    ReportProcessModel getProcessModel(ReportProcessModelArgs args);
    
    String reportEdit(TestingReport report);
    
    void saveTestingResult(ReportProcessModel request);
    
    String uploadSingleFile(MultipartFile uploadFile);
    
    void updataOrderProductForFile(ReportRequest request);
    
    void updataReportForFile(ReportRequest request);
    
    String getReportByOrderAndProduct(TestingReport request);
    
    String savePictures(TestingReport request);
    
    void saveTestingSample(ReportProcessModel request);
    
    void saveVerifySample(ReportProcessModel request);
    
    void solve(TestingReport request);

    void updateTestingReportRedundant();

    void updateTechnicalRecordToMap();

    void updateDataAnalysisRecordToMap();

    ResponseModel reportCallbackModel(ReportCallbackModel model);

    void send(SendDataRequest request);
    
    void remark(TestingReport request);
    
    Pagination<ReportSendCallBackModel> reportSendPaging(ReportSendSearcher searcher);
    
    ReportSendDataModel getSendData(String id);

    void updateReportStateJob();

    Pagination<TestingReport> assignPaging(ReportTaskSearch searcher, Integer pageNo, Integer pageSize);

    void updateTestingReportTechnicalMan();

    List<UserDetailsModel> getTechnicalManByReport(String reportIds);

    void assign(TestingReportAssignModel model);

    Pagination<TestingReport> assignEdPaging(ReportTaskSearch searcher, Integer pageNo, Integer pageSize);
    
    List<ReportAndVerifyModel> getReportAndVerifyModelByOrderId(String orderId);

    void updateTestingReportProductDutyMan();

    void generateReport(GenerateReportRequest request);

    void insertTestingReportUploadRecord(ReportUploadRecordRequest request);

}
