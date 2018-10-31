package com.todaysoft.lims.system.modules.bcm.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bcm.model.Config;
import com.todaysoft.lims.system.modules.bcm.service.IConfigService;
import com.todaysoft.lims.system.modules.bcm.service.request.ConfigListRequest;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/config")
public class ConfigsController extends BaseController
{
    @Autowired
    private IConfigService service;
    
    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public List<String> getConfigValues(@PathVariable String key)
    {
        return service.getConfigValues(key);
    }
    
    @RequestMapping("/list.do")
    public String list(ConfigListRequest searcher,
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Pagination<Config> pagination = service.paging(searcher, pageNo, 10);
        model.addAttribute("pagination", pagination);
        return "config/config_list";
        
    }
    
    @RequestMapping("/form.do")
    public String form(String id, ModelMap model)
    {
        Config config = service.getById(id);
        model.addAttribute("data", config);
        return "config/config_form";
        
    }
    
    @RequestMapping("/modify.do")
    public String modify(Config request, ModelMap model)
    {
        service.modify(request);
        return "redirect:/config/list.do";
        
    }
}
