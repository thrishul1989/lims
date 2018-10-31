package com.todaysoft.lims.system.modules.bmm.mvc;

import javax.servlet.http.HttpSession;

import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderSampleDetails;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.base.RecordFilter;
import com.todaysoft.lims.order.response.TestingMonitorOrder;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bmm.service.IOrderTestingMonitorService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;

@Controller
@RequestMapping(value = "/order")
public class OrderTestingMonitorController extends BaseController
{
    @Autowired
    private IOrderTestingMonitorService service;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;
    
    @RequestMapping(value = "/orderQueryByStatus.do")
    public String getOrdersByStatus(com.todaysoft.lims.order.request.OrderSearchRequest searcher, ModelMap model, HttpSession session)
    {
        int pageNo = null == searcher.getPageNo() ? 1 : searcher.getPageNo();
        int pageSize = null == searcher.getPageSize() ? 10 : searcher.getPageSize();
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        RecordFilter filter = getAccountDetails().getRecordFilter("STATE_QUERT");
        searcher.setFilter(filter);
        
        Pagination<TestingMonitorOrder> pagination = service.search(searcher);
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (TestingMonitorOrder tmo: pagination.getRecords())
            {
                if (StringUtils.isNotEmpty(tmo.getId()))
                {
                    Order order = orderService.getById(tmo.getId());
                    String projectManager = order.getProjectManager();
                    if (StringUtils.isNotEmpty(projectManager))
                    {
                        UserDetailsModel prjManagerUser = userService.getUserByID(projectManager);
                        tmo.setPrjManagerName(prjManagerUser.getArchive().getName());
                    }
                }
            }
        }

        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "/bmm/order/testing_order_list";
    }
    
    @RequestMapping("/testing_orders_redirect.do")
    public String redirectList(ModelMap model, HttpSession session)
    {
        return redirect(model, session);
    }
    
    private String redirect(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/order/orderQueryByStatus.do";
    }
}
