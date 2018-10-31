package com.todaysoft.lims.system.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.model.syncmodel.OrderDetail;
import com.todaysoft.lims.system.model.syncmodel.OrderSyncSearchModel;
import com.todaysoft.lims.system.modules.bpm.report.model.OrderSyncResponseModel;
import com.todaysoft.lims.system.modules.smm.model.APPSaleman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.base.RecordFilter;
import com.todaysoft.lims.order.request.OrderStatusScheduleRequest;
import com.todaysoft.lims.order.response.CommonOrderResponse;
import com.todaysoft.lims.order.response.OrderStatusScheduleModel;
import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.model.request.PhenoTypePointsRequest;
import com.todaysoft.lims.system.model.searcher.ContractSearcher;
import com.todaysoft.lims.system.model.searcher.SampleReceiveRecordSearcher;
import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.DataArea;
import com.todaysoft.lims.system.model.vo.OrderContrctSearcher;
import com.todaysoft.lims.system.model.vo.OrderSample;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.PaymentSearchRequest;
import com.todaysoft.lims.system.model.vo.Phenotype;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.ProductSample;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.TestingReSampleNoSampleModel;
import com.todaysoft.lims.system.model.vo.TestingScheduleActive;
import com.todaysoft.lims.system.model.vo.TestingTaskRequest;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.model.vo.TestingtechnicalanalyrecordTempory;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.contract.Contract;
import com.todaysoft.lims.system.model.vo.contract.ContractChangeSignUser;
import com.todaysoft.lims.system.model.vo.contract.ContractContent;
import com.todaysoft.lims.system.model.vo.contract.ContractPartyA;
import com.todaysoft.lims.system.model.vo.contract.ContractPartyB;
import com.todaysoft.lims.system.model.vo.contract.ContractPaymentRecord;
import com.todaysoft.lims.system.model.vo.contract.ContractSample;
import com.todaysoft.lims.system.model.vo.contract.ContractUser;
import com.todaysoft.lims.system.model.vo.disease.Disease;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.model.vo.order.AbnormalSolveModel;
import com.todaysoft.lims.system.model.vo.order.AddErrorSampleFormRequest;
import com.todaysoft.lims.system.model.vo.order.AppSampleBackApply;
import com.todaysoft.lims.system.model.vo.order.AppSampleTransport;
import com.todaysoft.lims.system.model.vo.order.ContractReconciliationRecord;
import com.todaysoft.lims.system.model.vo.order.DealScheduleModel;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderDeleteRequest;
import com.todaysoft.lims.system.model.vo.order.OrderExaminee;
import com.todaysoft.lims.system.model.vo.order.OrderExamineeDiagnosis;
import com.todaysoft.lims.system.model.vo.order.OrderExamineeGene;
import com.todaysoft.lims.system.model.vo.order.OrderExamineePhenotype;
import com.todaysoft.lims.system.model.vo.order.OrderFormRequest;
import com.todaysoft.lims.system.model.vo.order.OrderPaymentModel;
import com.todaysoft.lims.system.model.vo.order.OrderPhenotypePointRequest;
import com.todaysoft.lims.system.model.vo.order.OrderPrimarySample;
import com.todaysoft.lims.system.model.vo.order.OrderProduct;
import com.todaysoft.lims.system.model.vo.order.OrderProductForStatusScheduleModel;
import com.todaysoft.lims.system.model.vo.order.OrderProductTestingScheduleRequest;
import com.todaysoft.lims.system.model.vo.order.OrderResearchProduct;
import com.todaysoft.lims.system.model.vo.order.OrderResearchSample;
import com.todaysoft.lims.system.model.vo.order.OrderResearchSampleForUpload;
import com.todaysoft.lims.system.model.vo.order.OrderSampleDetails;
import com.todaysoft.lims.system.model.vo.order.OrderSampleGroup;
import com.todaysoft.lims.system.model.vo.order.OrderSampleReceiveingModel;
import com.todaysoft.lims.system.model.vo.order.OrderSampleTransportModel;
import com.todaysoft.lims.system.model.vo.order.OrderSampleView;
import com.todaysoft.lims.system.model.vo.order.OrderSearchRequest;
import com.todaysoft.lims.system.model.vo.order.OrderSubsidiarySample;
import com.todaysoft.lims.system.model.vo.order.ProductCancelRecord;
import com.todaysoft.lims.system.model.vo.order.ProductScheduleCancelSearcher;
import com.todaysoft.lims.system.model.vo.order.ScheduleQuery;
import com.todaysoft.lims.system.model.vo.order.TemporarySample;
import com.todaysoft.lims.system.model.vo.order.Temproary;
import com.todaysoft.lims.system.model.vo.order.TemproaryTestingTask;
import com.todaysoft.lims.system.model.vo.order.TestingSchedule;
import com.todaysoft.lims.system.model.vo.order.TestingScheduleHistory;
import com.todaysoft.lims.system.model.vo.order.TestingTaskResult;
import com.todaysoft.lims.system.model.vo.order.TestingTaskResultRequest;
import com.todaysoft.lims.system.model.vo.payment.OrderPaymentConfirm;
import com.todaysoft.lims.system.model.vo.payment.OrderRefund;
import com.todaysoft.lims.system.model.vo.samplereceiving.ReceivedSample;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleReceiving;
import com.todaysoft.lims.system.model.vo.samplereceiving.SampleReceivingDetail;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.service.IConfigService;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bmm.service.IOrderTestingMonitorService;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.BiologyAnnotationFailOperate;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.TestingSheet;
import com.todaysoft.lims.system.modules.bpm.report.model.ReportAndVerifyModel;
import com.todaysoft.lims.system.modules.bpm.report.service.IReportService;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSample;
import com.todaysoft.lims.system.modules.bpm.service.ITestingProcessService;
import com.todaysoft.lims.system.modules.bpm.service.TestingTaskSheetServiceFactory;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.ITechnicalAnalyService;
import com.todaysoft.lims.system.modules.report.model.ReportEmailForOrderStatusModel;
import com.todaysoft.lims.system.modules.report.model.TestingReport;
import com.todaysoft.lims.system.modules.report.service.IReportEmailService;
import com.todaysoft.lims.system.modules.smm.model.Dict;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.UserSimpleModel;
import com.todaysoft.lims.system.modules.smm.service.IAPPSalemanService;
import com.todaysoft.lims.system.modules.smm.service.IDataAuthorityService;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.service.IAreaService;
import com.todaysoft.lims.system.service.IContractService;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.IPaymentService;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ISampleReceivingService;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.adapter.request.ProductListRequest;
import com.todaysoft.lims.system.service.adapter.request.SamplePagingRequest;
import com.todaysoft.lims.system.util.AppToken;
import com.todaysoft.lims.system.util.Arith;
import com.todaysoft.lims.system.util.CommonResult;
import com.todaysoft.lims.system.util.Constant;
import com.todaysoft.lims.system.util.JsonUtils;
import com.todaysoft.lims.system.util.MD5Util;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.FileUtils;
import com.todaysoft.lims.utils.IdGen;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController
{
    @Autowired
    private IMetadataSampleService sampleService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IProductService productService;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private ITestingProcessService testingProcessService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ISampleReceivingService service;
    
    @Autowired
    private IDataAuthorityService dataAuthorityService;
    
    @Autowired
    private IAPPSalemanService appService;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IConfigService configService;
    
    @Autowired
    private ITaskService taskService;
    
    @Autowired
    private IPaymentService paymentService;
    
    @Autowired
    private IReportService reportService;
    
    @Autowired
    private IDictService dictService;
    
    @Autowired
    private IReportEmailService reportEmailService;
    
    @Autowired
    private ICustomerService cService;
    
    @Autowired
    private IOrderTestingMonitorService orderTestingMonitorService;
    
    @Autowired
    private TestingTaskSheetServiceFactory testingTaskSheetServiceFactory;
    
    @Autowired
    private ITechnicalAnalyService technicalAnalyService;
    
    @Autowired
    private PhenotypeController phenotypeController;
    
    /**
     * 新建临检、健康样本
     */
    @RequestMapping(value = "/createCheckOrder.do")
    public String createCheckOrder(OrderSample searcher, ModelMap model)
    {
        // 传递到前台，数量+金额
        Map<Integer, Integer> map = getSubPriceInitMap();
        for (Integer key : map.keySet())
        {
            searcher.setFreeCount(key);
            searcher.setExtraMoney(map.get(key) / 100);
        }
        List<Dict> classfys = dictService.getEntries("SAMPLE_PURPOSE");
        model.addAttribute("samplePurposeDict", JSONObject.toJSONString(classfys));
        
        List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
        model.addAttribute("sampleList", JSONObject.toJSONString(sampleList));
        model.addAttribute("sample", sampleList);
        model.addAttribute("searcher", JSONObject.toJSONString(searcher));
        
        int type = searcher.getOrderType();
        if (Constant.ORDER_RESEARCH_TYPE == type)
        {
            return "order/researchorder";
        }
        return "order/checkorder";
    }
    
    private Map<Integer, Integer> getSubPriceInitMap()
    {
        Map<Integer, Integer> subSamplePrice = new HashMap<Integer, Integer>();
        List<String> freeCountList = configService.getConfigValues(FREE_KEY);
        List<String> extraMoneyList = configService.getConfigValues(EXTRA_AMOUNT_KEY);
        Integer freeCount = 1, extraMoney = 30000;// 默认1个 , 200块
        if (Collections3.isNotEmpty(freeCountList))
        {
            freeCount = Integer.parseInt(freeCountList.get(0));
        }
        if (Collections3.isNotEmpty(extraMoneyList))
        {
            extraMoney = Integer.parseInt(extraMoneyList.get(0));
        }
        subSamplePrice.put(freeCount, extraMoney);
        
        return subSamplePrice;
    }
    
    /**
     * 新建科研样本
     */
    @RequestMapping(value = "/createResearchOrder.do")
    public String createResearchOrder(OrderSample searcher, ModelMap model)
    {
        List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
        model.addAttribute("sampleList", JSONObject.toJSONString(sampleList));
        model.addAttribute("sample", sampleList);
        return "order/researchorder";
    }
    
    @RequestMapping(value = "/paging.do")
    public String paging(OrderSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        RecordFilter filter = getAccountDetails().getRecordFilter("ORDER_LIST");
        searcher.setFilter(filter);
        Pagination<CommonOrderResponse> pagination = orderService.paging(searcher, pageNo, DEFAULTPAGESIZE);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (CommonOrderResponse cor : pagination.getRecords())
            {
                Order order = orderService.getById(cor.getId());
                if (StringUtils.isNotEmpty(order.getProjectManager()))
                {
                    UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                    cor.setPrjManagerName(prjManagerUser.getArchive().getName());
                }
            }
        }
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "order/order_list";
    }
    
    @RequestMapping("/orderFinancialReport/main.do")
    public String orderFinancialReport(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return "order/financialreport/order_financial_report_main";
    }
    
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    
    @RequestMapping("/exportOrderFinancialFile.do")
    @ResponseBody
    public String exportOrderFinancialFile(OrderSearchRequest searcher, HttpSession session) throws Exception
    {
        String sessionId = IdGen.uuid();
        searcher.setTaskId(sessionId);
        User user = userService.getUserByToken();
        searcher.setUserId(user.getId());
        UserDetailsModel userachive = userService.getUserByID(user.getId());
        searcher.setUserName(userachive.getArchive().getName());
        cachedThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String fileUrl = orderService.exportOrderFinancialFile(searcher);
                session.setAttribute(sessionId, fileUrl);
            }
        });
        return sessionId;
    }
    
    @RequestMapping("/exportOrderExcludeContractFile.do")
    @ResponseBody
    public String exportOrderExcludeContractFile(OrderSearchRequest searcher, HttpSession session) throws Exception
    {
        String sessionId = IdGen.uuid();
        searcher.setTaskId(sessionId);
        User user = userService.getUserByToken();
        searcher.setUserId(user.getId());
        UserDetailsModel userachive = userService.getUserByID(user.getId());
        searcher.setUserName(userachive.getArchive().getName());
        cachedThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String fileUrl = orderService.exportOrderExcludeContractFile(searcher);
                session.setAttribute(sessionId, fileUrl);
            }
        });
        return sessionId;
    }
    
    @RequestMapping("/exportOrderIncludeScheduleFile.do")
    @ResponseBody
    public String exportOrderIncludeScheduleFile(OrderSearchRequest searcher, HttpSession session) throws Exception
    {
        
        String sessionId = IdGen.uuid();
        searcher.setTaskId(sessionId);
        User user = userService.getUserByToken();
        searcher.setUserId(user.getId());
        UserDetailsModel userachive = userService.getUserByID(user.getId());
        searcher.setUserName(userachive.getArchive().getName());
        cachedThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String fileUrl = orderService.exportOrderIncludeScheduleFile(searcher);
                session.setAttribute(sessionId, fileUrl);
            }
        });
        return sessionId;
    }
    
    @RequestMapping(value = "/countSubSampleByRelationId.do")
    @ResponseBody
    public int countSubSampleByRelationId(OrderSearchRequest searcher)
    {
        int result = -1;
        List<OrderSubsidiarySample> count = orderService.countSubSampleByRelationId(searcher);
        if (Collections3.isNotEmpty(count))
        {
            result = count.size();
        }
        return result;
        
    }
    
    @RequestMapping(value = "/startErrPaging.do")
    public String startErrPaging(OrderSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        // **数据权限开始 。。。。。。。。。。。。。。。。。*//*
        RecordFilter filter = getAccountDetails().getRecordFilter("ERRO_ORDER");
        searcher.setFilter(filter);
        searcher.setSheduleStatus(1);// 启动异常
        
        Pagination<CommonOrderResponse> pagination = orderService.errPaging(searcher, pageNo, DEFAULTPAGESIZE);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (CommonOrderResponse cor : pagination.getRecords())
            {
                Order order = orderService.getById(cor.getId());
                if (StringUtils.isNotEmpty(order.getProjectManager()))
                {
                    UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                    cor.setPrjManagerName(prjManagerUser.getArchive().getName());
                }
            }
            
        }
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        
        return "order/order_err_list";
    }
    
    /**
     * 临检,健康订单查看
     */
    @RequestMapping(value = "/viewOrder.do")
    public String viewOrder(Order searcher, ModelMap model)
    {
        Order order = orderService.getById(searcher.getId().toString());
        if(StringUtils.isNotEmpty(order.getOrderType()) && order.getOrderType().equals("4"))
        {
            searcher.setShowCopyOrder("2"); //科研订单没有复制订单的功能
        }
        searcher.setTestingStatus(order.getTestingStatus());
        if (StringUtils.isNotEmpty(order.getOwnerId()))
        {
            Customer cc = customerService.get(order.getOwnerId());
            order.setOwner(cc);
        }
        
        if (Collections3.isNotEmpty(order.getOrderExamineeList()))
        {
            OrderExaminee o = order.getOrderExamineeList().get(0);
            
            if (StringUtils.isNotEmpty(o.getConsentFigures()))
            {// 获取路径
                StringBuffer zhiqing = new StringBuffer();
                String[] fileNames = o.getConsentFigures().split("\\,");
                for (String fileName : fileNames)
                {
                    zhiqing.append(FileUtils.getDownloadUrl(fileName) + ",");
                    
                }
                o.setConsentFiguresShow(zhiqing.toString());
            }
            if (StringUtils.isNotEmpty(o.getRecordFigures()))
            {// 获取路径
                StringBuffer fujian = new StringBuffer();
                String[] fileNames = o.getRecordFigures().split("\\,");
                for (String fileName : fileNames)
                {
                    fujian.append(FileUtils.getDownloadUrl(fileName) + ",");
                    
                }
                o.setRecordFiguresShow(fujian.toString());
            }
            if (StringUtils.isNotEmpty(o.getFamilyFigures()))
            {// 获取路径
                StringBuffer familyFigures = new StringBuffer();
                String[] fileNames = o.getFamilyFigures().split("\\,");
                for (String fileName : fileNames)
                {
                    familyFigures.append(FileUtils.getDownloadUrl(fileName) + ",");
                }
                o.setFamilyFiguresShow(familyFigures.toString());
            }
            if (StringUtils.isNotEmpty(o.getOrigin()))
            {
                String result = exchangeArea(o.getOrigin());
                DataArea target = areaService.get(Integer.parseInt(result));
                if (StringUtils.isNotEmpty(target))
                {
                    o.setOriginFullName(target.getFullName());
                }
                
            }
            
            model.addAttribute("OrderExaminee", o);
        }
        else
        {
            model.addAttribute("OrderExaminee", new ArrayList<OrderExaminee>());
        }
        
        resolveSamples(order);
        
        if (Collections3.isNotEmpty(order.getOrderRefund()))
        {
            List<OrderRefund> orderRefund = order.getOrderRefund();
            
            List<OrderProduct> orderProduct = null;
            
            for (OrderRefund r : orderRefund)
            {
                Double applyAmount = (double)0;
                String productString = r.getOrderProductId();
                
                if (StringUtils.isNotEmpty(productString))
                {
                    String[] ids = productString.split(",");
                    
                    for (String id : ids)
                    {
                        
                        OrderProduct p = orderService.getOrderProductById(id);
                        if (StringUtils.isNotEmpty(p))
                        {
                            orderProduct = new ArrayList<OrderProduct>();
                            orderProduct.add(p);
                            applyAmount = Arith.add(applyAmount, Double.valueOf(p.getProductPrice()));
                        }
                        
                    }
                    
                }
                r.setOrderProduct(orderProduct);
                r.setApplyAmount(applyAmount);
            }
        }
        
        List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
        
        order.setPaymentTime(order.getPayTime());
        List<OrderPaymentConfirm> paymentList = paymentService.getOrderPaymentConfirmByOrderId(searcher.getId());
        if (Collections3.isNotEmpty(paymentList))
        {
            order.setCheckTime(paymentList.get(0).getCheckTime());
        }
        if (null != order.getContract())
        {
            ContractContent content = contractService.getContractContentByContractId(order.getContract().getId());
            if (null != content && !"4".equals(content.getSettlementMode()))// 集中开票
                                                                            // (非一单一结)
            {
                List<ContractReconciliationRecord> recordList = orderService.getContractRecRecordByOrderId(searcher.getId());
                if (Collections3.isNotEmpty(recordList)) // 不定期对账
                {
                    order.setPaymentTime(recordList.get(0).getCreateTime());
                    order.setCheckTime(recordList.get(0).getCreateTime());
                }
                else
                {
                    List<ContractPaymentRecord> payList = orderService.getSettlePaymentByOrderId(searcher.getId());
                    if (Collections3.isNotEmpty(payList)) // 出账单对账
                    {
                        order.setPaymentTime(Collections3.getFirst(payList).getPaymentTime());
                        order.setCheckTime(Collections3.getFirst(payList).getCheckTime());
                    }
                }
            }
            
        }
        List<FinanceInvoiceTask> taskList = orderService.getInvoiceInfoByOrderId(searcher.getId().toString());
        order.setTaskList(taskList);
        List<TestingReport> reportList = reportService.getReportListByOrderId(searcher.getId().toString());
        order.setReportList(reportList);
        
        model.addAttribute("record", order);
        model.addAttribute("orderJson", JSONObject.toJSONString(order));
        model.addAttribute("sampleList", JSONObject.toJSONString(sampleList));
        model.addAttribute("sample", sampleList);
        model.addAttribute("searcher", searcher);
        if (StringUtils.isNotEmpty(searcher.getFlag()))
        {
            model.addAttribute("flag", searcher.getFlag());
        }
        return "order/checkorder_show";
    }
    
    public static final String FREE_KEY = "SUBSIDIARY_SAMPLE_BASE";
    
    public static final String EXTRA_AMOUNT_KEY = "SUBSIDIARY_SAMPLE_EXTRA";
    
    /**
     * 临检,健康订单修改页面
     */
    @RequestMapping(value = "/modifyOrder.do")
    public String modifyOrder(Order searcher, ModelMap model)
    {
        Order order = orderService.getById(searcher.getId());
        if (StringUtils.isNotEmpty(order))
        {
            // order.setSheduleStatus(1); //获取流程状态
            // 设置订单检测状态 非科研{1：DNA提取之前 2：DNA提取阶段 3：数据分析之前 4：出报告之前} 科研{1：DNA提取之前
            // 2：DNA提取阶段 3：生信分析之前}
            Map<String, Object> scheduleStatus = orderService.callbackOrderScheulStatus(order.getId());
            order.setSheduleStatus(Integer.parseInt(scheduleStatus.get("status").toString()));
            order.setOwner(customerService.get(order.getOwnerId()));
            order.setRecipientName(StringUtils.isEmpty(order.getRecipientName()) ? "" : order.getRecipientName());
            // 传递到前台，数量+金额
            Map<Integer, Integer> map = getSubPriceInitMap();
            for (Integer key : map.keySet())
            {
                order.setFreeCount(key);
                order.setExtraMoney(map.get(key) / 100);
            }
            
            model.addAttribute("order", order);
            
            resolvePic(model, order);
            
            resolveSamples(order);
            
            //修改页面回显项目管理人
            if (order.getProjectManager() != null && order.getProjectManager() != "")
            {
                UserDetailsModel udm = userService.getUserByID(order.getProjectManager());
                UserSimpleModel usm = new UserSimpleModel();
                usm.setId(udm.getId());
                usm.setName(udm.getArchive().getName());
                usm.setPhone(udm.getArchive().getPhone());
                model.addAttribute("prjManagerUser", JSONObject.toJSON(usm).toString());
            }
            else
            {
                model.addAttribute("prjManagerUser", 1);
            }
            
            List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
            model.addAttribute("orderJson", JSONObject.toJSONString(order));
            model.addAttribute("sampleList", JSONObject.toJSONString(sampleList));
            model.addAttribute("sample", sampleList);
            
            List<Dict> classfys = dictService.getEntries("SAMPLE_PURPOSE");
            model.addAttribute("samplePurposeDict", JSONObject.toJSONString(classfys));
            String type = order.getOrderType();
            
            if (null != type && type.equals(String.valueOf(Constant.ORDER_RESEARCH_TYPE)))
            {
                //修改页面回显项目管理人
                if (order.getProjectManager() != null && order.getProjectManager() != "")
                {
                    UserDetailsModel udm = userService.getUserByID(order.getProjectManager());
                    UserSimpleModel usm = new UserSimpleModel();
                    usm.setId(udm.getId());
                    usm.setName(udm.getArchive().getName());
                    usm.setPhone(udm.getArchive().getPhone());
                    model.addAttribute("prjManagerUser", JSONObject.toJSON(usm).toString());
                }
                else
                {
                    model.addAttribute("prjManagerUser", 1);
                }
                return "order/researchorder_form";
            }
        }
        return "order/checkorder_form";
    }
    
    private void resolveSamples(Order order)
    {
        if (StringUtils.isNotEmpty(order.getOrderPrimarySample()))
        {
            List<OrderPrimarySample> psample = order.getOrderPrimarySample();
            for (OrderPrimarySample o : psample)
            {
                if (StringUtils.isNotEmpty(o.getSampleTypeId()))
                {
                    MetadataSample metaSample = sampleService.get(o.getSampleTypeId());
                    if (StringUtils.isNotEmpty(metaSample))
                    {
                        o.setSamplereceivingUnit(metaSample.getReceivingUnit());
                        o.setSampleTypeName(metaSample.getName());
                    }
                }

            }
        }
        if (StringUtils.isNotEmpty(order.getOrderSubsidiarySample()))
        {
            
            List<OrderSubsidiarySample> ssample = order.getOrderSubsidiarySample();
            for (OrderSubsidiarySample o : ssample)
            {
                if (StringUtils.isNotEmpty(o.getSampleTypeId()))
                {
                    MetadataSample metaSample = sampleService.get(o.getSampleTypeId());
                    if (StringUtils.isNotEmpty(metaSample))
                    {
                        o.setSamplereceivingUnit(metaSample.getReceivingUnit());
                        o.setSampleTypeName(metaSample.getName());
                    }
                }
            }
        }
        if (StringUtils.isNotEmpty(order.getOrderSampleGroup()))
        {
            List<OrderSampleGroup> ssample = order.getOrderSampleGroup();
            for (OrderSampleGroup o : ssample)
            {
                if (StringUtils.isNotEmpty(o.getOrderResearchSample()))
                {
                    for (OrderResearchSample ors : o.getOrderResearchSample())
                    {
                        MetadataSample metaSample = sampleService.get(ors.getSampleTypeId());
                        if (StringUtils.isNotEmpty(metaSample))
                        {
                            ors.setSamplereceivingUnit(metaSample.getReceivingUnit());
                            ors.setSampleTypeName(metaSample.getName());
                        }
                    }
                    
                }
            }
        }
    }
    
    private void resolvePic(ModelMap model, Order order)
    {
        if (Collections3.isNotEmpty(order.getOrderExamineeList()))
        {
            OrderExaminee o = order.getOrderExamineeList().get(0);
            
            if (StringUtils.isNotEmpty(o.getConsentFigures()))
            {// 获取路径
                StringBuffer zhiqing = new StringBuffer();
                String[] fileNames = o.getConsentFigures().split("\\,");
                for (String fileName : fileNames)
                {
                    zhiqing.append(FileUtils.getDownloadUrl(fileName) + ",");
                    
                }
                o.setConsentFiguresShow(zhiqing.toString());
            }
            if (StringUtils.isNotEmpty(o.getRecordFigures()))
            {// 获取路径
                StringBuffer fujian = new StringBuffer();
                String[] fileNames = o.getRecordFigures().split("\\,");
                for (String fileName : fileNames)
                {
                    fujian.append(FileUtils.getDownloadUrl(fileName) + ",");
                    
                }
                o.setRecordFiguresShow(fujian.toString());
            }
            if (StringUtils.isNotEmpty(o.getFamilyFigures()))
            {// 获取路径
                StringBuffer familyFigures = new StringBuffer();
                String[] fileNames = o.getFamilyFigures().split("\\,");
                for (String fileName : fileNames)
                {
                    familyFigures.append(FileUtils.getDownloadUrl(fileName) + ",");
                }
                o.setFamilyFiguresShow(familyFigures.toString());
            }
            
            if (StringUtils.isNotEmpty(o.getOrigin()))
            {
                String result = exchangeArea(o.getOrigin());
                DataArea target = areaService.get(Integer.parseInt(result));
                if (StringUtils.isNotEmpty(target))
                {
                    o.setTreePath(target.getTreePath());
                    
                }
                o.setOrigin(result);
                
            }
            model.addAttribute("OrderExaminee", o);
        }
        else
        {
            model.addAttribute("OrderExaminee", new ArrayList<OrderExaminee>());
        }
    }
    
    private static String exchangeArea(String i)
    {
        String result = "1";
        String[] change = i.split(",");
        if (StringUtils.isNotEmpty(change))
        {
            result = change[change.length - 1];
        }
        return result;
    }
    
    @Autowired
    private AppToken token;
    
    @RequestMapping(value = "/create.do")
    @ResponseBody
    public Map<String, Object> create(OrderFormRequest request, ModelMap model, HttpSession session)
    {
        // 科技订单设置redis编码
        if (StringUtils.isEmpty(request.getCode()))
        {
            try
            {
                request.setCode(token.getOrderCode());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (StringUtils.isNotEmpty(request.getOrigin()))
        {
            DataArea parent = areaService.get(Integer.parseInt(request.getOrigin()));
            if (StringUtils.isNotEmpty(parent))
            {
                String treepath = parent.getTreePath();
                String origin = String.join("", treepath, request.getOrigin());
                if (StringUtils.isNotEmpty(treepath))
                {
                    request.setOrigin(origin.substring(1));
                }
            }
        }
        
        User user = userService.getUserByToken();
        if (user != null)
        {
            request.setSubmitterId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setSubmitterName(userachive.getArchive().getName());
        }
        
        // 组装组样本
        List<OrderSampleGroup> orderSampleGroup = JSON.parseArray(request.getParmarySample() + "", OrderSampleGroup.class);
        request.setOrderSampleGroup(orderSampleGroup);
        // 组装产品
        List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
        if (StringUtils.isNotEmpty(request.getOrderProduct()))
        {
            for (String productId : request.getOrderProduct().split("\\,"))
            {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setProductId(productId);
                orderProductList.add(orderProduct);
            }
            request.setOrderProductList(orderProductList);
        }
        
        // 组装临床诊断
        if (StringUtils.isNotEmpty(request.getOrderExamineeDiagnosis()))
        {
            for (String disseaseId : request.getOrderExamineeDiagnosis().split("\\,"))
            {
                OrderExamineeDiagnosis oED = new OrderExamineeDiagnosis();
                Disease disease = new Disease();
                disease.setId(disseaseId);
                oED.setDisease(disease);
                request.getOrderExamineeDiagnosisList().add(oED);
            }
        }
        
        // 组装临床表型
        if (StringUtils.isNotEmpty(request.getOrderExamineePhenotype()))
        {
            for (String phenoTypeId : request.getOrderExamineePhenotype().split("\\,"))
            {
                if (StringUtils.isNotEmpty(phenoTypeId))
                {
                    OrderExamineePhenotype oEP = new OrderExamineePhenotype();
                    Phenotype phenoType = new Phenotype();
                    phenoType.setId(phenoTypeId);
                    oEP.setPhenotype(phenoType);
                    request.getOrderExamineePhenotypeList().add(oEP);
                }
            }
        }
        // 组装基因数据
        if (StringUtils.isNotEmpty(request.getOrderExamineeGene()))
        {
            for (String geneId : request.getOrderExamineeGene().split("\\,"))
            {
                OrderExamineeGene oEG = new OrderExamineeGene();
                Gene gene = new Gene();
                gene.setId(geneId);
                oEG.setGene(gene);
                request.getOrderExamineeGeneList().add(oEG);
            }
        }
        // 组装主样本数据
        List<OrderPrimarySample> orderPrimarySample = JSON.parseArray(request.getParmarySample() + "", OrderPrimarySample.class);
        
        request.setOrderPrimarySample(orderPrimarySample);
        // 组装附属样本
        List<OrderSubsidiarySample> orderSubSample = JSON.parseArray(request.getSubsidiarySample() + "", OrderSubsidiarySample.class);
        if (Collections3.isNotEmpty(orderSubSample))
        {
            Iterator<OrderSubsidiarySample> it = orderSubSample.iterator();
            while (it.hasNext())
            {
                if (StringUtils.isEmpty(it.next().getSampleCode()))
                {
                    it.remove();
                }
                
            }
            
        }
        request.setOrderSubsidiarySample(orderSubSample);
        
        if (StringUtils.isNotEmpty(request.getId()))
        {
            orderService.update(request);
        }
        else
        {
            //新增订单增加南北区
            if(StringUtils.isNotEmpty(request.getCreatorId()))
            {
                APPSaleman appSaleman = appService.get(request.getCreatorId());
                if (appSaleman!=null)
                {
                    request.setBelongArea(appSaleman.getBelongArea());
                }
            }
            if (StringUtils.isNotEmpty(request.getCode()))
            {
                OrderSearchRequest searchrequest = new OrderSearchRequest();
                searchrequest.setCode(request.getCode());
                List<Order> preResult = orderService.list(searchrequest);
                if (Collections3.isNotEmpty(preResult))
                {
                    map.put("result", false);
                    map.put("msg", "存在重复编号！");
                    return map;
                }
                else
                {
                    orderService.create(request);
                }
            }
            else
            {
                orderService.create(request);
            }
            
        }
        
        map.put("result", true);
        return map;
    }
    
    /**
     * 修改订单疾病基因表型---发送打分接口
     * @param request
     * @param model
     * @param session
     * @return
     */
    
    @RequestMapping(value = "/modifyOrderDisease.do")
    @ResponseBody
    public Map<String, Object> modifyOrderDisese(OrderPhenotypePointRequest request, ModelMap model, HttpSession session)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        // 组装临床诊断
        if (StringUtils.isNotEmpty(request.getOrderExamineeDiagnosis()))
        {
            for (String disseaseId : request.getOrderExamineeDiagnosis().split("\\,"))
            {
                OrderExamineeDiagnosis oED = new OrderExamineeDiagnosis();
                Disease disease = new Disease();
                disease.setId(disseaseId);
                oED.setDisease(disease);
                request.getOrderExamineeDiagnosisList().add(oED);
            }
        }
        
        // 组装临床表型
        if (StringUtils.isNotEmpty(request.getOrderExamineePhenotype()))
        {
            for (String phenoTypeId : request.getOrderExamineePhenotype().split("\\,"))
            {
                if (StringUtils.isNotEmpty(phenoTypeId))
                {
                    OrderExamineePhenotype oEP = new OrderExamineePhenotype();
                    Phenotype phenoType = new Phenotype();
                    phenoType.setId(phenoTypeId);
                    oEP.setPhenotype(phenoType);
                    request.getOrderExamineePhenotypeList().add(oEP);
                }
            }
        }
        // 组装基因数据
        if (StringUtils.isNotEmpty(request.getOrderExamineeGene()))
        {
            for (String geneId : request.getOrderExamineeGene().split("\\,"))
            {
                OrderExamineeGene oEG = new OrderExamineeGene();
                Gene gene = new Gene();
                gene.setId(geneId);
                oEG.setGene(gene);
                request.getOrderExamineeGeneList().add(oEG);
            }
        }
        Order order = orderService.getById(request.getId());
        //发送打分接口

        if (StringUtils.isNotEmpty(order))
        {

            OrderExaminee orderExaminee = Collections3.getFirst(order.getOrderExamineeList());
            List<String> origin_phenotypeIds = new ArrayList<String>();
            List<String> phenotypeIds = new ArrayList<String>();
            if (orderExaminee != null && Collections3.isNotEmpty(orderExaminee.getOrderExamineePhenotype()))
            {
                origin_phenotypeIds = orderExaminee.getOrderExamineePhenotype().stream().map(o -> o.getPhenotype().getCode()).collect(Collectors.toList());
            }
            List<Phenotype> phenotypes = phenotypeController.getPhenotypeByIds(request.getOrderExamineePhenotype());
            if (Collections3.isNotEmpty(phenotypes))
            {
                phenotypeIds = phenotypes.stream().map(o -> o.getCode()).collect(Collectors.toList());
            }
            
            if (StringUtils.isNotEmpty(phenotypeIds) && Collections3.isNotEmpty(different(origin_phenotypeIds, phenotypeIds)))
            { //表型非空
                PhenoTypePointsRequest pointRequest = new PhenoTypePointsRequest();
                pointRequest.setFamilyGroupId(request.getFamilyGroupId()); //任务组ID
                if (Collections3.isNotEmpty(order.getOrderExamineeList()))
                {
                    pointRequest.setExamineeSex(Collections3.getFirst(order.getOrderExamineeList()).getSex());
                }
                pointRequest.setHpoCode(phenotypeIds);
                int returnResult =  technicalAnalyService.updatePhenoTypePoints(pointRequest);
                if (returnResult != 0)
                {
                    orderService.modifyOrderDisese(request); //前台知识库传对象id
                }
                else
                {
                    map.put("flag", 0);
                }

            }
            
        }
        
        map.put("result", true);

        return map;
    }
    
    /**
     * 样本接收扫码专用
     */
    @RequestMapping(value = "/list.do")
    @ResponseBody
    public CommonResult list(OrderSearchRequest request)
    {
        CommonResult result = new CommonResult();
        List<Order> o = orderService.list(request);
        if (Collections3.isNotEmpty(o))
        {
            result.setSuccess(true);
            result.setMsg("订单扫样匹配成功！");
            result.setData(o.get(0));
            List<OrderSampleView> list = o.get(0).getView();
            List<OrderSampleView> view = new ArrayList<OrderSampleView>();
            if (Collections3.isNotEmpty(list))
            {
                for (OrderSampleView orderSampleView : list)
                {
                    if (Constant.ORDER_SAMPLE_SENDING == orderSampleView.getSamplePackageStatus() && StringUtils.isNotEmpty(orderSampleView.getSampleType()))
                    {
                        MetadataSample m = sampleService.get(orderSampleView.getSampleType());
                        orderSampleView.setUnit(m.getReceivingUnit());
                        view.add(orderSampleView);
                    }
                }
                o.get(0).setView(view);
            }
            if (Collections3.isEmpty(view))
            {
                result.setSuccess(false);
                result.setMsg("提示：订单无待收样样本，请重新扫描其他订单！");
                return result;
            }
            return result;
        }
        else
        {
            result.setSuccess(false);
            result.setMsg("提示：订单扫样匹配失败！");
            return result;
        }
        
    }
    
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public void delete(OrderDeleteRequest request, ModelMap model, HttpSession session)
    {
        User user = userService.getUserByToken();
        if (StringUtils.isNotEmpty(user) && StringUtils.isNotEmpty(request.getId()))
        {
            request.setCancelId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setCancelName(userachive.getArchive().getName());
        }
        orderService.delete(request);
        // return "redirect:/order/paging.do";
    }
    
    @RequestMapping("/upload.do")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response, ModelMap model)
    {
        List<String> fileList = FileUtils.upload(request, response);
        Map<String, Object> m = new HashMap<String, Object>();
        List<String> initView = new ArrayList<>();
        List initViewConfig = new ArrayList<>();
        for (String file : fileList)
        {
            initView.add("<script>$('.file-zoom-content > .file-preview-image').css('height','700px');$('.file-zoom-content').css('height','auto');</script><img  src='"
                + FileUtils.getDownloadUrl(file)
                + "' class='file-preview-image' style='max-width:700px;height:200px;'/>"
                + "<a style='display:flex' href='"
                + request.getContextPath() + "/order/getSrcByPath.do?fileName=" + file + "'>下载</a>");
            
            Map<String, Object> conf = new HashMap<String, Object>();
            conf.put("caption", file);
            conf.put("width", "120px");
            conf.put("key", file);
            conf.put("size", file.length());
            initViewConfig.add(conf);
        }
        m.put("fileList", fileList);
        
        m.put("initialPreview", initView);
        m.put("initialPreviewConfig", initViewConfig);
        return m;
    }
    
    @RequestMapping("/getSrcByPath.do")
    public void getSrcByPath(HttpServletRequest req, HttpServletResponse resp, String fileName)
    {
        
        FileUtils.download(resp, fileName);
        
    }
    
    @RequestMapping(value = "/startOrder.do")
    public String startOrder(String id, OrderSearchRequest searcher, RedirectAttributes model, HttpSession session)
    {
        String result = testingProcessService.startProcess(id);
        model.addAttribute("result", result);
        return "redirect:/order/startErrPaging.do";
    }
    
    @ResponseBody
    @RequestMapping("/validate.do")
    public boolean validate(OrderSearchRequest request)
    {
        return orderService.validate(request);
    }
    
    @ResponseBody
    @RequestMapping("/validateSampleCode")
    public boolean validateSampleCode(OrderSearchRequest request)
    {
        return orderService.validateSampleCode(request);
    }
    
    @ResponseBody
    @RequestMapping("/validateSeCode")
    public boolean validateSeCode(OrderSearchRequest request)
    {
        return orderService.validateSeCode(request);
    }
    
    @ResponseBody
    @RequestMapping("/deletePic.do")
    public boolean deletePic()
    {
        return true;
    }
    
    @RequestMapping("/getOrderByContract")
    public String getOrderByContract(OrderContrctSearcher searcher, ModelMap model)
    {
        List<Order> orders = orderService.getOrderByContract(searcher);
        if (Collections3.isNotEmpty(orders))
        {
            for (Order order : orders)
            {
                Contract c = order.getContract();
                if (c != null)
                {
                    if (StringUtils.isNotEmpty(c.getSignUser()))
                    {
                        BusinessInfo b = appService.getBusinessInfo(c.getSignUser());
                        if (null != b)
                        {
                            c.setSignUsername(b.getRealName());
                        }
                    }
                }
                if (StringUtils.isNotEmpty(order.getOwnerId()))
                {
                    if (null != customerService.get(order.getOwnerId()))
                    {
                        order.setOwner(customerService.get(order.getOwnerId()));
                    }
                }
            }
        }
        List<TestingType> testingTypes = testingTypeService.testingTypeList();
        model.addAttribute("testingTypes", testingTypes);
        model.addAttribute("searcher", searcher);
        model.addAttribute("orders", orders);
        return "contract/contract_order";
    }
    
    @RequestMapping(value = "/areaTree.do")
    @ResponseBody
    public Map<String, String> area(Integer parentId)
    {
        if (parentId == null)
        {
            parentId = 0;
        }
        
        List<DataArea> areas;
        DataArea parent = areaService.get(parentId);
        if (parent != null)
        {
            areas = areaService.findByParentId(parentId);
        }
        else
        {
            areas = areaService.findRoots();
        }
        Map<String, String> options = new HashMap<String, String>();
        for (DataArea area : areas)
        {
            options.put(area.getId(), area.getName());
        }
        return options;
    }
    
    @RequestMapping(value = "/viewOrderByStatus.do")
    public String viewOrderByStatus(Order searcher, Integer viewStatus, String redirectStatus, ModelMap model)
    {
        Order order = orderService.getById(searcher.getId().toString());
        if (null == order)
        {
            throw new RuntimeException("没有匹配到该订单！！！");
        }
        /*
         * Product product = null; Map<String, Product> tempalteMap =
         * Maps.newHashMap(); List<OrderSampleGroup> ordersamplegroups =
         * order.getOrderSampleGroup(); List<OrderResearchProduct> orps =
         * Lists.newArrayList(); if (Collections3.isNotEmpty(ordersamplegroups))
         * { order.getOrderProductList().clear(); for (OrderSampleGroup
         * ordersamplegroup : ordersamplegroups) { List<OrderResearchSample>
         * orderresearchsamples = ordersamplegroup.getOrderResearchSample(); if
         * (Collections3.isNotEmpty(orderresearchsamples)) { for
         * (OrderResearchSample orderresearchsample : orderresearchsamples) {
         * List<OrderResearchProduct> orderresearchproducts =
         * orderresearchsample.getOrderResearchProduct(); if
         * (Collections3.isNotEmpty(orderresearchproducts)) { for
         * (OrderResearchProduct orderresearchproduct : orderresearchproducts) {
         * product = orderresearchproduct.getProduct();
         * tempalteMap.put(product.getId(), product); product =
         * orderresearchproduct.getProduct(); Map<String, List<TemporarySample>>
         * map = getSchuduleData(order, product);
         * orderresearchproduct.setTestingsamples(map.get("testingsamples"));
         * orderresearchproduct
         * .setVerificationSamples(map.get("verificationSamples")); } } } } } if
         * (!tempalteMap.isEmpty()) { Set<Entry<String, Product>> set =
         * tempalteMap.entrySet(); for (Entry<String, Product> entry : set) {
         * OrderResearchProduct orp = new OrderResearchProduct(); Map<String,
         * List<TemporarySample>> map = getSchuduleData(order,
         * entry.getValue()); orp.setTestingsamples(map.get("testingsamples"));
         * orp.setVerificationSamples(map.get("verificationSamples"));
         * orp.setProduct(entry.getValue()); orps.add(orp); } }
         * order.setOrderResearchProduct(orps); }
         * 
         * if (Collections3.isNotEmpty(order.getOrderProductList())) {
         * order.getOrderSampleGroup().clear(); for (OrderProduct orderproduct :
         * order.getOrderProductList()) { product = orderproduct.getProduct();
         * Map<String, List<TemporarySample>> map = getSchuduleData(order,
         * product); orderproduct.setTestingsamples(map.get("testingsamples"));
         * orderproduct.setVerificationSamples(map.get("verificationSamples"));
         * } }
         */
        /*
         * OrderStatusScheduleRequest request = new
         * OrderStatusScheduleRequest(); request.setOrderId(searcher.getId());
         * request.setOrderType(order.getOrderType()); Map<String,
         * List<OrderStatusScheduleModel>> productScheduleMap =
         * orderTestingMonitorService.getScheduleListByOrderId(request);
         * Set<String> key = productScheduleMap.keySet(); Set<String> set = new
         * HashSet<>(); List<OrderProductForStatusScheduleModel> records =
         * Lists.newArrayList(); for (String s : key) { if
         * (StringUtils.isNotEmpty(s)) { set.add(s.substring(0,
         * s.lastIndexOf("_"))); } } List<String> pnames =
         * Lists.newArrayList(set); for (String s : pnames) {
         * OrderProductForStatusScheduleModel scheduleModel = new
         * OrderProductForStatusScheduleModel();
         * scheduleModel.setProductName(s);
         * scheduleModel.setTestingsamples(productScheduleMap.get(s +
         * "_testing"));
         * scheduleModel.setVerificationSamples(productScheduleMap.get(s +
         * "_verify")); records.add(scheduleModel); }
         * order.setProductScheduleModel(records);
         */
        // 打包运输
        // List<OrderSampleTransportModel> sampleTransportList =
        // orderService.getAppSampleTransportRelationByOrderCode(order.getCode());
        
        // 样本接收
        // List<OrderSampleReceiveingModel> sampleReceiveingList =
        // orderService.getSampleReceiveingModelByOrderId(order.getId());
        // 财务下放
        OrderPaymentModel payment = orderService.getOrderPaymentModelByOrderId(order.getId());
        payment.setPaymentTime(order.getPayTime());
        if (null != order.getContract())
        {
            ContractContent content = contractService.getContractContentByContractId(order.getContract().getId());
            if (null != content && !"4".equals(content.getSettlementMode()))// 集中开票
                                                                            // (非一单一结)
            {
                List<ContractReconciliationRecord> recordList = orderService.getContractRecRecordByOrderId(searcher.getId());
                if (Collections3.isNotEmpty(recordList)) // 不定期对账
                {
                    payment.setPaymentTime(recordList.get(0).getCreateTime());
                    payment.setCheckTime(recordList.get(0).getCreateTime());
                }
                else
                {
                    List<ContractPaymentRecord> payList = orderService.getSettlePaymentByOrderId(searcher.getId());
                    if (Collections3.isNotEmpty(payList)) // 出账单对账
                    {
                        payment.setPaymentTime(Collections3.getFirst(payList).getPaymentTime());
                        payment.setCheckTime(Collections3.getFirst(payList).getCheckTime());
                    }
                }
            }
            
        }
        // 报告信息
        List<ReportAndVerifyModel> reportList = reportService.getReportAndVerifyModelByOrderId(order.getId());
        // 报告邮寄信息
        List<ReportEmailForOrderStatusModel> reportEmailList = reportEmailService.getListForStatusByOrderId(order.getId());
        order.setViewStatus(viewStatus);
        model.addAttribute("redirectStatus", redirectStatus);
        model.addAttribute("record", order);
        // model.addAttribute("sampleTransport", sampleTransportList);
        // model.addAttribute("sampleReceiveing", sampleReceiveingList);
        model.addAttribute("payment", payment);
        model.addAttribute("report", reportList);
        model.addAttribute("reportEmail", reportEmailList);
        return "order/checkorder_show_status";
    }
    
    // 打包运输
    @RequestMapping(value = "/getSampleTransportList.do", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderSampleTransportModel> getSampleTransportList(String orderCode, HttpSession session)
    {
        List<OrderSampleTransportModel> sampleTransportList = orderService.getAppSampleTransportRelationByOrderCode(orderCode);
        return sampleTransportList;
    }
    
    // 样本接收
    @RequestMapping(value = "/getSampleReceiveingList.do", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderSampleReceiveingModel> getSampleReceiveingList(String orderId, HttpSession session)
    {
        List<OrderSampleReceiveingModel> sampleReceiveingList = orderService.getSampleReceiveingModelByOrderId(orderId);
        return sampleReceiveingList;
    }
    
    // 订单流程
    @RequestMapping(value = "/getOrderScheduleList.do", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderProductForStatusScheduleModel> getSampleReceiveingList(OrderStatusScheduleRequest request, HttpSession session)
    {
        return searcherSchedule(request);
    }
    
    @RequestMapping(value = "/getOrderProductScheduleList.do", method = RequestMethod.GET)
    public String getOrderProductScheduleList(OrderStatusScheduleRequest searcher, ModelMap model, HttpSession session)
    {
        
        List<OrderProductForStatusScheduleModel> data = searcherSchedule(searcher);
        model.addAttribute("data", data);
        model.addAttribute("searcher", searcher);
        return "order/app_product_schedule_forcancel";
    }
    
    private List<OrderProductForStatusScheduleModel> searcherSchedule(OrderStatusScheduleRequest request)
    {
        Map<String, List<OrderStatusScheduleModel>> productScheduleMap = orderTestingMonitorService.getScheduleListByOrderId(request);
        Set<String> key = productScheduleMap.keySet();
        Set<String> set = new HashSet<>();
        List<OrderProductForStatusScheduleModel> records = Lists.newArrayList();
        for (String s : key)
        {
            if (StringUtils.isNotEmpty(s))
            {
                set.add(s.substring(0, s.lastIndexOf("_")));
            }
        }
        List<String> pnames = Lists.newArrayList(set);
        for (String s : pnames)
        {
            OrderProductForStatusScheduleModel scheduleModel = new OrderProductForStatusScheduleModel();
            scheduleModel.setProductName(s);
            scheduleModel.setTestingsamples(productScheduleMap.get(s + "_testing"));
            scheduleModel.setVerificationSamples(productScheduleMap.get(s + "_verify"));
            records.add(scheduleModel);
        }
        return records;
    }
    
    @RequestMapping(value = "/report_goBack.do", method = RequestMethod.GET)
    public String goBack(Integer status, String redirectStatus, ModelMap model, HttpSession session)
    {
        String redirectUrl = "";
        if (StringUtils.isNotEmpty(status))
        {
            if (status == 1)
            {
                redirectUrl = "redirect:/bpm/report/firstReview/list.do";
            }
            if (status == 2)
            {
                redirectUrl = "redirect:/bpm/report/secondReview/list.do";
            }
            if (status == 3)
            {
                redirectUrl = "redirect:/bpm/report/completedReport/list.do";
            }
            if (status == 4)
            {
                redirectUrl = "redirect:/bpm/report/waitAssign/tasks.do";
            }
            if (status == 5)
            {
                redirectUrl = "redirect:/bpm/report/assigned/list.do";
            }
            if (status == 6)
            {
                redirectUrl = "redirect:/concludingReport/assignedList.do?statu=0";
            }
            if (status == 7)
            {
                redirectUrl = "redirect:/concludingReport/assignedList.do?statu=1";
            }
            if (status == 8 || StringUtils.isNotEmpty(redirectStatus))
            {
                return "redirect:/bpm/report/handle.do?id=" + redirectStatus + "&redirectStatus=" + status;
            }
            if (status == 9 || StringUtils.isNotEmpty(redirectStatus))
            {
                return "redirect:/bpm/report/handle.do?id=" + redirectStatus + "&redirectStatus=" + status;
            }
        }
        else
        {
            redirectUrl = "redirect:/order/orderQueryByStatus.do";
        }
        return redirectList(model, session, redirectUrl);
    }
    
    @RequestMapping(value = "/schuduleNodeShow.do")
    public String viewProcess(Temproary data, ModelMap model)
    {
        
        try
        {
            List<TemproaryTestingTask> sourceNodes = Lists.newArrayList();// 所有节点
            List<TemproaryTestingTask> targetNodes = Lists.newArrayList();// 已执行节点
            List<TemproaryTestingTask> willDoNodes = Lists.newArrayList();//将要执行的节点
            List<TemproaryTestingTask> abnormalNodes = Lists.newArrayList();//异常节点
            TestingSchedule schedule = null;
            if (StringUtils.isNotEmpty(data.getScheduleId()))
            {
                schedule = orderService.getScheduleById(data.getScheduleId());//获取检测流程的相关信息
                String nodeStr = getSchduleNodes(schedule.getScheduleNodes());//获取检测流程各任务节点sematic,并把生信分析替换成生信注释
                List<TestingScheduleHistory> tshs = orderService.getTSHByScheduleId(data.getScheduleId());//该检测流程走过的历史节点
                
                String sampleCode = "";
                if (null != schedule)
                {
                    List<TemproaryTestingTask> nodes = taskService.getTaskNameBySemantic(StringUtils.isNotEmpty(nodeStr) ? nodeStr : null);///通过节点的semantic获取节点的名称
                    sourceNodes.addAll(nodes);
                    sampleCode = orderService.getTestingSample(schedule.getSampleId()).getSampleCode();//通过LIMS_TESTING_SCHEDULE的sample_ID查询 LIMS_TESTING_SAMPLE，获取样本编号信息
                    
                }
                
                Set<Date> startTime = new HashSet<Date>();
                Integer taskstate = null;// 4-暂停  5-取消
                for (TestingScheduleHistory testingScheduleHistory : tshs)
                {
                    
                    String testingTaskId = testingScheduleHistory.getTaskId();
                    String scheduleId = testingScheduleHistory.getScheduleId();
                    if (StringUtils.isNotEmpty(scheduleId))
                    {
                        TestingSchedule testingschedule = orderService.getScheduleById(scheduleId);
                        if (null != testingschedule.getProccessState() && 1 == testingschedule.getProccessState())//processState流程状态  0正常（流程的正常结束和异常结束也属于正常） 1暂停 2取消
                        {
                            // 暂停
                            taskstate = 4;
                        }
                        else if (null != testingschedule.getProccessState() && 2 == testingschedule.getProccessState())
                        {
                            // 取消
                            taskstate = 5;
                        }
                    }
                    if (StringUtils.isNotEmpty(testingTaskId))
                    {
                        TestingTaskRequest task;
                        if (StringUtils.isNotEmpty(testingScheduleHistory.getTaskRefer()))
                        {
                            
                            task = testingTaskSheetServiceFactory.getSerivceByTaskRefer(testingScheduleHistory.getTaskRefer()).getTTRById(testingTaskId);
                        }
                        else
                        {
                            task = testingTaskSheetServiceFactory.getSerivceByTaskRefer(null).getTTRById(testingTaskId);
                            
                            if (task.getName().equals("生信分析"))//以前走生信分析的订单，把生信注释替换成生信分析（用于展示）
                            {
                                List<TemproaryTestingTask> nodes = taskService.getTaskNameBySemantic(StringUtils.isNotEmpty(schedule.getScheduleNodes()) ? schedule.getScheduleNodes() : null);
                                sourceNodes = Lists.newArrayList();
                                sourceNodes.addAll(nodes);
                            }
                        }
                        if (null != task)
                        {
                            task.setSampleCode(sampleCode);
                            TestingSheet testingsheet;
                            TestingTaskResultRequest request = orderService.getDataById(task.getId());//通过task的id获取检测任务的结果（结果：0-成功 1-异常上报 2-异常重做）
                            if (null == request)
                            {
                                request = new TestingTaskResultRequest();
                                request.setTaskId(task.getId());
                            }
                            if (StringUtils.isNotEmpty(testingScheduleHistory.getTaskRefer()))
                            {
                                testingsheet =
                                    testingTaskSheetServiceFactory.getSerivceByTaskRefer(testingScheduleHistory.getTaskRefer())
                                        .getTestingSheetByTaskId(task.getId());
                            }
                            else
                            {
                                
                                testingsheet = testingTaskSheetServiceFactory.getSerivceByTaskRefer(null).getTestingSheetByTaskId(task.getId());
                                
                            }
                            
                            if ("相对定量".equals(task.getName()))
                            {
                                if (startTime.contains(task.getStartTime()))
                                {
                                    continue;
                                }
                                startTime.add(task.getStartTime());
                            }
                            if ("混样".equals(task.getName()))
                            {
                                if (startTime.contains(task.getStartTime()))
                                {
                                    continue;
                                }
                                startTime.add(task.getStartTime());
                            }
                            if (StringUtils.isEmpty(testingScheduleHistory.getTaskRefer()))
                            {
                                if (task.getStatus() == 1)//任务状态：1-待分配 2-待实验 3-已结束
                                {
                                    changeTaskStatus(taskstate, task);
                                    willDoNodes.add(getTask(request, task, testingsheet, testingScheduleHistory));
                                    continue;
                                }
                                if (null != task.getEndType())//endType任务结束类型：1-正常结束 2-异常异常
                                {
                                    if (task.getEndType() == 2 && 3 == tshs.size())
                                    {
                                        changeTaskStatus(taskstate, task);
                                        abnormalNodes.add(getTask(request, task, testingsheet, testingScheduleHistory));
                                        continue;
                                    }
                                }
                            }
                            else
                            {
                                if (checkStateWillorAbnorml(willDoNodes, abnormalNodes, testingScheduleHistory, task, testingsheet, request))
                                    continue;
                            }
                            
                            targetNodes.add(getTask(request, task, testingsheet, testingScheduleHistory));
                        }
                    }
                }
            }
            
            targetNodes.addAll(0, abnormalNodes);
            targetNodes.addAll(0, willDoNodes);
            
            model.addAttribute("targetNodes", targetNodes);
            model.addAttribute("targetNodesStr", JSONObject.toJSONString(targetNodes));
            model.addAttribute("sourceNodes", sourceNodes);
            model.addAttribute("sourceNodesStr", JSONObject.toJSONString(sourceNodes));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return "order/schuduleNode_show";
    }
    
    private boolean checkStateWillorAbnorml(List<TemproaryTestingTask> willDoNodes, List<TemproaryTestingTask> abnormalNodes, TestingScheduleHistory testingScheduleHistory, TestingTaskRequest task, TestingSheet testingsheet, TestingTaskResultRequest request)
    {
        if ("新技术分析".equals(task.getName()))
        {
            if (StringUtils.isNotEmpty(task.getStatus()) && task.getStatus() == 1)//待领取
            {
                willDoNodes.add(getTask(request, task, testingsheet, testingScheduleHistory));
                return true;
            }
        }
        else if ("生信注释".equals(task.getName()))
        {
            //（1）注释状态 0-进行中
            //（2）注释完成，但是质控状态不合格，还没有进行失败处理 或者 失败处理选择重新注释 或者 异常上报之后选择重新注释也要显示进行中
            if ((StringUtils.isNotEmpty(task.getAnnotationState()) && task.getAnnotationState() == 0)
                || (task.getAnnotationState() == 5 && StringUtils.isNotEmpty(task.getStatisticsState()) && task.getStatisticsState() == 1 && (null == task.getErrorState() || 3 == task.getErrorState())))
            {
                willDoNodes.add(getTask(request, task, testingsheet, testingScheduleHistory));
                return true;
            }
            
        }
        else if ("数据拆分".equals(task.getName()))
        {
            if (task.getStatus() == 0)//待拆分
            {
                willDoNodes.add(getTask(request, task, testingsheet, testingScheduleHistory));
                return true;
            }
            
        }
        return false;
    }
    
    public String getSchduleNodes(String str)//得到检测流程各节点，把生信分析替换成生信注释
    {
        String nodeStr = "";
        if (str != null)
        {
            for (String s : str.split("\\/"))
            {
                if (("BIOLOGY-ANALY").equals(s))//如果节点是生信分析，直接替换成生信注释
                {
                    s = "BIOLOGY-ANNOTATION";
                }
                nodeStr = nodeStr + s + "/";
                
            }
            nodeStr = nodeStr.substring(0, nodeStr.length() - 1);
        }
        
        return nodeStr;
    }
    
    public List<TemproaryTestingTask> splitString(String str)
    {
        List<TemproaryTestingTask> list = Lists.newArrayList();
        if (str != null)
        {
            for (String s : str.split("\\/"))
            {
                TemproaryTestingTask tn = new TemproaryTestingTask();
                Task t = taskService.getTaskBySemantic(s);//获取节点名称
                if (null != t)
                {
                    tn.setTaskName(t.getName());
                }
                list.add(tn);
            }
        }
        return list;
    }
    
    @RequestMapping(value = "/orderSampleList.do")
    public String orderSampleManager(OrderSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<OrderSampleDetails> pagination = orderService.orderSampleList(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "order/order_sample_list";
    }
    
    /**
     * 异常样本列表
     */
    @RequestMapping(value = "/errorSampleList.do")
    public String errorSampleList(OrderSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        searcher.setTransportStatus(Constant.ORDER_SAMPLE_ERROR); // 异常样本
        Pagination<OrderSampleDetails> pagination = orderService.orderSampleList(searcher, pageNo, DEFAULTPAGESIZE);
        
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (OrderSampleDetails osd : pagination.getRecords())
            {
                Order order = orderService.getById(osd.getOrderId());
                if (StringUtils.isNotEmpty(osd.getOrderId()))
                {
                    String projectManager = orderService.getById(osd.getOrderId()).getProjectManager();
                    if (StringUtils.isNotEmpty(projectManager))
                    {
                        UserDetailsModel prjManagerUser = userService.getUserByID(projectManager);
                        osd.setPrjManagerName(prjManagerUser.getArchive().getName());
                    }
                }
            }
        }
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "order/order_errorsample_list";
    }
    
    @Autowired
    private ISampleReceivingService sampleReceservice;
    
    /**
     * 样本管理 --- 查看样本详情
     * 
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/orderSampleDetail.do")
    public String orderSampleDetail(String id, ModelMap model, HttpSession session)
    {
        OrderSampleDetails entity = orderService.getSampleDetailById(id);
        if (StringUtils.isNotEmpty(entity))
        {
            Order order = orderService.getById(entity.getOrderId());
            model.addAttribute("order", order);
            
            switch (entity.getSampleBtype())
            {
                case 1:
                    OrderPrimarySample s = orderService.getOrderPrimarySampleById(entity.getSampleId());
                    model.addAttribute("orderPrimarySample", s);
                    break;
                case 2:
                    OrderSubsidiarySample s1 = orderService.getOrderSubsidiarySampleById(entity.getSampleId());
                    model.addAttribute("orderSubsidiarySample", s1);
                    break;
                case 3:
                    OrderResearchSample s2 = orderService.getOrderResearchSampleById(entity.getSampleId());
                    model.addAttribute("orderResearchSample", s2);
                    break;
                
                default:
                    break;
            }
            
            OrderSearchRequest searcher = new OrderSearchRequest();
            searcher.setSampleId(entity.getSampleId());
            List<AppSampleTransport> oo = orderService.getAppSampleTransport(searcher);
            if (Collections3.isNotEmpty(oo))
            {
                AppSampleTransport port = oo.get(0);
                if (StringUtils.isNotEmpty(port.getExpressPicture()))
                {// 获取路径
                    StringBuffer fujian = new StringBuffer();
                    String[] fileNames = port.getExpressPicture().split("\\,");
                    for (String fileName : fileNames)
                    {
                        fujian.append(FileUtils.getDownloadUrl(fileName) + ",");
                        
                    }
                    port.setExpressPictureShow(fujian.toString());
                }
                model.addAttribute("appSampleTransport", port);
                model.addAttribute("appSampleTransportJson", JSONObject.toJSONString(port));
            }
            
            List<AppSampleBackApply> apply = orderService.getBackSampleTransport(searcher);
            if (Collections3.isNotEmpty(apply))
            {
                AppSampleBackApply port = apply.get(0);
                model.addAttribute("appSampleBackApply", port);
            }
            
            SampleReceiveRecordSearcher search = new SampleReceiveRecordSearcher();
            search.setSampleCode(entity.getSampleCode());
            List<SampleReceivingDetail> result = sampleReceservice.getSampleDetailByCode(search);
            
            if (Collections3.isNotEmpty(result))
            {
                SampleReceivingDetail detail = result.get(0);
                SampleReceiving receiving = detail.getSampleReceiving();
                model.addAttribute("receicving", receiving);
                model.addAttribute("detail", detail);
                
            }
            
        }
        if(StringUtils.isNotEmpty(entity.getSampleTypeId()))
        {
            MetadataSample metadataSample = sampleService.get(entity.getSampleTypeId());
            if (StringUtils.isNotEmpty(metadataSample))
            {
                entity.setUnit(metadataSample.getReceivingUnit());
            }
        }

        model.addAttribute("record", entity);
        
        return "order/order_sample_detail";
    }
    
    /**
     * 处理异常样本 ---页面
     * 
     * @return
     */
    @RequestMapping(value = "handleErrorSample.do")
    public String handleErrorSample(Order searcher, ModelMap model)
    {
        OrderSampleDetails entity = orderService.getSampleDetailById(searcher.getId());
        Set<String> productIds = new HashSet<String>();
        if (StringUtils.isNotEmpty(entity))
        {
            Order order = orderService.getById(entity.getOrderId());
            if (StringUtils.isNotEmpty(order))
            {
                Customer customer = customerService.get(order.getOwnerId());
                order.setOwner(customer);
                model.addAttribute("order", order);
                
                MetadataSample m = sampleService.get(entity.getSampleTypeId());
                entity.setUnit(m != null ? m.getReceivingUnit() : "");
                model.addAttribute("sampleInfo", entity);
                
                List<OrderProduct> lists = order.getOrderProductList();
                
                if (Collections3.isNotEmpty(lists))
                {
                    productIds = lists.stream().map(o -> o.getProduct().getId()).collect(Collectors.toSet());
                }
                
                List<OrderSampleGroup> groups = order.getOrderSampleGroup();
                if (Collections3.isNotEmpty(groups))
                {
                    for (OrderSampleGroup g : groups)
                    {
                        List<OrderResearchSample> sa = g.getOrderResearchSample();
                        for (OrderResearchSample s : sa)
                        {
                            
                            List<OrderResearchProduct> products = s.getOrderResearchProduct();
                            if (Collections3.isNotEmpty(products))
                            {
                                productIds = products.stream().map(o -> o.getProduct().getId()).collect(Collectors.toSet());
                            }
                        }
                    }
                }
                
                ProductListRequest request = new ProductListRequest();
                request.setProductIds(productIds);
                List<MetadataSample> sampleList = productService.getSampleByProductIds(request);
                model.addAttribute("sample", sampleList);
                
            }
            
        }
        model.addAttribute("searcher", searcher);
        
        return "order/handle_error_sample_form";
    }
    
    /**
     * 处理异常样本 ---页面
     * 
     * @return
     */
    @RequestMapping(value = "viewhandleErrorSample.do")
    public String viewhandleErrorSample(Order searcher, ModelMap model)
    {
        OrderSampleDetails entity = orderService.getSampleDetailById(searcher.getId());
        if (StringUtils.isNotEmpty(entity))
        {
            Order order = orderService.getById(entity.getOrderId());
            Customer customer = customerService.get(order.getOwnerId());
            order.setOwner(customer);
            model.addAttribute("order", order);
            
            MetadataSample m = sampleService.get(entity.getSampleTypeId());
            entity.setUnit(m != null ? m.getReceivingUnit() : "");
            /**
             * 获取新样本的物流信息 --sj
             */
            if (StringUtils.isNotEmpty(entity.getSampleErrorNewNo()))
            {
                
                // 根据样本编号 获取id
                OrderSearchRequest ordersearcher = new OrderSearchRequest();
                String newsampleCode = entity.getSampleErrorNewNo();
                ordersearcher.setSampleCode(newsampleCode);
                ordersearcher.setSampleId(orderService.getSampleIdByCode(ordersearcher));
                List<AppSampleTransport> oo = orderService.getAppSampleTransport(ordersearcher);
                if (Collections3.isNotEmpty(oo))
                {
                    AppSampleTransport port = oo.get(0);
                    if (StringUtils.isNotEmpty(port.getExpressPicture()))
                    {// 获取路径
                        StringBuffer fujian = new StringBuffer();
                        String[] fileNames = port.getExpressPicture().split("\\,");
                        for (String fileName : fileNames)
                        {
                            fujian.append(FileUtils.getDownloadUrl(fileName) + ",");
                            
                        }
                        port.setExpressPictureShow(fujian.toString());
                    }
                    model.addAttribute("appSampleTransport", port);
                    model.addAttribute("appSampleTransportJson", JSONObject.toJSONString(port));
                }
                OrderSampleDetails newSampleDetail = orderService.getOrderSampleDetailBySampleId(ordersearcher.getSampleId());
                if (StringUtils.isNotEmpty(newSampleDetail))
                {
                    entity.setSampleErrorNewSize(newSampleDetail.getSampleSize());
                    entity.setSampleErrorNewsamplingDate(newSampleDetail.getSamplingDate());
                    entity.setSampleErrorNewTypeName(newSampleDetail.getSampleTypeName());
                    MetadataSample ms = sampleService.get(newSampleDetail.getSampleTypeId());
                    entity.setSampleErrorUnit(ms != null ? ms.getReceivingUnit() : "");
                }
                
            }
            
            model.addAttribute("sampleInfo", entity);
        }
        model.addAttribute("searcher", searcher);
        List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
        model.addAttribute("sample", sampleList);
        // ww 重新送样不送样再重新送样记录
        TestingReSampleNoSampleModel reSampleNoSample = orderService.getReSampleNoSampleRecords(entity.getSampleId());
        model.addAttribute("reSampleNoSample", reSampleNoSample);
        return "order/view_handle_error_sample_form";
    }
    
    /**
     * 异常样本 重新加样本
     * 
     * @param request
     * @param model
     * @param session
     * @return
     */
    
    @RequestMapping(value = "addErrorSample.do")
    public String addErrorSample(AddErrorSampleFormRequest request, ModelMap model, HttpSession session)
    {
        User user = userService.getUserByToken();
        if (StringUtils.isNotEmpty(user))
        {
            request.setErrorOperatorId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setErrorOperatorName(userachive.getArchive().getName());
        }
        orderService.addErrorSample(request);
        return redirectList(model, session, "redirect:/order/errorSampleList.do");
    }
    
    /**
     * 合同订单管理
     * 
     * @param searcher
     * @param model
     * @return
     */
    @RequestMapping(value = "/orderContractManager.do")
    public String orderContractManager(OrderSearchRequest searcher, ModelMap model)
    {
        model.addAttribute("searcher", searcher);
        model.addAttribute("flag", "update");
        return "order/order_contract_tab";
    }
    
    /**
     * 待确认列表
     * 
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/unConfirmOrderList.do")
    public String unConfirmOrderList(OrderSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = dataAuthorityInitial();
        if (dataAuthorityMap.containsKey("CONTRACT_ORDER"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("CONTRACT_ORDER"));
        }
        
        Pagination<CommonOrderResponse> pagination = orderService.unConfirmOrderList(searcher, pageNo, DEFAULTPAGESIZE);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (CommonOrderResponse cor : pagination.getRecords())
            {
                Order order = orderService.getById(cor.getId());
                if (StringUtils.isNotEmpty(order.getProjectManager()))
                {
                    UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                    cor.setPrjManagerName(prjManagerUser.getArchive().getName());
                }
            }
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        
        return "order/order_contract_unconfirm";
    }
    
    @Autowired
    private IContractService contractService;
    
    @RequestMapping(value = "/confirmedOrderList.do")
    public String confirmedOrderList(OrderSearchRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = dataAuthorityInitial();
        if (dataAuthorityMap.containsKey("CONTRACT_ORDER"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("CONTRACT_ORDER"));
        }
        Pagination<CommonOrderResponse> pagination = orderService.confirmedOrderList(searcher, pageNo, DEFAULTPAGESIZE);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (CommonOrderResponse cor : pagination.getRecords())
            {
                Order order = orderService.getById(cor.getId());
                if (StringUtils.isNotEmpty(order.getProjectManager()))
                {
                    UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                    cor.setPrjManagerName(prjManagerUser.getArchive().getName());
                }
            }
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        
        return "order/order_contract_confirmed";
    }
    
    /**
     * 合同订单 确认
     * 
     * @param request
     * @param model
     * @param session
     * @return
     */
    
    @RequestMapping(value = "/confirmOrderContract.do")
    public String confirmOrderContract(OrderFormRequest request, ModelMap model, HttpSession session)
    {
        orderService.confirmOrderContract(request);
        return "redirect:/order/unConfirmOrderList.do";
    }
    
    @RequestMapping("/uploadSearcherSample.do")
    @ResponseBody
    public List<OrderResearchSampleForUpload> uploadSearcherSample(@RequestParam MultipartFile uploadData, String hetongId)
    {
        List<OrderResearchSampleForUpload> group = orderService.uploadSearcherSample(uploadData, hetongId);
        return group;
        
    }
    
    public TestingScheduleHistory getScheduleHistoryBySchedule(TestingSchedule ts)
    {
        List<TestingScheduleHistory> tshs = orderService.getTSHByScheduleId(ts.getId());
        return Collections3.isNotEmpty(tshs) ? tshs.get(0) : null;
    }
    
    @RequestMapping("/showTransport.do")
    @ResponseBody
    public Map<String, Object> showTransport(String param)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            String no = "DA0EC9C2E607716086C5A9B4104DFF3E";
            String key = "JXhhvrMG8857";
            String sign = MD5Util.getMD5(param + key + no);
            String path = "http://poll.kuaidi100.com/poll/query.do?customer=" + no + "&param=" + param + "&sign=" + sign;
            URL url = new URL(path);
            URLConnection con = url.openConnection();
            con.setAllowUserInteraction(false);
            InputStream urlStream = url.openStream();
            String type = con.guessContentTypeFromStream(urlStream);
            type = "text/html";
            String charSet = null;
            if (type == null)
            {
                type = con.getContentType();
            }
            if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/html") < 0)
            {
                return null;
            }
            if (type.indexOf("charset=") > 0)
            {
                charSet = type.substring(type.indexOf("charset=") + 8);
            }
            charSet = "UTF-8";
            byte b[] = new byte[10000];
            int numRead = urlStream.read(b);
            String content = new String(b, 0, numRead);
            while (numRead != -1)
            {
                numRead = urlStream.read(b);
                if (numRead != -1)
                {
                    String newContent = new String(b, 0, numRead, charSet);
                    content += newContent;
                }
            }
            System.out.println("content:" + content);
            result.put("msg", content);
            urlStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return result;
        
    }
    
    /**
     * 合同付款 main
     * 
     * @param searcher
     * @param model
     * @return
     */
    @RequestMapping(value = "/contractPayManager.do")
    public String contractPayManager(PaymentSearchRequest searcher, ModelMap model)
    {
        return "order/contract_payment_tab";
    }
    
    @RequestMapping("/addOrderSampleForm.do")
    public String addOrderSample(Order searcher, ModelMap model)
    {
        Order order = orderService.getById(searcher.getId());
        
        order.setOwner(customerService.get(order.getOwnerId()));
        
        model.addAttribute("record", order);
        if (Collections3.isNotEmpty(order.getOrderExamineeList()))
        {
            OrderExaminee o = order.getOrderExamineeList().get(0);
            
            if (StringUtils.isNotEmpty(o.getOrigin()))
            {
                String result = exchangeArea(o.getOrigin());
                DataArea target = areaService.get(Integer.parseInt(result));
                if (StringUtils.isNotEmpty(target))
                {
                    o.setOriginFullName(target.getFullName());
                }
                
            }
            model.addAttribute("OrderExaminee", o);
        }
        else
        {
            model.addAttribute("OrderExaminee", new ArrayList<OrderExaminee>());
        }
        Set<MetadataSample> sampleList = new HashSet<MetadataSample>();
        if (Collections3.isNotEmpty(order.getOrderProductList()))
        {
            for (OrderProduct p : order.getOrderProductList())
            {
                if (StringUtils.isNotEmpty(p.getProduct()))
                {
                    List<ProductSample> sample = p.getProduct().getProductSampleList();
                    for (ProductSample s : sample)
                    {
                        sampleList.add(s.getSample());
                    }
                    
                }
                
            }
        }
        order.setIsAppendSample(orderService.checkOrderTechnicalStatus(order.getId()));
        // List<MetadataSample> sampleList = sampleService.list(new
        // SamplePagingRequest());
        model.addAttribute("orderJson", JSONObject.toJSONString(order));
        model.addAttribute("sampleList", JSONObject.toJSONString(sampleList));
        model.addAttribute("sample", sampleList);
        
        return "order/add_ordersample_form";
    }
    
    // 合同付款-查看
    @RequestMapping(value = "/view.do")
    public String view(String id, ModelMap model)
    {
        Contract contract = null;
        if (id != null)
        {
            contract = contractService.getContractById(id);
            ContractPartyA cpa = contractService.getContractPAByContractId(contract.getId());
            ContractPartyB cpb = contractService.getContractPBByContractId(contract.getId());
            // cpb.setCompanyName(service.getContractOrgById(cpb.getCompanyName()).getCompanyName());
            ContractContent cc = contractService.getContractContentByContractId(contract.getId());
            List<ContractUser> cus = contract.getConUsers();
            for (ContractUser contractUser : cus)
            {
                Customer c = cService.get(contractUser.getUserId());
                contractUser.setCustormer(c);
            }
            List<ContractSample> samples = contract.getConSamples();
            updateData(samples);
            cc.setDeliveryModes(getListBysplit(cc.getDeliveryMode()));
            model.addAttribute("cus", cus);
            model.addAttribute("cpa", cpa);
            model.addAttribute("cpb", cpb);
            model.addAttribute("cc", cc);
            List<ContractChangeSignUser> changeSignUserList = contractService.getChangeSignUserList(contract.getId());
            if (Collections3.isNotEmpty(changeSignUserList))
            {
                for (ContractChangeSignUser ccs : changeSignUserList)
                {
                    BusinessInfo bi = appService.getBusinessInfo(ccs.getBeforeSignUser());
                    if (null != bi)
                    {
                        ccs.setBeforeSignUserName(bi.getRealName());
                    }
                    BusinessInfo bi2 = appService.getBusinessInfo(ccs.getAfterSignUser());
                    if (null != bi2)
                    {
                        ccs.setAfterSignUserName(bi2.getRealName());
                    }
                }
            }
            model.addAttribute("changeSignUserList", changeSignUserList);
        }
        if (StringUtils.isNotEmpty(contract.getSignUser()))
        {
            BusinessInfo b = appService.getBusinessInfo(contract.getSignUser());
            if (null != b)
            {
                contract.setSignUsername(b.getRealName());
            }
        }
        model.addAttribute("contract", contract);
        return "contract/contract_view";
    }
    
    // 清单详情
    @RequestMapping("/viewContractBill.do")
    public String viewContractBill(String contractId, ModelMap model, HttpSession session)
    {
        Contract contract = contractService.getContractById(contractId);
        BusinessInfo b = appService.getBusinessInfo(contract.getSignUser());
        if (null != b)
        {
            contract.setSignUsername(b.getRealName());
        }
        model.addAttribute("data", contract);
        return "contract/contract_bill_view";
    }
    
    /**
     * 合同款项记录
     */
    @RequestMapping(value = "/viewPaymentRecord.do")
    public String getRecordByContractId(ContractSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<ContractPaymentRecord> pagination = contractService.getRecordByContractId(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        Contract contract = contractService.getContractById(searcher.getId());
        ContractContent cc = contractService.getContractContentByContractId(contract.getId());
        
        OrderSearchRequest orderSearcher = new OrderSearchRequest();
        orderSearcher.setContractId(contract.getId());
        orderSearcher.setIfResolveOrderInfo(false);
        List<Order> orderResult = orderService.list(orderSearcher);// 根据订单获取合同价格
        if (StringUtils.isNotEmpty(orderResult))
        {
            BigDecimal amount = new BigDecimal(0);
            BigDecimal incomingAmount = new BigDecimal(0);
            
            if (StringUtils.isNotEmpty(cc.getSettlementMode()) && !cc.getSettlementMode().equals("4")) // 非一单一结
            {
                for (Order o : orderResult)
                {
                    if (!o.getTestingStatus().equals(4))
                    { // 过滤已取消
                        amount = amount.add(new BigDecimal(o.getAlreadyGeneratedPrice()));
                    }
                }
            }
            else
            {
                for (Order o : orderResult)
                {
                    amount = amount.add(new BigDecimal(o.getAlreadyGeneratedPrice()));
                    incomingAmount = incomingAmount.add(new BigDecimal(o.getIncomingAmount()));
                }
                contract.setIncomingAmount(incomingAmount.multiply(new BigDecimal(100)).intValue());
            }
            contract.setOrderAmount(amount);
        }
        contract.setContractContent(cc);
        model.addAttribute("contract", contract);
        
        return "contract/contract_paymentrecord_view";
    }
    
    public List<ContractSample> updateData(List<ContractSample> samples)
    {
        if (Collections3.isNotEmpty(samples))
        {
            for (ContractSample contractSample : samples)
            {
                List<String> sampleNames = Lists.newArrayList();
                if (StringUtils.isNotEmpty(contractSample.getSampleTypeKeys()))
                    for (String str : contractSample.getSampleTypeKeys().split(","))
                    {
                        MetadataSample s = sampleService.get(str);
                        if (null != s)
                        {
                            sampleNames.add(s.getName());
                        }
                    }
                contractSample.setSampleNames(sampleNames);
            }
        }
        return samples;
    }
    
    public List<String> getListBysplit(String deliveryMode)
    {
        List<String> list = Lists.newArrayList();
        if (StringUtils.isNotEmpty(deliveryMode))
        {
            for (String str : deliveryMode.split(","))
            {
                list.add(str);
            }
        }
        return list;
    }
    
    @RequestMapping("/addOrderSample.do")
    @ResponseBody
    public Map<String, Object> addOrderSample(OrderFormRequest request, ModelMap model, HttpSession session)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userService.getUserByToken();
        if (StringUtils.isNotEmpty(user))
        {
            request.setCreatorId(user.getId());
            UserDetailsModel userachive = userService.getUserByID(user.getId());
            request.setCreatorName(userachive.getArchive().getName());
        }
        
        // 组装附属样本
        List<OrderSubsidiarySample> orderSubSample = JSON.parseArray(request.getSubsidiarySample() + "", OrderSubsidiarySample.class);
        if (Collections3.isNotEmpty(orderSubSample))
        {
            Iterator<OrderSubsidiarySample> it = orderSubSample.iterator();
            while (it.hasNext())
            {
                if (StringUtils.isEmpty(it.next().getSampleCode()))
                {
                    it.remove();
                }
                
            }
            
        }
        request.setOrderSubsidiarySample(orderSubSample);
        
        orderService.addOrderSample(request);
        map.put("result", true);
        return map;
    }
    
    @RequestMapping("/modifyShedule.do")
    @ResponseBody
    public Map modifyShedule(DealScheduleModel scheduleModel)
    {
        Map res = new HashMap<>();
        res.put("result", 0);
        res.put("resultInfo", "流程异常，请联系管理员!");
        if ("pause".equals(scheduleModel.getOperate()))
        {
            List<TestingScheduleActive> activeTasks = taskService.getActiveTasks(scheduleModel.getSheduleId());
            if (Collections3.isEmpty(activeTasks))
            {
                res.put("result", 9);
                res.put("resultInfo", "流程重新送样或已上报，无法操作!");
                return res;
            }
        }
        
        taskService.modifyShedule(scheduleModel);
        res.put("result", 1);
        res.put("resultInfo", "操作成功!");
        return res;
    }
    
    @RequestMapping("/stopShedule.do")
    @ResponseBody
    public Map stopShedule(DealScheduleModel scheduleModel)
    {
        Map res = new HashMap<>();
        res.put("result", 0);
        res.put("resultInfo", "流程异常，请联系管理员!");
        
        List<TestingScheduleActive> activeTasks = taskService.getActiveTasks(scheduleModel.getSheduleId());
        if (Collections3.isEmpty(activeTasks))
        {
            res.put("result", 9);
            res.put("resultInfo", "流程重新送样或已上报，无法操作!");
            return res;
        }
        
        taskService.modifyShedule(scheduleModel);
        res.put("result", 1);
        res.put("resultInfo", "操作成功!");
        return res;
    }
    
    /**
     * 确认取消页面
     * @param searcher
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    
    @RequestMapping(value = "/app/cancelSechedulePaging.do")
    public String cancelSechedulePaging(ProductScheduleCancelSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<ProductCancelRecord> pagination = orderService.cancelOrderProductSechedulePaging(searcher, pageNo, DEFAULTPAGESIZE);
        
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        return "order/app_cancelSechedulePaging";
    }
    
    /**
     * 确认取消处理
     * @param request
     * @return
     */
    @RequestMapping("/cancelSheduleByOrderProduct.do")
    @ResponseBody
    public Map<String, Object> cancelSheduleByOrderProduct(OrderProductTestingScheduleRequest request)
    {
        Map<String, Object> res = new HashMap<>();
        taskService.cancelSheduleByOrderProduct(request);
        res.put("success", true);
        return res;
    }
    
    @RequestMapping("/getProductCancelRecordById.do")
    @ResponseBody
    public ProductCancelRecord getProductCancelRecordById(String id)
    {
        return orderService.getProductCancelRecordById(id);
    }
    
    public Map<String, List<TemporarySample>> getSchuduleData(Order order, Product product)
    {
        
        Map<String, List<TemporarySample>> map = new HashMap<String, List<TemporarySample>>();
        // 检测，对照样本集合
        List<TemporarySample> testingsamples = Lists.newArrayList();
        // 验证样本集合chromosomalLocation不为空
        List<TemporarySample> verificationSamples = Lists.newArrayList();
        // 验证样本集合chromosomalLocation为空 2017.11.9 ww
        List<TemporarySample> verificationSamplesforNull = Lists.newArrayList();
        // 验证
        ScheduleQuery verificationsample = new ScheduleQuery(order.getId(), product.getId(), 2);
        List<TestingSchedule> verificationSampleList = orderService.getTestingSchedulesByData(verificationsample);
        for (TestingSchedule schedule : verificationSampleList)
        {
            
            String testingsampleId = schedule == null ? null : schedule.getSampleId();
            String methodId = schedule == null ? null : schedule.getMethodId();
            TestingMethod testingMethod = orderService.getTestingMethodById(methodId);
            TestingSample testingsample = orderService.getTestingSample(testingsampleId);
            ReceivedSample receivedsample = null == testingsample ? null : testingsample.getReceivedSample();
            String codes = getCodes(schedule);
            if (StringUtils.isNotEmpty(codes))
            {
                receivedsample.setSampleCode(codes);
            }
            TestingtechnicalanalyrecordTempory sangerverifyrecord = null;
            if (StringUtils.isNotEmpty(schedule.getVerifyKey()))
            {
                
                if ("Sanger验证".equals(testingMethod.getName()) || "PCR-NGS验证".equals(testingMethod.getName()))
                {
                    sangerverifyrecord = orderService.getSangerVerifyRecordById(schedule.getVerifyKey());
                }
                else if ("MLPA验证".equals(testingMethod.getName()))
                {
                    sangerverifyrecord = orderService.getMLPAVerifyRecordById(schedule.getVerifyKey());
                }
                else if ("QPCR验证".equals(testingMethod.getName()))
                {
                    sangerverifyrecord = orderService.getQPCRVerifyRecordById(schedule.getVerifyKey());
                }
            }
            
            String sequenceCode = "";
            
            if (StringUtils.isNotEmpty(schedule.getVerifyTarget()))
            {
                sequenceCode = orderService.getSequenceCode(schedule.getVerifyTarget());
            }
            String testingMethodName = "";
            testingMethodName = testingMethod.getName();
            String chromosomalLocation = "";
            chromosomalLocation = sangerverifyrecord.getChromosomalLocation();
            String sampleName = "";
            sampleName = receivedsample.getSampleName();
            Integer businessType = receivedsample.getBusinessType();
            if (StringUtils.isNotEmpty(chromosomalLocation))
            {
                verificationSamples.add(new TemporarySample(product, testingMethod, schedule, getScheduleHistoryBySchedule(schedule), receivedsample,
                    sangerverifyrecord, sequenceCode, orderService.getTaskStartDate(schedule.getId()), testingMethodName, chromosomalLocation, sampleName,
                    businessType));
            }
            else
            {
                verificationSamplesforNull.add(new TemporarySample(product, testingMethod, schedule, getScheduleHistoryBySchedule(schedule), receivedsample,
                    sangerverifyrecord, sequenceCode, orderService.getTaskStartDate(schedule.getId()), testingMethodName, chromosomalLocation, sampleName,
                    businessType));
            }
            
        }
        // 检测
        ScheduleQuery testsample = new ScheduleQuery(order.getId(), product.getId(), 1);
        List<TestingSchedule> testingSampleList = orderService.getTestingSchedulesByData(testsample);
        for (TestingSchedule schedule : testingSampleList)
        {
            String sampleId = schedule.getSampleId();
            ReceivedSample receivedsample = orderService.getReceivedSample(sampleId);
            if (null == receivedsample)
            {
                TestingSample testingsample = orderService.getTestingSample(sampleId);
                receivedsample = null == testingsample ? null : testingsample.getReceivedSample();
            }
            String codes = getCodes(schedule);
            if (StringUtils.isNotEmpty(codes))
            {
                receivedsample.setSampleCode(codes);
            }
            String methodId = schedule.getMethodId();
            TestingMethod testingMethod = orderService.getTestingMethodById(methodId);
            
            String sequenceCode = "";
            
            sequenceCode = orderService.getSequenceCode(schedule.getId());
            
            testingsamples.add(new TemporarySample(product, testingMethod, schedule, getScheduleHistoryBySchedule(schedule), receivedsample, sequenceCode,
                orderService.getTaskStartDate(schedule.getId())));
        }
        map.put("testingsamples", testingsamples);
        
        verificationSamples.sort(Comparator.comparing(TemporarySample::getTestingMethodName)
            .thenComparing(TemporarySample::getChromosomalLocation)
            .thenComparing(TemporarySample::getBusinessType)
            .thenComparing(TemporarySample::getSampleName));
        verificationSamplesforNull.sort(Comparator.comparing(TemporarySample::getTestingMethodName)
            .thenComparing(TemporarySample::getBusinessType)
            .thenComparing(TemporarySample::getSampleName));
        verificationSamples.addAll(verificationSamplesforNull);
        map.put("verificationSamples", verificationSamples);
        return map;
    }
    
    public TemproaryTestingTask getTask(TestingTaskResultRequest request, TestingTaskRequest task, TestingSheet testingsheet, TestingScheduleHistory history)
    {
        String remark = "";
        /*
         * if (null != request) { AbnormalSolveRecord abRecord =
         * orderService.getRemarkByAbnormal(request.getTaskId()); if (null !=
         * abRecord) { remark = abRecord.getRemark(); } if
         * (StringUtils.isEmpty(remark) &&
         * StringUtils.isNotEmpty(request.getRemark())) { remark =
         * request.getRemark(); } } if (StringUtils.isNotEmpty(remark)) { if
         * (StringUtils.isNotEmpty(history.getRemark())) { remark = remark +
         * "-暂停/取消备注：" + history.getRemark(); } } else { remark =
         * history.getRemark(); }
         */
        if (null != request)
        {
            TestingTaskResult taskResult = orderService.getTestingTaskResultById(request.getTaskId());
            if (null != taskResult)
            {
                if (StringUtils.isNotEmpty(taskResult.getRemark()))
                {
                    remark = taskResult.getRemark();
                }
                else
                {
                    if (StringUtils.isNotEmpty(taskResult.getDetails()))
                    {
                        if (taskResult.getDetails().contains("{"))
                        {
                            JSONObject temp = JSONObject.parseObject(taskResult.getDetails());
                            Object obj = temp.get("map");
                            if (null == obj)
                            {
                                Object obre = temp.get("unqualifiedRemark");
                                if (null != obre)
                                {
                                    remark = taskResult.getRemark();
                                }
                            }
                            else
                            {
                                Map map = (Map)obj;
                                if (null != map.get("备注"))
                                {
                                    remark = taskResult.getRemark();
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                remark = history.getRemark();
            }
        }
        
        TemproaryTestingTask ttt = new TemproaryTestingTask();
        ttt.setPauseName(history.getPauseName());
        ttt.setPauseTime(history.getPauseTime());
        ttt.setRestartName(history.getRestartName());
        ttt.setRestartTime(history.getRestartTime());
        ttt.setRemark(remark);
        ttt.setTaskId(task.getId());
        ttt.setTaskName(task.getName());
        ttt.setEndTime(task.getEndTime());
        ttt.setStatus(getStatus(task.getStatus(), history.getTaskRefer(), task));
        ttt.setEndStatus(getEndStatus(task.getEndType()));
        if ("BIOLOGY-ANNOTATION".equals(task.getSemantic()))
        {
            if (StringUtils.isNotEmpty(task.getErrorState()) && task.getErrorState() == 2)
            { //异常上报
                ttt.setEndStatus(getEndStatus(2));
            }
            if (5 == task.getAnnotationState() && (StringUtils.isNotEmpty(task.getStatisticsState())) && 1 == task.getStatisticsState()
                && (null == task.getErrorState()))//注释完成，质控状态为1，还未进行失败处理，不显示完成时间
            {
                ttt.setEndTime(null);
            }
            if (task.getStatisticsState() == null)//质控状态为空，说明未完成，不显示完成时间
            {
                ttt.setEndTime(null);
            }
        }
        
        if (StringUtils.isNotEmpty(task.getSemantic())
            && (task.getSemantic().equals("LIBRARY-CAP") || task.getSemantic().equals("LIBRARY-QC") || task.getSemantic().equals("LIBRARY-CRE") || task.getSemantic()
                .equals("DNA-QC")))
        {
            if (StringUtils.isNotEmpty(task.getTestingSampleCode()))
            {
                ttt.setReceivedSampleCode(task.getTestingSampleCode());//文库捕获、文库质检、文库构建跟DAN质检取取实验样本编号
            }
        }
        else
        {
            if (StringUtils.isNotEmpty(task.getReceivedSampleCode()))
            {
                ttt.setReceivedSampleCode(task.getReceivedSampleCode());
            }
            else if (StringUtils.isNotEmpty(task.getInputSample()) && StringUtils.isNotEmpty(task.getInputSample().getReceivedSample())
                && StringUtils.isNotEmpty(task.getInputSample().getReceivedSample().getSampleCode()))
            {
                ttt.setReceivedSampleCode(task.getInputSample().getReceivedSample().getSampleCode());
            }
            else
            {
                TestingTaskRequest tt = orderService.getDNATaskByHistory(history); //通过DNA质检来获取原始样本编号
                if (StringUtils.isNotEmpty(tt) && StringUtils.isNotEmpty(tt.getId()))
                {
                    TestingTaskRequest DNATask = testingTaskSheetServiceFactory.getSerivceByTaskRefer(null).getTTRById(tt.getId());
                    if (StringUtils.isNotEmpty(DNATask.getReceivedSampleCode()))
                    {
                        ttt.setReceivedSampleCode(DNATask.getReceivedSampleCode());
                    }
                    else if (StringUtils.isNotEmpty(DNATask.getInputSample()) && StringUtils.isNotEmpty(DNATask.getInputSample().getReceivedSample())
                        && StringUtils.isNotEmpty(DNATask.getInputSample().getReceivedSample().getSampleCode()))
                    {
                        ttt.setReceivedSampleCode(DNATask.getInputSample().getReceivedSample().getSampleCode());
                    }
                }
                else
                {//没找到，说明下放流程无DNA质检的记录，可能是验证流程，取流程的原始样本
                    TestingSchedule schedule = orderService.getScheduleById(history.getScheduleId());//获取检测流程的相关信息
                    String receivedCode = orderService.getTestingSample(schedule.getSampleId()).getReceivedSample().getSampleCode();
                    if (StringUtils.isNotEmpty(receivedCode))
                    {
                        ttt.setReceivedSampleCode(receivedCode);
                    }
                }
            }
            
        }
        
        if (null != testingsheet)
        {
            ttt.setTesterName(testingsheet.getTesterName());
            ttt.setAssignerTime(testingsheet.getAssignTime());
            ttt.setTaskCode(testingsheet.getCode());
            if ("生信注释".equals(task.getName()))
            {
                if (StringUtils.isNotEmpty(task.getErrorState()) && 2 == task.getErrorState())
                {
                    BiologyAnnotationFailOperate operate = orderService.getAnnotationFailOperateByTask(task.getId());
                    if (StringUtils.isNotEmpty(operate))
                    {
                        ttt.setTesterName(operate.getOperatorName()); //如果有异常任务接收人展示异常上报人
                    }
                }
                else
                {
                    ttt.setTesterName("接口自动");
                }
            }
        }
        return ttt;
    }
    
    public String getScheduleHistoryStatus(Integer state)
    {
        if (state == 1)
        {
            return "暂停流程";
        }
        else if (state == 2)
        {
            return "重新开始流程";
        }
        else
        {
            return "取消流程";
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
            return null;
        }
    }
    
    public String getStatus(Integer state, String taskRefer, TestingTaskRequest task)
    {
        if (null != state)
        {
            if (StringUtils.isEmpty(taskRefer))
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
            else if ("NGS_SEQUECING_TASK".equals(taskRefer))
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
                    return "上机中";
                }
                else if (state == 4)
                {
                    return "已结束(正常结束)";
                }
                else if (state == 5)
                {
                    return "异常下机";
                }
                else
                {
                    return "已取消";
                }
            }
            else if ("BIOLOGY_DIVISION_TASK".equals(taskRefer))
            {
                if (state == 0)
                {
                    return "待拆分";
                }
                else if (state == 1)
                {
                    return "正在拆分";
                }
                else if (state == 2)
                {
                    return "生成md5校验文件";
                }
                else if (state == 3)
                {
                    return "统计数据产量";
                }
                else if (state == 4)
                {
                    return "清理缓存";
                }
                else if (state == 5)
                {
                    return "已结束(正常结束)";
                }
                else if (state == 6)
                {
                    return "拆分失败";
                }
                else
                {
                    return "已取消";
                }
            }
            else if ("BIOLOGY-ANNOTATION".equals(taskRefer))//注释状态
            {
                //（1）注释状态 0-进行中
                //(2)注释完成，但是质控不合格，并且没有进行失败处理，显示进行中
                //(2)注释完成，但是质控不合格，失败处理 处理策略选重新注释，状态变为0，显示进行中
                if (task.getAnnotationState() == 0 || task.getAnnotationState() == 5
                    && (StringUtils.isNotEmpty(task.getStatisticsState()) && 1 == task.getStatisticsState())
                    && (null == task.getErrorState() || 3 == task.getErrorState()))
                {
                    return "进行中";
                }
                else if (task.getAnnotationState() == 1)
                {
                    return "过滤";
                }
                else if (task.getAnnotationState() == 2)
                {
                    return "比对";
                }
                else if (task.getAnnotationState() == 3)
                {
                    return "call突变";
                }
                else if (task.getAnnotationState() == 4)
                {
                    return "注释";
                }
                else if (task.getAnnotationState() == 5 && (StringUtils.isNotEmpty(task.getStatisticsState()) && 0 == task.getStatisticsState())
                    && (StringUtils.isNotEmpty(task.getErrorState())) && 1 == task.getErrorState())//注释完成，进行手动合格，显示已结束（正常结束）
                {
                    return "已结束(正常结束)";
                }
                else if (task.getAnnotationState() == 5 && (StringUtils.isNotEmpty(task.getStatisticsState()) && 1 == task.getStatisticsState())
                    && (StringUtils.isNotEmpty(task.getErrorState())) && 2 == task.getErrorState())//注释完成，但是质控不合格，上报处理，显示已结束（异常结束）
                {
                    return "已结束";
                }
                else if (task.getAnnotationState() == 5 && (StringUtils.isNotEmpty(task.getStatisticsState()) && 0 == task.getStatisticsState()))
                {
                    return "已结束(正常结束)";
                }
                else if (task.getAnnotationState() == 6)
                {
                    return "出现错误";
                }
                else if (task.getAnnotationState() == 7)
                {
                    return "打分中";
                }
                else if (task.getAnnotationState() == 10)
                {
                    return "解析文件中";
                }
                else
                {
                    return "已取消";
                }
                
            }
            else if ("TECHNICAL-ANALYSIS".equals(taskRefer))//任务状态：1-待领取  2-已领取 3-可处理 4-提交复核 5-复核通过 6-复核不通过
            {
                if (state == 1)
                {
                    return "待领取";
                }
                else if (state == 2)
                {
                    return "已领取";
                }
                else if (state == 3)
                {
                    return "可处理";
                }
                else if (state == 4)
                {
                    return "提交复核";
                }
                else if (state == 5)
                {
                    return "已结束(正常结束)";
                }
                else if (state == 6)
                {
                    return "复核不通过";
                }
                else if (state == 7)
                {
                    return "已结束";//异常上报
                }
                else
                {
                    return "已取消";
                }
                
            }
            
        }
        else
        {
            return null;
        }
        return null;
    }
    
    public String getCodes(TestingSchedule schedule)
    {
        List<String> list = Lists.newArrayList();
        List<TestingScheduleHistory> histories = Lists.newArrayList();
        if (StringUtils.isNotEmpty(schedule.getId()))
        {
            histories = orderService.getTSHByScheduleId(schedule.getId());
        }
        if (Collections3.isNotEmpty(histories))
        {
            Collections.reverse(histories);
            for (TestingScheduleHistory history : histories)
            {
                if (StringUtils.isNotEmpty(history.getTaskId()))
                {
                    TestingTaskRequest testingtask;
                    if (StringUtils.isNotEmpty(history.getTaskRefer()))
                    {
                        testingtask = testingTaskSheetServiceFactory.getSerivceByTaskRefer(history.getTaskRefer()).getTTRById(history.getTaskId());
                    }
                    else
                    {
                        testingtask = testingTaskSheetServiceFactory.getSerivceByTaskRefer(null).getTTRById(history.getTaskId());
                    }
                    
                    if ("PRIMER-DESIGN".equals(testingtask.getSemantic()) || "PCR-NGS-PRIMER-DESIGN".equals(testingtask.getSemantic()))
                    {
                        continue;
                    }
                    if (null != testingtask)
                    {
                        TestingSample testingSample = testingtask.getInputSample();
                        if (null != testingSample)
                        {
                            ReceivedSample receivedsample = testingSample.getReceivedSample();
                            if (null != receivedsample)
                            {
                                if (!list.contains(receivedsample.getSampleCode()))
                                {
                                    list.add(receivedsample.getSampleCode());
                                }
                            }
                        }
                    }
                }
                
            }
        }
        return Collections3.convertToString(list, "/");
        
    }
    
    @RequestMapping(value = "getOrderStatusByCode.do")
    @ResponseBody
    public Integer getOrderStatusByCode(String code)
    {
        return orderService.getOrderStatusByCode(code);
    }
    
    private void getSequenceCodes(StringBuffer sequenceCodes, TemporarySample ts, List<String> codes)
    {
        if (StringUtils.isNotEmpty(ts.getSequenceCode()) && !codes.contains(ts.getSequenceCode()))
        {
            if (StringUtils.isEmpty(sequenceCodes))
            {
                sequenceCodes.append(ts.getSequenceCode());
            }
            else
            {
                sequenceCodes.append(",").append(ts.getSequenceCode());
            }
            codes.add(ts.getSequenceCode());
        }
    }
    
    private void changeTaskStatus(Integer taskstate, TestingTaskRequest task)
    {
        if (null != taskstate)
        {
            task.setStatus(taskstate);
        }
    }
    
    @RequestMapping(value = "/orderView_goBack.do", method = RequestMethod.GET)
    public String goBack(String redirectStatus, ModelMap model, HttpSession session)
    {
        if (StringUtils.isNotEmpty(redirectStatus))
        {
            return "forward:/bpm/report/handle.do?id=" + redirectStatus;
        }
        else
        {
            return redirectList(model, session, "redirect:/order/orderQueryByStatus.do");
        }
    }
    
    @RequestMapping(value = "/changePaymentConfirmStatus", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> changePaymentConfirmStatus()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = orderService.changePaymentConfirmStatus();
        @SuppressWarnings("unchecked")
        Map<String, Object> result = JsonUtils.asObject(msg, Map.class);
        map.put("总共耗时", result.get("time") + "秒");
        map.put("受影响行", result.get("total"));
        return map;
    }
    
    @RequestMapping(value = "/changeOrderProduct", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> changeOrderProduct()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = orderService.changeOrderProduct();
        @SuppressWarnings("unchecked")
        Map<String, Object> result = JsonUtils.asObject(msg, Map.class);
        map.put("总共耗时", result.get("time") + "秒");
        map.put("受影响行", result.get("total"));
        return map;
    }
    
    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    @ResponseBody
    private void changeStatus()
    {
        String msg = orderService.changeStatus();
    }
    
    @RequestMapping(value = "/searchReceiveStatus", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> searchReceiveStatus()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        orderService.searchReceiveStatus();
        map.put("msg", "maintain后台服务正在处理，请查看日志");
        return map;
    }
    
    @RequestMapping(value = "/synchronizeOrderPayTime", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> synchronizeOrderPayTime()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        orderService.synchronizeOrderPayTime();
        map.put("msg", "订单付款时间同步成功");
        return map;
    }
    
    @RequestMapping(value = "/schuduleCancelDetailShow.do")
    public String cancelDetailView(String scheduleId, ModelMap model)
    {
        List<AbnormalSolveModel> cancelDetails = orderService.getCancelDetailView(scheduleId);
        model.addAttribute("cancelDetails", cancelDetails);
        return "order/schuduleCancelDetail_show";
    }
    
    /*
     * @RequestMapping(value = "/searchSamplesByCustomerId.do")
     * 
     * @ResponseBody public List<GanalysisSampleInfo>
     * searchSamplesByCustomerId(SampleSynchronizeRequest request) {
     * List<GanalysisSampleInfo> sampleInfos =
     * orderService.searchSamplesByCustomerId(request); return sampleInfos; }
     */
    
    @RequestMapping(value = "/resolveAccountStatement")
    @ResponseBody
    public String resolveAccountStatement(@RequestBody String data, ModelMap model)
    {
        String entry = orderService.resolveAccountStatement(data);
        return entry;
    }
    
    @RequestMapping(value = "/copyOrderForm.do")
    public String copyOrderForm(Order searcher, ModelMap model)
    {
        Order order = orderService.getById(searcher.getId());
        if (StringUtils.isNotEmpty(order))
        {
            order.setOwner(customerService.get(order.getOwnerId()));
            order.setRecipientName(StringUtils.isEmpty(order.getRecipientName()) ? "" : order.getRecipientName());
            // 传递到前台，数量+金额
            Map<Integer, Integer> map = getSubPriceInitMap();
            for (Integer key : map.keySet())
            {
                order.setFreeCount(key);
                order.setExtraMoney(map.get(key) / 100);
            }
            
            model.addAttribute("order", order);
            
            resolvePic(model, order);
            
            resolveSamples(order);
            
            //修改页面回显项目管理人
            if (order.getProjectManager() != null && order.getProjectManager() != "")
            {
                UserDetailsModel udm = userService.getUserByID(order.getProjectManager());
                UserSimpleModel usm = new UserSimpleModel();
                usm.setId(udm.getId());
                usm.setName(udm.getArchive().getName());
                usm.setPhone(udm.getArchive().getPhone());
                model.addAttribute("prjManagerUser", JSONObject.toJSON(usm).toString());
            }
            else
            {
                model.addAttribute("prjManagerUser", 1);
            }

            List<MetadataSample> sampleList = sampleService.list(new SamplePagingRequest());
            model.addAttribute("orderJson", JSONObject.toJSONString(order));
            model.addAttribute("sampleList", JSONObject.toJSONString(sampleList));
            model.addAttribute("sample", sampleList);
            
            List<Dict> classfys = dictService.getEntries("SAMPLE_PURPOSE");
            model.addAttribute("samplePurposeDict", JSONObject.toJSONString(classfys));
            
        }
        return "order/checkorder_copy_form";
    }

    //--  订单数据同步服务接口访问 入口
    @RequestMapping(value = "/sync/batchSearch", method = RequestMethod.POST)
    @ResponseBody
    public OrderSyncResponseModel batchSearch(@RequestBody(required = false) String data) {
        OrderSyncResponseModel response = new OrderSyncResponseModel();
        if (StringUtils.isEmpty(data)) {
            response.setStatusCode("0001");
            response.setMessage("参数不能为空");
        } else {
            OrderSyncSearchModel request = JsonUtils.asObject(data, OrderSyncSearchModel.class);
            if (null != request) {
                try {
                    List<OrderDetail> resultList = orderService.batchSearch(request);
                    response.setStatusCode("0000");
                    response.setMessage("成功");
                    response.setData(resultList);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatusCode("0001");
                    response.setMessage("系统内部错误");
                }
            } else {
                response.setStatusCode("0001");
                response.setMessage("参数格式异常");
            }

        }
        return response;
    }

    @RequestMapping(value = "/sync/searchByOrderCode", method = RequestMethod.POST)
    @ResponseBody
    public OrderSyncResponseModel searchByOrderCode(@RequestBody(required = false) String data) {
        OrderSyncResponseModel response = new OrderSyncResponseModel();
        if (StringUtils.isNotEmpty(data)) {
            OrderSyncSearchModel request = JsonUtils.asObject(data, OrderSyncSearchModel.class);
            String orderFormCode = request.getOrderFormCode();
            if (null != request && StringUtils.isNotEmpty(orderFormCode)) {
                OrderDetail result = orderService.searchByOrderCode(orderFormCode);
                response.setStatusCode("0000");
                response.setMessage("成功");
                response.setData(result);
            } else {
                response.setStatusCode("0001");
                response.setMessage("orderFormCode参数不能为空");
            }
        } else {
            response.setStatusCode("0001");
            response.setMessage("orderFormCode参数不能为空");
        }
        return response;
    }

    @RequestMapping(value = "/sync/test")
    @ResponseBody
    public String test() {
        return orderService.getOrderSyncTest();
    }
    
}
