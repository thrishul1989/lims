package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.qpcr.QpcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IQpcrCompleteService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/bpm/completeTasks/qpcr")
public class QpcrCompleteController extends BaseController
{
    @Autowired
    private IQpcrCompleteService service;
    
    @RequestMapping("/qpcr.do")
    public String getQpcrSheet(String id, ModelMap model)
    {
        QpcrSheet sheet = service.getQpcrSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/qpcr/qpcr";
    }
}
