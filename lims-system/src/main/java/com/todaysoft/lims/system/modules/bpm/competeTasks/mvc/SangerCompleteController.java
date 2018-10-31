package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sanger.DataPcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sanger.FirstPcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sanger.PrimerDesignSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.ISangerCompleteService;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRSheetModel;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/bpm/completeTasks/sanger")
public class SangerCompleteController extends BaseController
{
    
    @Autowired
    private ISangerCompleteService sangerService;
    
    @RequestMapping("/primer_design.do")
    public String primerDesignSheet(String id, ModelMap model)
    {
        PrimerDesignSheet sheet = sangerService.getPrimerDesignSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/sanger/primer_design";
    }
    
    @RequestMapping("/first_pcr.do")
    public String firstPcrSheet(String id, ModelMap model)
    {
        FirstPcrSheet sheet = sangerService.getFirstPcrSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/sanger/first_pcr";
    }
    
    @RequestMapping("/second_pcr.do")
    public String secondPcrSheet(String id, ModelMap model)
    {
        SecondPCRSheetModel sheet = sangerService.getSecondPCRSubmitModel(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/sanger/second_pcr";
    }
    
    @RequestMapping("/data_pcr.do")
    public String dataPcrSheet(String id, ModelMap model)
    {
        DataPcrSheet sheet = sangerService.getDataPcrSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/sanger/data_pcr";
    }
}
