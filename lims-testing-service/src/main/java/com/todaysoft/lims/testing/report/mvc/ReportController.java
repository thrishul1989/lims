package com.todaysoft.lims.testing.report.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.base.entity.TestingReport;
import com.todaysoft.lims.testing.base.entity.User;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.ons.IMessageProducer;
import com.todaysoft.lims.testing.report.dao.ReportSendSearcher;
import com.todaysoft.lims.testing.report.dao.ReportTaskSearcher;
import com.todaysoft.lims.testing.report.model.ReportAndVerifyModel;
import com.todaysoft.lims.testing.report.model.ReportCallbackModel;
import com.todaysoft.lims.testing.report.model.ReportDataModel;
import com.todaysoft.lims.testing.report.model.ReportHandleModel;
import com.todaysoft.lims.testing.report.model.ReportProcessModel;
import com.todaysoft.lims.testing.report.model.ReportProcessModelArgs;
import com.todaysoft.lims.testing.report.model.ReportRequest;
import com.todaysoft.lims.testing.report.model.ReportSendCallBackModel;
import com.todaysoft.lims.testing.report.model.ReportSendDataModel;
import com.todaysoft.lims.testing.report.model.ReportTaskDetail;
import com.todaysoft.lims.testing.report.model.ReportUploadRecordRequest;
import com.todaysoft.lims.testing.report.model.ReportUploadRequest;
import com.todaysoft.lims.testing.report.model.ResponseModel;
import com.todaysoft.lims.testing.report.model.ReturnModel;
import com.todaysoft.lims.testing.report.model.SendDataRequest;
import com.todaysoft.lims.testing.report.model.TestingReportAssignModel;
import com.todaysoft.lims.testing.report.model.TestingReportPagingRequest;
import com.todaysoft.lims.testing.report.service.IReportService;

@RestController
@RequestMapping("/bpm/testing/report")
public class ReportController
{
    @Autowired
    private IReportService service;
    
    @Autowired
    private IMessageProducer producer;
    
    @Autowired
    private SMMAdapter smmadapter;
    
    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public Pagination<TestingReport> paging(@RequestBody(required = false) TestingReportPagingRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        if (null == request)
        {
            request = new TestingReportPagingRequest();
            request.setPageNo(1);
            request.setPageSize(10);
        }
        UserMinimalModel loginer = smmadapter.getLoginUser(token);
        
        ReportTaskSearcher searcher = new ReportTaskSearcher();
        if (null != loginer)
        {
            searcher.setUserId(loginer.getId());
        }
        searcher.setMarketingCenter(request.getMarketingCenter());
        searcher.setOrderCode(request.getOrderCode());
        searcher.setStatus(request.getStatus());
        searcher.setAnalType(request.getAnalType());
        searcher.setContractNumber(request.getContractNumber());
        searcher.setCustomer(request.getCustomer());
        searcher.setProductCode(request.getProductCode());
        searcher.setProductName(request.getProductName());
        searcher.setTestUnit(request.getTestUnit());
        searcher.setSampleCode(request.getSampleCode());
        searcher.setTechnicalMan(request.getTechnicalMan());
        searcher.setInspectMan(request.getInspectMan());
        searcher.setStartDate(request.getStartDate());
        searcher.setEndDate(request.getEndDate());
        searcher.setResubmit(request.getResubmit());
        searcher.setProductDutyMan(request.getProductDutyMan());
        Pagination<TestingReport> pagination =
            service.paging(searcher, null == request.getPageNo() ? 1 : request.getPageNo(), null == request.getPageSize() ? 10 : request.getPageSize());
        /*  if (null != pagination && Collections3.isNotEmpty(pagination.getRecords()))
          {
              for (TestingReport tr : pagination.getRecords())
              {
                  if (Collections3.isNotEmpty(tr.getReviewList()))
                  {
                      tr.setRemark(Collections3.getLast(tr.getReviewList()).getReviewOpinion());
                  }
              }
          }*/
        return pagination;
    }
    
