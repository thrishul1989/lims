package com.todaysoft.lims.maintain.order.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.maintain.order.service.IOrderService;

@RestController
@RequestMapping("/maintain/order")
public class OrderController
{
    @Autowired
    private IOrderService service;
    
    /**
     * 刷付款待确认数据
     * @return
     */
    @RequestMapping(value = "/changePaymentConfirmStatus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> changePaymentConfirmStatus()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        long startTime = System.currentTimeMillis();
        int count = service.changePaymentConfirmStatus();
        long endTime = System.currentTimeMillis();
        map.put("time", (endTime - startTime) / 1000);
        map.put("total", count);
        return map;
    }
    
    /**
     * 更改合同下产品费用--冗余字段
     * @return
     */
    
    @RequestMapping(value = "/changeOrderProduct", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> changeOrderProduct()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        long startTime = System.currentTimeMillis();
        List<String> result = service.changeOrderProduct();
        long endTime = System.currentTimeMillis();
        map.put("time", (endTime - startTime) / 1000);
        map.put("total", result != null ? result.size() : 0);
        return map;
        
    }
    
    /**
     * 符合订单已完成状态，变更状态
     * @return
     */
    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> changeStatus()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        service.changeStatus();
        return map;
    }
    
    @RequestMapping(value = "/searchReceiveStatus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchReceiveStatus()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        service.searchReceiveStatus();
        return map;
    }
    
    @RequestMapping(value = "/synchronizeOrderPayTime", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> synchronizeOrderPayTime()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        service.synchronizeOrderPayTime();
        return map;
    }
    
}
