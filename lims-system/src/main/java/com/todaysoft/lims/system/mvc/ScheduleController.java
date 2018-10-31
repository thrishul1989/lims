package com.todaysoft.lims.system.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.model.searcher.ScheduleSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Schedule;
import com.todaysoft.lims.system.service.IScheduleService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController extends BaseController
{
    @Autowired
    private IScheduleService service;
    
    @RequestMapping("/list.do")
    public String list(ScheduleSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<Schedule> pagination = service.paging(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "schedule/schedule_list";
    }
    
    @RequestMapping(value = "/view.do")
    public String viewSchedule(String id, ModelMap model)
    {
        
        Schedule data = service.get(id);
        model.addAttribute("data", data);
        return "schedule/schedule_view";
    }
}
