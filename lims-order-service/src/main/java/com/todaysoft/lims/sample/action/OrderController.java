package com.todaysoft.lims.sample.action;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.entity.FinanceInvoiceTask;
import com.todaysoft.lims.sample.entity.Product;
import com.todaysoft.lims.sample.entity.SampleOrder;
import com.todaysoft.lims.sample.entity.TestingMethod;
import com.todaysoft.lims.sample.entity.contract.ContractPaymentRecord;
import com.todaysoft.lims.sample.entity.order.*;
import com.todaysoft.lims.sample.entity.payment.OrderRefund;
import com.todaysoft.lims.sample.entity.payment.OrderRefundRecord;
import com.todaysoft.lims.sample.model.*;
import com.todaysoft.lims.sample.model.order.*;
import com.todaysoft.lims.sample.model.request.OrderListRequest;
import com.todaysoft.lims.sample.model.request.SampleSearchRequest;
import com.todaysoft.lims.sample.model.request.TestingNodeSearchRequest;
import com.todaysoft.lims.sample.ons.IMessageProducer;
import com.todaysoft.lims.sample.ons.event.SampleAbnormalSolveEvent;
import com.todaysoft.lims.sample.service.IOrderService;
import com.todaysoft.lims.sample.service.ISampleReceiveService;
import com.todaysoft.lims.sample.service.ISampleReceivingService;
import com.todaysoft.lims.sample.service.adapter.TestingAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bmm/order")
public class OrderController
{
    @Autowired
    private ISampleReceiveService service;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ISampleReceivingService sampleReceivingService;
    
    @Autowired
    private IMessageProducer producer;
    
    @Autowired
    private TestingAdapter testingAdapter;
    
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    
    /**
    * 下拉框展现所有未关联样本接收订单
    * @param request
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<SampleOrder> getSampleOrder(@RequestBody OrderListRequest request)
    {
        return service.list(request);
    }
    
    @RequestMapping(value = "/paging")
    public Pagination<SampleOrder> paging(@RequestBody OrderListRequest request)
    {
        return service.paging(request);
    }
    
    /**
     * 根据id获取订单
     * @param id
     * @return
     */
    @RequestMapping(value = "/getOrderById/{id}", method = RequestMethod.GET)
    public SampleOrder getOrder(@PathVariable String id)
    {
        return service.getOrderById(id);
    }
    
    /**********************NEW****************************************/
    /**
     * 新增订单
     * @param request
     */
    
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public void createOrder(@RequestBody OrderFormRequest request)
    {
        String id = orderService.createOrder(request);
        producer.sendOrderSubmitMessage(id);
    }

    @RequestMapping(value = "/modifyOrder")
    public void modify(@RequestBody OrderFormRequest request)
        {
         Order order = orderService.getOrderById(request.getId());
         order.setProjectManager(request.getProjectManager());
         orderService.modifyOrder(order);
    }

    /**
     * 修改订单
     * @param request
     */
    
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    public void updateOrder(@RequestBody OrderFormRequest request)
    {
        orderService.updateOrder(request);
        producer.sendOrderModifyMessage(request.getId());
    }
    
    /**
     * 修改订单疾病信息
     * @param request
     */
    @RequestMapping(value = "/modifyOrderDisese", method = RequestMethod.POST)
    public void modifyOrderDisese(@RequestBody OrderFormRequest request)
    {
        orderService.modifyOrderDisese(request);
        producer.sendOrderModifyMessage(request.getId());
    }
    
    /**
     * 更改订单产品价格
     * @param request
     */
    @RequestMapping(value = "/updateOrderById", method = RequestMethod.POST)
    public void updateOrderById(@RequestBody DefaultInvoiceRequest request)
    {
        orderService.updateOrderById(request);
    }
    
    @RequestMapping(value = "/getOrderSampleView", method = RequestMethod.POST)
    public List<OrderSampleView> getOrderSampleView(@RequestBody OrderFormRequest request)
    {
        return orderService.getOrderSampleView(request.getId());
    }
    
    /**
     * 删除订单
     * @param
     */
    
    @RequestMapping(value = "/deleteOrderById")
    public void deleteOrder(@RequestBody OrderDeleteRequest request)
    {
        orderService.deleteOrder(request);
        producer.sendOrderCancelMessage(request.getId());
    }
    
