package com.todaysoft.lims.system.modules.bpm.dt.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTAssignModel;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTAssignRequest;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTSubmitModel;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTTask;
import com.todaysoft.lims.system.modules.bpm.dt.service.IDTService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class DTController extends BaseController
{
    @Autowired
    private IDTService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/dt_tasks.do")
    public String mlpaTasks(DTAssignableTaskSearcher searcher, ModelMap model)
    {
        List<DTTask> tasks = service.getDTAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/dt_list";
    }
    
    @RequestMapping("/dt_assign.do")
    @FormInputView
    public String getMlpaAssignModel(DTAssignArgs args, ModelMap model)
    {
        DTAssignModel modelTasks = service.getDTAssignModel(args);
        model.addAttribute("data", modelTasks);
        return "bpm/assign/dt_assign_form";
    }
    
    @RequestMapping("/dt_assign_submit.do")
    @FormSubmitHandler
    public String assignMlpa(DTAssignRequest request)
    {
        service.assignDT(request);
        return "redirect:/testing/dt_tasks.do";
    }
    
    @RequestMapping("/dt_test.do")
    @FormInputView
    public String getMlpaSubmitModel(String id, ModelMap model)
    {
        DTSubmitModel sheet = service.getDTSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        sheet.setTester(user.getName());
        model.addAttribute("sheet", sheet);
        return "bpm/test/dt_test_form";
    }
    
    @RequestMapping("/dt_test_submit.do")
    @FormSubmitHandler
    public String submitMlpa(DTSubmitModel request)
    {
        service.submitDT(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
