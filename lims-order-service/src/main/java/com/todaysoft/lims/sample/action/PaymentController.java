package com.todaysoft.lims.sample.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.PaymentSearchRequest;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.payment.OrderPaymentConfirm;
import com.todaysoft.lims.sample.entity.payment.OrderPaymentView;
import com.todaysoft.lims.sample.entity.payment.OrderPos;
import com.todaysoft.lims.sample.entity.payment.OrderRefundRecord;
import com.todaysoft.lims.sample.entity.payment.OrderTransfer;
import com.todaysoft.lims.sample.model.payment.PaymentFormRequest;
import com.todaysoft.lims.sample.ons.IMessageProducer;
import com.todaysoft.lims.sample.service.IFinanceInvoiceTaskService;
import com.todaysoft.lims.sample.service.IPaymentService;
import com.todaysoft.lims.sample.service.ISampleReceivingService;

@RestController
@RequestMapping("/bmm/payment")
public class PaymentController
{
    @Autowired
    private IPaymentService paymentService;
    
    @Autowired
    private ISampleReceivingService sampleReceivingService;
    
    @Autowired
    private IFinanceInvoiceTaskService fitService;
    
    @Autowired
    private IMessageProducer producer;
    
    @RequestMapping(value = "/appPaymentConfirm")
    public Pagination<Order> appPaymentConfirm(@RequestBody PaymentSearchRequest request)
    {
        return paymentService.appPaymentConfirm(request);
    }
    
    @RequestMapping(value = "/delayConfirmList")
    public Pagination<Order> delayConfirmList(@RequestBody PaymentSearchRequest request)
    {
        return paymentService.delayConfirmList(request);
    }
    
    @RequestMapping(value = "/cancelOrderList")
    public Pagination<Order> cancelOrderList(@RequestBody PaymentSearchRequest request)
    {
        return paymentService.cancelOrderList(request);
    }
    
    @RequestMapping(value = "/replenishmentList")
    public Pagination<Order> replenishmentList(@RequestBody PaymentSearchRequest request)
    {
        return paymentService.replenishmentList(request);
    }
    
    @RequestMapping(value = "/refundmentList")
    public Pagination<Order> refundmentList(@RequestBody PaymentSearchRequest request)
    {
        return paymentService.refundmentList(request);
    }
    
    @RequestMapping(value = "/getParmentInfoByOrderId")
    public List<OrderPos> getParmentInfoByOrderId(@RequestBody PaymentSearchRequest request)
    {
        return paymentService.getParmentInfoByOrderId(request);
        
    }
    
    /**
     * 付款待确认
     * @param request
     */
    
    @RequestMapping(value = "/confirmPayment")
    public void confirmPayment(@RequestBody PaymentFormRequest request)
    {
        boolean result = paymentService.confirmPayment(request);
        
        if (result)
        {
            paymentService.checkOrderFinish(request.getOrderId());
            producer.sendPaymentConfirmedMessage(request.getOrderId());
            producer.sendOrderAutoStartMessage(request.getOrderId());
        }
        
    }
    
    /**
     * 待确认 --提交付款
     * @param request
     */
    @RequestMapping(value = "/backConfirmPayment")
    public void backConfirmPayment(@RequestBody PaymentFormRequest request)
    {
        Boolean result = paymentService.backConfirmPayment(request);
        
        if (result)
        {
            paymentService.checkOrderFinish(request.getOrderId());
            producer.sendPaymentConfirmedMessage(request.getOrderId());
            //sampleReceivingService.autoStartProcess(request.getOrderId());
            producer.sendOrderAutoStartMessage(request.getOrderId());
        }
        
    }
    
    /**
     * 延迟订单  --- 付款
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/confirmDelayOrder")
    public void confirmDelayOrder(@RequestBody PaymentFormRequest request)
    {
        paymentService.confirmDelayOrder(request);
        producer.sendPaymentConfirmedMessage(request.getOrderId());
        // sampleReceivingService.autoStartProcess(request.getOrderId());
        producer.sendOrderAutoStartMessage(request.getOrderId());
    }
    
    /**
     * 补款确认
     * @param request
     */
    @RequestMapping(value = "/confirmReplenish")
    public void confirmReplenish(@RequestBody PaymentFormRequest request)
    {
        paymentService.confirmReplenish(request);
        paymentService.checkOrderFinish(request.getOrderId());
    }
    
    /**
     * 退款确认
     * @param request
     */
    @RequestMapping(value = "/confirmRefund")
    public void confirmRefund(@RequestBody PaymentFormRequest request)
    {
        paymentService.confirmRefund(request);
        //申请退款时，查询如果当前订单为可开票，则置为待开票状态
        fitService.modifyByRefund(request.getOrderId());
    }
    
    /**
     * 查看订单的转账详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTransInfoByOrderId")
    public List<OrderTransfer> getTransInfoByOrderId(@RequestBody PaymentSearchRequest request)
    {
        return paymentService.getTransInfoByOrderId(request);
        
    }
    
    /**
     * 缴费列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/paymentHistory")
    public Pagination<Order> paymentHistory(@RequestBody PaymentSearchRequest request)
    {
        return paymentService.paymentHistory(request);
    }
    
    @RequestMapping(value = "/getPayMentViewByOrderId")
    public List<OrderPaymentView> getPayMentViewByOrderId(@RequestBody PaymentSearchRequest request)
    {
        return paymentService.getPayMentViewByOrderId(request);
        
    }
    
    @RequestMapping(value = "/getPayMentById/{id}")
    public OrderPaymentConfirm getPayMentById(@PathVariable String id)
    {
        return paymentService.getPayMentById(id);
        
    }
    
    @RequestMapping(value = "/getRefundRecordById/{id}")
    public OrderRefundRecord getRefundRecordById(@PathVariable String id)
    {
        return paymentService.getRefundRecordById(id);
        
    }
    
    /**
     * 合同付款 ---清单 && 不定期
     * @param request
     */
    
    @RequestMapping(value = "/contractPayment")
    public void contractPayment(@RequestBody PaymentFormRequest request)
    {
        paymentService.contractPayment(request);
    }
    
    @RequestMapping(value = "/getOrderPaymentConfirmByOrderId")
    public List<OrderPaymentConfirm> getOrderPaymentConfirmByOrderId(@RequestBody PaymentFormRequest request)
    {
        return paymentService.getOrderPaymentConfirmByOrderId(request.getOrderId());
        
    }
    
    /**
     * 判断 非科研订单  是否达到已 完成状态  
     */
    @RequestMapping(value = "/checkOrderFinish/{orderId}")
    public boolean checkOrderFinish(String orderId)
    {
        return paymentService.checkOrderFinish(orderId);
    }
    
    @RequestMapping(value = "/checkOrderIsCancel/{orderId}")
    public boolean checkOrderIsCancel(String orderId)
    {
        return paymentService.checkOrderIsCancel(orderId);
    }
    
    public Integer recaclOrderProduct(String orderId)
    {
        return paymentService.recaclOrderProduct(orderId);
    }
    
}
