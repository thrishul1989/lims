package com.todaysoft.lims.system.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.todaysoft.lims.system.model.syncmodel.OrderDetail;
import com.todaysoft.lims.system.model.syncmodel.OrderSyncSearchModel;
import com.todaysoft.lims.system.model.vo.order.*;
import com.todaysoft.lims.system.model.vo.testingtask.TestingTask;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnnotationFailOperate;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.todaysoft.lims.order.request.OrderColumnFilter;
import com.todaysoft.lims.order.request.OrderTableFilter;
import com.todaysoft.lims.order.request.SampleSynchronizeRequest;
import com.todaysoft.lims.order.response.CommonOrderResponse;
import com.todaysoft.lims.order.response.GanalysisSampleInfo;
import com.todaysoft.lims.system.model.searcher.ContractProductRequest;
import com.todaysoft.lims.system.model.vo.OrderContrctSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.TestingReSampleNoSampleModel;
import com.todaysoft.lims.system.model.vo.TestingTaskRequest;
import com.todaysoft.lims.system.model.vo.TestingtechnicalanalyrecordTempory;
import com.todaysoft.lims.system.model.vo.contract.ContractPaymentRecord;
import com.todaysoft.lims.system.model.vo.order.orderReportForm.BaseInfoModel;
import com.todaysoft.lims.system.model.vo.payment.OrderRefund;
import com.todaysoft.lims.system.model.vo.payment.OrderRefundRecord;
import com.todaysoft.lims.system.model.vo.samplereceiving.ReceivedSample;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bpm.abnormal.model.AbnormalSolveRecord;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSample;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.system.service.adapter.request.SamplePagingRequest;
import com.todaysoft.lims.system.util.ConfigManage;
import com.todaysoft.lims.system.util.DateUtil;
import com.todaysoft.lims.system.util.ExcelUtil;
import com.todaysoft.lims.system.util.OrderConstant;
import com.todaysoft.lims.system.util.OrderSamplePurposeMenu;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class OrderService extends RestService implements IOrderService
{
    private static Logger log = LoggerFactory.getLogger(OrderService.class);
    
    @Override
    public void updateOrder(OrderFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/modifyOrder");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public List<String> upload(HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        File localFile = null;
        File file = new File(ConfigManage.getkey("uploadPath"));
        List<String> fileList = new ArrayList<String>();
        
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
                    SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
                    Date date = new Date();
                    String fileName = dateFormater.format(date) + mFile.getOriginalFilename();
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    String path = file.getAbsolutePath().toString() + "\\" + fileName;
                    localFile = new File(path);
                    try
                    {
                        mFile.transferTo(localFile);
                    }
                    catch (IllegalStateException | IOException e)
                    {
                        e.printStackTrace();
                    }
                    fileList.add(path);
                }
            }
        }
        return fileList;
    }
    
    @Override
    @SystemServiceLog(description = "订单管理-修改", type = 1)
    public void update(OrderFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/updateOrder");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public void create(OrderFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/createOrder");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public Order getById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrder/{id}");
        return template.getForObject(url, Order.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public OrderProduct getOrderProductById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderProductById/{id}");
        return template.getForObject(url, OrderProduct.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public Pagination<CommonOrderResponse> paging(OrderSearchRequest request, int pageNo, int pageSize)
    {
        
        OrderTableFilter searcherFilter = new OrderTableFilter();
        searcherFilter.setRequireDelaySearch(true); // 需要left join
        OrderColumnFilter columnFilter = new OrderColumnFilter();
        columnFilter.setRequireDelayColumn(false); // 不需要过滤
        
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setOrderByClause(" o.SUBMIT_TIME desc");
        String url = GatewayService.getServiceUrl("/orders/order_search");
        
        request.setTableFilter(searcherFilter);
        request.setColumnFilter(columnFilter);
        ResponseEntity<Pagination<CommonOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<CommonOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<CommonOrderResponse> errPaging(OrderSearchRequest request, int pageNo, int pageSize)
    {
        OrderTableFilter searcherFilter = new OrderTableFilter();
        searcherFilter.setRequireDelaySearch(false); // 需要left join
        OrderColumnFilter columnFilter = new OrderColumnFilter();
        columnFilter.setRequireDelayColumn(false); // 不需要过滤
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/orders/order_search");
        ResponseEntity<Pagination<CommonOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<CommonOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    @SystemServiceLog(description = "订单管理-删除", type = 1)
    public void delete(OrderDeleteRequest request)
    {
        /*
         * String url =
         * GatewayService.getServiceUrl("/bmm/order/deleteOrderById/{id}");
         * template.delete(url, Collections.singletonMap("id", id));
         */
        String url = GatewayService.getServiceUrl("/bmm/order/deleteOrderById");
        template.postForObject(url, request, void.class);
    }
    
    @Override
    public List<Order> list(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/orderList");
        ResponseEntity<List<Order>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<List<Order>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String getCodeById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getCode/{id}");
        return template.getForObject(url, String.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public void updateOrderProduct(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/updateOrderProduct/{id}");
        template.getForObject(url, String.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public boolean validate(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/validate");
        boolean res = template.postForObject(url, request, boolean.class);
        return res;
    }
    
    @Override
    public boolean validateSampleCode(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/validateSampleCode");
        return template.postForObject(url, request, boolean.class);
        
    }
    
    @Override
    public boolean validateSeCode(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/validateSeCode");
        return template.postForObject(url, request, boolean.class);
        
    }
    
    @Override
    public List<Order> getOrderByContract(OrderContrctSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderByContract");
        ResponseEntity<List<Order>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderContrctSearcher>(searcher), new ParameterizedTypeReference<List<Order>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<Order> getOrderByAppSaleman(OrderSearchRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderByAppSaleman");
        ResponseEntity<List<Order>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(searcher), new ParameterizedTypeReference<List<Order>>()
            {
            });
        return exchange.getBody();
        
    }
    
    @Override
    public Pagination<Order> getOrdersByStatus(OrderSearchRequest request, int pageNo, int pageSize)
    {
        
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bmm/order/getOrdersByStatus");
        ResponseEntity<Pagination<Order>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<Pagination<Order>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<OrderSampleView> getOrderSampleView(String orderId)
    {
        
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderSampleView");
        ResponseEntity<List<OrderSampleView>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<Map>(Collections.singletonMap("id", orderId)),
                new ParameterizedTypeReference<List<OrderSampleView>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public TestingSchedule getOnlySchedule(ScheduleQuery request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getOnlySchedule");
        ResponseEntity<TestingSchedule> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ScheduleQuery>(request), new ParameterizedTypeReference<TestingSchedule>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<TestingScheduleHistory> getTSHByScheduleId(String scheduleId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getTSHByScheduleID/{scheduleId}");
        ResponseEntity<List<TestingScheduleHistory>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TestingScheduleHistory>>()
            {
            }, scheduleId);
        return exchange.getBody();
    }
    
    @Override
    public List<TestingNode> getPrfeNode(Temproary data)
    {
        String url = GatewayService.getServiceUrl("/bcm/sample/getPreTestingTaskNode");
        ResponseEntity<List<TestingNode>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Temproary>(data), new ParameterizedTypeReference<List<TestingNode>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public TestingMethod getTMById(String testingMethodId)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getTMById/{testingMethodId}");
        return template.getForObject(url, TestingMethod.class, Collections.singletonMap("testingMethodId", testingMethodId));
    }
    
    @Override
    public Pagination<OrderSampleDetails> orderSampleList(OrderSearchRequest request, int pageNo, int pageSize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bmm/order/orderSampleList");
        ResponseEntity<Pagination<OrderSampleDetails>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<OrderSampleDetails>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public OrderSampleDetails getSampleDetailById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getSampleDetailById/{id}");
        return template.getForObject(url, OrderSampleDetails.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public OrderPrimarySample getOrderPrimarySampleById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderPrimarySampleById/{id}");
        return template.getForObject(url, OrderPrimarySample.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public OrderSubsidiarySample getOrderSubsidiarySampleById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderSubsidiarySampleById/{id}");
        return template.getForObject(url, OrderSubsidiarySample.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public OrderResearchSample getOrderResearchSampleById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderResearchSampleById/{id}");
        return template.getForObject(url, OrderResearchSample.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public List<AppSampleTransport> getAppSampleTransport(OrderSearchRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getAppSampleTransport");
        ResponseEntity<List<AppSampleTransport>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(searcher), new ParameterizedTypeReference<List<AppSampleTransport>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<AppSampleBackApply> getBackSampleTransport(OrderSearchRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getBackSampleTransport");
        ResponseEntity<List<AppSampleBackApply>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(searcher), new ParameterizedTypeReference<List<AppSampleBackApply>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<CommonOrderResponse> unConfirmOrderList(OrderSearchRequest request, int pageNo, int pageSize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setOrderByClause("o.SUBMIT_TIME asc");
        
        OrderTableFilter tableFilter = new OrderTableFilter();
        tableFilter.setRequireOrderContractSearch(true);
        OrderColumnFilter columnFilter = new OrderColumnFilter();
        columnFilter.setRequireOrderContractColumn(true);
        request.setTableFilter(tableFilter);
        request.setColumnFilter(columnFilter);
        request.setSchedulePaymentStatus(0);
        request.setNotTestingStatus(OrderConstant.ORDER_TESTING_CANCELED);
        String url = GatewayService.getServiceUrl("/orders/order_search");
        ResponseEntity<Pagination<CommonOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<CommonOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<CommonOrderResponse> confirmedOrderList(OrderSearchRequest request, int pageNo, int pageSize)
    {
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setOrderByClause("o.SUBMIT_TIME asc");
        
        OrderTableFilter tableFilter = new OrderTableFilter();
        tableFilter.setRequireOrderContractSearch(true);
        OrderColumnFilter columnFilter = new OrderColumnFilter();
        columnFilter.setRequireOrderContractColumn(true);
        request.setTableFilter(tableFilter);
        request.setColumnFilter(columnFilter);
        request.setOrderByClause("  o.SUBMIT_TIME desc");
        request.setSchedulePaymentStatus(1);
        String url = GatewayService.getServiceUrl("/orders/order_search");
        ResponseEntity<Pagination<CommonOrderResponse>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderSearchRequest>(request),
                new ParameterizedTypeReference<Pagination<CommonOrderResponse>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    @SystemServiceLog(description = "合同订单管理-检测确认", type = 2)
    public void confirmOrderContract(OrderFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/confirmOrderContract");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public List<TestingSchedule> getTestingSchedules(String scheduleId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getTestingSchedules/{scheduleId}");
        ResponseEntity<List<TestingSchedule>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TestingSchedule>>()
        {
        }, scheduleId);
        return exchange.getBody();
    }
    
    @Override
    public TestingMethod getTestingMethodById(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getTestingMethodById/{id}");
        return template.getForObject(url, TestingMethod.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public TestingSchedule getScheduleById(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getScheduleById/{id}");
        return template.getForObject(url, TestingSchedule.class, Collections.singletonMap("id", id));
    }
    
    @Autowired
    private IMetadataSampleService service;
    
    @Autowired
    private IProductService productService;
    
    @Override
    public List<OrderResearchSampleForUpload> uploadSearcherSample(MultipartFile uploadData, String hetongId)
    {
        try
        {
            ImportExcel ei = new ImportExcel(uploadData, 0, 0);
            List<OrderResearchSampleForUpload> group = ei.getDataList(OrderResearchSampleForUpload.class);
            for (OrderResearchSampleForUpload o : group)
            {
                SamplePagingRequest request = new SamplePagingRequest();
                request.setName(o.getSampleTypeName());
                request.setMatchModel(true);
                List<MetadataSample> sample = service.list(request);
                o.setSampleTypeId(sample != null ? sample.get(0).getId() : "");
                if (StringUtils.isNotEmpty(o.getProductUploadString()))
                {
                    String pro = o.getProductUploadString();
                    String[] array = pro.split(";");
                    List<Product> productList = new ArrayList<Product>();
                    for (String name : array)
                    {
                        ContractProductRequest searcher = new ContractProductRequest();
                        searcher.setName(name);
                        searcher.setContractId(hetongId);
                        List<Product> product = getProductByContractAndName(searcher);
                        
                        if (Collections3.isNotEmpty(product))
                        {
                            productList.add(product.get(0));
                        }
                    }
                    o.setOrderResearchProduct(productList);
                }
                
            }
            return group;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        
    }
    
    private List<Product> getProductByContractAndName(ContractProductRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getProductByContractIdAndProductNames");
        ResponseEntity<List<Product>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ContractProductRequest>(searcher), new ParameterizedTypeReference<List<Product>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<TestingSchedule> getTestingSchedulesByData(ScheduleQuery request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getTestingSchedulesByData");
        ResponseEntity<List<TestingSchedule>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ScheduleQuery>(request), new ParameterizedTypeReference<List<TestingSchedule>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskResultRequest getDataById(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getTestingTaskResultById/{id}");
        return template.getForObject(url, TestingTaskResultRequest.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public List<Order> geneBilllist(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/geneBilllist");
        ResponseEntity<List<Order>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<List<Order>>()
            {
            });
        return exchange.getBody();
        
    }
    
    @Override
    @SystemServiceLog(description = "订单管理-追加样本", type = 1)
    public void addOrderSample(OrderFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/addOrderSample");
        template.postForObject(url, request, Boolean.class);
    }
    
    @Override
    public TestingSample getTestingSample(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getTestingSample/{id}");
        return template.getForObject(url, TestingSample.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public OrderSampleView getOrderSampleViewBySampleId(String sampleld)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderSampleViewBySampleId/{sampleld}");
        return template.getForObject(url, OrderSampleView.class, Collections.singletonMap("sampleld", sampleld));
    }
    
    @Override
    public ReceivedSample getReceivedSample(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getReceivedSample/{id}");
        return template.getForObject(url, ReceivedSample.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public TestingtechnicalanalyrecordTempory getSangerVerifyRecordById(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getSangerVerifyRecordById/{id}");
        return template.getForObject(url, TestingtechnicalanalyrecordTempory.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public List<OrderSubsidiarySample> countSubSampleByRelationId(OrderSearchRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/countSubSampleByRelationId");
        ResponseEntity<List<OrderSubsidiarySample>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(searcher), new ParameterizedTypeReference<List<OrderSubsidiarySample>>()
            {
            });
        return exchange.getBody();
        
    }
    
    @Override
    public TestingtechnicalanalyrecordTempory getMLPAVerifyRecordById(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getMLPAVerifyRecordById/{id}");
        return template.getForObject(url, TestingtechnicalanalyrecordTempory.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public TestingtechnicalanalyrecordTempory getQPCRVerifyRecordById(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getQPCRVerifyRecordById/{id}");
        return template.getForObject(url, TestingtechnicalanalyrecordTempory.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public Map<String, Object> callbackOrderScheulStatus(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/{id}/status");
        return template.getForObject(url, Map.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public List<OrderRefund> getOrderRefundList(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderRefundList/{orderId}");
        
        ResponseEntity<List<OrderRefund>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderRefund>>()
        {
        }, orderId);
        return exchange.getBody();
    }
    
    @Override
    public OrderRefundRecord getOrderRefundHandler(String applyId)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderRefundHandlerInfo/{applyId}");
        
        ResponseEntity<OrderRefundRecord> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<OrderRefundRecord>()
        {
        }, applyId);
        return exchange.getBody();
    }
    
    @Override
    public String getIdByCode(String code)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderId/{code}");
        return template.getForObject(url, String.class, Collections.singletonMap("code", code));
    }
    
    @Override
    public void addErrorSample(AddErrorSampleFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/addErrorSample");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public AppSampleTransport getAppSampleTransportById(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getAppSampleTransportById/{id}");
        return template.getForObject(url, AppSampleTransport.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public List<AppSampleTransportRelation> getAppSampleTransportRelationList(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getAppSampleTransportRelationList/{packageId}");
        ResponseEntity<List<AppSampleTransportRelation>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AppSampleTransportRelation>>()
            {
            }, id);
        return exchange.getBody();
    }
    
    @Override
    public OrderSampleDetails getOrderSampleDetailBySampleId(String sampleld)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderSampleDetailBySampleId/{sampleld}");
        return template.getForObject(url, OrderSampleDetails.class, Collections.singletonMap("sampleld", sampleld));
    }
    
    @Override
    public String getSampleIdByCode(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getSampleIdByCode");
        
        ResponseEntity<String> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<String>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Integer getOrderStatusByCode(String code)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderStatusByCode/{code}");
        return template.getForObject(url, Integer.class, Collections.singletonMap("code", code));
    }
    
    @Override
    public String getSequenceCode(String scheduleId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getSequenceCode/{scheduleId}");
        return template.getForObject(url, String.class, Collections.singletonMap("scheduleId", scheduleId));
    }
    
    @Override
    public List<ContractReconciliationRecord> getContractRecRecordByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getContractRecRecordByOrderId/{orderId}");
        ResponseEntity<List<ContractReconciliationRecord>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ContractReconciliationRecord>>()
            {
            }, Collections.singletonMap("orderId", orderId));
        return exchange.getBody();
    }
    
    @Override
    public List<ContractPaymentRecord> getSettlePaymentByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getSettlePaymentByOrderId/{orderId}");
        ResponseEntity<List<ContractPaymentRecord>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ContractPaymentRecord>>()
            {
            }, Collections.singletonMap("orderId", orderId));
        return exchange.getBody();
    }
    
    @Override
    public List<FinanceInvoiceTask> getInvoiceInfoByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getInvoiceInfoByOrderId/{orderId}");
        ResponseEntity<List<FinanceInvoiceTask>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<FinanceInvoiceTask>>()
            {
            }, Collections.singletonMap("orderId", orderId));
        return exchange.getBody();
    }
    
    @Override
    public AbnormalSolveRecord getRemarkByAbnormal(String taskId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getRemarkByAbnormal/{taskId}");
        return template.getForObject(url, AbnormalSolveRecord.class, Collections.singletonMap("taskId", taskId));
    }
    
    @Override
    public String changePaymentConfirmStatus()
    {
        String url = GatewayService.getServiceUrl("/maintain/order/changePaymentConfirmStatus");
        return template.getForObject(url, String.class);
    }
    
    @Override
    public String changeOrderProduct()
    {
        String url = GatewayService.getServiceUrl("/maintain/order/changeOrderProduct");
        return template.getForObject(url, String.class);
    }
    
    @Override
    public String changeStatus()
    {
        String url = GatewayService.getServiceUrl("/maintain/order/changeStatus");
        return template.getForObject(url, String.class);
    }
    
    @Override
    public String searchReceiveStatus()
    {
        String url = GatewayService.getServiceUrl("/maintain/order/searchReceiveStatus");
        return template.getForObject(url, String.class);
        
    }
    
    @Override
    public List<AbnormalSolveModel> getCancelDetailView(String scheduleId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getCancelDetails/{scheduleId}");
        ResponseEntity<List<AbnormalSolveModel>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AbnormalSolveModel>>()
            {
            }, Collections.singletonMap("scheduleId", scheduleId));
        return exchange.getBody();
    }
    
    @Override
    public TestingReSampleNoSampleModel getReSampleNoSampleRecords(String sampleId)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getReSampleNoSampleRecords/{sampleId}");
        ResponseEntity<TestingReSampleNoSampleModel> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<TestingReSampleNoSampleModel>()
            {
            }, Collections.singletonMap("sampleId", sampleId));
        return exchange.getBody();
    }
    
    @Override
    public List<OrderSampleTransportModel> getAppSampleTransportRelationByOrderCode(String orderCode)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getAppSampleTransportRelationByOrderCode/{orderCode}");
        ResponseEntity<List<OrderSampleTransportModel>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderSampleTransportModel>>()
            {
            }, Collections.singletonMap("orderCode", orderCode));
        return exchange.getBody();
    }
    
    @Override
    public List<OrderSampleReceiveingModel> getSampleReceiveingModelByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getSampleReceiveingModelByOrderId/{orderId}");
        ResponseEntity<List<OrderSampleReceiveingModel>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderSampleReceiveingModel>>()
            {
            }, Collections.singletonMap("orderId", orderId));
        return exchange.getBody();
    }
    
    @Override
    public OrderPaymentModel getOrderPaymentModelByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderPaymentModelByOrderId/{orderId}");
        ResponseEntity<OrderPaymentModel> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<OrderPaymentModel>()
        {
        }, Collections.singletonMap("orderId", orderId));
        return exchange.getBody();
    }
    
    @Override
    public String exportOrderFinancialFile(OrderSearchRequest searcher)
    {
        InputStream in = null;
        OutputStream os = null;
        String time = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        File file = new File("ORDER_FINANCIAL_FILE" + time + ".xlsx");
        
        try
        {
            in = OrderService.class.getResourceAsStream("/taskTemplate/order/ORDER_FINANCIAL.xlsx");
            TestSheetService.inputstreamToFile(in, file);
            String path = file.getPath();
            
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            
            if (Collections3.isNotEmpty(callOrderFinancialProduce(searcher)))
            {
                List<OrderFinancialModel> list = getExportRecords(searcher);
                Map<Integer, Object> data = null;
                for (OrderFinancialModel model : list)
                {
                    List<ReportFinancialOrderInvoice> lists = model.getReportFinancialOrderInvoice();
                    if (Collections3.isNotEmpty(lists))
                    {
                        for (ReportFinancialOrderInvoice i : lists)
                        {
                            data = new HashMap<Integer, Object>();
                            setDataToExcel(model, data);
                            data.put(21, i.getAmount()); // 这里数据库存的是元
                            data.put(22, i.getDrawerNo());
                            data.put(23, DateUtil.format(i.getInvoiceTime(), "yyyy-MM-dd HH:mm:ss"));
                            data.put(24, i.getName());
                            datalist.add(data);
                        }
                        
                    }
                    else
                    {
                        data = new HashMap<Integer, Object>();
                        setDataToExcel(model, data);
                        datalist.add(data);
                    }
                }
            }
            
            String[] heads =
                new String[] {"A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2", "I2", "J2", "K2", "L2", "M2", "N2", "O2", "P2", "Q2", "R2", "S2", "T2", "U2",
                    "V2", "W2", "X2"}; // 必须为列表头部所有位置集合，
                                       // 输出
                                       // 数据单元格样式和头部单元格样式保持一致
            excel.writeDateList(path, heads, datalist, wsheet);
            
            // 写到输出流并移除资源
            excel.writeAndClose(path, os);
            os.flush();
            
        }
        catch (FileNotFoundException e)
        {
            log.error(e.getMessage(), e);
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
        finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    log.error(e.getMessage(), e);
                }
            }
        }
        
        return file.getAbsolutePath();
    }
    
    private void setDataToExcel(OrderFinancialModel model, Map<Integer, Object> data)
    {
        data.put(1, model.getSettlementType());
        data.put(2, model.getCode());
        data.put(3, DateUtil.format(model.getSubmitTime(), "yyyy-MM-dd HH:mm:ss"));
        data.put(4, model.getOwnerName());
        data.put(5, StringUtils.isNotEmpty(model.getProductNames()) ? model.getProductNames() : "");
        data.put(6, StringUtils.isNotEmpty(model.getCompanyName()) ? model.getCompanyName() : "");
        data.put(7, StringUtils.isNotEmpty(model.getSamplingAmount()) ? model.getSamplingAmount() : "");
        data.put(8, model.getCreatorName());
        data.put(9, StringUtils.isNotEmpty(model.getExamineeName()) ? model.getExamineeName() : "");
        data.put(10, DateUtil.format(model.getCheckTime(), "yyyy-MM-dd HH:mm:ss"));
        data.put(11, model.getOrderAmount());
        data.put(12, model.getReduceAmount());
        data.put(13, model.getNeedPay());
        data.put(14, model.getIncomingAmount());
        data.put(15, model.getPayType());
        data.put(16, StringUtils.isNotEmpty(model.getPosNo()) ? model.getPosNo() : "");
        data.put(17, StringUtils.isNotEmpty(model.getPaymenter()) ? model.getPaymenter() : "");
        data.put(18, StringUtils.isNotEmpty(model.getInvoiceApplyType()) ? model.getInvoiceApplyType() : "");
        data.put(19, StringUtils.isNotEmpty(model.getInvoiceTitle()) ? model.getInvoiceTitle() : "");
        data.put(20, StringUtils.isNotEmpty(model.getInvoiceContent()) ? model.getInvoiceContent() : "");// //basic
                                                                                                         // (专、普)
    }
    
    private List<OrderFinancialModel> getExportRecords(OrderSearchRequest searcher)
    {
        int pageNo = 1;
        int pageSize = 100;
        List<OrderFinancialModel> records = new ArrayList<OrderFinancialModel>();
        
        Pagination<OrderFinancialModel> pagination;
        
        do
        {
            searcher.setPageNo(pageNo++);
            searcher.setPageSize(pageSize);
            pagination = paging(searcher);
            
            if (!CollectionUtils.isEmpty(pagination.getRecords()))
            {
                records.addAll(pagination.getRecords());
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Search records for page {} successful, total count {}, total page {}.",
                    pageNo,
                    pagination.getTotalCount(),
                    pagination.getTotalPage());
            }
            
        } while (!pagination.isLastPage());
        
        if (log.isDebugEnabled())
        {
            log.debug("Search records successful, total count {}.", records.size());
        }
        
        return records;
    }
    
    public Pagination<OrderFinancialModel> paging(OrderSearchRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/export/finance/financialPaging");
        ResponseEntity<Pagination<OrderFinancialModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderSearchRequest>(searcher),
                new ParameterizedTypeReference<Pagination<OrderFinancialModel>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public String exportOrderExcludeContractFile(OrderSearchRequest searcher)
    {
        InputStream in = null;
        OutputStream os = null;
        String time = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        File file = new File("CLINICAL_ORDER_FINANCIAL_FILE" + time + ".xlsx");
        
        try
        {
            in = OrderService.class.getResourceAsStream("/taskTemplate/order/CLINICAL_ORDER_FINANCIAL.xlsx");
            TestSheetService.inputstreamToFile(in, file);
            String path = file.getPath();
            
            ExcelUtil excel = new ExcelUtil();
            Workbook wbModule = excel.getTempWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
            
            if (Collections3.isNotEmpty(callOrderClinicalProduce(searcher)))
            {
                List<OrderClinicalModel> list = getClinicalOrderRecords(searcher);
                Map<Integer, Object> data = null;
                for (OrderClinicalModel model : list)
                {
                    List<ReportClinicalOrderInvoice> lists = model.getReportClinicalOrderInvoice();
                    if (Collections3.isNotEmpty(lists))
                    {
                        for (ReportClinicalOrderInvoice i : lists)
                        {
                            data = new HashMap<Integer, Object>();
                            setClinicalDataToExcel(model, data);
                            data.put(10, DateUtil.format(i.getInvoiceTime(), "yyyy/MM/dd"));
                            data.put(11, i.getDrawerNo());
                            data.put(12, i.getName());
                            data.put(13, model.getInvoiceContent()); // 开票内容
                            data.put(14, i.getAmount());
                            data.put(15, model.getRecipientPhone());
                            data.put(16, model.getRecipientAddress());
                            data.put(17, i.getTrackNumber());
                            datalist.add(data);
                        }
                        
                    }
                    else
                    {
                        data = new HashMap<Integer, Object>();
                        setClinicalDataToExcel(model, data);
                        datalist.add(data);
                    }
                }
            }
            
            String[] heads = new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1", "O1", "P1", "Q1", "R1"}; // 必须为列表头部所有位置集合， 输出 数据单元格样式和头部单元格样式保持一致
            excel.writeDateList(path, heads, datalist, wsheet);
            
            // 写到输出流并移除资源
            excel.writeAndClose(path, os);
            os.flush();
            
        }
        catch (FileNotFoundException e)
        {
            log.error(e.getMessage(), e);
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
        finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    log.error(e.getMessage(), e);
                }
            }
        }
        
        return file.getAbsolutePath();
    }
    
    private void setClinicalDataToExcel(OrderClinicalModel model, Map<Integer, Object> data)
    {
        data.put(1, model.getCreatorName());
        data.put(2, model.getCode());
        data.put(3, model.getOrderType());
        data.put(4, model.getCompanyName());
        data.put(5, model.getExamineeName());
        data.put(6, model.getProductNames());
        data.put(7, model.getTestingStatus());
        data.put(8, model.getPaymentStatus());
        data.put(9, model.getIncomingAmount());
        data.put(18, StringUtils.isNotEmpty(model.getRemark()) ? model.getRemark() : "");
        
    }
    
    private List<OrderClinicalModel> getClinicalOrderRecords(OrderSearchRequest searcher)
    {
        int pageNo = 1;
        int pageSize = 1000;
        List<OrderClinicalModel> records = new ArrayList<OrderClinicalModel>();
        
        Pagination<OrderClinicalModel> pagination;
        
        do
        {
            searcher.setPageNo(pageNo++);
            searcher.setPageSize(pageSize);
            pagination = clinicalOrderPaging(searcher);
            
            if (!CollectionUtils.isEmpty(pagination.getRecords()))
            {
                records.addAll(pagination.getRecords());
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Search records for page {} successful, total count {}, total page {}.",
                    pageNo,
                    pagination.getTotalCount(),
                    pagination.getTotalPage());
            }
            
        } while (!pagination.isLastPage());
        
        if (log.isDebugEnabled())
        {
            log.debug("Search records successful, total count {}.", records.size());
        }
        
        return records;
    }
    
    private Pagination<OrderClinicalModel> clinicalOrderPaging(OrderSearchRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/export/finance/clinicalOrderPaging");
        ResponseEntity<Pagination<OrderClinicalModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderSearchRequest>(searcher),
                new ParameterizedTypeReference<Pagination<OrderClinicalModel>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public String exportOrderIncludeScheduleFile(OrderSearchRequest searcher)
    {
        InputStream in = null;
        OutputStream os = null;
        String time = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        File file = new File("ORDER_SCHEDULE_FILE" + time + ".xlsx");
        String[] heads =
            new String[] {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1", "O1", "P1", "Q1", "R1", "S1", "T1", "U1", "V1",
                "W1", "X1", "Y1", "Z1", "[1", "\\1", "]1", "^1", "_1"}; // 必须为列表头部所有位置集合，
                                                                        // 输出
                                                                        // 数据单元格样式和头部单元格样式保持一致
        try
        {
            in = OrderService.class.getResourceAsStream("/taskTemplate/order/ORDER_SCHEDULE.xlsx");
            TestSheetService.inputstreamToFile(in, file);
            String path = file.getPath();
            
            ExcelUtil excel = new ExcelUtil();
            SXSSFWorkbook wbModule = (SXSSFWorkbook)excel.getTempFinancialWorkbook(path);
            Sheet wsheet = wbModule.getSheetAt(0);
            os = new FileOutputStream(file);
            CellStyle style = createStyle(wsheet);
            int pageNo = 1;
            int pageSize = 200;
            
            Pagination<OrderScheduleModel> pagination;
            
            if (Collections3.isNotEmpty(callOrderScheduleProduce(searcher)))
            {
                do
                {
                    searcher.setPageNo(pageNo);
                    searcher.setPageSize(pageSize);
                    pagination = getOrderScheduleRecords(searcher);
                    if (log.isDebugEnabled())
                    {
                        log.debug("Search records for page {} successful, total count {}, total page {}.",
                            pageNo,
                            pagination.getTotalCount(),
                            pagination.getTotalPage());
                    }
                    List<OrderScheduleModel> dataList = pagination.getRecords();
                    List<Map<Integer, Object>> dataLists = writeToData(dataList, pageNo, pageSize);
                    Row row = null;
                    Cell c1 = null;
                    for (int j = 0; j < dataList.size(); j++)
                    {
                        row = wsheet.createRow(j + ((pageNo - 1) * pageSize) + 1);
                        Map<Integer, Object> model = dataLists.get(j);
                        for (int k = 0; k < heads.length; k++)
                        {
                            c1 = row.createCell(k);
                            c1.setCellValue((StringUtils.isNotEmpty(model.get(k + 1))) ? model.get(k + 1).toString() : "");
                            c1.setCellStyle(style);
                        }
                        
                    }
                    
                    pageNo++;
                    
                } while (!pagination.isLastPage());
                if (log.isDebugEnabled())
                {
                    log.debug("Search records successful, total count {}.", pagination.getTotalCount());
                }
                wbModule.write(os);
                
                os.flush();
                os.close();
                wbModule.dispose();
            }
            
        }
        catch (FileNotFoundException e)
        {
            log.error(e.getMessage(), e);
        }
        
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
            
        }
        finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    log.error(e.getMessage(), e);
                }
            }
        }
        
        return file.getAbsolutePath();
    }
    
    private CellStyle createStyle(Sheet wsheet)
    {
        Font font = wsheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)11);
        CellStyle style = wsheet.getWorkbook().createCellStyle();
        style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        return style;
    }
    
    public List<String> callOrderFinancialProduce(OrderSearchRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/export/finance/callOrderFinancialProduce");
        ResponseEntity<List<String>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(searcher), new ParameterizedTypeReference<List<String>>()
            {
            });
        return exchange.getBody();
    }
    
    public List<String> callOrderClinicalProduce(OrderSearchRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/export/finance/callOrderClinicalProduce");
        ResponseEntity<List<String>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(searcher), new ParameterizedTypeReference<List<String>>()
            {
            });
        return exchange.getBody();
    }
    
    public List<String> callOrderScheduleProduce(OrderSearchRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/export/finance/callOrderScheduleProduce");
        ResponseEntity<List<String>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(searcher), new ParameterizedTypeReference<List<String>>()
            {
            });
        return exchange.getBody();
        
    }
    
    private List<Map<Integer, Object>> writeToData(List<OrderScheduleModel> list, int pageNo, int pageSizest) throws FileNotFoundException, IOException
    {
        int i = ((pageNo - 1) * pageSizest) + 1;
        List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
        Map<Integer, Object> data = null;
        for (OrderScheduleModel model : list)
        {
            data = new HashMap<Integer, Object>();
            data.put(1, i);
            data.put(2, model.getCode());
            data.put(3, model.getOrderType());
            data.put(4, model.getTestingStatus());
            data.put(5, model.getExamineeName());
            data.put(6, model.getOwnerName());
            data.put(7, model.getCompanyName());
            data.put(8, model.getCreatorName());
            data.put(9, model.getSubmitTime());
            data.put(10, model.getNeedPay());
            data.put(11, model.getReduceAmount());
            data.put(12, model.getIncomingAmount());
            data.put(13, model.getProductName());
            data.put(14, model.getRefundStatus());
            data.put(15, model.getSampleCode());
            data.put(16, model.getSampleTypeName());
            data.put(17, OrderSamplePurposeMenu.hintOfValue(model.getPurpose()));
            data.put(18, model.getCheckName());
            
            String sampleVerify = model.getSampleCode();
            if (StringUtils.isNotEmpty(model.getVerifyChrPosition()))
            {
                sampleVerify = sampleVerify + "/" + model.getVerifyChrPosition();
            }
            data.put(19, sampleVerify);
            data.put(20, i);
            data.put(21, model.getTesterName());
            data.put(22, model.getTaskName());
            data.put(23, model.getAssignTime());
            data.put(24, model.getEndTime());
            data.put(25, model.getCheckTaskcode());
            data.put(26, model.getPauseName());
            data.put(27, model.getPauseTime());
            data.put(28, model.getRestartName());
            data.put(29, model.getRestartTime());
            data.put(30, model.getRemark()); // 异常备注
            String statusText = getStatus(model.getStatus());
            if (StringUtils.isNotEmpty(model.getEndType()))
            {
                statusText = statusText + "(" + getEndStatus(model.getEndType()) + ")";
            }
            data.put(31, statusText);
            datalist.add(data);
            
            i++;
        }
        return datalist;
        
    }
    
    public TemproaryTestingTask getTask(TestingTaskResultRequest request, TestingTaskRequest task, TestingSheet testingsheet, TestingScheduleHistory history)
    {
        String remark = "";
        if (null != request)
        {
            AbnormalSolveRecord abRecord = getRemarkByAbnormal(request.getTaskId());
            if (null != abRecord)
            {
                remark = abRecord.getRemark();
            }
            if (StringUtils.isEmpty(remark) && StringUtils.isNotEmpty(request.getRemark()))
            {
                remark = request.getRemark();
            }
        }
        if (StringUtils.isNotEmpty(remark))
        {
            if (StringUtils.isNotEmpty(history.getRemark()))
            {
                remark = remark + "-暂停/取消备注：" + history.getRemark();
            }
        }
        else
        {
            remark = history.getRemark();
        }
        TemproaryTestingTask ttt = new TemproaryTestingTask();
        ttt.setSampleCode(task.getReceivedSampleCode());
        ttt.setVerifyChromosomePosition(task.getVerifyChromosomePosition());
        ttt.setPauseName(history.getPauseName());
        ttt.setPauseTime(history.getPauseTime());
        ttt.setRestartName(history.getRestartName());
        ttt.setRestartTime(history.getRestartTime());
        ttt.setRemark(remark);
        ttt.setTaskId(task.getId());
        ttt.setTaskName(task.getName());
        ttt.setEndTime(task.getEndTime());
        ttt.setStatus(getStatus(task.getStatus()));
        ttt.setEndStatus(getEndStatus(task.getEndType()));
        if (null != testingsheet)
        {
            ttt.setTesterName(testingsheet.getTesterName());
            ttt.setAssignerTime(testingsheet.getAssignTime());
            ttt.setTaskCode(testingsheet.getCode());
        }
        return ttt;
    }
    
    public String getStatus(Integer state)
    {
        if (null != state)
        {
            if (state == 1)
            {
                return "待分配";
            }
            else if (state == 2)
            {
                return "待实验";
            }
            else if (state == 3)
            {
                return "已结束";
            }
            else if (state == 4)
            {
                return "已暂停";
            }
            else
            {
                return "已取消";
            }
        }
        else
        {
            return "";
        }
    }
    
    public String getEndStatus(Integer status)
    {
        if (null != status)
        {
            
            if (status == 1)
            {
                return "正常结束";
            }
            else
            {
                return "异常结束";
            }
        }
        else
        {
            return "";
        }
    }
    
    private Pagination<OrderScheduleModel> getOrderScheduleRecords(OrderSearchRequest searcher)
    {
        String url = GatewayService.getServiceUrl("/export/finance/orderSchedulePaging");
        ResponseEntity<Pagination<OrderScheduleModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<OrderSearchRequest>(searcher),
                new ParameterizedTypeReference<Pagination<OrderScheduleModel>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public Date getTaskStartDate(String scheduleId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getTaskStartDate/{scheduleId}");
        return template.getForObject(url, Date.class, Collections.singletonMap("scheduleId", scheduleId));
    }
    
    @Override
    public List<BaseInfoModel> getOrderForBaseInfoList(OrderSearchRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/getOrderListForBaseInfo");
        ResponseEntity<List<BaseInfoModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<List<BaseInfoModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public TestingTaskResult getTestingTaskResultById(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getTestingTaskResultById/{id}");
        return template.getForObject(url, TestingTaskResult.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public void synchronizeOrderPayTime()
    {
        String url = GatewayService.getServiceUrl("/maintain/order/synchronizeOrderPayTime");
        template.getForObject(url, String.class);
    }
    
    @Override
    public List<GanalysisSampleInfo> searchSamplesByCustomerId(SampleSynchronizeRequest request)
    {
        String url = GatewayService.getServiceUrl("/searchSamplesByCustomerId");
        ResponseEntity<List<GanalysisSampleInfo>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<SampleSynchronizeRequest>(request),
                new ParameterizedTypeReference<List<GanalysisSampleInfo>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void modifyOrderDisese(OrderFormRequest request)
    {
        String url = GatewayService.getServiceUrl("/bmm/order/modifyOrderDisese");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public String resolveAccountStatement(String data)
    {
        String url = GatewayService.getServiceUrl("/orders/resolveAccountStatement");
        return template.postForObject(url, Collections.singletonMap("data", data), String.class);
    }
    
    @Override
    public Boolean checkOrderTechnicalStatus(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/{id}/checkOrderTechnicalStatus");
        return template.getForObject(url, Boolean.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public Pagination<ProductCancelRecord> cancelOrderProductSechedulePaging(ProductScheduleCancelSearcher searcher, int pageNo, int defaultpagesize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(defaultpagesize);
        String url = GatewayService.getServiceUrl("/bpm/testing/order/cancelOrderProductSechedulePaging");
        ResponseEntity<Pagination<ProductCancelRecord>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ProductScheduleCancelSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<ProductCancelRecord>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public ProductCancelRecord getProductCancelRecordById(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/order/getProductCancelRecordById/{id}");
        return template.getForObject(url, ProductCancelRecord.class, Collections.singletonMap("id", id));
    }
    @Override
    public TestingTaskRequest getDNATaskByHistory(TestingScheduleHistory history) {
        String url = GatewayService.getServiceUrl("/bpm/testing/getDNATaskByScheduleAndTime");
        TestingTask task = template.postForObject(url, history, TestingTask.class);
        TestingTaskRequest  request = new TestingTaskRequest();
        if (task!=null)
        {
            request.setId(task.getId());
            return request;
        }
        return  null;
    }

    @Override
    public TestingScheduleHistory getScheduleHistoryByTaskAndSchedule(ScheduleHistoryRequest request) {
        String url = GatewayService.getServiceUrl("/bpm/testing/getScheduleHistoryByTaskAndSchedule");
        return template.postForObject(url, request, TestingScheduleHistory.class);
    }

    @Override
    public BiologyAnnotationFailOperate getAnnotationFailOperateByTask(String taskId) {
        String url = GatewayService.getServiceUrl("/biology/annotation/getAnnotationFailOperateByTask/{taskId}");
        return template.getForObject(url, BiologyAnnotationFailOperate.class, Collections.singletonMap("taskId", taskId));
    }

    @Override
    public String getOrderSyncTest() {
        String url = GatewayService.getServiceUrl("/order/sync/test");
        return template.getForObject(url, String.class);
    }

    @Override
    public OrderDetail searchByOrderCode(String orderFormCode) {
        String url = GatewayService.getServiceUrl("/order/sync/searchByOrderCode/{orderFormCode}");
        return template.getForObject(url, OrderDetail.class, Collections.singletonMap("orderFormCode", orderFormCode));
    }

    @Override
    public List<OrderDetail> batchSearch(OrderSyncSearchModel request) {
        String url = GatewayService.getServiceUrl("/order/sync/batchSearch");
        ResponseEntity<List<OrderDetail>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSyncSearchModel>(request), new ParameterizedTypeReference<List<OrderDetail>>() {
                });
        return exchange.getBody();
    }
}
