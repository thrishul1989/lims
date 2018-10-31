package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sangerTest.DataPcrSangerSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sangerTest.FirstPCRSangerSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.ISangerTestCompleteService;
import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SecondPCRSangerSheetModel;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/bpm/completeTasks/sangerTest")
public class SangerTestCompleteController extends BaseController
{
    
    @Autowired
    private ISangerTestCompleteService service;
    
    @RequestMapping("/first_pcr_sanger.do")
    public String firstPcrSangerSheet(String id, ModelMap model)
    {
        FirstPCRSangerSheet sheet = service.getFirstPCRSangerSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/sangerTest/first_pcr_sanger";
    }
    
    @RequestMapping("/second_pcr_sanger.do")
    public String secondPcrSheet(String id, ModelMap model)
    {
        SecondPCRSangerSheetModel sheet = service.getSecondPCRSangerSheetModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/sangerTest/second_pcr_sanger";
    }
    
    @RequestMapping("/data_pcr_sanger.do")
    public String dataPcrSheet(String id, ModelMap model)
    {
        DataPcrSangerSheet sheet = service.getDataPcrSangerSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/sangerTest/data_pcr_sanger";
    }
}
