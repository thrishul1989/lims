package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.dt.DtDataSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IDTCompleteService;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTSubmitModel;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/bpm/completeTasks/dt")
public class DTCompleteController extends BaseController
{
    @Autowired
    private IDTCompleteService service;
    
    @RequestMapping("/dt.do")
    public String getDTSubmitModel(String id, ModelMap model)
    {
        DTSubmitModel sheet = service.getDTSubmitModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/dt/dt";
    }
    
    @RequestMapping("/dt_data.do")
    public String getDtDataSheet(String id, ModelMap model)
    {
        DtDataSheet sheet = service.getDtDataSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/dt/dt_data";
    }
}
