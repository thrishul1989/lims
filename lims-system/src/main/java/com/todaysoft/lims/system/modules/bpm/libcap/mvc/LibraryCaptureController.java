package com.todaysoft.lims.system.modules.bpm.libcap.mvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bcm.service.IConfigService;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignArgs;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignModel;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignRequest;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSheetModel;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureTask;
import com.todaysoft.lims.system.modules.bpm.libcap.service.ILibraryCaptureService;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ITestingTaskService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/testing")
public class LibraryCaptureController extends BaseController
{
    @Autowired
    private ILibraryCaptureService service;
    
    @Autowired
    private IConfigService configService;
    
    @Autowired
    private ITestingTaskService taskService;
    
    @RequestMapping("/wk_catch_tasks.do")
    public String assignable(LibraryCaptureAssignableTaskSearcher searcher, ModelMap model)
    {
        List<LibraryCaptureTask> tasks = service.getAssignableList(searcher);
        //閲嶆柊澶勭悊Probe灞炴�锛屼笉鑳借秴杩�涓�
        for (LibraryCaptureTask task : tasks)
        {
            if (!StringUtils.isEmpty(task.getProbes()))
            {
                task.setProbeLessFive(service.probeLessFive(task.getProbes()));
                task.setProbeTitle(service.probeTitle(task.getProbes()));
            }
        }
        
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/lib_catch_list";
    }
    
    @RequestMapping("/wk_catch_assign_check.do")
    @ResponseBody
    public Set<String> assignCheck(LibraryCaptureAssignArgs args, ModelMap model)
    {
        Set<String> res = new HashSet<>();
        System.out.println(123);
        Map<String, Integer> orderCodes = new HashMap<>();
        for (String taskId : args.getKeys().split("\\,"))
        {
            TestingTask task = taskService.get(taskId);
            if (orderCodes.containsKey(task.getOrderCode()))
            {
                orderCodes.put(task.getOrderCode(), orderCodes.get(task.getOrderCode())+1);
            }
            else
            {
                orderCodes.put(task.getOrderCode(), 1);
            }
        }
        List<LibraryCaptureTask> tasks = service.getAssignableList(new LibraryCaptureAssignableTaskSearcher());
        for (Map.Entry<String, Integer> entry : orderCodes.entrySet())
        {
            String orderCode = entry.getKey();
            Integer count = entry.getValue();
            Integer factCount = 0;
            for (LibraryCaptureTask task : tasks)
            {
                if (orderCode.equals(task.getOrderCode()))
                {
                    factCount++;
                }
            }
            if (factCount > count)
            {
                res.add(orderCode);
            }
        }
        return res;
    }
    
    @RequestMapping("/wk_catch_assign.do")
    @FormInputView
    public String assign(LibraryCaptureAssignArgs args, ModelMap model)
    {
        LibraryCaptureAssignModel data = service.getAssignModel(args);
        BigDecimal defaultInputSize = configService.getLibraryCaptureDefaultInputSize();
        model.addAttribute("inputSampleTypeId", args.getSampleType());
        model.addAttribute("data", data);
        model.addAttribute("defaultInputSize", defaultInputSize);
        return "bpm/assign/lib_catch_assign_form";
    }
    
    @RequestMapping("/wk_catch_assign_submit.do")
    @FormSubmitHandler
    public String assign(LibraryCaptureAssignRequest request)
    {
        service.assign(request);
        return "redirect:/testing/wk_catch_tasks.do";
    }
    
    @RequestMapping("/wk_catch_test.do")
    @FormInputView
    public String submit(String id, ModelMap model)
    {
        LibraryCaptureSheetModel sheet = service.getSubmitModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/test/lib_catch_test_form";
    }
    
    @RequestMapping("/wk_catch_test_submit.do")
    @FormSubmitHandler
    public String submit(LibraryCaptureSubmitRequest request)
    {
        service.submit(request);
        return "redirect:/testing/test_tasks.do";
    }
    
    @RequestMapping("/wk_catch/validateBatchCode.do")
    @ResponseBody
    public boolean validateBatchCode(String batchCode)
    {
        return service.validateBatchCode(batchCode);
    }
}
