package com.todaysoft.lims.system.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.model.searcher.NoticeSearcher;
import com.todaysoft.lims.system.model.searcher.ScheduleSearcher;
import com.todaysoft.lims.system.model.vo.Notice;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Schedule;
import com.todaysoft.lims.system.service.INoticeService;
import com.todaysoft.lims.system.service.IScheduleService;

@RequestMapping("/personal")
@Controller
public class PersonalController extends BaseController
{
    @Autowired
    private IScheduleService sService;
    
    @Autowired
    private INoticeService nService;
    
    @RequestMapping("/list.do")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<Schedule> schedulePagination = sService.paging(new ScheduleSearcher(), pageNo, DEFAULTPAGESIZE);
        Pagination<Notice> noticePagination = nService.paging(new NoticeSearcher(), pageNo, DEFAULTPAGESIZE);
        model.addAttribute("schedulePagination", schedulePagination);
        model.addAttribute("noticePagination", noticePagination);
        session.setAttribute("s-pageNo", pageNo);
        return "person/person_list";
    }
}
