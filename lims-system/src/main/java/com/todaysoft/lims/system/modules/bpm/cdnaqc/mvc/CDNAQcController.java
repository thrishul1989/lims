package com.todaysoft.lims.system.modules.bpm.cdnaqc.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcAssignArgs;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcAssignModel;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcAssignSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcTask;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.service.ICDNAQcService;
import com.todaysoft.lims.system.modules.bpm.model.test.args.TestArgs;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class CDNAQcController extends BaseController
{
    @Autowired
    private ICDNAQcService service;
    
    @Autowired
    private IUserService userService;
    
    //DNA质检提取待办事项
    @RequestMapping("/cdna_qc_tasks.do")
    public String cdnaQcTasks(CDNAQcTaskSearcher searcher, ModelMap model)
    {
        List<CDNAQcTask> tasks = service.cdnaQcTasks(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/cdna_qc_list";
    }
    
    //DNA质检下达页面
    @RequestMapping("/cdna_qc_assign.do")
    @FormInputView
    public String cdnaQcAssign(CDNAQcAssignArgs args, ModelMap model)
    {
        CDNAQcAssignModel data = service.getCDNAQcAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/assign/cdna_qc_assign_form";
    }
    
    //DNA质检任务下达
    @RequestMapping("/cdna_qc_assign_submit.do")
    @FormSubmitHandler
    public String cdnaQcAssign(CDNAQcAssignSheet data)
    {
        service.cdnaQcAssign(data);
        return "redirect:/testing/cdna_qc_tasks.do";
    }
    
    //DNA质检实验页面
    @RequestMapping("/cdna_qc_test.do")
    @FormInputView
    public String cdnaQcTest(TestArgs args, ModelMap model)
    {
        CDNAQcSheet sheet = service.getCDNAQcTestModel(args);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/cdna_qc_test_form";
    }
    
    //DNA质检实验提交
    @RequestMapping("/cdna_qc_test_submit.do")
    @FormSubmitHandler
    public String cdnaQcTestSubmit(CDNAQcSubmitSheetModel request)
    {
        service.submitSheet(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
