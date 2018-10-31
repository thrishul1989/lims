package com.todaysoft.lims.system.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderSampleView;
import com.todaysoft.lims.system.model.vo.payment.OrderDelay;
import com.todaysoft.lims.system.model.vo.payment.OrderDelayCheck;
import com.todaysoft.lims.system.model.vo.payment.OrderReduce;
import com.todaysoft.lims.system.model.vo.payment.OrderReduceCheck;
import com.todaysoft.lims.system.model.vo.payment.OrderRefund;
import com.todaysoft.lims.system.model.vo.payment.OrderRefundCheck;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.IOrderApplyService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

/**
 * 订单延迟申请，退款申请，减免申请
 * */
@Controller
@RequestMapping("/orderApply")
public class OrderApplyController extends BaseController
{
    
    @Autowired
    private IOrderApplyService orderApplyService;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IOrderService orderService;
    
    @RequestMapping(value = "/refundList.do")
    public String refundList(OrderRefund searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<OrderRefund> pagination = orderApplyService.refundList(searcher, pageNo, 10);
        for (OrderRefund refund : pagination.getRecords())
        {
            Order order = orderApplyService.getOrderByRefund(refund.getId());
            if (null != order)
            {
                refund.setOrderId(order);
                
            }
            for (OrderRefundCheck check : refund.getOrderRefundCheckList())
            {
                String roleType = roleType(check.getCheckerId(), check.getRoleType());
                if (StringUtils.isNotEmpty(roleType))
                {
                    check.setRoleType(roleType);
                }
            }
            
        }
        
        List<Customer> customerList = customerService.listActivity(new Customer());
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (OrderRefund o : pagination.getRecords())
            {
                for (Customer c : customerList)
                {
                    if (StringUtils.isNotEmpty(o.getOrderId().getOwnerId()) && o.getOrderId().getOwnerId().equals(c.getId()))
                    {
                        o.getOrderId().setOwner(c);
                    }
                    
                }
                
            }
            
        }
        //判断审批节点
        
