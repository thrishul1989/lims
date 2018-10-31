package com.todaysoft.lims.system.modules.bpm.dnaext.mvc;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bcm.service.ITestingMethodService;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractAssignModel;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractAssignSheet;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractSheet;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractTask;
import com.todaysoft.lims.system.modules.bpm.dnaext.model.DNAExtractTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dnaext.service.IDNAExtractService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class DNAExtractController extends BaseController
{
    @Autowired
    private IDNAExtractService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestingMethodService testingMethodService;

    private static Logger logger = LoggerFactory.getLogger(DNAExtractController.class);
    
    @RequestMapping("/dna_extract_tasks.do")
    public String dnaExtractTasks(DNAExtractTaskSearcher searcher, ModelMap model)
    {
        List<DNAExtractTask> tasks = service.getAssignableList(searcher);
        
        List<TestingMethod> testingMethodList = testingMethodService.listAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("testingMethodList", testingMethodList);
        model.addAttribute("searcher", searcher);
        return "bpm/process/dna_extract_list";
    }
    
    @RequestMapping("/dna_extract_assign.do")
    @FormInputView
    public String dnaExtractAssign(DNAExtractAssignArgs args, ModelMap model)
    {
        DNAExtractAssignModel data = service.getAssignModel(args);
        model.addAttribute("inputSampleTypeId", args.getSampleType());
        model.addAttribute("data", data);
        return "bpm/assign/dna_extract_assign_form";
    }
    
    @RequestMapping("/dna_extract_assign_submit.do")
    @FormSubmitHandler
    public String dnaExtractAssign(DNAExtractAssignSheet data)
    {
        service.assign(data);
        return "redirect:/testing/dna_extract_tasks.do";
    }
    
    @RequestMapping("/dna_extract_test.do")
    @FormInputView
    public String dnaExtractTest(String id, ModelMap model)
    {
        DNAExtractSheet sheet = service.getSheet(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/dna_extract_test_form";
    }
    
    @RequestMapping("/dna_extract_test_submit.do")
    @FormSubmitHandler
    public String dnaExtractTestSubmit(String id)
    {
        service.submitSheet(id);
        return "redirect:/testing/test_tasks.do ";
    }
    
    @ResponseBody
    @RequestMapping("/downloadCode/dna")
    public String downloadCode(String id)
    {
        Date start = new Date();
        DNAExtractSheet sheet = service.getSheet(id);
        InputStream is = DNAExtractController.class.getResourceAsStream("/taskTemplate/sample_code.xlsx");
        String result = service.downloadCode(is, sheet);
        Date end = new Date();
        long value = (end.getTime() - start.getTime());
        logger.info("文件操作导入导出-DNA提取实验-导出DNA编号-共用时："+value +"毫秒");
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadDnaExtractData", method = RequestMethod.POST)
    public String downloadDnaExtractData(String sheetId)
    {
        Date start = new Date();
        DNAExtractSheet entity = service.getSheet(sheetId);
        InputStream is = DNAExtractController.class.getResourceAsStream("/taskTemplate/DNA_EXTRACT.xlsx");
        String result = service.downloadDnaExtractData(is, entity);
        Date end = new Date();
        long value = (end.getTime() - start.getTime());
        logger.info("文件操作导入导出-DNA提取实验-导出任务单-共用时："+value +"毫秒");
        return result;
    }
}
