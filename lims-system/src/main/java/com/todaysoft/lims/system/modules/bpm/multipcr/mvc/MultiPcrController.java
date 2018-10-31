package com.todaysoft.lims.system.modules.bpm.multipcr.mvc;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.todaysoft.lims.system.modules.bpm.dnaext.mvc.DNAExtractController;
import com.todaysoft.lims.system.modules.bpm.longcre.model.LongcreSubmitSheet;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrAssignTask;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrTask;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrAssignRequest;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrSubmitModel;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrSubmitSheet;
import com.todaysoft.lims.system.modules.bpm.multipcr.service.IMultiPcrService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ITestSheetService;

@Controller
@RequestMapping("/testing")
public class MultiPcrController extends BaseController
{
    @Autowired
    private IMultiPcrService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IProductService productService;
    
    
    
    @ResponseBody
    @RequestMapping(value = "/downloadMultipcrOutput", method = RequestMethod.POST)
    public String downloadOutput(String sheetId)
    {
        MultipcrSubmitModel sheet = service.getMultipcrSubmitModel(sheetId);
        
        InputStream is = DNAExtractController.class.getResourceAsStream("/taskTemplate/multipcr/MULTIPCR_PRINT.xlsx");
        
        return service.downloadOutput(is, sheet);
    }
    
    
    
    
    @ResponseBody
    @RequestMapping(value = "/downloadMultipcr", method = RequestMethod.POST)
    public String downloadMultipcr(String sheetId)
    {
        MultipcrSubmitModel sheet = service.getMultipcrSubmitModel(sheetId);
        
        AuthorizedUser user = userService.getByToken();
        sheet.setPcrTester(user.getName());
        for (MultipcrAssignModel assignModel : sheet.getTasks())
        {
            for (MultiPcrTask task : assignModel.getTasks())
            {
                task.setDnaConcn((BigDecimal)JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("concn"));
                task.setQualityLevel(JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
            }
            
        }
        
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return service.zipFilesMultipcr(zipfile, sheet);
    }
    
    @RequestMapping("/multipcr_task.do")
    public String MultipcrTasks(MultiPcrAssignableTaskSearcher searcher, ModelMap model)
    {
        List<MultiPcrAssignTask> tasks = service.getMultipcrAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/multipcr_list";
    }
    
    @RequestMapping("/multipcr_assign.do")
    @FormInputView
    public String getMultipctAssignModel(MultipcrAssignArgs args, ModelMap model)
    {
        List<MultipcrAssignModel> data = service.getMultipcrAssignModel(args);
        for (MultipcrAssignModel assignModel : data)
        {
            if(assignModel.getTasks().isEmpty()){
                model.addAttribute("result", "该产品尚未配置引物!");//没有引物
                return "bpm/assign/multipcr_assign_form";
            }
            for (MultiPcrTask task : assignModel.getTasks())
            {
                task.setDnaConcn((BigDecimal)JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("concn"));
                task.setQualityLevel(JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
                
            }
        }
        model.addAttribute("data", data);
        model.addAttribute("result", "");
        return "bpm/assign/multipcr_assign_form";
    }
    
    @RequestMapping("/multipcr_assign_submit.do")
    @FormSubmitHandler
    public String assignMultipcr(MultipcrAssignRequest request)
    {
        
        service.assignMultipcr(request);
        return "redirect:/testing/multipcr_task.do";
    }
    
    @RequestMapping("/multipcr_test.do")
    @FormInputView
    public String getMultipcrSubmitModel(String id, ModelMap model)
    {
        MultipcrSubmitModel sheet = service.getMultipcrSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        sheet.setPcrTester(user.getName());
        
        for (MultipcrAssignModel assignModel : sheet.getTasks())
        {
            for (MultiPcrTask task : assignModel.getTasks())
            {
                task.setDnaConcn((BigDecimal)JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("concn"));
                task.setQualityLevel(JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
            }
            
        }
        
        model.addAttribute("sheet", sheet);
        return "bpm/test/multipcr_test_form";
    }
    
    @RequestMapping("/multipcr_test_submit.do")
    @FormSubmitHandler
    public String submitMultipcr(MultipcrSubmitSheet request)
    {
        
        service.submitMultipcr(request);
        return "redirect:/testing/test_tasks.do";
    }
    
}
