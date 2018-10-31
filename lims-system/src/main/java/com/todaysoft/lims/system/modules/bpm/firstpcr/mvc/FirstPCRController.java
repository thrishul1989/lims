package com.todaysoft.lims.system.modules.bpm.firstpcr.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.searcher.CompanySearcher;
import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Company;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignArgs;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignModel;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignRequest;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRTask;
import com.todaysoft.lims.system.modules.bpm.firstpcr.service.IFirstPCRService;
import com.todaysoft.lims.system.modules.smm.model.APPSalemanSearcher;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IAPPSalemanService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.ICompanyService;
import com.todaysoft.lims.system.service.ICustomerService;

@Controller
@RequestMapping("/testing")
public class FirstPCRController extends BaseController
{
    @Autowired
    private IFirstPCRService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ICompanyService companyService;
    
    @Autowired
    private IAPPSalemanService appsaleService;
    
    @Autowired
    private ICustomerService customerService;
    
    @RequestMapping("/pcrOneList.do")
    public String getFirstPCRAssignableList(FirstPCRAssignableTaskSearcher searcher, ModelMap model)
    {
        List<FirstPCRTask> tasks = service.getFirstPCRAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/sanger/process/first_pcr_list";
    }
    
    @RequestMapping("/first_pcr_assign.do")
    @FormInputView
    public String getFirstPCRAssignModel(FirstPCRAssignArgs args, ModelMap model)
    {
        FirstPCRAssignModel data = service.getFirstPCRAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/sanger/assign/first_pcr_assign_form";
    }
    
    @RequestMapping("/first_pcr_assign_submit.do")
    @FormSubmitHandler
    public String assignFirstPCR(FirstPCRAssignRequest data)
    {
        service.assignFirstPCR(data);
        return "redirect:/testing/pcrOneList.do";
    }
    
    @RequestMapping("/first_pcr_test.do")
    @FormInputView
    public String getFirstPCRSubmitModel(String id, ModelMap model)
    {
        FirstPCRSheetModel sheet = service.getFirstPCRSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/sanger/test/first_pcr_test_form";
    }
    
    @RequestMapping("/first_pcr_submit.do")
    @FormSubmitHandler
    public String submitFirstPCR(FirstPCRSubmitRequest request)
    {
        service.submitFirstPCR(request);
        return "redirect:/testing/test_tasks.do ";
    }
    
    @RequestMapping("/companyList.do")
    @ResponseBody
    public List<Company> list(CompanySearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Pagination<Company> pagination = companyService.paging(searcher, pageNo, 10);
        List<Company> companyList = pagination.getRecords();
        return companyList;
    }
    
    @RequestMapping("/customerList.do")
    @ResponseBody
    public List<Customer> list(Customer searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Pagination<Customer> pagination = customerService.paging(searcher, pageNo, 10);
        List<Customer> customerList = pagination.getRecords();
        return customerList;
    }
    
    @RequestMapping("/saleManList.do")
    @ResponseBody
    public List<BusinessInfo> list(APPSalemanSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        
        Pagination<BusinessInfo> pagination = appsaleService.paging(searcher, pageNo, 10);
        List<BusinessInfo> businessList = pagination.getRecords();
        return businessList;
    }
}
