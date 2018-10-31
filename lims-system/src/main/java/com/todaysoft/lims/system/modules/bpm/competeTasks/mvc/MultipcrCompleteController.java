package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.multipcr.MultipcrQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IMultipcrCompleteService;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultiPcrTask;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrSubmitModel;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/bpm/completeTasks/multipcr")
public class MultipcrCompleteController extends BaseController
{
    @Autowired
    private IMultipcrCompleteService service;
    
    @RequestMapping("/multipcr.do")
    public String getMultipcrSubmitModel(String id, ModelMap model)
    {
        MultipcrSubmitModel sheet = service.getMultipcrSubmitModel(id);
        sheet.getTasks().sort((x, y) -> x.getTasks().get(0).getTestingCode().compareTo(y.getTasks().get(0).getTestingCode()));
        for (MultipcrAssignModel assignModel : sheet.getTasks())
        {
            for (MultiPcrTask task : assignModel.getTasks())
            {
                task.setDnaConcn((BigDecimal)JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("concn"));
                task.setQualityLevel(JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
            }
        }
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/multipcr/multipcr";
    }
    
    @RequestMapping("/multipcr_qc.do")
    public String getMultipcrQcSheet(String id, ModelMap model)
    {
        MultipcrQcSheet sheet = service.getMultipcrQcSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/multipcr/multipcr_qc";
    }
}
