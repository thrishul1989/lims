package com.todaysoft.lims.smm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.APPSaleman;
import com.todaysoft.lims.smm.entity.BusinessInfo;
import com.todaysoft.lims.smm.request.APPSalemanRequest;
import com.todaysoft.lims.smm.service.IAPPSalemanService;

@RestController
@RequestMapping("/smm/appsaleman")
public class APPSalemanController
{
    @Autowired
    private IAPPSalemanService service;
    
    @RequestMapping("/paging")
    public Pagination<BusinessInfo> paging(@RequestBody APPSalemanRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping("/list")
    public List<String> list(@RequestBody APPSalemanRequest request)
    {
        return service.list(request);
    }
    
    @RequestMapping("/listAll")
    public List<BusinessInfo> listAll(@RequestBody APPSalemanRequest request)
    {
        return service.listAll(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APPSaleman getUserByID(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody APPSaleman request)
    {
        service.create(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody APPSaleman request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/getBusinessInfo/{id}", method = RequestMethod.GET)
    public BusinessInfo getBusinessInfo(@PathVariable String id)
    {
        return service.getBusinessInfo(id);
        
    }
    
    @RequestMapping("/getDataById/{userId}")
    public boolean getDataById(@PathVariable String userId)
    {
        return service.getDataById(userId);
    }
    
    @RequestMapping("/getCustomerByAppId/{appId}")
    public boolean getCustomerByAppId(@PathVariable String appId)
    {
        return service.getCustomerByAppId(appId);
    }

    @RequestMapping("/getAppSaleByids/{ids}")
    public List<APPSaleman> getAppSaleByids(@PathVariable String ids)
    {
        return service.getAppSaleByids(ids);
    }
    
}
