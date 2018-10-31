package com.todaysoft.lims.system.modules.bpm.qpcr.mvc;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrAssignRequest;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitModel;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrTask;
import com.todaysoft.lims.system.modules.bpm.qpcr.service.IQpcrService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/testing")
public class QpcrController extends BaseController
{
    @Autowired
    private IQpcrService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/qpcr_tasks.do")
    public String qpcrTasks(QpcrAssignableTaskSearcher searcher, ModelMap model)
    {
        List<QpcrTask> tasks = service.getQpcrAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/qpcr_list";
    }
    
    @RequestMapping("/qpcr_assign.do")
    @FormInputView
    public String getQpcrAssignModel(QpcrAssignArgs args, ModelMap model)
    {
        QpcrAssignModel data = service.getQpcrAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/assign/qpcr_assign_form";
    }
    
    @RequestMapping("/qpcr_assign_submit.do")
    @FormSubmitHandler
    public String assignQpcr(QpcrAssignRequest request)
    {
        service.assignQpcr(request);
        return "redirect:/testing/qpcr_tasks.do";
    }
    
    @RequestMapping("/qpcr_test.do")
    @FormInputView
    public String getQpcrSubmitModel(String id, ModelMap model)
    {
        QpcrSubmitModel sheet = service.getQpcrSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        sheet.setTester(user.getName());
        model.addAttribute("sheet", sheet);
        return "bpm/test/qpcr_test_form";
    }
    
    @RequestMapping("/qpcr_test_submit.do")
    @FormSubmitHandler
    public String submitQpcr(QpcrSubmitRequest request, HttpSession session)
    {
        DataAnalysisParseModel parseResult = (DataAnalysisParseModel)session.getAttribute("QPCR_DATA_RESULT");
        session.removeAttribute("QPCR_DATA_RESULT");
        if(null != parseResult)
        {
            request.setParseModel(parseResult);
        }
        service.submitQpcr(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
