package com.todaysoft.lims.system.modules.report.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderSampleDetails;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.qiniu.util.Json;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethodSearcher;
import com.todaysoft.lims.system.modules.report.model.ReportEmailModel;
import com.todaysoft.lims.system.modules.report.model.ReportEmailSearcher;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendFormModel;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendModel;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendSearcher;
import com.todaysoft.lims.system.modules.report.service.ITestingDataSendService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping(value = "/testingDataSend")
public class TestingDataSendingController extends BaseController
{
    @Autowired
    private ITestingDataSendService service;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    
    @RequestMapping(value = "/assignedList.do")
    public String paging(TestingDataSendSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<TestingDataSendModel> pagination = service.paging(searcher);

        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (TestingDataSendModel tdsm : pagination.getRecords())
            {
                Order order = orderService.getById(tdsm.getOrderId());
                if (StringUtils.isNotEmpty(order.getProjectManager()))
                {
                    UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                    tdsm.setPrjManagerName(prjManagerUser.getArchive().getName());
                }
            }
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "report/dataSend/dataSend_list";
    }
    
    @RequestMapping(value = "/dataSend.do", method = RequestMethod.GET)
    public String dataSend(String orderId, ModelMap model)
    {
        
        List<TestingDataSendFormModel> list = service.getDetail(orderId);
        Map<String, List<TestingDataSendFormModel>> res = filterGroup(list);
        model.addAttribute("res", res);
        return "report/dataSend/dataSend_form";
    }
    
    @RequestMapping(value = "/view.do", method = RequestMethod.GET)
    public String view(String orderId, ModelMap model)
    {
        
        List<TestingDataSendFormModel> list = service.getDetail(orderId);
        Map<String, List<TestingDataSendFormModel>> res = filterGroup(list);
        model.addAttribute("res", res);
        return "report/dataSend/view_form";
    }
    
    @RequestMapping(value = "/dataSend", method = RequestMethod.POST)
    public String Send(String dataSendIds, ModelMap model, HttpSession session)
    {
        
        service.send(dataSendIds);
        return redirectList(model, session, "redirect:/testingDataSend/assignedList.do?statu=0");
        
    }
    
    private Map<String, List<TestingDataSendFormModel>> filterGroup(List<TestingDataSendFormModel> list)
    {
        Map<String, List<TestingDataSendFormModel>> res = new HashMap<>();
        for (int i = 0; i <= list.size() - 1; i++)
        {
            
            TestingDataSendFormModel model = JSON.parseObject(JSON.toJSONString(list.get(i)), TestingDataSendFormModel.class);
            if (res.containsKey(model.getProductName()))
            {
                res.get(model.getProductName()).add(model);
            }
            else
            {
                List<TestingDataSendFormModel> ll = new ArrayList<TestingDataSendFormModel>();
                ll.add(model);
                res.put(model.getProductName(), ll);
            }
        }
        
        return res;
    }
    
}
