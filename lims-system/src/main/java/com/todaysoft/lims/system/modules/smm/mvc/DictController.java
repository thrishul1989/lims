package com.todaysoft.lims.system.modules.smm.mvc;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.Dict;
import com.todaysoft.lims.system.modules.smm.model.DictSearcher;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;

@Controller
@RequestMapping("/smm/dict")
public class DictController extends BaseController
{
    @Autowired
    private IDictService service;
    
    @RequestMapping("/list.do")
    public String list(DictSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<Dict> pagination = service.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "smm/dict/dict_list";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        Dict data = service.get(id);
        model.addAttribute("data", data);
        return "smm/dict/dict_form";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(Dict request, ModelMap model, HttpSession session)
    {
        service.modify(request);
        return redirectList(model, session);
    }
    
    @RequestMapping("/display.do")
    public String display(String id, ModelMap model)
    {
        Dict dict = service.get(id);
        model.addAttribute("dict", dict);
        return "smm/dict/dict_details";
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/smm/dict/list.do";
    }
    
    @RequestMapping("/getEntries/{category}")
    @ResponseBody
    private List<Dict> getEntries(@PathVariable String category)
    {
        return service.getEntries(category);
    }
}
