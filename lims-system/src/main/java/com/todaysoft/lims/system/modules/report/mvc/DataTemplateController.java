package com.todaysoft.lims.system.modules.report.mvc;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.model.DataTemplateColumn;
import com.todaysoft.lims.system.modules.report.service.IDataTemplateService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/data/dataTemplate")
public class DataTemplateController extends BaseController
{
    @Autowired
    private IDataTemplateService service;
    
    @RequestMapping(value = "/list.do")
    public String paging(DataTemplate searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<DataTemplate> pagination = service.paging(searcher);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "report/dataTemplate/dataTemplate_list";
    }
    
    @RequestMapping(value = "/view.do")
    public String view(String id, ModelMap model)
    {
        DataTemplate data = service.get(id);
        model.addAttribute("data", data);
        return "report/dataTemplate/dataTemplate_view";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    @FormInputView
    public String createForward(ModelMap model)
    {
        List<TestingMethod> list = service.getTestingMethodList();
        model.addAttribute("testingMethodList", list);
        return "report/dataTemplate/dataTemplate_form";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(DataTemplate dataTemplate, ModelMap model, HttpSession session)
    {
        
        service.create(dataTemplate);
        return redirectList(model, session, "redirect:/data/dataTemplate/list.do");
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    @FormInputView
    public String modifyForward(String id, ModelMap model)
    {
        DataTemplate data = service.get(id);
        model.addAttribute("data", data);
        List<TestingMethod> list = service.getTestingMethodList();
        model.addAttribute("testingMethodList", list);
        return "report/dataTemplate/dataTemplate_form";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(DataTemplate dataTemplate, ModelMap model, HttpSession session)
    {
        service.modify(dataTemplate);
        return redirectList(model, session, "redirect:/data/dataTemplate/list.do");
    }
    
    @RequestMapping(value = "/delete.do")
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session, "redirect:/data/dataTemplate/list.do");
    }
    
    @ResponseBody
    @RequestMapping(value = "/validate.do", method = RequestMethod.POST)
    public boolean validate(DataTemplate request)
    {
        return service.validate(request);
    }
    
    @RequestMapping("/selectDataTemplate.do")
    @ResponseBody
    public List<DataTemplate> dataTemplateList(String key)
    {
        DataTemplate searcher = new DataTemplate();
        searcher.setName(key);
        searcher.setPageNo(1);
        searcher.setPageSize(10);
        return service.dataTemplateList(searcher);
    }
    
    @RequestMapping(value = "/getDataTemplate.do")
    @ResponseBody
    public DataTemplate getDataTemplate(String id)
    {
        return service.get(id);
    }
    
    @RequestMapping("/dataTemplColumnList.do")
    @ResponseBody
    public List<DataTemplateColumn> dataTemplateColumnList(DataTemplateColumn request)
    {
        if (StringUtils.isNotEmpty(request.getDataTemplateId()))
        {
            DataTemplate dt = service.get(request.getDataTemplateId());
            request.setTemplate(dt);
        }
        List<DataTemplateColumn> list = service.dataTemplateColumnList(request);
        return list;
    }
    
    @RequestMapping("/dataTemplateList.do")
    @ResponseBody
    public List<DataTemplate> dataTemplateLists(String testingMethodId)
    {
        return service.dataTemplateList(testingMethodId);
    }
    
    @RequestMapping("/validateTestingMethod")
    @ResponseBody
    public boolean validateTestingMethod(String testingMethodId)
    {
        return service.validateTestingMethod(testingMethodId);
    }
}
