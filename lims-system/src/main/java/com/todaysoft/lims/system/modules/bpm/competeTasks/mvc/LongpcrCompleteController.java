package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.longpcr.LongpcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.longpcr.LongqcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.ILongpcrCompleteService;
import com.todaysoft.lims.system.modules.bpm.longcre.model.LongcreSubmitSheet;
import com.todaysoft.lims.system.modules.bpm.longcre.servicve.ILongcreService;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/bpm/completeTasks/longpcr")
public class LongpcrCompleteController extends BaseController
{
    
    @Autowired
    private ILongpcrCompleteService longpcrCompleteService;
    
    @Autowired
    private ILongcreService longcreService;
    
    @RequestMapping("/longpcr.do")
    public String longpcrSheet(String id, ModelMap model)
    {
        LongpcrSheet sheet = longpcrCompleteService.getLongpcrSheet(id);
        for (LongpcrAssignModel assignModel : sheet.getTasks())
        {
            for (LongPcrTask task : assignModel.getTasks())
            {
                task.setDnaConcn((BigDecimal)JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("concn"));
                task.setQualityLevel(JSON.parseObject(task.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
            }
            
        }
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/longpcr/longpcr";
    }
    
    @RequestMapping("/longcre.do")
    public String longcreSubmitSheet(String id, ModelMap model)
    {
        LongcreSubmitSheet sheet = longcreService.getLongcreSubmitModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/longpcr/longcre";
    }
    
    @RequestMapping("/longqc.do")
    public String longqcSheet(String id, ModelMap model)
    {
        LongqcSheet sheet = longpcrCompleteService.getLongqcSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/longpcr/longqc";
    }
}
