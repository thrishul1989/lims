package com.todaysoft.lims.system.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.model.searcher.LogSearcher;
import com.todaysoft.lims.system.model.vo.Log;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.ILogService;

@Controller
@RequestMapping("/log")
public class LogController extends BaseController{
    @Autowired
    private ILogService service;
    
    @RequestMapping("/list.do")
    public String list(LogSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Pagination<Log> pagination = service.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "log/log_list";
    }
    
    @RequestMapping("/create.do")
    public String create(Log data)
    {
        service.create(data);
        return "redirect:list.do";
    }
}
