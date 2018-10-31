package com.todaysoft.lims.system.modules.bpm.cdnaext.mvc;

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
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractAssignArgs;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractAssignModel;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractAssignSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractTask;
import com.todaysoft.lims.system.modules.bpm.cdnaext.model.CDNAExtractTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.cdnaext.service.ICDNAExtractService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class CDNAExtractController extends BaseController
{
    @Autowired
    private ICDNAExtractService service;
    
    @Autowired
    private IUserService userService;
    
    //CDNA提取待办事项
    @RequestMapping("/cdna_extract_tasks.do")
    public String cdnaExtractTasks(CDNAExtractTaskSearcher searcher, ModelMap model)
    {
        List<CDNAExtractTask> tasks = service.getAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/cdna_extract_list";
    }
    
    //CDNA提取下达页面
    @RequestMapping("/cdna_extract_assign.do")
    @FormInputView
    public String cdnaExtractAssign(CDNAExtractAssignArgs args, ModelMap model)
    {
        CDNAExtractAssignModel data = service.getAssignModel(args);
        model.addAttribute("inputSampleTypeId", args.getSampleType());
        model.addAttribute("data", data);
        return "bpm/assign/cdna_extract_assign_form";
    }
    
    @RequestMapping("/cdna_extract_assign_submit.do")
    @FormSubmitHandler
    public String cdnaExtractAssign(CDNAExtractAssignSheet data)
    {
        service.assign(data);
        return "redirect:/testing/cdna_extract_tasks.do";
    }
    
    @RequestMapping("/cdna_extract_test.do")
    @FormInputView
    public String cdnaExtractTest(String id, ModelMap model)
    {
        CDNAExtractSheet sheet = service.getSheet(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/cdna_extract_test_form";
    }
    
    @RequestMapping("/cdna_extract_test_submit.do")
    @FormSubmitHandler
    public String cdnaExtractTestSubmit(String id)
    {
        service.submitSheet(id);
        return "redirect:/testing/test_tasks.do ";
    }
    
    @ResponseBody
    @RequestMapping("/downloadCode/cdna")
    public String downloadCode(String id)
    {
        CDNAExtractSheet sheet = service.getSheet(id);
        InputStream is = CDNAExtractController.class.getResourceAsStream("/taskTemplate/sample_code.xlsx");
        return service.downloadCode(is, sheet);
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadCdnaExtractData", method = RequestMethod.POST)
    public String downloadCdnaExtractData(String sheetId)
    {
        CDNAExtractSheet entity = service.getSheet(sheetId);
        InputStream is = CDNAExtractController.class.getResourceAsStream("/taskTemplate/CDNA_EXTRACT.xlsx");
        return service.downloadCdnaExtractData(is, entity);
        
    }
}