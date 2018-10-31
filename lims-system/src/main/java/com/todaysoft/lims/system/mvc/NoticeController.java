package com.todaysoft.lims.system.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.model.searcher.NoticeSearcher;
import com.todaysoft.lims.system.model.vo.Notice;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.INoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController
{
    @Autowired
    private INoticeService service;
    
    @RequestMapping("/list.do")
    public String list(NoticeSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<Notice> pagination = service.paging(searcher, pageNo, DEFAULTPAGESIZE);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "notice/notice_list";
    }
    
    @RequestMapping(value = "/view.do")
    public String viewNotice(String id, ModelMap model)
    {
        
        Notice data = service.get(id);
        model.addAttribute("data", data);
        return "notice/notice_view";
    }
    
}