    /**
     * 根据id查询订单对象
     * @param id
     */
    @RequestMapping(value = "/getOrder/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable String id)
    {
        Order order = orderService.getOrderById(id);
        return order;
    }
    
    @RequestMapping(value = "/getOrderProductById/{id}", method = RequestMethod.GET)
    public OrderProduct getOrderProductById(@PathVariable String id)
    {
        OrderProduct order = orderService.getOrderProductById(id);
        return order;
    }
    
    @RequestMapping(value = "/getCode/{id}", method = RequestMethod.GET)
    public String getCodeById(@PathVariable String id)
    {
        String code = orderService.getCodeById(id);
        return code;
    }
    
    @RequestMapping(value = "/getSampleIdByCode", method = RequestMethod.POST)
    public String getSampleIdByCode(@RequestBody OrderSearchRequest request)
    {
        String id = orderService.getSampleIdByCode(request.getSampleCode());
        return id;
    }
    
    /**
     * 分页查询订单信息
     * @param request
     */
    @RequestMapping(value = "/pagingOrder")
    public Pagination<Order> pagingOrder(@RequestBody OrderSearchRequest request)
    {
        return orderService.pagingOrder(request);
    }
    
    /**
     * 分页查询订单信息
     * @param request
     */
    @RequestMapping(value = "/errPagingOrder")
    public Pagination<Order> errPagingOrder(@RequestBody OrderSearchRequest request)
    {
        return orderService.errPagingOrder(request);
    }
    
    /**
     * 订单列表
     * @param request  -----收样扫描订单 ---setView
     */
    @RequestMapping(value = "/orderList")
    private List<Order> orderList(@RequestBody OrderSearchRequest request)
    {
        return orderService.orderList(request);
    }

    @RequestMapping(value = "getOrderRefundList/{orderId}")
    private List<OrderRefund> getOrderRefundList(@PathVariable String orderId)
    {
        return orderService.getOrderRefundList(orderId);
    }
    
    @RequestMapping(value = "getOrderRefundHandlerInfo/{applyId}")
    private OrderRefundRecord getOrderRefundHandlerInfo(@PathVariable String applyId)
    {
        return orderService.getOrderRefundHandlerInfo(applyId);
    }
    
    /**
     * 根据合同查询未出账单的订单
     * @param request
     * @return
     */
    
    @RequestMapping(value = "/geneBilllist")
    private List<Order> geneBilllist(@RequestBody OrderSearchRequest request)
    {
        return orderService.geneBilllist(request);
    }
    
    @RequestMapping(value = "/getOrderPrimarySampleById/{id}", method = RequestMethod.GET)
    public OrderPrimarySample getOrderPrimarySampleById(@PathVariable String id)
    {
        OrderPrimarySample order = orderService.getOrderPrimarySampleById(id);
        return order;
    }
    
    @RequestMapping(value = "/getOrderSubsidiarySampleById/{id}", method = RequestMethod.GET)
    public OrderSubsidiarySample getOrderSubsidiarySampleById(@PathVariable String id)
    {
        OrderSubsidiarySample order = orderService.getOrderSubsidiarySampleById(id);
        return order;
    }
    
    @RequestMapping(value = "/countSubSampleByRelationId")
    public List<OrderSubsidiarySample> countSubSampleByRelationId(@RequestBody OrderSearchRequest request)
    {
        return orderService.countSubSampleByRelationId(request);
        
    }
    
    @RequestMapping(value = "/getOrderResearchSampleById/{id}", method = RequestMethod.GET)
    public OrderResearchSample getOrderResearchSampleById(@PathVariable String id)
    {
        OrderResearchSample order = orderService.getOrderResearchSampleById(id);
        return order;
    }
    
    @RequestMapping(value = "/getAppSampleTransport")
    public List<AppSampleTransport> getAppSampleTransport(@RequestBody OrderSearchRequest request)
    {
        List<AppSampleTransport> order = orderService.getAppSampleTransport(request);
        return order;
    }

    
    @RequestMapping(value = "/getBackSampleTransport")
    public List<AppSampleBackApply> getBackSampleTransport(@RequestBody OrderSearchRequest request)
    {
        List<AppSampleBackApply> order = orderService.getBackSampleTransport(request);
        return order;
    }
    
    /**
     *  根据订单和样本code查询数据
     * @param
     * @return
     */
    
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    public TestingOrder getTestingOrder(@PathVariable String id)
    {
        return orderService.getTestingOrderById(id);
    }
    
    @RequestMapping(value = "/getOrderProducts/{id}", method = RequestMethod.GET)
    private List<TestingProduct> getOrderProducts(@PathVariable String id)
    {
        return orderService.getOrderProducts(id);
    }
    
    /*****************样本管理start*********************************/
    @RequestMapping(value = "/orderSampleList", method = RequestMethod.POST)
    public Pagination<OrderSampleDetails> orderSampleList(@RequestBody OrderSearchRequest request)
    {
        return orderService.orderSampleList(request);
    }
    
    @RequestMapping(value = "/getSampleDetailById/{id}", method = RequestMethod.GET)
    public OrderSampleDetails getSampleDetailById(@PathVariable String id)
    {
        return orderService.getSampleDetailById(id);
        
    }
    
    /*****************样本管理end*********************************/
    
    /*****************合同订单start*********************************/
    @RequestMapping(value = "/unConfirmOrderList")
    public Pagination<Order> unConfirmOrderList(@RequestBody OrderSearchRequest request)
    {
        return orderService.unConfirmOrderList(request);
    }
    
    @RequestMapping(value = "/confirmedOrderList")
    public Pagination<Order> confirmedOrderList(@RequestBody OrderSearchRequest request)
    {
        return orderService.confirmedOrderList(request);
    }
    
    @RequestMapping(value = "/confirmOrderContract", method = RequestMethod.POST)
    public void confirmOrderContract(@RequestBody OrderFormRequest request)
    {
        orderService.confirmOrderContract(request);
        producer.sendContractOrderTestingConfirmedMessage(request.getId());
        sampleReceivingService.autoStartProcess(request.getId());
    }
    
    /*****************合同订单end*********************************/
    
    @RequestMapping(value = "/getTestingSamples", method = RequestMethod.POST)
    private List<TestingSample> getTestingSamples(@RequestBody SampleSearchRequest request)
    {
        return orderService.getTestingSamples(request);
    }
    
    @RequestMapping(value = "/getTestingTaskNode", method = RequestMethod.POST)
    private List<TestingNode> getTestingTaskNode(@RequestBody TestingNodeSearchRequest request)
    {
        return orderService.getTestingTaskNodes(request);
    }
    
    @RequestMapping(value = "/getResearchTestingProducts/{id}", method = RequestMethod.GET)
    private List<TestingProduct> getTestingTaskNode(@PathVariable String id)
    {
        return orderService.getResearchTestingProducts(id);
    }
    
    @RequestMapping(value = "/getReceivedSample/{id}", method = RequestMethod.GET)
    private DNAExtractVariables getReceivedSample(@PathVariable String id)
    {
        return orderService.getReceivedSample(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody OrderFormRequest request)
    {
        return orderService.validate(request);
    }
    
    @RequestMapping(value = "/validateSampleCode", method = RequestMethod.POST)
    public boolean validateSampleCode(@RequestBody OrderSearchRequest request)
    {
        return orderService.validateSampleCode(request);
    }
    
    @RequestMapping(value = "/validateSeCode", method = RequestMethod.POST)
    public boolean validateSeCode(@RequestBody OrderSearchRequest request)
    {
        return orderService.validateSeCode(request);
    }
    
    @RequestMapping(value = "/updateOrderProduct/{id}", method = RequestMethod.GET)
    public void updateOrderProduct(@PathVariable String id)
    {
        orderService.updateOrderProduct(id);
    }
    
    @RequestMapping("/getOrderByContract")
    public List<Order> getOrderByContract(@RequestBody OrderContrctSearcher searcher)
    {
        return orderService.getOrderByContract(searcher);
    }

    @RequestMapping(value = "/getOrderByAppSaleman")
    public List<Order> getOrderByAppSaleman(@RequestBody OrderSearchRequest searcher)
    {
        return orderService.getOrderByAppSaleman(searcher);
    }

    @RequestMapping("/simple")
    public OrderSimpleModel getOrderSimpleModel(@RequestBody BioSampleSimpleModel bssm)
    {
        return orderService.getOrderSimpleModel(bssm);
    }
    
    @RequestMapping("/verifySample/{sampleId}")
    public OrderVerifySampleModel getOrderVerifySampleModel(@PathVariable String sampleId)
    {
        return orderService.getOrderSubSampleById(sampleId);
    }
    
    @RequestMapping("/{id}/samples/verify")
    public List<OrderVerifySampleModel> getOrderVerifySamples(@PathVariable String id)
    {
        return orderService.getOrderVerifySamples(id);
    }
    
    @RequestMapping(value = "/getOrdersByStatus")
    public Pagination<Order> getOrdersByStatus(@RequestBody OrderSearchRequest request)
    {
        return orderService.getOrdersByStatus(request);
    }
    
    @RequestMapping(value = "/getTMById/{testingMethodId}", method = RequestMethod.GET)
    public TestingMethod getTMById(@PathVariable String testingMethodId)
    {
        return orderService.getTMById(testingMethodId);
    }
    
    /**
     * 对订单进行追加样本
     * @param request
     */
    
    @RequestMapping(value = "/addOrderSample", method = RequestMethod.POST)
    public void addOrderSample(@RequestBody OrderFormRequest request)
    {
        orderService.addOrderSample(request);
    }
    
    @RequestMapping(value = "/getOrderSampleViewBySampleId/{sampleld}", method = RequestMethod.GET)
    public OrderSampleView getOrderSampleViewBySampleId(@PathVariable String sampleld)
    {
        return orderService.getOrderSampleViewBySampleId(sampleld);
    }
    
    @RequestMapping(value = "/getOrderSampleDetailBySampleId/{sampleld}", method = RequestMethod.GET)
    public OrderSampleDetails getOrderSampleDetailBySampleId(@PathVariable String sampleld)
    {
        return orderService.getOrderSampleDetailBySampleId(sampleld);
    }
    
    @RequestMapping(value = "/set_sample_abnormal", method = RequestMethod.POST)
    public void setSampleAbnormal(@RequestBody SetSampleAbnormalRequest request)
    {
        orderService.setSampleAbnormal(request);
    }
    
    @RequestMapping(value = "/{id}/samples/details", method = RequestMethod.GET)
    public OrderSampleDetailsResponse getOrderSampleDetails(@PathVariable String id)
    {
        return orderService.getOrderSampleDetails(id);
    }
    
    @RequestMapping(value = "/getOrderId/{code}", method = RequestMethod.GET)
    public String getIdByCode(@PathVariable String code)
    {
        return orderService.getIdByCode(code);
    }
    
    @RequestMapping(value = "/getOrderByCode/{code}", method = RequestMethod.GET)
    public Order getOrderByCode(@PathVariable String code)
    {
        return orderService.getOrderByCode(code);
    }
    
    @RequestMapping(value = "/addErrorSample")
    public void addErrorSample(@RequestBody AddErrorSampleFormRequest request)
    {
        SampleAbnormalSolveEvent event = orderService.addErrorSample(request);
        
        if (null != event)
        {
            testingAdapter.solvedSample(event.getAbnormalSampleId(), event.getStrategy(), event.getResendSampleId());
            log.info("Sample resampling Strategy,abnormal sample id is: " + event.getAbnormalSampleId() + ".strage is :" + event.getStrategy()
                + ".resampleId is :" + event.getResendSampleId());
            producer.sendSampleAbnormalSolvedMessage(event.getAbnormalSampleId(), event.getStrategy(), event.getResendSampleId());
        }
        else
        {
            log.info("event Sample resampling is null");
        }
        
    }
    
    @RequestMapping(value = "getAppSampleTransportById/{id}", method = RequestMethod.GET)
    public AppSampleTransport getAppSampleTransportById(@PathVariable String id)
    {
        return orderService.getAppSampleTransportById(id);
    }
    
    @RequestMapping(value = "getAppSampleTransportRelationList/{packageId}", method = RequestMethod.GET)
    public List<AppSampleTransportRelation> getAppSampleTransportRelationList(@PathVariable String packageId)
    {
        return orderService.getAppSampleTransportRelationList(packageId);
    }
    
    @RequestMapping(value = "/getOrderStatusByCode/{code}", method = RequestMethod.GET)
    public Integer getOrderStatusByCode(@PathVariable String code)
    {
        return orderService.getOrderStatusByCode(code);
    }
    
    @RequestMapping(value = "/getTaskByOrderId/{orderId}", method = RequestMethod.GET)
    public List<FinanceInvoiceTask> getTaskByOrderId(@PathVariable String orderId)
    {
        return orderService.getTaskByOrderId(orderId);
    }
    
    @RequestMapping(value = "/getTaskByOrders", method = RequestMethod.POST)
    public List<FinanceInvoiceTask> getTaskByOrders(@RequestBody TemporaryRequest orders)
    {
        return orderService.getTaskByOrders(orders);
    }
    
    @RequestMapping(value = "/getContractRecRecordByOrderId/{orderId}")
    public List<ContractReconciliationRecord> getContractRecRecordByContractId(@PathVariable String orderId)
    {
        return orderService.getContractRecRecordByOrderId(orderId);
    }
    
    @RequestMapping(value = "/getSettlePaymentByOrderId/{orderId}")
    public List<ContractPaymentRecord> getSettlePaymentByOrderId(@PathVariable String orderId)
    {
        return orderService.getSettlePaymentByOrderId(orderId);
    }
    
    @RequestMapping(value = "/getInvoiceInfoByOrderId/{orderId}")
    public List<FinanceInvoiceTask> getInvoiceInfoByOrderId(@PathVariable String orderId)
    {
        return orderService.getInvoiceInfoByOrderId(orderId);
    }
    
    /* @RequestMapping(value = "/getTasksByContractInvoiceInfos", method = RequestMethod.POST)
     public List<FinanceInvoiceTask> getTasksByContractInvoiceInfos(@RequestBody TemporaryRequest request)
     {
         return orderService.getTasksByContractInvoiceInfos(request);
     }*/
    
    @RequestMapping(value = "/getProductByContractIdAndProductNames", method = RequestMethod.POST)
    public List<Product> getProductByContractIdAndProductNames(@RequestBody ContractProductRequest request)
    {
        return orderService.getProductByContractIdAndProductNames(request);
    }
    
    @RequestMapping(value = "/getReSampleNoSampleRecords/{sampleId}")
    public TestingReSampleNoSampleModel getReSampleNoSampleRecords(@PathVariable String sampleId)
    {
        return orderService.getReSampleNoSampleRecords(sampleId);
    }
    
    @RequestMapping("/financialPaging")
    public Pagination<OrderFinancial> financialPaging(@RequestBody OrderSearchRequest request)
    {
        return orderService.financialPaging(request);
    }
    
    @RequestMapping("/clinicalOrderPaging")
    public Pagination<OrderFinancial> clinicalOrderPaging(@RequestBody OrderSearchRequest request)
    {
        return orderService.clinicalOrderPaging(request);
    }
    
    /*  @RequestMapping("/callOrderScheduleProduce")
      public List<String> callOrderScheduleProduce(@RequestBody OrderSearchRequest request)
      {
          return orderService.callOrderScheduleProduce(request);
      }
      
      @RequestMapping("/orderSchedulePaging")
      public Pagination<OrderScheduleModel> orderSchedulePaging(@RequestBody OrderSearchRequest request)
      {
          return orderService.orderSchedulePaging(request);
      }
      
      @RequestMapping(value = "/getScheduleByQuery", method = RequestMethod.POST)
      public List<TestingScheduleRequest> getScheduleByQuery(@RequestBody ScheduleQuery searcher)
      {
          return orderService.getScheduleByQuery(searcher);
      }*/
    
    @RequestMapping(value = "/getAppSampleTransportRelationByOrderCode/{orderCode}", method = RequestMethod.GET)
    public List<OrderSampleTransportModel> getAppSampleTransportRelationByOrderCode(@PathVariable String orderCode)
    {
        return orderService.getAppSampleTransportRelationByOrderCode(orderCode);
    }
    
    @RequestMapping(value = "/getSampleReceiveingModelByOrderId/{orderId}", method = RequestMethod.GET)
    public List<OrderSampleReceiveingModel> getSampleReceiveingModelByOrderId(@PathVariable String orderId)
    {
        return orderService.getSampleReceiveingModelByOrderId(orderId);
    }
    
    @RequestMapping(value = "/getOrderPaymentModelByOrderId/{orderId}", method = RequestMethod.GET)
    public OrderPaymentModel getOrderPaymentModelByOrderId(@PathVariable String orderId)
    {
        return orderService.getOrderPaymentModelByOrderId(orderId);
    }
    
    @RequestMapping(value = "/getOrderListForBaseInfo")
    private List<BaseInfoModel> getOrderListForBaseInfo(@RequestBody OrderSearchRequest request)
    {
        return orderService.getOrderForBaseInfoList(request);
    }
    
}
