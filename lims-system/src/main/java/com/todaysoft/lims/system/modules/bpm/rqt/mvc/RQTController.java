package com.todaysoft.lims.system.modules.bpm.rqt.mvc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignArgs;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignModel;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignRequest;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTSheetModel;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTTask;
import com.todaysoft.lims.system.modules.bpm.rqt.service.IRQTService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class RQTController extends BaseController
{
    @Autowired
    private IRQTService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/rqt/validateIndex.do")
    @ResponseBody
    public Map<String, List<String>> validateIndex(String ids)
    {
        Map<String, List<String>> validateIndex = service.validateIndex(ids);
        return validateIndex;
    }
    
    @RequestMapping("/pooling_tasks.do")
    public String getRQTAssignableList(RQTAssignableTaskSearcher searcher, ModelMap model)
    {
        List<RQTTask> tasks = service.getRQTAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/process/rqt_list";
    }
    
    @RequestMapping("/rqt_assign.do")
    @FormInputView
    public String getRQTAssignModel(RQTAssignArgs args, ModelMap model)
    {
        RQTAssignModel data = service.getRQTAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/assign/rqt_assign_form";
    }
    
    @RequestMapping("/rqt_assign_submit.do")
    @FormSubmitHandler
    public String assignRQT(RQTAssignRequest data)
    {
        service.assignRQT(data);
        return "redirect:/testing/pooling_tasks.do";
    }
    
    @RequestMapping("/rqt_test.do")
    @FormInputView
    public String getRQTSubmitModel(String id, ModelMap model)
    {
        RQTSheetModel sheet = service.getRQTSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/test/rqt_test_form";
    }
    
    @RequestMapping("/rqt_submit.do")
    @FormSubmitHandler
    public String submitRQT(RQTSubmitRequest request)
    {
        service.submitRQT(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
