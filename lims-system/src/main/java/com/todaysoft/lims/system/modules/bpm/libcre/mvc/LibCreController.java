package com.todaysoft.lims.system.modules.bpm.libcre.mvc;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.dnaext.mvc.DNAExtractController;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibCreAssignArgs;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibCreAssignModel;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibCreTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateAssignRequest;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateSheet;
import com.todaysoft.lims.system.modules.bpm.libcre.model.LibraryCreateTask;
import com.todaysoft.lims.system.modules.bpm.libcre.service.ILibCreService;
import com.todaysoft.lims.system.modules.bpm.longqc.model.LongqcTestSheet;
import com.todaysoft.lims.system.modules.bpm.model.test.args.TestArgs;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class LibCreController extends BaseController
{
    @Autowired
    private ILibCreService service;
    
    @Autowired
    private IUserService userService;
    
    //文库创建待办事项
    @RequestMapping("/wk_create_tasks.do")
    public String libCreTasks(LibCreTaskSearcher searcher, ModelMap model)
    {
        List<LibraryCreateTask> tasks = service.libCreTasks(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/lib_create_list";
    }
    
    //文库创建下达页面
    @RequestMapping("/wk_create_assign.do")
    @FormInputView
    public String libCreAssign(LibCreAssignArgs args, ModelMap model)
    {
        LibCreAssignModel data = service.getLibCreAssignModel(args);
        model.addAttribute("inputSampleTypeId", args.getSampleType());
        model.addAttribute("data", data);
        return "bpm/assign/lib_create_assign_form";
    }
    
    //文库创建任务下达
    @RequestMapping("/lib_create_assign_submit.do")
    @FormSubmitHandler
    public String libCreAssign(LibraryCreateAssignRequest data)
    {
        service.libCreAssign(data);
        return "redirect:/testing/wk_create_tasks.do";
    }
    
    //文库创建实验页面
    @RequestMapping("/lib_create_test.do")
    @FormInputView
    public String libCreTest(TestArgs args, ModelMap model)
    {
        LibraryCreateSheet sheet = service.getSheet(args);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/lib_create_test_form";
    }
    
    //文库创建实验提交
    @RequestMapping("/lib_create_test_submit.do")
    @FormSubmitHandler
    public String dnaQcTestSubmit(String id)
    {
        service.submitSheet(id);
        return "redirect:/testing/test_tasks.do ";
    }
    
    @ResponseBody
    @RequestMapping("/downloadCode/library")
    public String downloadCode(TestArgs args)
    {
        LibraryCreateSheet sheet = service.getSheet(args);
        InputStream is = LibCreController.class.getResourceAsStream("/taskTemplate/sample_code.xlsx");
        return service.downloadCode(is, sheet);
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadLibCre", method = RequestMethod.POST)
    public String downloadLongqc(String sheetId)
    {
        
        TestArgs args = new TestArgs();
        args.setId(sheetId);
        LibraryCreateSheet sheet = service.getSheet(args);
        
        AuthorizedUser user = userService.getByToken();
        sheet.setTesterName(user.getName());
        
        InputStream is = LibCreController.class.getResourceAsStream("/taskTemplate/LIBCRE_TASK.xlsx");
        
        return service.download(is, sheet);
    }
    
}
