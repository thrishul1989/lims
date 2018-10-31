package com.todaysoft.lims.system.modules.report.mvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.model.vo.User;

import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.modules.bmm.model.DefaultInvoiceModel;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bmm.service.IDefaultInvoiceService;
import com.todaysoft.lims.system.modules.invoice.service.IInvoiceSendService;
import com.todaysoft.lims.system.modules.invoice.service.request.InvoiceSendRequest;
import com.todaysoft.lims.system.modules.report.model.ReportEmailAssignRequest;
import com.todaysoft.lims.system.modules.report.model.ReportEmailModel;
import com.todaysoft.lims.system.modules.report.model.ReportEmailSearcher;
import com.todaysoft.lims.system.modules.report.model.TestingReportEmail;
import com.todaysoft.lims.system.modules.report.model.WhetherEmailModel;
import com.todaysoft.lims.system.modules.report.service.IReportEmailService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IAPPSalemanService;
import com.todaysoft.lims.system.modules.smm.service.IInvoiceUserService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/repors/reportEmail")
public class ReportEmailController extends BaseController
{
    @Autowired
    private IReportEmailService service;
    
    @Autowired
    private IDefaultInvoiceService invoiceService;
    
    @Autowired
    private IInvoiceSendService invoiceSendService;
    
    @Autowired
    private IInvoiceUserService invoiceUserService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IAPPSalemanService appSalemanService;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private IOrderService orderSerivice;
    
    @RequestMapping(value = "/assignedList.do")
    public String paging(ReportEmailSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<ReportEmailModel> pagination = service.paging(searcher);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (ReportEmailModel rem : pagination.getRecords())
            {
                if (StringUtils.isNotEmpty(rem.getOrderId()))
                {
                    Order order = orderSerivice.getById(rem.getOrderId());
                    if (StringUtils.isNotEmpty(order.getProjectManager()))
                    {
                        UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                        rem.setPrjManagerName(prjManagerUser.getArchive().getName());
                    }
                }
            }
        }

