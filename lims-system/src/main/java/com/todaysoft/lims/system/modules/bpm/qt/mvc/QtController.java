package com.todaysoft.lims.system.modules.bpm.qt.mvc;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bcm.service.IConfigService;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignArgs;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignModel;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignRequest;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtSubmitModel;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtTask;
import com.todaysoft.lims.system.modules.bpm.qt.service.IQtService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class QtController extends BaseController
{
    @Autowired
    private IQtService service;
    
    @Autowired
    private IConfigService configService;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/qt_tasks.do")
    public String getAssignableList(QtAssignableTaskSearcher searcher, ModelMap model)
    {
        List<QtTask> tasks = service.getAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/qt_list";
    }
    
    @RequestMapping("/qt_assign.do")
    @FormInputView
    public String getAssignModel(QtAssignArgs args, ModelMap model)
    {
        QtAssignModel data = service.getAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/assign/qt_assign_form";
    }
    
    @RequestMapping("/qt_assign_submit.do")
    @FormSubmitHandler
    public String assign(QtAssignRequest request)
    {
        service.assign(request);
        return "redirect:/testing/qt_tasks.do";
    }
    
    @RequestMapping("/qt_test.do")
    @FormInputView
    public String getSubmitModel(String id, ModelMap model)
    {
        QtSubmitModel sheet = service.getSubmitModel(id);
        BigDecimal defaultFragmentLength = configService.getQTDefaultFragmentLength();
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        model.addAttribute("defaultFragmentLength", defaultFragmentLength);
        return "bpm/test/qt_test_form";
    }
    
    @RequestMapping("/qt_test_submit.do")
    @FormSubmitHandler
    public String submit(QtSubmitRequest request)
    {
        service.submit(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
