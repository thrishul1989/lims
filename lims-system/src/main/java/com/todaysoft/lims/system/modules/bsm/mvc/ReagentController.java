package com.todaysoft.lims.system.modules.bsm.mvc;

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

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Reagent;
import com.todaysoft.lims.system.modules.bsm.model.ReagentSeacher;
import com.todaysoft.lims.system.modules.bsm.service.IReagentService;
import com.todaysoft.lims.system.modules.bsm.service.request.ReagentCreateRequest;
import com.todaysoft.lims.system.modules.bsm.service.request.ReagentModifyRequest;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping(value = "/reagent")
public class ReagentController extends BaseController
{
    @Autowired
    private IReagentService reagentService;
    
    @RequestMapping(value = "/list.do")
    public String getMaterialList(ReagentSeacher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<Reagent> pagination = reagentService.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "equipment/reagent_list";
    }
    
    @ResponseBody
    @RequestMapping(value = "/unique.do")
    public boolean unique(String id, String code)
    {
        return reagentService.unique(id, code);
    }
    
    //增加 修改判断 
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        return "equipment/reagent_form";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(ReagentCreateRequest request)
    {
        reagentService.create(request);
        return "redirect:/reagent/list.do";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    @FormInputView
    public String checkedMaterial(String id, ModelMap model)
    {
        Reagent m = reagentService.get(id);
        model.addAttribute("material", m);
        return "equipment/reagent_form";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(ReagentModifyRequest request)
    {
        reagentService.modify(request);
        return "redirect:/reagent/list.do";
    }
    
    @RequestMapping(value = "/delete.do")
    public String delete(String id)
    {
        reagentService.delete(id);
        return "redirect:/reagent/list.do";
    }
    
    @RequestMapping(value = "/display.do")
    public String viewMaterial(String id, ModelMap model)
    {
        Reagent data = new Reagent();
        data.setId(id);
        Reagent m = reagentService.get(id);
        model.addAttribute("material", m);
        return "equipment/reagent_view";
    }
    
    /**
     * 模糊试剂多选
     * */
    @RequestMapping(value = "/reagentSelect.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Reagent> reagentSelectForMul(ReagentSeacher searcher)
    {
        Pagination<Reagent> pagination = reagentService.paging(searcher, 1, 10);
        return pagination.getRecords();
    }
    
    /**
     * 模糊试剂单选
     * */
    @RequestMapping(value = "/reagentSelectForSingle.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> reagentSelectForSingle(ReagentSeacher searcher)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "");
        Pagination<Reagent> pagination = reagentService.paging(searcher, 1, 10);
        map.put("value", pagination.getRecords());
        return map;
    }
}
