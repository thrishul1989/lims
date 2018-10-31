package com.todaysoft.lims.system.modules.bpm.dpcrsanger.mvc;

import java.util.List;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.*;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.service.IDataPcrSangerService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Controller
@RequestMapping("/testing")
public class DataPcrSangerController extends BaseController
{
    @Autowired
    private IDataPcrSangerService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/dataAnalysisSangerList.do")
    public String getDataPcrAssignableList(DataPcrSangerAssignableTaskSearcher searcher, ModelMap model)
    {
        List<DataPcrSangerTask> tasks = service.getDataPcrAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/sangerTest/process/data_pcr_sanger_list";
    }
    
    @RequestMapping("/data_pcr_sanger_assign.do")
    @FormInputView
    public String geDataPcrAssignModel(DataPcrSangerAssignArgs args, ModelMap model)
    {
        DataPcrSangerAssignModel data = service.getDataPcrAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/sangerTest/assign/data_pcr_sanger_assign_form";
    }
    
    @RequestMapping("/data_pcr_sanger_assign_submit.do")
    @FormSubmitHandler
    public String assignDataPcr(DataPcrSangerAssignRequest data)
    {
        service.assignDataPcr(data);
        return "redirect:/testing/dataAnalysisSangerList.do";
    }
    
    @RequestMapping("/data_pcr_sanger_test.do")
    @FormInputView
    public String getDataPcrSubmitModel(String id, ModelMap model)
    {
        DataPcrSangerSheetModel sheet = service.getDataPcrSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/sangerTest/test/data_pcr_sanger_test_form";
    }
    
    @RequestMapping("/data_pcr_sanger_submit.do")
    @FormSubmitHandler
    public String submitDataPcr(DataPcrSangerSubmitRequest request,HttpSession session)
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("DPCR_SANGER_DATA_RESULT");
        session.removeAttribute("DPCR_DATA_RESULT");
        if(null != parseResult)
        {
            request.setParseModel(parseResult);
        }
        service.submitDataPcr(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