    @RequestMapping(value = "assign/tasks", method = RequestMethod.POST)
    public Pagination<TestingReport> assignPaging(@RequestBody(required = false) TestingReportPagingRequest request)
    {
        if (null == request)
        {
            request = new TestingReportPagingRequest();
            request.setPageNo(1);
            request.setPageSize(10);
        }
        
        ReportTaskSearcher searcher = new ReportTaskSearcher();
        searcher.setMarketingCenter(request.getMarketingCenter());
        searcher.setOrderCode(request.getOrderCode());
        searcher.setStatus(request.getStatus());
        searcher.setAnalType(request.getAnalType());
        searcher.setContractNumber(request.getContractNumber());
        searcher.setCustomer(request.getCustomer());
        searcher.setProductCode(request.getProductCode());
        searcher.setProductName(request.getProductName());
        searcher.setTestUnit(request.getTestUnit());
        searcher.setSampleCode(request.getSampleCode());
        searcher.setTechnicalMan(request.getTechnicalMan());
        searcher.setInspectMan(request.getInspectMan());
        searcher.setStartDate(request.getStartDate());
        searcher.setEndDate(request.getEndDate());
        searcher.setResubmit(request.getResubmit());
        searcher.setReportState(request.getReportState());
        searcher.setProductDutyMan(request.getProductDutyMan());
        Pagination<TestingReport> pagination =
            service.assignPaging(searcher, null == request.getPageNo() ? 1 : request.getPageNo(), null == request.getPageSize() ? 10 : request.getPageSize());
        return pagination;
    }
    
    @RequestMapping(value = "assigned/tasks", method = RequestMethod.POST)
    public Pagination<TestingReport> assignedPaging(@RequestBody(required = false) TestingReportPagingRequest request)
    {
        if (null == request)
        {
            request = new TestingReportPagingRequest();
            request.setPageNo(1);
            request.setPageSize(10);
        }
        
        ReportTaskSearcher searcher = new ReportTaskSearcher();
        searcher.setMarketingCenter(request.getMarketingCenter());
        searcher.setOrderCode(request.getOrderCode());
        searcher.setStatus(request.getStatus());
        searcher.setAnalType(request.getAnalType());
        searcher.setContractNumber(request.getContractNumber());
        searcher.setCustomer(request.getCustomer());
        searcher.setProductCode(request.getProductCode());
        searcher.setProductName(request.getProductName());
        searcher.setTestUnit(request.getTestUnit());
        searcher.setSampleCode(request.getSampleCode());
        searcher.setTechnicalMan(request.getTechnicalMan());
        searcher.setInspectMan(request.getInspectMan());
        searcher.setStartDate(request.getStartDate());
        searcher.setEndDate(request.getEndDate());
        searcher.setResubmit(request.getResubmit());
        searcher.setReportState(request.getReportState());
        searcher.setProductDutyMan(request.getProductDutyMan());
        Pagination<TestingReport> pagination =
            service.assignedPaging(searcher, null == request.getPageNo() ? 1 : request.getPageNo(), null == request.getPageSize() ? 10 : request.getPageSize());
        return pagination;
    }
    
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public ReportTaskDetail getDetails(@PathVariable String id)
    {
        return service.getDetails(id);
    }
    
    @RequestMapping(value = "/batchUpload", method = RequestMethod.POST)
    public ReturnModel batchUpload(@RequestBody(required = false) ReportUploadRequest request)
    {
        ReturnModel rm = service.batchUpload(request);
        producer.sendReportGenerateMessage(rm.getOrderProductIds());
        return rm;
    }
    
    @RequestMapping(value = "/reportData", method = RequestMethod.POST)
    public ReportDataModel getReportDataByIds(@RequestBody(required = false) TestingReport request)
    {
        return service.getModelByReportId(request);
    }
    
    @RequestMapping(value = "/batchUpload0", method = RequestMethod.POST)
    public ReturnModel batchUpload0(@RequestBody(required = false) ReportUploadRequest request)
    {
        ReturnModel rm = service.batchUpload(request);
        producer.sendReportGenerateMessage(rm.getOrderProductIds());
        return rm;
    }
    
    @RequestMapping(value = "/dealReport", method = RequestMethod.POST)
    public String dealReport(@RequestBody(required = false) TestingReportPagingRequest request)
    {
        return service.dealReport(request);
    }
    
