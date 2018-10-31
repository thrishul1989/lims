package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.mlpa.MlpaDataSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IMLPACompleteService;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestSubmitModel;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/bpm/completeTasks/mlpa")
public class MLPACompleteController extends BaseController
{
    @Autowired
    private IMLPACompleteService service;
    
    @RequestMapping("/mlpa.do")
    public String getMlpaSubmitModel(String id, ModelMap model)
    {
        MlpaTestSubmitModel sheet = service.getMlpaSubmitModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/mlpa/mlpa";
    }
    
    @RequestMapping("/mlpa_data.do")
    public String getMlpaDataSheet(String id, ModelMap model)
    {
        MlpaDataSheet sheet = service.getMlpaDataSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/mlpa/mlpa_data";
    }
}
