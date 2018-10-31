package com.todaysoft.lims.system.modules.bcm.mvc;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethodSearcher;
import com.todaysoft.lims.system.modules.bcm.service.ITestingMethodService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestingTypeService;

@Controller
@RequestMapping("/bcm/testing/methods")
public class TestingMethodController extends BaseController
{
    @Autowired
    private ITestingMethodService service;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @RequestMapping("/list.do")
    public String list(TestingMethodSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<TestingMethod> pagination = service.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher).addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "bcm/testing/methods/method_list";
    }
    
    @RequestMapping("/display.do")
    public String display(String id, ModelMap model)
    {
        TestingMethod data = service.get(id);
        model.addAttribute("data", data);
        return "bcm/testing/methods/method_details";
    }
    
    @RequestMapping("/testingMethodList.do")
    @ResponseBody
    public List<TestingMethod> list(TestingMethodSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Pagination<TestingMethod> pagination = service.paging(searcher, pageNo, 10);
        List<TestingMethod> testingMethodList = pagination.getRecords();
        return testingMethodList;
    }
}