    @RequestMapping(value = "/submitRedirectList", method = RequestMethod.POST)
    public void submitRedirectList(@RequestBody(required = false) TestingReportPagingRequest request)
    {
        ReturnModel rm = service.updateTestingReport(request);
        producer.sendReportGenerateMessage(rm.getOrderProductIds());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TestingReport get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/getReportListByOrderId/{orderId}", method = RequestMethod.GET)
    public List<TestingReport> getReportListByOrderId(@PathVariable String orderId)
    {
        return service.getReportListByOrderId(orderId);
    }
    
    @RequestMapping(value = "/getHandleModel/{id}", method = RequestMethod.GET)
    public ReportHandleModel getHandleModel(@PathVariable String id)
    {
        return service.getHandleModel(id);
    }
    
    @RequestMapping(value = "/getProcessModel", method = RequestMethod.POST)
    public ReportProcessModel getProcessModel(@RequestBody ReportProcessModelArgs args)
    {
        return service.getProcessModel(args);
    }
    
    @RequestMapping(value = "/reportEdit", method = RequestMethod.POST)
    public String reportEdit(@RequestBody TestingReport request)
    {
        return service.reportEdit(request);
    }
    
    @RequestMapping(value = "/saveTestingResult", method = RequestMethod.POST)
    public void saveTestingResult(@RequestBody ReportProcessModel request)
    {
        service.saveTestingResult(request);
    }
    
    @RequestMapping(value = "/updataOrderProductForFile", method = RequestMethod.POST)
    public void updataOrderProductForFile(@RequestBody ReportRequest request)
    {
        service.updataOrderProductForFile(request);
    }
    
    @RequestMapping(value = "/updataReportForFile", method = RequestMethod.POST)
    public void updataReportForFile(@RequestBody ReportRequest request)
    {
        service.updataReportForFile(request);
    }
    
    @RequestMapping(value = "/insertTestingReportUploadRecord", method = RequestMethod.POST)
    public void insertTestingReportUploadRecord(@RequestBody ReportUploadRecordRequest request)
    {
        service.insertTestingReportUploadRecord(request);
    }
    
    @RequestMapping(value = "/getReportByOrderAndProduct", method = RequestMethod.POST)
    public String getReportByOrderAndProduct(@RequestBody TestingReport request)
    {
        return service.getReportByOrderAndProduct(request);
    }
    
    @RequestMapping(value = "/savePictures", method = RequestMethod.POST)
    public String savePictures(@RequestBody TestingReport request)
    {
        return service.savePictures(request);
    }
    
    @RequestMapping(value = "/saveTestingSample", method = RequestMethod.POST)
    public void saveTestingSample(@RequestBody ReportProcessModel request)
    {
        service.saveTestingSample(request);
    }
    
    @RequestMapping(value = "/saveVerifySample", method = RequestMethod.POST)
    public void saveVerifySample(@RequestBody ReportProcessModel request)
    {
        service.saveVerifySample(request);
    }
    
    @RequestMapping(value = "/solve", method = RequestMethod.POST)
    public void solve(@RequestBody TestingReport request)
    {
        ReturnModel rm = service.solve(request);
        producer.sendReportGenerateMessage(rm.getOrderProductIds());
    }
    
    @RequestMapping(value = "/generate/callback", method = RequestMethod.POST)
    public ResponseModel callback(@RequestBody ReportCallbackModel request)
    {
        return service.generateCallback(request);
    }
    
    @RequestMapping(value = "/sendReport", method = RequestMethod.POST)
    public void sendReport(@RequestBody SendDataRequest request)
    {
        service.send(request);
    }
    
    @RequestMapping(value = "/remark", method = RequestMethod.POST)
    public void sendReport(@RequestBody TestingReport request)
    {
        service.remark(request);
    }
    
    @RequestMapping(value = "/reportSendList", method = RequestMethod.POST)
    public Pagination<ReportSendCallBackModel> reportSendPaging(@RequestBody ReportSendSearcher searcher)
    {
        return service.reportSendPaging(searcher);
    }
    
    @RequestMapping(value = "/getSendData/{id}", method = RequestMethod.GET)
    public ReportSendDataModel getSendData(@PathVariable String id)
    {
        return service.getSendDataForView(id);
    }
    
    @RequestMapping(value = "/getUpdateList", method = RequestMethod.GET)
    public List<TestingReport> sendReport()
    {
        return service.getUpdateList();
    }
    
    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public void dnaExtractAssign(@RequestBody TestingReportAssignModel request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        service.assign(request, token);
    }
    
    @RequestMapping(value = "/getReportTaskUsers", method = RequestMethod.GET)
    public List<User> getReportTaskUsers()
    {
        return service.getReportTaskUsers();
    }
    
    @RequestMapping(value = "/getReportAndVerifyModelByOrderId/{orderId}", method = RequestMethod.GET)
    public List<ReportAndVerifyModel> getReportAndVerifyModelByOrderId(@PathVariable String orderId)
    {
        return service.getReportAndVerifyModelByOrderId(orderId);
    }
    
}
