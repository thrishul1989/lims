package com.todaysoft.lims.system.modules.bpm.longpcr.mvc;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrAssignTask;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignRequest;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrSubmitContent;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrSubmitModel;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrSubmitRequestExcel;
import com.todaysoft.lims.system.modules.bpm.longpcr.service.ILongPcrService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IProductService;
import com.todaysoft.lims.system.service.ITestSheetService;

@Controller
@RequestMapping("/testing")
public class LongPcrController extends BaseController
{
    @Autowired
    private ILongPcrService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITestSheetService testSheetService;
    
    @Autowired
    private IProductService productService;
    
    @ResponseBody
    @RequestMapping(value = "/uploadLongpcr", method = RequestMethod.POST)
    public List<LongpcrSubmitRequestExcel> uploadLongpcr(@RequestParam MultipartFile uploadData, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException
    {
        File file = productService.upload(request, response, model);
        List<LongpcrSubmitRequestExcel> list = service.uploadData(file);
        return list;
    }
    
    @ResponseBody
    @RequestMapping(value = "/downloadLongpcr", method = RequestMethod.POST)
    public String downloadLongpcr(String sheetId)
    {
        LongpcrSubmitModel sheet = service.getLongpcrSubmitModel(sheetId);
        
        AuthorizedUser user = userService.getByToken();
        sheet.setPcrTester(user.getName());
        
        for (LongpcrAssignModel assignModel : sheet.getTasks())
        {
            for (LongPcrTask task : assignModel.getTasks())
            {
                task.setDnaConcn((BigDecimal)JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("concn"));
                task.setQualityLevel(JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
            }
            
        }
        
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormater.format(new Date());
        File zipfile = new File(time + "_" + sheet.getCode() + ".zip");
        return service.zipFilesLongpcr(zipfile, sheet);
    }
    
    @RequestMapping("/longpcr_task.do")
    public String longpcrTasks(LongPcrAssignableTaskSearcher searcher, ModelMap model)
    {
        List<LongPcrAssignTask> tasks = service.getLongpcrAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/longpcr_list";
    }
    
    @RequestMapping("/longpcr_assign.do")
    @FormInputView
    public String getLongpctAssignModel(LongpcrAssignArgs args, ModelMap model)
    {
        List<LongpcrAssignModel> data = service.getLongpcrAssignModel(args);
        for (LongpcrAssignModel assignModel : data)
        {
            
            for (LongPcrTask task : assignModel.getTasks())
            {
                task.setDnaConcn((BigDecimal)JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("concn"));
                task.setQualityLevel(JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
                
            }
        }
        model.addAttribute("data", data);
        return "bpm/assign/longpcr_assign_form";
    }
    
    @RequestMapping("/longpcr_assign_submit.do")
    @FormSubmitHandler
    public String assignLongpcr(LongpcrAssignRequest request)
    {
        
        service.assignLongpcr(request);
        return "redirect:/testing/longpcr_task.do";
    }
    
    @RequestMapping("/longpcr_test.do")
    @FormInputView
    public String getLongpcrSubmitModel(String id, ModelMap model)
    {
        LongpcrSubmitModel sheet = service.getLongpcrSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        sheet.setPcrTester(user.getName());
        
        for (LongpcrAssignModel assignModel : sheet.getTasks())
        {
            for (LongPcrTask task : assignModel.getTasks())
            {
                task.setDnaConcn((BigDecimal)JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("concn"));
                task.setQualityLevel(JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
            }
            
        }
        model.addAttribute("sheet", sheet);
        
       
        
        return "bpm/test/longpcr_test_form";
    }
    
    @RequestMapping("/longpcr_test_submit.do")
    @FormSubmitHandler
    public String submitLongpcr(LongpcrSubmitContent request)
    {
       
        service.submitLongpcr(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
