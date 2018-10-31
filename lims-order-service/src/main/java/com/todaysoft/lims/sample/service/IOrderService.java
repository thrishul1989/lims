package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.entity.FinanceInvoiceTask;
import com.todaysoft.lims.sample.entity.Product;
import com.todaysoft.lims.sample.entity.TestingMethod;
import com.todaysoft.lims.sample.entity.contract.ContractContent;
import com.todaysoft.lims.sample.entity.contract.ContractPaymentRecord;
import com.todaysoft.lims.sample.entity.order.*;
import com.todaysoft.lims.sample.entity.payment.OrderRefund;
import com.todaysoft.lims.sample.entity.payment.OrderRefundRecord;
import com.todaysoft.lims.sample.model.*;
import com.todaysoft.lims.sample.model.order.*;
import com.todaysoft.lims.sample.model.request.SampleSearchRequest;
import com.todaysoft.lims.sample.model.request.TestingNodeSearchRequest;
import com.todaysoft.lims.sample.ons.event.SampleAbnormalSolveEvent;

import java.util.List;

public interface IOrderService
{
    List<TestingTask> getOrderProductTestingTasks(String mId, String sampleInstId, String productId);
    
    String createOrder(OrderFormRequest request);

    void modifyOrder(Order order);

    Order getOrderById(String id);
    
    String getCodeById(String id);
    
    Pagination<Order> pagingOrder(OrderSearchRequest request);
    
    Pagination<Order> errPagingOrder(OrderSearchRequest request);
    
    void updateOrder(OrderFormRequest request);
    
    void deleteOrder(OrderDeleteRequest request);
    
    List<Order> orderList(OrderSearchRequest request);
    
    //List<OrderSampleView> getSampleByView(OrderSearchRequest request);
    
    TestingOrder getTestingOrderById(String id);
    
    List<TestingProduct> getOrderProducts(String id);
    
    List<TestingSample> getTestingSamples(SampleSearchRequest request);
    
    List<TestingNode> getTestingTaskNodes(TestingNodeSearchRequest request);
    
    List<TestingProduct> getResearchTestingProducts(String sampleId);
    
    DNAExtractVariables getReceivedSample(String sampleId);
    
    boolean validate(OrderFormRequest request);
    
    boolean validateSampleCode(OrderSearchRequest request);
    
    boolean validateSeCode(OrderSearchRequest request);
    
    List<Order> getOrderByContract(OrderContrctSearcher searcher);

    List<Order> getOrderByAppSaleman(OrderSearchRequest searcher);
    
    OrderSimpleModel getOrderSimpleModel(BioSampleSimpleModel bssm);
    
    void updateOrderProduct(String id);
    
    List<OrderSampleView> getOrderSampleView(String id);
    
    List<OrderVerifySampleModel> getOrderVerifySamples(String id);
    
    OrderVerifySampleModel getOrderSubSampleById(String sampleId);
    
    Pagination<Order> getOrdersByStatus(OrderSearchRequest request);
    
    TestingMethod getTMById(String testingMethodId);
    
    OrderProduct getOrderProductById(String id);
    
    Pagination<OrderSampleDetails> orderSampleList(OrderSearchRequest request);
    
    OrderSampleDetails getSampleDetailById(String id);
    
    OrderPrimarySample getOrderPrimarySampleById(String id);
    
    OrderSubsidiarySample getOrderSubsidiarySampleById(String id);
    
    OrderResearchSample getOrderResearchSampleById(String id);
    
    List<AppSampleTransport> getAppSampleTransport(OrderSearchRequest request);
    
    List<AppSampleBackApply> getBackSampleTransport(OrderSearchRequest request);
    
    Pagination<Order> unConfirmOrderList(OrderSearchRequest request);
    
    Pagination<Order> confirmedOrderList(OrderSearchRequest request);
    
    void confirmOrderContract(OrderFormRequest request);
    
    List<Order> geneBilllist(OrderSearchRequest request);
    
    void addOrderSample(OrderFormRequest request);
    
    OrderSampleView getOrderSampleViewBySampleId(String sampleld);
    
    void setSampleAbnormal(SetSampleAbnormalRequest request);
    
    OrderSampleDetailsResponse getOrderSampleDetails(String id);
    
    List<OrderSubsidiarySample> countSubSampleByRelationId(OrderSearchRequest id);
    
    List<OrderRefund> getOrderRefundList(String orderId);
    
    OrderRefundRecord getOrderRefundHandlerInfo(String applyId);
    
    String getIdByCode(String code);
    
    SampleAbnormalSolveEvent addErrorSample(AddErrorSampleFormRequest request);
    
    AppSampleTransport getAppSampleTransportById(String id);
    
    List<AppSampleTransportRelation> getAppSampleTransportRelationList(String packageId);
    
    OrderSampleDetails getOrderSampleDetailBySampleId(String sampleld);
    
    String getSampleIdByCode(String code);
    
    Integer getOrderStatusByCode(String code);
    
    List<FinanceInvoiceTask> getTaskByOrderId(String orderId);
    
    List<FinanceInvoiceTask> getTaskByOrders(TemporaryRequest orders);
    
    void updateOrderContractAmountJob();
    
    List<ContractReconciliationRecord> getContractRecRecordByOrderId(String orderId);
    
    List<FinanceInvoiceTask> getInvoiceInfoByOrderId(String orderId);
    
    //List<FinanceInvoiceTask> getTasksByContractInvoiceInfos(TemporaryRequest orders);
    
    ContractContent getContractContentByContractId(String contractId);
    
    List<Product> getProductByContractIdAndProductNames(ContractProductRequest request);
    
    List<ContractPaymentRecord> getSettlePaymentByOrderId(String orderId);
    
    SimpleOrder getSimpleOrderById(String id);
    
    void updateOrderById(DefaultInvoiceRequest request);
    
    Order getOrderByCode(String code);
    
    TestingReSampleNoSampleModel getReSampleNoSampleRecords(String sampleId);
    
    Pagination<OrderFinancial> financialPaging(OrderSearchRequest request);
    
    List<OrderSampleTransportModel> getAppSampleTransportRelationByOrderCode(String orderCode);
    
    List<OrderSampleReceiveingModel> getSampleReceiveingModelByOrderId(String orderId);
    
    OrderPaymentModel getOrderPaymentModelByOrderId(String orderId);
    
    Pagination<OrderFinancial> clinicalOrderPaging(OrderSearchRequest request);
    
    //   Pagination<OrderScheduleModel> orderSchedulePaging(OrderSearchRequest request);
    
    //  List<TestingScheduleRequest> getScheduleByQuery(ScheduleQuery searcher);
    
    List<BaseInfoModel> getOrderForBaseInfoList(OrderSearchRequest request);
    
    void modifyOrderDisese(OrderFormRequest request);
    
    //  List<String> callOrderScheduleProduce(OrderSearchRequest request);
    
}
