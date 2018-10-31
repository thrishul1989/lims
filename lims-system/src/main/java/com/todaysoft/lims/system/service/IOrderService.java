package com.todaysoft.lims.system.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.todaysoft.lims.system.model.syncmodel.OrderDetail;
import com.todaysoft.lims.system.model.syncmodel.OrderSyncSearchModel;
import com.todaysoft.lims.system.model.vo.*;
import com.todaysoft.lims.system.model.vo.order.*;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnnotationFailOperate;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.order.request.SampleSynchronizeRequest;
import com.todaysoft.lims.order.response.CommonOrderResponse;
import com.todaysoft.lims.order.response.GanalysisSampleInfo;
import com.todaysoft.lims.system.model.vo.contract.ContractPaymentRecord;
import com.todaysoft.lims.system.model.vo.order.orderReportForm.BaseInfoModel;
import com.todaysoft.lims.system.model.vo.payment.OrderRefund;
import com.todaysoft.lims.system.model.vo.payment.OrderRefundRecord;
import com.todaysoft.lims.system.model.vo.samplereceiving.ReceivedSample;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.AbnormalSolveRecord;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSample;

public interface IOrderService
{
    
    void updateOrder(OrderFormRequest order);
    
    public List<String> upload(HttpServletRequest request, HttpServletResponse response, ModelMap model);
    
    public void update(OrderFormRequest request);
    
    public void create(OrderFormRequest request);
    
    public Order getById(String id);
    
    public String getCodeById(String id);
    
    public Pagination<CommonOrderResponse> paging(OrderSearchRequest searcher, int pageNo, int defaultpagesize);
    
    Pagination<CommonOrderResponse> errPaging(OrderSearchRequest searcher, int pageNo, int defaultpagesize);
    
    public void delete(OrderDeleteRequest request);
    
    public List<Order> list(OrderSearchRequest request);
    
    public boolean validate(OrderSearchRequest request);
    
    public boolean validateSampleCode(OrderSearchRequest request);
    
    public boolean validateSeCode(OrderSearchRequest request);
    
    public List<Order> getOrderByContract(OrderContrctSearcher searcher);
    
    public List<Order> getOrderByAppSaleman(OrderSearchRequest searcher);
    
    void updateOrderProduct(String id);
    
    public Pagination<Order> getOrdersByStatus(OrderSearchRequest searcher, int pageNo, int defaultpagesize);
    
    public List<OrderSampleView> getOrderSampleView(String orderId);
    
    TestingSchedule getOnlySchedule(ScheduleQuery request);
    
    List<TestingScheduleHistory> getTSHByScheduleId(String scheduleID);
    
    List<TestingNode> getPrfeNode(Temproary data);
    
    TestingMethod getTMById(String testingMethodId);
    
    public OrderProduct getOrderProductById(String id);
    
    public Pagination<OrderSampleDetails> orderSampleList(OrderSearchRequest searcher, int pageNo, int defaultpagesize);
    
    public OrderSampleDetails getSampleDetailById(String searcher);
    
    public OrderPrimarySample getOrderPrimarySampleById(String sampleId);
    
    public OrderSubsidiarySample getOrderSubsidiarySampleById(String sampleId);
    
    public OrderResearchSample getOrderResearchSampleById(String sampleId);
    
    public List<AppSampleTransport> getAppSampleTransport(OrderSearchRequest searcher);
    
    public List<AppSampleBackApply> getBackSampleTransport(OrderSearchRequest searcher);
    
    public Pagination<CommonOrderResponse> unConfirmOrderList(OrderSearchRequest searcher, int pageNo, int defaultpagesize);
    
    public Pagination<CommonOrderResponse> confirmedOrderList(OrderSearchRequest searcher, int pageNo, int defaultpagesize);
    
    public void confirmOrderContract(OrderFormRequest request);
    
    List<TestingSchedule> getTestingSchedules(String id);
    
    TestingMethod getTestingMethodById(String id);
    
    TestingSchedule getScheduleById(String id);
    
    public List<OrderResearchSampleForUpload> uploadSearcherSample(MultipartFile uploadData, String id);
    
