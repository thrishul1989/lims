package com.todaysoft.lims.system.modules.bpm.secondpcr.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRAssignArgs;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRAssignModel;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRAssignRequest;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRTask;
import com.todaysoft.lims.system.modules.bpm.secondpcr.service.ISecondPCRService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class SecondPCRController extends BaseController
{
    @Autowired
    private ISecondPCRService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/pcrTwoList.do")
    public String getSecondPCRAssignableList(SecondPCRAssignableTaskSearcher searcher, ModelMap model)
    {
        List<SecondPCRTask> tasks = service.getSecondPCRAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/sanger/process/second_pcr_list";
    }
    
    @RequestMapping("/second_pcr_assign.do")
    @FormInputView
    public String getSecondPCRAssignModel(SecondPCRAssignArgs args, ModelMap model)
    {
        SecondPCRAssignModel data = service.getSecondPCRAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/sanger/assign/second_pcr_assign_form";
    }
    
    @RequestMapping("/second_pcr_assign_submit.do")
    @FormSubmitHandler
    public String assignSecondPCR(SecondPCRAssignRequest data)
    {
        service.assignSecondPCR(data);
        return "redirect:/testing/pcrTwoList.do";
    }
    
    @RequestMapping("/second_pcr_test.do")
    @FormInputView
    public String getSecondPCRSubmitModel(String id, ModelMap model)
    {
        SecondPCRSheetModel sheet = service.getSecondPCRSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/sanger/test/second_pcr_test_form";
    }
    
    @RequestMapping("/second_pcr_submit.do")
    @FormSubmitHandler
    public String submitSecondPCR(SecondPCRSubmitRequest request)
    {
        service.submitSecondPCR(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
