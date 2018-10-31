package com.todaysoft.lims.system.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.model.vo.OrderSample;
import com.todaysoft.lims.system.service.IOrderSampleService;
import com.todaysoft.lims.system.service.ISampleReceiveService;

@Controller
@RequestMapping(value = "/orderSample")
public class OrderSampleController extends BaseController
{
    @Autowired
    private IOrderSampleService orderSampleService;
    
    @Autowired
    private ISampleReceiveService sampleReceiveService;
    
    /**
     * 订单样本
     * */
    @RequestMapping(value = "/list.do")
    public String getSampleOrderList(OrderSample searcher, ModelMap model)
    {
        //OrderSampleDetail detail = orderSampleService.getOrderSampleDetail(searcher.getOrderId());
        model.addAttribute("searcher", searcher);
        // model.addAttribute("detail", detail);
        
        return "ordersample/ordersample_list";
        
    }
    
}
