package com.todaysoft.lims.system.modules.bpm.dpcr.mvc;

import java.util.List;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrAssignRequest;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSheetModel;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrTask;
import com.todaysoft.lims.system.modules.bpm.dpcr.service.IDataPcrService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/testing")
public class DataPcrController extends BaseController
{
    @Autowired
    private IDataPcrService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/dataAnalysisList.do")
    public String getDataPcrAssignableList(DataPcrAssignableTaskSearcher searcher, ModelMap model)
    {
        List<DataPcrTask> tasks = service.getDataPcrAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/sanger/process/data_pcr_list";
    }
    
    @RequestMapping("/data_pcr_assign.do")
    @FormInputView
    public String geDataPcrAssignModel(DataPcrAssignArgs args, ModelMap model)
    {
        DataPcrAssignModel data = service.getDataPcrAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/sanger/assign/data_pcr_assign_form";
    }
    
    @RequestMapping("/data_pcr_assign_submit.do")
    @FormSubmitHandler
    public String assignDataPcr(DataPcrAssignRequest data)
    {
        service.assignDataPcr(data);
        return "redirect:/testing/dataAnalysisList.do";
    }
    
    @RequestMapping("/data_pcr_test.do")
    @FormInputView
    public String getDataPcrSubmitModel(String id, ModelMap model)
    {
        DataPcrSheetModel sheet = service.getDataPcrSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/sanger/test/data_pcr_test_form";
    }
    
    @RequestMapping("/data_pcr_submit.do")
    @FormSubmitHandler
    public String submitDataPcr(DataPcrSubmitRequest request, HttpSession session)
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("DPCR_DATA_RESULT");
        session.removeAttribute("DPCR_DATA_RESULT");
        if(null != parseResult)
        {
            request.setParseModel(parseResult);
        }
        service.submitDataPcr(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
