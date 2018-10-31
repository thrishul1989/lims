package com.todaysoft.lims.system.modules.bsm.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.UserFeedback;
import com.todaysoft.lims.system.modules.bsm.model.UserFeedbackSearcher;
import com.todaysoft.lims.system.modules.bsm.service.IUserFeedbackService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping(value = "/userFeedback")
public class UserFeedbackController extends BaseController
{
    @Autowired
    private IUserFeedbackService userFeedbackService;
    
    @RequestMapping("/list.do")
    public String page(UserFeedbackSearcher searcher,
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model,
        HttpSession session)
    {
        Pagination<UserFeedback> pagination = userFeedbackService.paging(searcher, pageNo, 10);
        model.addAttribute("pagination", pagination).addAttribute("searcher", searcher);
        return "bsm/userFeedback/userFeedback_list";
    }
}
