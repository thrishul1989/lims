package com.todaysoft.lims.system.modules.bpm.pcrngstest.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestAssignArgs;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestAssignModel;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestAssignRequest;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestTask;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.service.IPcrNgsTestService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class PcrNgsTestController extends BaseController
{
    @Autowired
    private IPcrNgsTestService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/pcr_ngs_test_tasks.do")
    public String getPcrNgsTestAssignableList(PcrNgsTestAssignableTaskSearcher searcher, ModelMap model)
    {
        List<PcrNgsTestTask> tasks = service.getPcrNgsTestAssignableList(searcher);
        
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        
        return "bpm/pcr_ngs/process/pcr_ngs_test_list";
    }
    
    @RequestMapping("/pcr_ngs_test_assign.do")
    @FormInputView
    public String getPcrNgsTestAssignModel(PcrNgsTestAssignArgs args, ModelMap model)
    {
        PcrNgsTestAssignModel data = service.getPcrNgsTestAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/pcr_ngs/assign/pcr_ngs_test_assign_form";
    }
    
    @RequestMapping("/pcr_ngs_test_assign_submit.do")
    @FormSubmitHandler
    public String assignPcrNgsTest(PcrNgsTestAssignRequest data)
    {
        service.assignPcrNgsTest(data);
        return "redirect:/testing/pcr_ngs_test_tasks.do";
    }
    
    @RequestMapping("/pcr_ngs_test.do")
    @FormInputView
    public String getPcrNgsTestSubmitModel(String id, ModelMap model)
    {
        PcrNgsTestSheetModel sheet = service.getPcrNgsTestSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/pcr_ngs/test/pcr_ngs_test_form";
    }
    
    @RequestMapping("/pcr_ngs_test_submit.do")
    @FormSubmitHandler
    public String submitPcrNgsTest(PcrNgsTestSubmitRequest request)
    {
        service.submitPcrNgsTest(request);
        return "redirect:/testing/test_tasks.do ";
    }
    
}
