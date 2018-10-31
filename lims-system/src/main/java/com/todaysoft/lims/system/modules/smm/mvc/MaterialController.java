package com.todaysoft.lims.system.modules.smm.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.TestingConfigSearcher;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.WarningTestingConfigModel;
import com.todaysoft.lims.system.modules.invoice.model.InvoiceSendSearcher;
import com.todaysoft.lims.system.modules.smm.model.MaterialModel;
import com.todaysoft.lims.system.modules.smm.model.MaterialSearcher;
import com.todaysoft.lims.system.modules.smm.service.IMaterialService;
import com.todaysoft.lims.system.modules.smm.service.request.MaterialCreateRequest;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;

@Controller
@RequestMapping("/smm/material")
public class MaterialController extends BaseController
{
    @Autowired
    private IMaterialService service;
    
    @RequestMapping(value = "/tabList.do")
    public String paging(InvoiceSendSearcher searcher, ModelMap model)
    {
        model.addAttribute("searcher", searcher);
        model.addAttribute("flag", "update");
        return "smm/material/material_list_tab";
    }
    
    @RequestMapping(value = "/sortList.do")
    public String sortList(MaterialSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setType(1);//类别
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<MaterialModel> pagination = service.sortPaging(searcher);
       
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "smm/material/material_sort_list";
    }
    
    @RequestMapping(value = "/nameList.do")
    public String nameList(MaterialSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setType(2);
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<MaterialModel> pagination = service.sortPaging(searcher);
       
        model.addAttribute("sortList", service.getSortList());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "smm/material/material_name_list";
    }
    
    @RequestMapping(value = "/sortCreate.do", method = RequestMethod.GET)
    public String createPage(ModelMap model)
    {
        model.addAttribute("type", 1);
        return "smm/material/material_form";
    }
    
    @RequestMapping(value = "/sortCreate.do", method = RequestMethod.POST)
    public String create(MaterialCreateRequest data, ModelMap model, HttpSession session)
    {
        //Map<String, Object> map = new HashMap<String, Object>();
        data.setType(1);
        service.create(data);
        return redirectSortList(model, session);
        //map.put("result", true);
        //return map;
    }
    
    //物资类别唯一性校验
    @ResponseBody
    @RequestMapping(value="/validate.do",method = RequestMethod.POST)
    public boolean validate(MaterialSearcher request)
    {
        boolean flag = service.validate(request);
        return flag;
    }
    
    @RequestMapping(value = "/sortModify.do", method = RequestMethod.GET)
    public String modify(String id, ModelMap model)
    {
        MaterialModel material = service.getById(id);
        model.addAttribute("material", material);
        return "smm/material/material_form";
        
    }
    
    @RequestMapping(value = "/sortModify.do", method = RequestMethod.POST)
    public String modify(MaterialCreateRequest request, ModelMap model, HttpSession session)
    {
        service.modify(request);
        return redirectSortList(model, session);
    }
    
    private String redirectSortList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/smm/material/sortList.do";
    }
    
    @RequestMapping("/sortDelete.do")
    @ResponseBody
    public Integer delete(String id, ModelMap model, HttpSession session)
    {
        Integer a = service.delete(id);
        return a;
    }
    
    @RequestMapping("/stop.do")
    public String stop(String id, ModelMap model, HttpSession session)
    {
        service.setDisable(id);
        return "redirect:/smm/material/sortList.do";
    }
    
    @RequestMapping("/enable.do")
    public String setEnable(String id, ModelMap model, HttpSession session)
    {
        service.setEnable(id);
        return redirectSortList(model, session);
    }
    
    //--------------------------------------物资名称----------------------------------------
    
    @RequestMapping(value = "/nameCreate.do", method = RequestMethod.GET)
    public String createNamePage(ModelMap model)
    {
        model.addAttribute("type", 2);
        model.addAttribute("sortList", service.getSortList());
        return "smm/material/material_name_form";
    }
    
    @RequestMapping(value = "/nameCreate.do", method = RequestMethod.POST)
    public String createName(MaterialCreateRequest data, ModelMap model, HttpSession session)
    {
        data.setType(2);
        service.create(data);
        return redirectNameList(model, session);
    }
    
    @RequestMapping(value = "/nameModify.do", method = RequestMethod.GET)
    public String modifyName(String id, ModelMap model)
    {
        MaterialModel material = service.getById(id);
        model.addAttribute("material", material);
        model.addAttribute("sortList", service.getSortList());
        return "smm/material/material_name_form";
        
    }
    
    @RequestMapping(value = "/nameModify.do", method = RequestMethod.POST)
    public String modifyName(MaterialCreateRequest request, ModelMap model, HttpSession session)
    {
        service.modify(request);
        return redirectNameList(model, session);
    }
    
    @RequestMapping("/nameDelete.do")
    @ResponseBody
    public Integer deleteForName(String id, ModelMap model, HttpSession session)
    {
        Integer a = service.delete(id);
        return a;
    }
    
    @RequestMapping("/stopForName.do")
    public String stopForName(String id, ModelMap model, HttpSession session)
    {
        service.setDisable(id);
        return "redirect:/smm/material/nameList.do";
    }
    
    @RequestMapping("/enableForName.do")
    public String setEnableForName(String id, ModelMap model, HttpSession session)
    {
        service.setEnable(id);
        return redirectNameList(model, session);
    }
    
    private String redirectNameList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/smm/material/nameList.do";
    }
}
