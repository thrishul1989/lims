package com.todaysoft.lims.system.modules.bmm.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.model.vo.contract.Contract;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.order.request.OrderSearchRequest;
import com.todaysoft.lims.order.response.ScheduleMonitorOrder;
import com.todaysoft.lims.schedule.response.OrderPostponedDetails;
import com.todaysoft.lims.schedule.response.OrderScheduleMonitorResponse;
import com.todaysoft.lims.system.model.searcher.TaskSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.modules.bmm.model.OrderSchedulePlanTaskModel;
import com.todaysoft.lims.system.modules.bmm.service.IOrderScheduleMonitorService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;
import com.todaysoft.lims.system.service.ITaskService;
import com.todaysoft.lims.system.service.IUserService;

@Controller
@RequestMapping(value = "/bmm/schedule")
public class OrderScheduleMonitorController extends BaseController
{
    @Autowired
    private IOrderScheduleMonitorService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITaskService taskService;

    @Autowired
    private IOrderService orderService;
    
    @RequestMapping("/orders.do")
    public String orders(ModelMap model)
    {
        model.addAttribute("flag", "update");
        return "/bmm/schedule/order_list";
    }
    
    @RequestMapping("/priorityOrders.do")
    public String priorityOrders(OrderSearchRequest searcher, ModelMap model, HttpSession session)
    {
        int pageNo = null == searcher.getPageNo() ? 1 : searcher.getPageNo();
        int pageSize = null == searcher.getPageSize() ? 10 : searcher.getPageSize();
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        Pagination<ScheduleMonitorOrder> pagination = service.search(searcher);
        if (Collections3.isNotEmpty(pagination.getRecords())) {
            for (ScheduleMonitorOrder smo : pagination.getRecords()) {
                String projectManager = orderService.getById(smo.getId()).getProjectManager();
                if (StringUtils.isNotEmpty(projectManager)) {
                    UserDetailsModel prjManagerUser = userService.getUserByID(projectManager);
                    smo.setPrjManagerName(prjManagerUser.getArchive().getName());
                }
            }
        }

        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        TaskSearcher taskSearcher = new TaskSearcher();
        List<Task> taskList = taskService.list(taskSearcher);
        model.addAttribute("taskList", taskList);
        return "/bmm/schedule/order_priority_list";
    }
    
    @RequestMapping("/unPriorityOrders.do")
    public String unPriorityOrders(OrderSearchRequest searcher, ModelMap model, HttpSession session)
    {
        int pageNo = null == searcher.getPageNo() ? 1 : searcher.getPageNo();
        int pageSize = null == searcher.getPageSize() ? 10 : searcher.getPageSize();
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        searcher.setPriorityStatus(5);//已完成
        
        Pagination<ScheduleMonitorOrder> pagination = service.search(searcher);
        if (Collections3.isNotEmpty(pagination.getRecords())) {
            for (ScheduleMonitorOrder smo : pagination.getRecords()) {
                String projectManager = orderService.getById(smo.getId()).getProjectManager();
                if (StringUtils.isNotEmpty(projectManager)) {
                    UserDetailsModel prjManagerUser = userService.getUserByID(projectManager);
                    smo.setPrjManagerName(prjManagerUser.getArchive().getName());
                }
            }
        }

        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "/bmm/schedule/order_unPriority_list";
    }
    
    @RequestMapping("/correct_order.do")
    @ResponseBody
    public Map<String, Object> correct(String id)
    {
        return service.correct(id);
    }
    
    @RequestMapping("/orders_redirect.do")
    public String ordersRedirect(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:priorityOrders.do";
    }
    
    @RequestMapping("/orders_unPriorityRedirect.do")
    public String unPriorityOrdersRedirect(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:unPriorityOrders.do";
    }
    
    @RequestMapping("/order.do")
    public String order(String id, ModelMap model)
    {
        OrderScheduleMonitorResponse response = service.getOrderScheduleMonitorDetails(id);
        model.addAttribute("data", response);
        return "/bmm/schedule/order_details";
    }
    
    @RequestMapping("/group.do")
    @ResponseBody
    public List<OrderSchedulePlanTaskModel> getGroupTasks(String id)
    {
        return service.getGroupPlanTasks(id);
    }
    
    @RequestMapping("/orders_postponed_details.do")
    @ResponseBody
    public Map<String, Object> getOrderPostponedDetails(String keys)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        
        try
        {
            List<OrderPostponedDetails> records = service.getOrderPostponedDetails(keys);
            data.put("success", true);
            data.put("records", records);
        }
        catch (Exception e)
        {
            data.put("success", false);
        }
        
        return data;
    }
    
    @RequestMapping(value = "/updateOrderUrgent.do")
    public String updateOrderUrgent(String orderId, Integer ifUrgent, ModelMap model, HttpSession session)
    {
        User user = userService.getUserByToken();
        service.updateOrderUrgent(orderId, ifUrgent, user.getUsername());
        return redirectList(model, session);
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/bmm/schedule/priorityOrders.do";
    }
}
