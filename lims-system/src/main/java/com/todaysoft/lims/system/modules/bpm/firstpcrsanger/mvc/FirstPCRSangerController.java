package com.todaysoft.lims.system.modules.bpm.firstpcrsanger.mvc;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.system.model.searcher.CompanySearcher;
import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Company;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model.*;
import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.service.IFirstPCRSangerService;
import com.todaysoft.lims.system.modules.smm.model.APPSalemanSearcher;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IAPPSalemanService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ICompanyService;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/testing")
public class FirstPCRSangerController extends BaseController
{
    @Autowired
    private IFirstPCRSangerService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ICompanyService companyService;
    
    @Autowired
    private IAPPSalemanService appsaleService;
    
    @Autowired
    private ICustomerService customerService;
    
    @RequestMapping("/pcrOneSangerList.do")
    public String getFirstPCRAssignableList(FirstPCRSangerAssignableTaskSearcher searcher, ModelMap model)
    {
        List<FirstPCRSangerTask> tasks = service.getFirstPCRAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);

        return "bpm/sangerTest/process/first_pcr_sanger_list";
    }
    
    @RequestMapping("/first_pcr_sanger_assign.do")
    @FormInputView
    public String getFirstPCRAssignModel(FirstPCRSangerAssignArgs args, ModelMap model)
    {
        FirstPCRSangerAssignModel data = service.getFirstPCRAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/sangerTest/assign/first_pcr_sanger_assign_form";
    }
    
    @RequestMapping("/first_pcr_sanger_assign_submit.do")
    @FormSubmitHandler
    public String assignFirstPCR(FirstPCRSangerAssignRequest data)
    {
        service.assignFirstPCR(data);
        return "redirect:/testing/pcrOneSangerList.do";
    }
    
    @RequestMapping("/first_pcr_sanger_test.do")
    @FormInputView
    public String getFirstPCRSubmitModel(String id, ModelMap model)
    {
        FirstPCRSangerSheetModel sheet = service.getFirstPCRSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        List<FirstPCRSangerTask> printTasks = Lists.newArrayList();
        List<FirstPCRSangerTask> sampleTasks = Lists.newArrayList();
        List<String> primerList = Lists.newArrayList();
        List<String> sampleList = Lists.newArrayList();
        for(FirstPCRSangerTask task:sheet.getTasks())
        {
            String str = task.getForwardPrimerLocationTemp()+"_"+task.getForwardPrimerCode();
            if(!primerList.contains(str))
            {
                printTasks.add(task);
                primerList.add(str);
            }
            String sampleStr = task.getSampleCode()+"_"+task.getSampleLocationTemp();
            if(!sampleList.contains(sampleStr))
            {
                sampleTasks.add(task);
                sampleList.add(sampleStr);
            }
        }
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName()).addAttribute("printTasks",printTasks).addAttribute("sampleTasks",sampleTasks);
        return "bpm/sangerTest/test/first_pcr_sanger_test_form";
    }
    
    @RequestMapping("/first_pcr_sanger_submit.do")
    @FormSubmitHandler
    public String submitFirstPCR(FirstPCRSangerSubmitRequest request)
    {
        service.submitFirstPCR(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