    List<TestingSchedule> getTestingSchedulesByData(ScheduleQuery request);
    
    TestingTaskResultRequest getDataById(String id);
    
    public List<Order> geneBilllist(OrderSearchRequest searcher);
    
    public void addOrderSample(OrderFormRequest request);
    
    TestingSample getTestingSample(String id);
    
    OrderSampleView getOrderSampleViewBySampleId(String sampleld);
    
    OrderSampleDetails getOrderSampleDetailBySampleId(String sampleld);
    
    ReceivedSample getReceivedSample(String id);
    
    TestingtechnicalanalyrecordTempory getSangerVerifyRecordById(String id);
    
    public List<OrderSubsidiarySample> countSubSampleByRelationId(OrderSearchRequest searcher);
    
    TestingtechnicalanalyrecordTempory getMLPAVerifyRecordById(String id);
    
    TestingtechnicalanalyrecordTempory getQPCRVerifyRecordById(String id);
    
    public Map<String, Object> callbackOrderScheulStatus(String id);
    
    public List<OrderRefund> getOrderRefundList(String id);
    
    public OrderRefundRecord getOrderRefundHandler(String id);
    
    String getIdByCode(String code);
    
    public void addErrorSample(AddErrorSampleFormRequest request);
    
    public AppSampleTransport getAppSampleTransportById(String id);
    
    public List<AppSampleTransportRelation> getAppSampleTransportRelationList(String id);
    
    public String getSampleIdByCode(OrderSearchRequest sampleErrorNewNo);
    
    public Integer getOrderStatusByCode(String code);
    
    String getSequenceCode(String sequenceCode);
    
    List<ContractReconciliationRecord> getContractRecRecordByOrderId(String orderId);
    
    List<ContractPaymentRecord> getSettlePaymentByOrderId(String orderId);
    
    List<FinanceInvoiceTask> getInvoiceInfoByOrderId(String orderId);
    
    AbnormalSolveRecord getRemarkByAbnormal(String taskId);
    
    public String changePaymentConfirmStatus();
    
    public String changeOrderProduct();
    
    public String changeStatus();
    
    public String searchReceiveStatus();
    
    List<AbnormalSolveModel> getCancelDetailView(String scheduleId);
    
    TestingReSampleNoSampleModel getReSampleNoSampleRecords(String sampleId);
    
    public String exportOrderFinancialFile(OrderSearchRequest searcher);
    
    List<OrderSampleTransportModel> getAppSampleTransportRelationByOrderCode(String orderCode);
    
    List<OrderSampleReceiveingModel> getSampleReceiveingModelByOrderId(String orderId);
    
    OrderPaymentModel getOrderPaymentModelByOrderId(String orderId);
    
    public String exportOrderExcludeContractFile(OrderSearchRequest searcher);
    
    public String exportOrderIncludeScheduleFile(OrderSearchRequest searcher);
    
    Date getTaskStartDate(String scheduleId);
    
    List<BaseInfoModel> getOrderForBaseInfoList(OrderSearchRequest request);
    
    TestingTaskResult getTestingTaskResultById(String id);
    
    public void synchronizeOrderPayTime();
    
    public List<GanalysisSampleInfo> searchSamplesByCustomerId(SampleSynchronizeRequest request);
    
    public void modifyOrderDisese(OrderFormRequest request);
    
    public String resolveAccountStatement(String data);
    
    Boolean checkOrderTechnicalStatus(String id);
    
    Pagination<ProductCancelRecord> cancelOrderProductSechedulePaging(ProductScheduleCancelSearcher searcher, int pageNo, int defaultpagesize);
    
    ProductCancelRecord getProductCancelRecordById(String id);

    TestingTaskRequest getDNATaskByHistory(TestingScheduleHistory history);

    TestingScheduleHistory getScheduleHistoryByTaskAndSchedule(ScheduleHistoryRequest request);

    BiologyAnnotationFailOperate getAnnotationFailOperateByTask(String taskId);

    List<OrderDetail> batchSearch(OrderSyncSearchModel request);

    OrderDetail searchByOrderCode(String orderFormCode);

    String getOrderSyncTest();
}
