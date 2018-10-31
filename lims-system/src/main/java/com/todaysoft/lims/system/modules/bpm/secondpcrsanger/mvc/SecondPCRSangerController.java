package com.todaysoft.lims.system.modules.bpm.secondpcrsanger.mvc;

import java.util.List;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.service.ISecondPCRSangerService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class SecondPCRSangerController extends BaseController
{
    @Autowired
    private ISecondPCRSangerService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/pcrTwoSangerList.do")
    public String getSecondPCRAssignableList(SecondPCRSangerAssignableTaskSearcher searcher, ModelMap model)
    {
        List<SecondPCRSangerTask> tasks = service.getSecondPCRAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/sangerTest/process/second_pcr_sanger_list";
    }
    
    @RequestMapping("/second_pcr_sanger_assign.do")
    @FormInputView
    public String getSecondPCRAssignModel(SecondPCRSangerAssignArgs args, ModelMap model)
    {
        SecondPCRSangerAssignModel data = service.getSecondPCRAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/sangerTest/assign/second_pcr_sanger_assign_form";
    }
    
    @RequestMapping("/second_pcr_sanger_assign_submit.do")
    @FormSubmitHandler
    public String assignSecondPCR(SecondPCRSangerAssignRequest data)
    {
        service.assignSecondPCR(data);
        return "redirect:/testing/pcrTwoSangerList.do";
    }
    
    @RequestMapping("/second_pcr_sanger_test.do")
    @FormInputView
    public String getSecondPCRSubmitModel(String id, ModelMap model)
    {
        SecondPCRSangerSheetModel sheet = service.getSecondPCRSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/sangerTest/test/second_pcr_sanger_test_form";
    }
    
    @RequestMapping("/second_pcr_sanger_submit.do")
    @FormSubmitHandler
    public String submitSecondPCR(SecondPCRSangerSubmitRequest request)
    {
        service.submitSecondPCR(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
