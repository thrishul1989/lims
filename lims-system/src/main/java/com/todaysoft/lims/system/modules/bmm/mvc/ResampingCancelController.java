package com.todaysoft.lims.system.modules.bmm.mvc;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.util.Collections3;
import com.todaysoft.lims.system.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.order.OrderSearchRequest;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecord;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecordDetails;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecordHandleForm;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecordSearcher;
import com.todaysoft.lims.system.modules.bmm.service.IResamplingCancelService;
import com.todaysoft.lims.system.modules.invoice.model.InvoiceSendSearcher;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;
import com.todaysoft.lims.system.service.security.AccountDetails;

@Controller
@RequestMapping(value = "/bmm/resampling")
public class ResampingCancelController extends BaseController
{
    @Autowired
    private IResamplingCancelService service;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;
    
    @RequestMapping("/cancel_list.do")
    public String tabList(InvoiceSendSearcher searcher, ModelMap model)
    {
        model.addAttribute("searcher", searcher);
        model.addAttribute("flag", "update");
        return "bmm/resampling/cancel_list_tab";
    }
    
    @RequestMapping("/cancel_list_wating.do")
    public String paging(ResamplingCancelRecordSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        
        //**数据权限开始  。。。。。。。。。。。。。。。。。*//*
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("CANCEL_SAMPLE_LIST"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("CANCEL_SAMPLE_LIST"));
        }
        
        Pagination<ResamplingCancelRecord> pagination = service.paging(searcher, pageNo, pageSize);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (ResamplingCancelRecord resample : pagination.getRecords())
            {
                Order order = orderService.getById(orderService.getIdByCode(resample.getOrderCode()));
                if (StringUtils.isNotEmpty(order.getProjectManager()))
                {
                    UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                    resample.setPrjManagerName(prjManagerUser.getArchive().getName());
                }
            }
        }

        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "/bmm/resampling/cancel_list";
    }
    
    @RequestMapping("/cancel_list_solve.do")
    public String solvePaging(ResamplingCancelRecordSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        
        //**数据权限开始  。。。。。。。。。。。。。。。。。*//*
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = account.getDataAuthoritySearcher();
        if (dataAuthorityMap.containsKey("CANCEL_SAMPLE_LIST"))
        {
            searcher.setDataAuthoritySearcher(dataAuthorityMap.get("CANCEL_SAMPLE_LIST"));
        }
        Pagination<ResamplingCancelRecord> pagination = service.solvePaging(searcher, pageNo, pageSize);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (ResamplingCancelRecord resample : pagination.getRecords()) {
                Order order = orderService.getById(orderService.getIdByCode(resample.getOrderCode()));
                if (StringUtils.isNotEmpty(order.getProjectManager())) {
                    UserDetailsModel prjManagerUser = userService.getUserByID(order.getProjectManager());
                    resample.setPrjManagerName(prjManagerUser.getArchive().getName());
                }
            }
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "/bmm/resampling/cancel_list_solve";
    }
    
    
    @RequestMapping(value = "/cancel_handle.do", method = RequestMethod.GET)
    @FormInputView
    public String handle(String id, ModelMap model)
    {
        ResamplingCancelRecordDetails details = service.getDetails(id);
        model.addAttribute("data", details);
        return "/bmm/resampling/cancel_handle";
    }
    
    @RequestMapping(value = "/cancel_handle.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String handle(ResamplingCancelRecordHandleForm data, ModelMap model, HttpSession session)
    {
        service.handle(data);
        return redirectList(model, session);
    }
    
    @RequestMapping("/cancel_display.do")
    public String display(String id, ModelMap model)
    {
        ResamplingCancelRecordDetails details = service.getDetails(id);
        model.addAttribute("data", details);
        return "/bmm/resampling/cancel_details";
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/bmm/resampling/cancel_list_wating.do";
    }
    
    @RequestMapping("/cancel_solve_display.do")
    public String solveDisplay(String id, ModelMap model)
    {
        ResamplingCancelRecordDetails details = service.getSolveDetails(id);
        model.addAttribute("data", details);
        return "/bmm/resampling/cancel_details";
    }
    
}
