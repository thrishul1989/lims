package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.fluo.FluoAnalyseSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.fluo.FluoAnalyseSheetTask;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IFluoCompleteService;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestSubmitModel;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestTask;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/bpm/completeTasks/fluo")
public class FluoCompleteController extends BaseController
{
    @Autowired
    private IFluoCompleteService service;
    
    @RequestMapping("/fluotest.do")
    public String getFluoTestSubmitModel(String id, ModelMap model)
    {
        FluoTestSubmitModel sheet = service.getFluoTestSubmitModel(id);
        for (FluoTestTask assignModel : sheet.getTasks())
        {
            assignModel.setDnaConcn((BigDecimal)JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("concn"));
            assignModel.setQualityLevel(JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
        }
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/fluo/fluotest";
    }
    
    @RequestMapping("/fluoanalyse.do")
    public String getFluoAnalyseSheet(String id, ModelMap model)
    {
        FluoAnalyseSheet sheet = service.getFluoAnalyseSheet(id);
        for (FluoAnalyseSheetTask assignModel : sheet.getTasks())
        {
            assignModel.setDnaConcn((BigDecimal)JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("concn"));
            assignModel.setQualityLevel(JSON.parseObject(assignModel.getTestingTask().getInputSample().getAttributes()).get("qualityLevel").toString());
        }
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/fluo/fluoanalyse";
    }
}
