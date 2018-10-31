package com.todaysoft.lims.system.mvc;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.analyze.AnalyzeMethod;
import com.todaysoft.lims.system.model.vo.analyze.AnalyzeMethodPagingRequest;
import com.todaysoft.lims.system.service.IAnalyzeService;
import com.todaysoft.lims.system.service.impl.OrderService;

@Controller
@RequestMapping(value = "/analyzeMethod")
public class AnalyzeController extends BaseController
{
    @Autowired
    private IAnalyzeService service;
    
    public static final int pageSize = 10;
    
	
	
    
    @RequestMapping("/list.do")
    public String list(AnalyzeMethodPagingRequest searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<AnalyzeMethod> pagination = service.paging(searcher, pageNo, pageSize);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "analyzeMethod/list";
    }
    
    //增加，修改判断
    @RequestMapping(value = "/form.do")
    public String createPage(Integer id, ModelMap model)
    {
        AnalyzeMethod data = new AnalyzeMethod();
        if (id != null)
        {
            data = service.getAnalyzeById(id);
        }
        model.addAttribute("data", data);
        
        return "analyzeMethod/form";
    }
    
    //修改
    @RequestMapping(value = "/modify.do")
    public String modify(AnalyzeMethod request, ModelMap model, HttpSession session)
    {
        service.modify(request);
        return redirectList(model, session, "redirect:/analyzeMethod/list.do");
    }
    
    //增加
    @RequestMapping(value = "/create.do")
    public String create(AnalyzeMethod request, ModelMap model, HttpSession session)
    {
        service.create(request);
        return redirectList(model, session, "redirect:/analyzeMethod/list.do");
    }
    
    //删除
    @ResponseBody
    @RequestMapping(value = "/delete.do")
    public boolean delete(Integer id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return true;
    }
    
    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping("/validate.do")
    public boolean validate(AnalyzeMethod request)
    {
        return service.validate(request);
        
    }
    
    /**
     * 控件获取下拉列表
     * @param request
     * @return
     */
    @RequestMapping("/manageList.do")
    @ResponseBody
    public List<AnalyzeMethod> manageList(AnalyzeMethod request)
    {
        return service.list(request);
    }
    
}