        model.addAttribute("searcher", searcher).addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "orderApply/orderRefundApply_list";
    }
    
    @RequestMapping(value = "/refundShow.do")
    public String refundShow(String id, ModelMap model, HttpSession session)
    {
        
        List<OrderRefund> refunds = new ArrayList<OrderRefund>();
        OrderRefund orderRefund = orderApplyService.getRefund(id);
        refunds.add(orderRefund);
        for (OrderRefundCheck check : orderRefund.getOrderRefundCheckList())
        {
            String roleType = roleType(check.getCheckerId(), check.getRoleType());
            if (StringUtils.isNotEmpty(roleType))
            {
                check.setRoleType(roleType);
            }
        }
        
        Order order = orderApplyService.getOrderByRefund(orderRefund.getId());
        if (null != order)
        {
            orderRefund.setOrderId(order);
            
        }
        
        List<Customer> customerList = customerService.listActivity(new Customer());
        for (Customer c : customerList)
        {
            if (StringUtils.isNotEmpty(orderRefund.getOrderId().getOwnerId()) && orderRefund.getOrderId().getOwnerId().equals(c.getId()))
            {
                orderRefund.getOrderId().setOwner(c);
            }
            
        }
        
        //获取样本视图
        List<OrderSampleView> listView = orderService.getOrderSampleView(orderRefund.getOrderId().getId());
        if (null != listView && Collections3.isNotEmpty(listView))
        {
            orderRefund.getOrderId().setView(listView);
        }
        session.setAttribute("refunds", refunds);
        return "orderApply/orderRefundApply_show";
        
    }
    
    @RequestMapping(value = "/refundShowByOrder.do")
    public String refundShowByOrder(String orderId, ModelMap model, HttpSession session)
    {
        
        List<OrderRefund> refunds = orderApplyService.getRefunds(orderId);
        
        for (OrderRefund orderRefund : refunds)
        {
            
            for (OrderRefundCheck check : orderRefund.getOrderRefundCheckList())
            {
                String roleType = roleType(check.getCheckerId(), check.getRoleType());
                if (StringUtils.isNotEmpty(roleType))
                {
                    check.setRoleType(roleType);
                }
            }
            
            Order order = orderApplyService.getOrderByRefund(orderRefund.getId());
            if (null != order)
            {
                orderRefund.setOrderId(order);
                
            }
            
            List<Customer> customerList = customerService.listActivity(new Customer());
            for (Customer c : customerList)
            {
                if (StringUtils.isNotEmpty(orderRefund.getOrderId().getOwnerId()) && orderRefund.getOrderId().getOwnerId().equals(c.getId()))
                {
                    orderRefund.getOrderId().setOwner(c);
                }
                
            }
            
            //获取样本视图
            List<OrderSampleView> listView = orderService.getOrderSampleView(orderRefund.getOrderId().getId());
            if (null != listView && Collections3.isNotEmpty(listView))
            {
                orderRefund.getOrderId().setView(listView);
            }
        }
        
        session.setAttribute("refunds", refunds);
        return "orderApply/orderRefundApply_show";
        
    }
    
    @RequestMapping(value = "/reduceList.do")
    public String reduceList(OrderReduce searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<OrderReduce> pagination = orderApplyService.reduceList(searcher, pageNo, 10);
        for (OrderReduce reduce : pagination.getRecords())
        {
            Order order = orderApplyService.getOrderByReduce(reduce.getId());
            if (null != order)
            {
                reduce.setOrderId(order);
                
            }
            
            for (OrderReduceCheck check : reduce.getOrderReduceCheckList())
            {
                String roleType = roleType(check.getCheckerId(), check.getRoleType());
                if (StringUtils.isNotEmpty(roleType))
                {
                    check.setRoleType(roleType);
                }
            }
            
        }
        
        List<Customer> customerList = customerService.listActivity(new Customer());
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (OrderReduce o : pagination.getRecords())
            {
                for (Customer c : customerList)
                {
                    if (StringUtils.isNotEmpty(o.getOrderId().getOwnerId()) && o.getOrderId().getOwnerId().equals(c.getId()))
                    {
                        o.getOrderId().setOwner(c);
                    }
                    
                }
                
            }
            
        }
        //判断审批节点
        
        model.addAttribute("searcher", searcher).addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "orderApply/orderReduceApply_list";
    }
    
    @RequestMapping(value = "/reduceShow.do")
    public String reduceShow(String id, ModelMap model, HttpSession session)
    {
        OrderReduce orderReduce = orderApplyService.getReduce(id);
        for (OrderReduceCheck check : orderReduce.getOrderReduceCheckList())
        {
            String roleType = roleType(check.getCheckerId(), check.getRoleType());
            if (StringUtils.isNotEmpty(roleType))
            {
                check.setRoleType(roleType);
            }
        }
        
        Order order = orderApplyService.getOrderByReduce(orderReduce.getId());
        if (null != order)
        {
            orderReduce.setOrderId(order);
            
        }
        
        List<Customer> customerList = customerService.listActivity(new Customer());
        for (Customer c : customerList)
        {
            if (StringUtils.isNotEmpty(orderReduce.getOrderId().getOwnerId()) && orderReduce.getOrderId().getOwnerId().equals(c.getId()))
            {
                orderReduce.getOrderId().setOwner(c);
            }
            
        }
        
        //获取样本视图
        List<OrderSampleView> listView = orderService.getOrderSampleView(orderReduce.getOrderId().getId());
        if (null != listView && Collections3.isNotEmpty(listView))
        {
            orderReduce.getOrderId().setView(listView);
        }
        session.setAttribute("reduce", orderReduce);
        return "orderApply/orderReduceApply_show";
        
    }
    
    @RequestMapping(value = "/delayList.do")
    public String delayList(OrderDelay searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        
        Pagination<OrderDelay> pagination = orderApplyService.delayList(searcher, pageNo, 10);
        for (OrderDelay delay : pagination.getRecords())
        {
            Order order = orderApplyService.getOrderByDelay(delay.getId());
            if (null != order)
            {
                delay.setOrderId(order);
                
            }
            
            for (OrderDelayCheck check : delay.getOrderDelayCheckList())
            {
                String roleType = roleType(check.getCheckerId(), check.getRoleType());
                if (StringUtils.isNotEmpty(roleType))
                {
                    check.setRoleType(roleType);
                }
            }
            
        }
        
        List<Customer> customerList = customerService.listActivity(new Customer());
        if (Collections3.isNotEmpty(pagination.getRecords()))
        {
            for (OrderDelay o : pagination.getRecords())
            {
                for (Customer c : customerList)
                {
                    if (StringUtils.isNotEmpty(o.getOrderId().getOwnerId()) && o.getOrderId().getOwnerId().equals(c.getId()))
                    {
                        o.getOrderId().setOwner(c);
                    }
                    
                }
                
            }
            
        }
        //判断审批节点
        
        model.addAttribute("searcher", searcher).addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "orderApply/orderDelayApply_list";
    }
    
    @RequestMapping(value = "/delayShow.do")
    public String delayShow(String id, ModelMap model, HttpSession session)
    {
        OrderDelay orderDelay = orderApplyService.getDelay(id);
        for (OrderDelayCheck check : orderDelay.getOrderDelayCheckList())
        {
            String roleType = roleType(check.getCheckerId(), check.getRoleType());
            if (StringUtils.isNotEmpty(roleType))
            {
                check.setRoleType(roleType);
            }
        }
        
        Order order = orderApplyService.getOrderByDelay(orderDelay.getId());
        if (null != order)
        {
            orderDelay.setOrderId(order);
            
        }
        
        List<Customer> customerList = customerService.listActivity(new Customer());
        for (Customer c : customerList)
        {
            if (StringUtils.isNotEmpty(orderDelay.getOrderId().getOwnerId()) && orderDelay.getOrderId().getOwnerId().equals(c.getId()))
            {
                orderDelay.getOrderId().setOwner(c);
            }
            
        }
        
        //获取样本视图
        List<OrderSampleView> listView = orderService.getOrderSampleView(orderDelay.getOrderId().getId());
        if (null != listView && Collections3.isNotEmpty(listView))
        {
            orderDelay.getOrderId().setView(listView);
        }
        session.setAttribute("delay", orderDelay);
        return "orderApply/orderDelayApply_show";
        
    }
    
    //查询审核人级别
    public String roleType(String id, String roleType)
    {
        BusinessInfo info = customerService.getBusiness(id);
        if (null != info)
        {
            switch (info.getRoleType())
            {
                case "1":
                    return "销售总监";
                    
                case "2":
                    return "销售经理";
                    
                case "3":
                    return "销售主管";
                    
                case "4":
                    return "销售工程师";
                    
            }
        }
        
        return roleType;
        
    }
    
}
