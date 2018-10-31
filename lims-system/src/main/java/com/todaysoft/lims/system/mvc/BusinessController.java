package com.todaysoft.lims.system.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.service.ICustomerService;

@Controller
@RequestMapping("/business")
public class BusinessController extends BaseController
{
    
    @Autowired
    private ICustomerService customerService;
    
    /**
     * 模糊匹配业务员
     */
    @RequestMapping(value = "/businessSelect.do", method = RequestMethod.GET)
    @ResponseBody
    public List businessSelect(BusinessInfo searcher)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        List<BusinessInfo> list = customerService.businessList(searcher);
        return list;
    }
    
    @RequestMapping(value = "/businessSelect", method = RequestMethod.GET)
    @ResponseBody
    public List selectBusiness(BusinessInfo searcher)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        List<BusinessInfo> list = customerService.getBusinesses(searcher);
        return list;
    }
    
    @RequestMapping(value = "/businessSelectByTestingType", method = RequestMethod.GET)
    @ResponseBody
    public List selectBusinessByTestingType(BusinessInfo searcher)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        List<BusinessInfo> list = customerService.getBusinessesByTestingType(searcher);
        return list;
    }
    
    @RequestMapping(value = "/businessSelectForSingle", method = RequestMethod.GET)
    @ResponseBody
    public Map businessSelectForSingle(BusinessInfo searcher)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        List<BusinessInfo> list = customerService.getBusinesses(searcher);
        map.put("value", list);
        return map;
    }
    
}
