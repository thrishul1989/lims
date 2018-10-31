package com.todaysoft.lims.system.modules.bpm.dtdata.mvc;

import java.util.List;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.*;
import com.todaysoft.lims.system.modules.bpm.dtdata.service.IDTDataService;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/testing")
public class DTDataController extends BaseController
{
    @Autowired
    private IDTDataService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/dtDataAnalysisList.do")
    public String getDataPcrAssignableList(DTDataAssignableTaskSearcher searcher, ModelMap model)
    {
        List<DTDataTask> tasks = service.getDataPcrAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/dt_data_list";
    }
    
    @RequestMapping("/dt_data_assign.do")
    @FormInputView
    public String geDataPcrAssignModel(DTDataAssignArgs args, ModelMap model)
    {
        DTDataAssignModel data = service.getDataPcrAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/assign/dt_data_assign_form";
    }
    
    @RequestMapping("/dt_data_assign_submit.do")
    @FormSubmitHandler
    public String assignDataPcr(DTDataAssignRequest data)
    {
        service.assignDataPcr(data);
        return "redirect:/testing/dtDataAnalysisList.do";
    }
    
    @RequestMapping("/dt_data_test.do")
    @FormInputView
    public String getDataPcrSubmitModel(String id, ModelMap model)
    {
        DTDataSheetModel sheet = service.getDataPcrSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/dt_data_test_form";
    }
    
    @RequestMapping("/dt_data_submit.do")
    @FormSubmitHandler
    public String submitDataPcr(DTDataSubmitRequest request,HttpSession session)
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("DT_DATA_RESULT");
        session.removeAttribute("DT_DATA_RESULT");
        if(null != parseResult)
        {
            request.setParseModel(parseResult);
        }
        service.submitDataPcr(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