        LinkedHashMap<String, List<ReportEmailModel>> res = new LinkedHashMap<String, List<ReportEmailModel>>();
        filter(pagination.getRecords(), res, 1);
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        model.addAttribute("res", res);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "report/reportEmail/reportEmail_list";
    }
    
    @RequestMapping(value = "/whetherCanEmail.do")
    @ResponseBody
    public WhetherEmailModel whetherCanEmail(HttpServletRequest request, HttpServletResponse response, ModelMap model, HttpSession session)
    {
        String req = request.getParameter("orderIds");
        String emailOrNot = request.getParameter("emailOrNot");
        String[] ids = req.split("\\,");
        WhetherEmailModel m = new WhetherEmailModel();
        if ("1".equals(emailOrNot))
        {
            m.setEmailOrNot(true);
        }
        else if ("0".equals(emailOrNot))
        {
            m.setEmailOrNot(false);
        }
        List<String> orderIds = new ArrayList<String>();
        Collections.addAll(orderIds, ids);
        m.setWhetherOrderIds(orderIds);
        WhetherEmailModel res = service.whetherCanEmail(m);
        return res;
        
    }
    
    @RequestMapping(value = "/report.do")
    public String report(HttpServletRequest request, HttpServletResponse response, ModelMap model, HttpSession session)
    {
        String reportEmailIds = request.getParameter("reportEmails").toString();
        
        List<ReportEmailModel> reportEmails = service.getByIds(reportEmailIds);
        //查询订单发票号
        for (ReportEmailModel bean : reportEmails)
        {
            DefaultInvoiceModel invoiceModel = invoiceService.getInfoByOrderId(bean.getOrderId());
            bean.setInvoiceModel(invoiceModel);
        }
        model.addAttribute("record", reportEmails);
        
        return "report/reportEmail/report_view";
        
    }
    
    @RequestMapping(value = "/noReport.do")
    public String noReport(HttpServletRequest request, HttpServletResponse response, ModelMap model, HttpSession session)
    {
        String reportEmailIds = request.getParameter("noReportEmails").toString();
        
        service.noReport(reportEmailIds);
        
        return "redirect:myReport.do";
        
    }
    
    @RequestMapping(value = "/report")
    public String reportEmail(ReportEmailModel request, HttpServletResponse response, ModelMap model, HttpSession session)
    {
        //提交寄送信息
        service.reportEmail(request);
        //寄送发票
        if (StringUtils.isNotEmpty(request.getReportInvoiceOrderIds()))
        {
            InvoiceSendRequest invoiceRequest = new InvoiceSendRequest();
            List<String> list = new ArrayList<String>();
            List<String> categorys = new ArrayList<String>();
            for (String orderId : request.getReportInvoiceOrderIds().split("\\,"))
            {
                FinanceInvoiceTask financeInvoiceTask = invoiceService.getFinanceInvoiceByOrderId(orderId);
                list.add(financeInvoiceTask.getRecordKey());
                categorys.add(financeInvoiceTask.getCategory());
            }
            AuthorizedUser loginUser = userService.getByToken();
            invoiceRequest.setOperateId(loginUser.getId());
            invoiceRequest.setOperateName(loginUser.getUsername());
            invoiceRequest.setRecordKeys(Joiner.on(",").join(list));
            invoiceRequest.setSendTime(new Date());
            invoiceRequest.setSendDetail(request.getEmailContent());
            invoiceRequest.setTrackNumber(request.getEmailNo());
            invoiceRequest.setTransportName(request.getEmailName());
            invoiceRequest.setTransportPhone(request.getEmailPhone());
            invoiceRequest.setTransportType(request.getEmailType());
            invoiceRequest.setCategorys(Joiner.on(",").join(categorys));
            invoiceSendService.create(invoiceRequest);
        }
        
        return "redirect:myReport.do";
    }
    
    @RequestMapping(value = "/assign.do")
    @ResponseBody
    public String assign(HttpServletRequest request, HttpServletResponse response, ModelMap model, HttpSession session)
    {
        String req = request.getParameter("req");
        ReportEmailAssignRequest re = JSON.parseObject(req, ReportEmailAssignRequest.class);
        service.assign(re);
        
        return "1";
        
    }
    
    @RequestMapping("/noEmailView.do")
    public String noEmailView(String id, ModelMap model)
    {
        TestingReportEmail email = service.getById(id);
        Order order = orderSerivice.getById(email.getOrder().getId());
        Customer customer = customerService.get(order.getOwnerId());
        order.setOwner(customer);
        String examinee = Collections3.getFirst(order.getOrderExamineeList()).getName();
        FinanceInvoiceTask financeInvoiceTask = invoiceService.getFinanceInvoiceByOrderId(order.getId());
        String productCode = email.getProduct().getCode();
        String samoleCode = Collections3.getFirst(order.getOrderPrimarySample()).getSampleCode();

        //查询订单发票号
        DefaultInvoiceModel invoiceModel = invoiceService.getInfoByOrderId(order.getId());
        order.setInvoiceModel(invoiceModel);

        model.addAttribute("orderExaminee", examinee);
        model.addAttribute("reportEmail", email);
        model.addAttribute("record", order);
        model.addAttribute("financeInvoiceTask", financeInvoiceTask);
        model.addAttribute("productCodes", productCode);
        model.addAttribute("samoleCodes", samoleCode);
        return "report/reportEmail/noEmailView";
    }
    
    @RequestMapping(value = "/myReport.do")
    public String myReport(ModelMap model, HttpSession session)
    {
        return "report/reportEmail/myreport_tab";
        
    }
    
    @RequestMapping(value = "/myReportList")
    public String myReportList(ReportEmailSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        //管理员可以查看所有
        if (StringUtils.isEmpty(searcher.getAssignedId()))
        {
            AuthorizedUser loginUser = userService.getByToken();
            searcher.setAssignedId(loginUser.getId());
        }
        
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        Pagination<ReportEmailModel> pagination = service.paging(searcher);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (ReportEmailModel rem : pagination.getRecords())
            {
                if (StringUtils.isNotEmpty(rem.getOrderId()))
                {
                    Order order = orderSerivice.getById(rem.getOrderId());
                    if (StringUtils.isNotEmpty(order.getProjectManager()))
                    {
                        UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                        rem.setPrjManagerName(prjManagerUser.getArchive().getName());
                    }
                }
            }
        }

        LinkedHashMap<String, List<ReportEmailModel>> res = new LinkedHashMap<String, List<ReportEmailModel>>();
        filter(pagination.getRecords(), res, searcher.getStatus());
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        model.addAttribute("res", res);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "report/reportEmail/myEmail_list";
    }
    
    //已处理列表
    @RequestMapping(value = "/handledList.do")
    public String handledList(ReportEmailSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        //管理员可以查看所有
        /*if (StringUtils.isEmpty(searcher.getAssignedId()))
        {
            AuthorizedUser loginUser = userService.getByToken();
            searcher.setAssignedId(loginUser.getId());
        }*/
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = dataAuthorityInitial();
        if (dataAuthorityMap.containsKey("REPORT_EMAIL_HANDLED"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("REPORT_EMAIL_HANDLED"));
        }
        
        searcher.setStatus(3);
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        Pagination<ReportEmailModel> pagination = service.paging(searcher);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (ReportEmailModel rem : pagination.getRecords())
            {
                if (StringUtils.isNotEmpty(rem.getOrderId()))
                {
                    Order order = orderSerivice.getById(rem.getOrderId());
                    if (StringUtils.isNotEmpty(order.getProjectManager()))
                    {
                        UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                        rem.setPrjManagerName(prjManagerUser.getArchive().getName());
                    }
                }
            }
        }

        LinkedHashMap<String, List<ReportEmailModel>> res = new LinkedHashMap<String, List<ReportEmailModel>>();
        filter(pagination.getRecords(), res, searcher.getStatus());
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        model.addAttribute("res", res);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "report/reportEmail/handled_list";
    }
    
    //合并
    private void filter(List<ReportEmailModel> records, LinkedHashMap<String, List<ReportEmailModel>> res, Integer status)
    {
        for (ReportEmailModel model : records)
        {
            if (StringUtils.isNotEmpty(model.getId()))
            {
                if (2 == status)
                {//待处理
                
                    if (!res.containsKey(model.getOrderId()))
                    {
                        List<ReportEmailModel> list = new ArrayList<ReportEmailModel>();
                        list.add(model);
                        res.put(model.getOrderId(), list);
                        
                    }
                    else
                    {
                        List<ReportEmailModel> list = res.get(model.getOrderId());
                        list.add(model);
                    }
                    
                }
                else if (3 == status)
                {//已经处理
                    if ("3".equals(model.getStatus()) || "4".equals(model.getStatus()))
                    {
                        if (!res.containsKey(model.getOrderId()))
                        {
                            List<ReportEmailModel> list = new ArrayList<ReportEmailModel>();
                            list.add(model);
                            res.put(model.getOrderId(), list);
                            
                        }
                        else
                        {
                            List<ReportEmailModel> list = res.get(model.getOrderId());
                            list.add(model);
                        }
                    }
                }
                else if (1 == status)
                {//未分配
                    if ("1".equals(model.getStatus()))
                    {
                        if (!res.containsKey(model.getOrderId()))
                        {
                            List<ReportEmailModel> list = new ArrayList<ReportEmailModel>();
                            list.add(model);
                            res.put(model.getOrderId(), list);
                            
                        }
                        else
                        {
                            List<ReportEmailModel> list = res.get(model.getOrderId());
                            list.add(model);
                        }
                    }
                }
            }
        }
        
    }
    
}
