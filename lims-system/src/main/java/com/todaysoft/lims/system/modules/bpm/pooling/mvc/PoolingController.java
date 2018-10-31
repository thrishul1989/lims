package com.todaysoft.lims.system.modules.bpm.pooling.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingAssignModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingAssignRequest;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.pooling.service.IPoolingService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class PoolingController extends BaseController
{
    @Autowired
    private IPoolingService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/pooling/unique.do")
    @ResponseBody
    public boolean isCodeUnique(String poolingCode)
    {
        return service.isCodeUnique(poolingCode);
    }
    
    @RequestMapping("/pooling_assign.do")
    @FormInputView
    public String getPoolingAssignModel(String id, ModelMap model)
    {
        PoolingAssignModel sheet = service.getPoolingAssignModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/assign/pooling_assign_form";
    }
    
    @RequestMapping("/pooling_assign_submit.do")
    @FormSubmitHandler
    public String assignPooling(PoolingAssignRequest request)
    {
        service.assignPooling(request);
        return "redirect:/process/poolingList.do";
    }
    
    @RequestMapping("/pooling_test.do")
    @FormInputView
    public String getPoolingSubmitModel(String id, ModelMap model)
    {
        PoolingSubmitModel sheet = service.getPoolingSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/pooling_test_form";
    }
    
    @RequestMapping("/pooling_submit.do")
    @FormSubmitHandler
    public String submitPooling(PoolingSubmitRequest request)
    {
        service.submitPooling(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
