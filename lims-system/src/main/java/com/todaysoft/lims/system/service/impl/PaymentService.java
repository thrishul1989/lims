package com.todaysoft.lims.system.service.impl;

import com.todaysoft.lims.order.request.OrderColumnFilter;
import com.todaysoft.lims.order.request.OrderTableFilter;
import com.todaysoft.lims.order.response.CommonOrderResponse;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.PaymentSearchRequest;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.payment.*;
import com.todaysoft.lims.system.service.IPaymentService;
import com.todaysoft.lims.system.util.Constant;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.system.util.OrderConstant;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;
import com.todaysoft.lims.utils.excel.ImportExcel;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Service
public class PaymentService extends RestService implements IPaymentService
{
    
    @Override
    public Pagination<Order> appPaymentConfirm(PaymentSearchRequest request, int pageNo, int defaultpagesize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        
        String url = GatewayService.getServiceUrl("/bmm/payment/appPaymentConfirm");
        ResponseEntity<Pagination<Order>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PaymentSearchRequest>(request), new ParameterizedTypeReference<Pagination<Order>>()
            {
            });
        return exchange.getBody();
    }
    
    /**
     * 待付款确认列表
     */
    @Override
    public Pagination<CommonOrderResponse> appPaymentList(PaymentSearchRequest request, int pageNo, int defaultpagesize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        request.setPaymentStatus(OrderConstant.ORDER_PAYMENT_CONFIRMING);
        String url = GatewayService.getServiceUrl("/orders/order_search");
        ResponseEntity<Pagination<CommonOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PaymentSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<CommonOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    /**
     * 待付款
     */
    @Override
    public Pagination<CommonOrderResponse> backPaymentList(PaymentSearchRequest request, int pageNo, int defaultpagesize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        
        request.setPaymentStatus(OrderConstant.ORDER_PAYMENT_UNPAID);
        request.setSettlementType(OrderConstant.ORDER_SETTLEMENT_SINGLE);
        request.setNotTestingStatus(OrderConstant.ORDER_TESTING_CANCELED);
        String url = GatewayService.getServiceUrl("/orders/order_search");
        ResponseEntity<Pagination<CommonOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PaymentSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<CommonOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<CommonOrderResponse> replenishmentList(PaymentSearchRequest request, int pageNo, int defaultpagesize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        request.setNotTestingStatus(OrderConstant.ORDER_TESTING_CANCELED);
        request.setPaymentStatus(OrderConstant.ORDER_PAYMENT_PAID);
        request.setSettlementType(OrderConstant.ORDER_SETTLEMENT_SINGLE);
        OrderTableFilter tableFilter = new OrderTableFilter();
        tableFilter.setRequireReduceSearch(true); //需要left join
        request.setTableFilter(tableFilter);
        OrderColumnFilter columnFilter = new OrderColumnFilter();
        columnFilter.setRequireReduceColumn(true); //需要过滤查询条件
        request.setColumnFilter(columnFilter);
        String url = GatewayService.getServiceUrl("/orders/order_search");
        
        ResponseEntity<Pagination<CommonOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PaymentSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<CommonOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<CommonOrderResponse> refundmentList(PaymentSearchRequest request, int pageNo, int defaultpagesize,boolean isHandle)
    {
        OrderTableFilter tableFilter = new OrderTableFilter();
        tableFilter.setRequireRefundSearch(true); //需要left join
        OrderColumnFilter columnFilter = new OrderColumnFilter();
        if (isHandle==false)
        {
            columnFilter.setRequireRefundColumn(true); //未处理
        }
        else
        {
            columnFilter.setRequireAlreadyRefundColumn(true);//已处理
        }
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        request.setColumnFilter(columnFilter);
        request.setTableFilter(tableFilter);
        
        String url = GatewayService.getServiceUrl("/orders/order_search");
        ResponseEntity<Pagination<CommonOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PaymentSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<CommonOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    /**
     * 付款信息
     */
    @Override
    public List<OrderPos> getParmentInfoByOrderId(PaymentSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/getParmentInfoByOrderId");
        ResponseEntity<List<OrderPos>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PaymentSearchRequest>(request), new ParameterizedTypeReference<List<OrderPos>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void confirmPayment(PaymentFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/confirmPayment");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void backConfirmPayment(PaymentFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/backConfirmPayment");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void confirmDelayOrder(PaymentFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/confirmDelayOrder");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public List<OrderTransfer> getTransInfoByOrderId(PaymentSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/getTransInfoByOrderId");
        ResponseEntity<List<OrderTransfer>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PaymentSearchRequest>(request), new ParameterizedTypeReference<List<OrderTransfer>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void confirmReplenish(PaymentFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/confirmReplenish");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public Pagination<CommonOrderResponse> paymentHistory(PaymentSearchRequest request, int pageNo, int defaultpagesize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        request.setSettlementType(OrderConstant.ORDER_SETTLEMENT_SINGLE);
        String url = GatewayService.getServiceUrl("/orders/order_search");
        ResponseEntity<Pagination<CommonOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PaymentSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<CommonOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<OrderPaymentView> getPayMentViewByOrderId(PaymentSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/getPayMentViewByOrderId");
        ResponseEntity<List<OrderPaymentView>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PaymentSearchRequest>(request), new ParameterizedTypeReference<List<OrderPaymentView>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void confirmRefund(PaymentFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/confirmRefund");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public OrderPaymentConfirm getConfirmById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/getPayMentById/{id}");
        return template.getForObject(url, OrderPaymentConfirm.class, id);
        
    }
    
    @Override
    public OrderRefundRecord getRefundRecordById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/getRefundRecordById/{id}");
        return template.getForObject(url, OrderRefundRecord.class, id);
    }
    
    @Override
    public Pagination<CommonOrderResponse> delayConfirmList(PaymentSearchRequest request, int pageNo, int defaultpagesize)
    {
        OrderTableFilter tableFilter = new OrderTableFilter();
        tableFilter.setRequireDelaySearch(true); //需要left join
        OrderColumnFilter columnFilter = new OrderColumnFilter();
        columnFilter.setRequireDelayColumn(true); //不需要过滤
        
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        request.setColumnFilter(columnFilter);
        request.setTableFilter(tableFilter);
        request.setNotTestingStatus(OrderConstant.ORDER_TESTING_CANCELED);
        request.setOrderByClause("  ISNULL(delay.PLAN_PAY_TIME) , delay.PLAN_PAY_TIME asc");
        String url = GatewayService.getServiceUrl("/orders/order_search");
        ResponseEntity<Pagination<CommonOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PaymentSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<CommonOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void contractPayment(PaymentFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/payment/contractPayment");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public String downloadConfirmList(InputStream is, List<Order> orders)
    {
        File tempFile = new File("order_confirm.xlsx");
        TestSheetService.inputstreamToFile(is, tempFile);
        String path = tempFile.getPath();
        OutputStream os = null;
        
        try
        {
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(tempFile);
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            PaymentSearchRequest request = new PaymentSearchRequest();
            for (Order o : orders)
            {
                request.setId(o.getId());
                List<OrderReduce> reduce = o.getOrderReduce();
                String reduceResult = "";
                String statusResult = "";
                OrderReduce union = new OrderReduce();
                if (Collections3.isEmpty(reduce))
                {
                    reduceResult = "否";
                }
                else
                {
                    reduceResult = "是";
                    union = reduce.get(0);
                    if (0 == union.getStatus())
                    {
                        statusResult = "待审核";
                    }
                    else if (1 == union.getStatus())
                    {
                        statusResult = " 审核通过";
                    }
                    else
                    {
                        statusResult = " 审未通过";
                    }
                }
                
                if (StringUtils.isNotEmpty(o.getPayType()))
                {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, o.getCode());
                    if (Constant.ORDER_PAYTYPE_POS.equals(o.getPayType()))
                    {
                        List<OrderPos> orderPos = getParmentInfoByOrderId(request);
                        OrderPos info = new OrderPos();
                        if (!Collections3.isEmpty(orderPos))
                        {
                            info = orderPos.get(0);
                        }
                        
                        data.put(2, "POS机");
                        data.put(3, info.getPosNo());
                        data.put(4, info.getReceiptRolls());
                        datalist.add(data);
                    }
                    else if (Constant.ORDER_PAYTYPE_TRANS.equals(o.getPayType()))
                    {
                        List<OrderTransfer> orderTrans = getTransInfoByOrderId(request);
                        OrderTransfer info = new OrderTransfer();
                        if (!Collections3.isEmpty(orderTrans))
                        {
                            info = orderTrans.get(0);
                        }
                        data.put(2, "转账");
                        data.put(5, info.getTransferMember());
                        data.put(6, StringUtils.isNotEmpty(info.getTransferDate()) ? DateUtils.formatDateTime(info.getTransferDate()) : "");
                        data.put(7, info.getTransferNo());
                        data.put(8, info.getRemark());
                        data.put(14, info.getTransferMember());
                        datalist.add(data);
                    }
                    data.put(9, o.getRealPrice());
                    data.put(10, reduceResult);
                    data.put(11, statusResult);
                    data.put(12, o.getReduceAmount() == null ? 0 : o.getReduceAmount());
                    data.put(13, union.getCheckAmount() == null ? 0 : union.getCheckAmount() / 100);
                    
                    data.put(15, "");
                    data.put(16, "");
                    data.put(17, "");
                    String payMent ="";
                    if (o.getTestingStatus()==1){
                        payMent="待检测下放";
                    }else if (o.getTestingStatus()==2){
                        payMent="检测中";
                    }else if (o.getTestingStatus()==3){
                        payMent="已暂停";
                    }else if (o.getTestingStatus()==4){
                        payMent="已取消";
                    }else if (o.getTestingStatus()==5){
                        payMent="已完成";
                    }
                    data.put(18,payMent);
                }
                
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1", "O1", "P1", "Q1","R1"}; // 必须为列表头部所有位置集合， 输出
            
            excel.writeDateList(path, heads, datalist, wsheet);
            
            // 写到输出流并移除资源
            excel.writeAndClose(path, os);
            os.flush();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String abPath = tempFile.getAbsolutePath();
        return abPath;
    }
    
    @Override
    public List<OrderConfirmModel> parse(HttpServletRequest request, HttpServletResponse response)
    {
        
        List<OrderConfirmModel> models = new ArrayList<OrderConfirmModel>();
        
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (cmr.isMultipart(request))
        {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)(request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext())
            {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null)
                {
                    try
                    {
                        ImportExcel ei = new ImportExcel(mFile, 0, 0);
                        models = ei.getDataList(OrderConfirmModel.class);
                        
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return models;
        
    }
    
    @Override
    public List<OrderPaymentConfirm> getOrderPaymentConfirmByOrderId(String orderId)
    {
        PaymentFormRequest request = new PaymentFormRequest();
        request.setOrderId(orderId);
        String url = GatewayService.getServiceUrl("/bmm/payment/getOrderPaymentConfirmByOrderId");
        ResponseEntity<List<OrderPaymentConfirm>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PaymentFormRequest>(request), new ParameterizedTypeReference<List<OrderPaymentConfirm>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<Order> cancelOrderList(PaymentSearchRequest request, int pageNo, int defaultpagesize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(defaultpagesize);
        
        String url = GatewayService.getServiceUrl("/bmm/payment/cancelOrderList");
        ResponseEntity<Pagination<Order>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PaymentSearchRequest>(request), new ParameterizedTypeReference<Pagination<Order>>()
            {
            });
        return exchange.getBody();
    }
    
}
