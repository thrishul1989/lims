package com.todaysoft.lims.system.modules.bpm.dnaqc.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcAssignModel;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcAssignSheet;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcTask;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dnaqc.service.IDNAQcService;
import com.todaysoft.lims.system.modules.bpm.model.test.args.TestArgs;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class DNAQcController extends BaseController
{
    @Autowired
    private IDNAQcService service;
    
    @Autowired
    private IUserService userService;
    
    //DNA质检提取待办事项
    @RequestMapping("/dna_qc_tasks.do")
    public String dnaQcTasks(DNAQcTaskSearcher searcher, ModelMap model)
    {
        List<DNAQcTask> tasks = service.dnaQcTasks(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/dna_qc_list";
    }
    
    //DNA质检下达页面
    @RequestMapping("/dna_qc_assign.do")
    @FormInputView
    public String dnaQcAssign(DNAQcAssignArgs args, ModelMap model)
    {
        DNAQcAssignModel data = service.getDNAQcAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/assign/dna_qc_assign_form";
    }
    
    //DNA质检任务下达
    @RequestMapping("/dna_qc_assign_submit.do")
    @FormSubmitHandler
    public String dnaQcAssign(DNAQcAssignSheet data)
    {
        service.dnaQcAssign(data);
        return "redirect:/testing/dna_qc_tasks.do";
    }
    
    //DNA质检实验页面
    @RequestMapping("/dna_qc_test.do")
    @FormInputView
    public String dnaQcTest(TestArgs args, ModelMap model)
    {
        DNAQcSheet sheet = service.getDNAQcTestModel(args);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/dna_qc_test_form";
    }
    
    //DNA质检实验提交
    @RequestMapping("/dna_qc_test_submit.do")
    @FormSubmitHandler
    public String dnaQcTestSubmit(DNAQcSubmitSheetModel request)
    {
        service.submitSheet(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
