package com.todaysoft.lims.system.modules.bpm.fluotest.mvc;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignArgs;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignModel;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignRequest;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignTask;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestSubmitModel;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestTask;
import com.todaysoft.lims.system.modules.bpm.fluotest.service.IFluoTestService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.TestingSheetController;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ITestSheetService;

@Controller
@RequestMapping("/testing")
public class FluoTestController extends BaseController
{
    
    @Autowired
    private IFluoTestService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IProductService productService;
    
    @RequestMapping("/fluo_test_task.do")
    public String fluoTestTasks(FluoTestAssignableTaskSearcher searcher, ModelMap model)
    {
        List<FluoTestAssignTask> tasks = service.getfluoTestAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/fluotest_list";
    }
    
    @RequestMapping("/fluo_test_assign.do")
    @FormInputView
    public String getFluoTestAssignModel(FluoTestAssignArgs args, ModelMap model)
    {
        FluoTestAssignModel data = service.getFluoTestAssignModel(args);
        for (FluoTestTask assignModel : data.getTasks())
        {
            
            assignModel.setDnaConcn((BigDecimal)JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("concn"));
            assignModel.setQualityLevel(JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
            
        }
        model.addAttribute("data", data);
        return "bpm/assign/fluotest_assign_form";
    }
    
    @RequestMapping("/fluo_test_assign_submit.do")
    @FormSubmitHandler
    public String assignFluoTest(FluoTestAssignRequest request)
    {
        service.assignFluoTest(request);
        return "redirect:/testing/fluo_test_task.do";
    }
    
    @RequestMapping("/fluo_test_test.do")
    @FormInputView
    public String getFluoTestSubmitModel(String id, ModelMap model)
    {
        FluoTestSubmitModel sheet = service.getFluoTestSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        sheet.setTester(user.getName());
        
        for (FluoTestTask assignModel : sheet.getTasks())
        {
            assignModel.setDnaConcn((BigDecimal)JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("concn"));
            assignModel.setQualityLevel(JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
            
        }
        model.addAttribute("sheet", sheet);
        
        sheet.getTasks().sort((x, y) -> x.getTestingCode().compareTo(y.getTestingCode()));
        
        return "bpm/test/fluotest_test_form";
    }
    
    @RequestMapping("/fluo_test_test_submit.do")
    @FormSubmitHandler
    public String submitFluoTest(FluoTestSubmitModel request)
    {
        service.submitFluoTest(request);
        return "redirect:/testing/test_tasks.do ";
    }
    
    @ResponseBody
    @RequestMapping(value = "/fluo/downloadFluoTestData", method = RequestMethod.POST)
    public String downloadFluoTestData(String sheetId)
    {
        FluoTestSubmitModel sheet = service.getFluoTestSubmitModel(sheetId);
        for (FluoTestTask assignModel : sheet.getTasks())
        {
            setAttr(assignModel);
        }
        sheet.getTasks().sort((x, y) -> x.getTestingCode().compareTo(y.getTestingCode()));
        InputStream is = TestingSheetController.class.getResourceAsStream("/taskTemplate/fluo/FLUO_TEST.xlsx");
        return service.downloadFluoTestData(is, sheet);
    }
    
    private void setAttr(FluoTestTask assignModel)
    {
        assignModel.setDnaConcn((BigDecimal)JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("concn"));
        assignModel.setQualityLevel(JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
    }
}
