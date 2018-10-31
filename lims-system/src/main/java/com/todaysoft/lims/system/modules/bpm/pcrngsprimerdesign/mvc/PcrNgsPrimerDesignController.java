package com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignAssignArgs;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignAssignModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignAssignRequest;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignSheetModel;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.PcrNgsPrimerDesignTask;
import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.service.IPcrNgsPrimerDesignService;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/testing")
public class PcrNgsPrimerDesignController extends BaseController
{
    @Autowired
    private IPcrNgsPrimerDesignService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/pcr_ngs_primer_design_tasks.do")
    public String getPrimerDesignAssignableList(PcrNgsPrimerDesignAssignableTaskSearcher searcher, ModelMap model)
    {
        List<PcrNgsPrimerDesignTask> tasks = service.getPrimerDesignAssignableList(searcher);
        model.addAttribute("tasks", tasks);
        model.addAttribute("searcher", searcher);
        return "bpm/pcr_ngs/process/pcr_ngs_primer_design_list";
    }
    
    @RequestMapping("/pcr_ngs_primer_design_assign.do")
    @FormInputView
    public String getPrimerDesignAssignModel(PcrNgsPrimerDesignAssignArgs args, ModelMap model)
    {
        PcrNgsPrimerDesignAssignModel data = service.getPrimerDesignAssignModel(args);
        model.addAttribute("data", data);
        return "bpm/pcr_ngs/assign/pcr_ngs_primer_design_assign_form";
    }
    
    @RequestMapping("/pcr_ngs_primer_design_assign_submit.do")
    @FormSubmitHandler
    public String assignPrimerDesign(PcrNgsPrimerDesignAssignRequest data)
    {
        service.assignPrimerDesign(data);
        return "redirect:/testing/pcr_ngs_primer_design_tasks.do";
    }
    
    @RequestMapping("/pcr_ngs_primer_design_test.do")
    @FormInputView
    public String getPrimerDesignSubmitModel(String id, ModelMap model)
    {
        PcrNgsPrimerDesignSheetModel sheet = service.getPrimerDesignSubmitModel(id);
        AuthorizedUser user = userService.getByToken();
        model.addAttribute("sheet", sheet).addAttribute("user", user.getName());
        return "bpm/pcr_ngs/test/pcr_ngs_primer_design_test_form";
    }
    
    @RequestMapping("/pcr_ngs_primer_design_submit.do")
    @FormSubmitHandler
    public String submitPrimerDesign(PcrNgsPrimerDesignSubmitRequest request)
    {
        service.submitPrimerDesign(request);
        return "redirect:/testing/test_tasks.do ";
    }
}
