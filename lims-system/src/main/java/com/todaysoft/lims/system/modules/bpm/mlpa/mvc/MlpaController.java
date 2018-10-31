package com.todaysoft.lims.system.modules.bpm.mlpa.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignArgs;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignModel;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignRequest;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestSubmitModel;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestTask;
import com.todaysoft.lims.system.modules.bpm.mlpa.service.IMlpaService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class MlpaController extends BaseController
{
    @Autowired
    private IMlpaService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/mlpa_tasks.do")
    public String mlpaTasks(MlpaTestAssignableTaskSearcher searcher, ModelMap model)
    {
        List<MlpaTestTask> tasks = service.getMlpaAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/mlpa_list";
    }
    
    @RequestMapping("/mlpa_assign.do")
    @FormInputView
    public String getMlpaAssignModel(MlpaTestAssignArgs args, ModelMap model)
    {
        MlpaTestAssignModel modelTasks = service.getMlpaAssignModel(args);
        model.addAttribute("modelTasks", modelTasks);
        return "bpm/assign/mlpa_assign_form";
    }
    
    @RequestMapping("/mlpa_assign_submit.do")
    @FormSubmitHandler
    public String assignMlpa(MlpaTestAssignRequest request)
    {
        service.assignMlpa(request);
        return "redirect:/testing/mlpa_tasks.do";
    }
    
    @RequestMapping("/mlpa_test.do")
    @FormInputView
    public String getMlpaSubmitModel(String id, ModelMap model)
    {
        MlpaTestSubmitModel sheet = service.getMlpaSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        sheet.setTester(user.getName());
        model.addAttribute("sheet", sheet);
        return "bpm/test/mlpa_test_form";
    }
    
    @RequestMapping("/mlpa_test_submit.do")
    @FormSubmitHandler
    public String submitMlpa(MlpaTestSubmitModel request)
    {
        service.submitMlpa(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
