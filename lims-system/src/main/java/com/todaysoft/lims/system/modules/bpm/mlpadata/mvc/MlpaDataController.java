package com.todaysoft.lims.system.modules.bpm.mlpadata.mvc;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.*;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/testing")
public class MlpaDataController extends BaseController
{
    @Autowired
    private IMlpaDataService service;
    
    @Autowired
    private IUserService userService;

    @Autowired
    private IMlpaDataDataService dataService;
    
    @RequestMapping("/mlpa_data_tasks.do")
    public String mlpaDataTasks(MlpaDataAssignableTaskSearcher searcher, ModelMap model)
    {
        List<MlpaDataTask> tasks = service.getMlpaDataAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/mlpa_data_list";
    }
    
    @RequestMapping("/mlpaData_assign.do")
    @FormInputView
    public String getMlpaDataAssignModel(MlpaDataAssignArgs args, ModelMap model)
    {
        List<MlpaDataTask> modelTasks = service.getMlpaDataAssignModel(args);
        model.addAttribute("modelTasks", modelTasks);
        return "bpm/assign/mlpa_data_assign_form";
    }
    
    @RequestMapping("/mlpa_data_assign_submit.do")
    @FormSubmitHandler
    public String assignMlpaData(MlpaDataAssignRequest data)
    {
        service.assignMlpaData(data);
        return "redirect:/testing/mlpa_data_tasks.do";
    }
    
    @RequestMapping("/mlpa_data_test.do")
    @FormInputView
    public String getMlpaDataSubmitModel(String id, ModelMap model)
    {
        MlpaDataSheetModel sheet = service.getMlpaDataSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/mlpa_data_test_form";
    }
    
    @RequestMapping("/mlpa_data_submit.do")
    @FormSubmitHandler
    public String mlpaDataTestSubmit(MlpaDataSubmitSheetModel request,HttpSession session)
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("MLPA_DATA_RESULT");
        session.removeAttribute("MLPA_DATA_RESULT");
        if(null != parseResult)
        {
            request.setParseModel(parseResult);
        }
        service.submitSheet(request);
        return "redirect:/testing/test_tasks.do ";
    }
    
}
