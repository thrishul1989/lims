package com.todaysoft.lims.system.modules.bpm.pcrngsdata.mvc;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataAssignArgs;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataAssignModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataAssignRequest;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataTask;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.service.IPcrNgsDataService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/testing")
public class PcrNgsDataController extends BaseController
{
    @Autowired
    private IPcrNgsDataService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/pcr_ngs_data_analysis_tasks.do")
    public String getDataPcrAssignableList(PcrNgsDataAssignableTaskSearcher searcher, ModelMap model)
    {
        List<PcrNgsDataTask> tasks = service.getDataPcrAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/pcr_ngs/process/pcr_ngs_data_list";
    }
    
    @RequestMapping("/pcr_ngs_data_assign.do")
    @FormInputView
    public String geDataPcrAssignModel(PcrNgsDataAssignArgs args, ModelMap model)
    {
        PcrNgsDataAssignModel data = service.getDataPcrAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/pcr_ngs/assign/pcr_ngs_data_assign_form";
    }
    
    @RequestMapping("/pcr_ngs_data_assign_submit.do")
    @FormSubmitHandler
    public String assignDataPcr(PcrNgsDataAssignRequest data)
    {
        service.assignDataPcr(data);
        return "redirect:/testing/pcr_ngs_data_analysis_tasks.do";
    }
    
    @RequestMapping("/pcr_ngs_data_test.do")
    @FormInputView
    public String getDataPcrSubmitModel(String id, ModelMap model)
    {
        PcrNgsDataSheetModel sheet = service.getDataPcrSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/pcr_ngs/test/pcr_ngs_data_test_form";
    }
    
    @RequestMapping("/pcr_ngs_data_submit.do")
    @FormSubmitHandler
    public String submitDataPcr(PcrNgsDataSubmitRequest request, HttpSession session)
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("PCRNGS_DATA_RESULT");
        session.removeAttribute("PCRNGS_DATA_RESULT");
        if(null != parseResult)
        {
            request.setParseModel(parseResult);
        }
        service.submitDataPcr(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
