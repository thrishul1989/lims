package com.todaysoft.lims.system.modules.bpm.primerdesign.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignAssignArgs;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignAssignModel;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignAssignRequest;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignSheetModel;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignTask;
import com.todaysoft.lims.system.modules.bpm.primerdesign.service.IPrimerDesignService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class PrimerDesignController extends BaseController
{
    @Autowired
    private IPrimerDesignService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/primer_design_tasks.do")
    public String getPrimerDesignAssignableList(PrimerDesignAssignableTaskSearcher searcher, ModelMap model)
    {
        List<PrimerDesignTask> tasks = service.getPrimerDesignAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/sanger/process/primer_design_list";
    }
    
    @RequestMapping("/primer_design_assign.do")
    @FormInputView
    public String getPrimerDesignAssignModel(PrimerDesignAssignArgs args, ModelMap model)
    {
        PrimerDesignAssignModel data = service.getPrimerDesignAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/sanger/assign/primer_design_assign_form";
    }
    
    @RequestMapping("/primer_design_assign_submit.do")
    @FormSubmitHandler
    public String assignPrimerDesign(PrimerDesignAssignRequest data)
    {
        service.assignPrimerDesign(data);
        return "redirect:/testing/primer_design_tasks.do";
    }
    
    @RequestMapping("/primer_design_test.do")
    @FormInputView
    public String getPrimerDesignSubmitModel(String id, ModelMap model)
    {
        PrimerDesignSheetModel sheet = service.getPrimerDesignSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/sanger/test/primer_design_test_form";
    }
    
    @RequestMapping("/primer_design_submit.do")
    @FormSubmitHandler
    public String submitPrimerDesign(PrimerDesignSubmitRequest request)
    {
        service.submitPrimerDesign(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
