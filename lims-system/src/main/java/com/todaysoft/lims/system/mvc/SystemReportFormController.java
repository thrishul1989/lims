package com.todaysoft.lims.system.mvc;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.system.model.searcher.ContractSystemRequest;
import com.todaysoft.lims.system.model.searcher.CustomerSystemRequest;
import com.todaysoft.lims.system.model.searcher.CycleSystemRequest;
import com.todaysoft.lims.system.model.searcher.OrderSystemRequest;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.model.vo.contract.reportForm.ReportSystemContractInfo;
import com.todaysoft.lims.system.model.vo.order.customerOrderReportForm.ReportSystemCustomerInfo;
import com.todaysoft.lims.system.model.vo.order.orderReportForm.ReportSystemOrderInfo;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.service.impl.CycleConfigService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ISystemReportFormService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.utils.IdGen;

@Controller
@RequestMapping(value = "/systemReportForm")
public class SystemReportFormController extends BaseController
{
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private ISystemReportFormService service;
    
    @Autowired
    private CycleConfigService cycleConfigservice;
    
    @Autowired
    private IUserService userService;
    
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    
    @RequestMapping(value = "/orderReportForm.do")
    public String orderReportForm(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception
    {
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        return "systemReportForm/order_report_form";
    }
    
    @RequestMapping(value = "/download.do", method = RequestMethod.POST)
    @ResponseBody
    public String dowloadData(OrderSystemRequest searcher, HttpServletRequest request, HttpSession session)
    {
        AuthorizedUser user = userService.getByToken();
        searcher.setUserId(user.getId());
        searcher.setUserName(user.getName());
        String sessionId = IdGen.uuid();
        searcher.setTaskId(sessionId);
        cachedThreadPool.execute(new Runnable() {
            public void run() {
                Map<String,List<String>> mapTitle = (Map<String, List<String>>)JSON.parse(searcher.getColNames());
                List<ReportSystemOrderInfo> models = service.orderReportFormListForWkcreate(searcher);
                String fileUrl = "";
                try {
                    fileUrl = service.writer("orderReportForm", "xls", models, mapTitle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                session.setAttribute(sessionId,fileUrl);
            }
        });
        return sessionId;
    }
    
    @RequestMapping(value = "/customerOrderReportForm.do")
    public String customerOrderReportForm(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception
    {
        return "systemReportForm/customer_order_report_form";
    }

    @RequestMapping(value = "/customerDownload.do", method = RequestMethod.POST)
    @ResponseBody
    public String dowloadData(CustomerSystemRequest searcher, HttpServletRequest request, HttpSession session)
    {
        AuthorizedUser user = userService.getByToken();
        searcher.setUserId(user.getId());
        searcher.setUserName(user.getName());
        String sessionId = IdGen.uuid();
        searcher.setTaskId(sessionId);
        cachedThreadPool.execute(new Runnable() {
            public void run() {
                Map<String,List<String>> mapTitle = (Map<String, List<String>>)JSON.parse(searcher.getColNames());
                List<ReportSystemCustomerInfo> models = service.customerOrderReportFormListForWkcreate(searcher);
                String fileUrl = "";
                try {
                    fileUrl = service.writerCustomer("customerOrderReportForm", "xls", models, mapTitle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                session.setAttribute(sessionId,fileUrl);
            }
        });
        return sessionId;
    }

    @RequestMapping(value = "/contractOrderReportForm.do")
    public String contractOrderReportForm(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception
    {
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        model.addAttribute("testingTypeList", testingTypeList);
        return "systemReportForm/contract_order_report_form";
    }
    
    @RequestMapping(value = "/contractDownload.do", method = RequestMethod.POST)
    @ResponseBody
    public String contractDownload(ContractSystemRequest searcher, HttpServletRequest request, HttpSession session)
    {
        AuthorizedUser user = userService.getByToken();
        searcher.setUserId(user.getId());
        searcher.setUserName(user.getName());
        String sessionId = IdGen.uuid();
        searcher.setTaskId(sessionId);
        cachedThreadPool.execute(new Runnable() {
            public void run() {
                Map<String,List<String>> mapTitle = (Map<String, List<String>>)JSON.parse(searcher.getColNames());
                List<ReportSystemContractInfo> models = service.contractReportFormListForWkcreate(searcher);
                String fileUrl = "";
                try {
                    fileUrl = service.writerContract("contractOrderReportForm", "xls", models, mapTitle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                session.setAttribute(sessionId,fileUrl);
            }
        });
        return sessionId;
    }
    
    @RequestMapping(value = "/otherReportForm.do")
    public String otherReportForm(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception
    {
        List<TestingType> testingTypeList = testingTypeService.testingTypeList();
        List<TestingMethod> testingMethodList = cycleConfigservice.getTestingMethodList();
        model.addAttribute("testingTypeList", testingTypeList);
        model.addAttribute("testingMethodList", testingMethodList);
        return "systemReportForm/other_report_form";
    }
    
    //其他报表导出
    @RequestMapping("/exportOtherReportForm.do")
    @ResponseBody
    public String exportOtherReportForm(CycleSystemRequest searcher, HttpSession session)
    {
        /*if (StringUtils.isEmpty(searcher.getCreateTimeStart()) && StringUtils.isEmpty(searcher.getCreateTimeEnd()))
        {
            searcher.setCreateTimeStart(DateUtil.getOneMonthBefore(new Date(), "yyyy-MM-dd"));
            searcher.setCreateTimeEnd(DateUtil.format(new Date(), "yyyy-MM-dd"));
        }
        if (StringUtils.isEmpty(searcher.getPlanTimeStart()) && StringUtils.isEmpty(searcher.getPlanTimeEnd()))
        {
            searcher.setPlanTimeStart(DateUtil.getOneMonthBefore(new Date(), "yyyy-MM-dd"));
            searcher.setPlanTimeEnd(DateUtil.format(new Date(), "yyyy-MM-dd"));
        }
        if (StringUtils.isEmpty(searcher.getActualTimeStart()) && StringUtils.isEmpty(searcher.getActualTimeEnd()))
        {
            searcher.setActualTimeStart(DateUtil.getOneMonthBefore(new Date(), "yyyy-MM-dd"));
            searcher.setActualTimeEnd(DateUtil.format(new Date(), "yyyy-MM-dd"));
        }*/
        AuthorizedUser user = userService.getByToken();
        searcher.setUserId(user.getId());
        searcher.setUserName(user.getName());
        String sessionId = IdGen.uuid();
        searcher.setTaskId(sessionId);
        cachedThreadPool.execute(new Runnable() {
            public void run() {
                String fileUrl = service.exportOtherReportForm(searcher);
                session.setAttribute(sessionId,fileUrl);
            }
        });
        return sessionId;
    }
}
