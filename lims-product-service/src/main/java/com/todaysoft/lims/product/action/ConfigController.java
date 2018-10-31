package com.todaysoft.lims.product.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.Config;
import com.todaysoft.lims.product.model.request.ConfigListRequest;
import com.todaysoft.lims.product.service.IConfigService;

@RestController
@RequestMapping("/bcm/configs")
public class ConfigController
{
    @Autowired
    private IConfigService service;
    
    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public List<String> getConfigValues(@PathVariable String key)
    {
        return service.getConfigValues(key);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Config> getConfigs()
    {
        return service.getConfigs();
    }
    
    @RequestMapping(value = "/paging")
    public Pagination<Config> paging(@RequestBody ConfigListRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping(value = "/modify")
    public void modify(@RequestBody Config request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/getConfig/{id}")
    public Config getById(@PathVariable String id)
    {
        return service.getById(id);
        
    }
}
