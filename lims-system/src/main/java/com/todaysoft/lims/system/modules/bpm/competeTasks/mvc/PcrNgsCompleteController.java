package com.todaysoft.lims.system.modules.bpm.competeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs.PcrNgsDataSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs.PcrNgsPrimerDesignSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs.PcrNgsTestSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IPcrNgsCompleteService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping("/bpm/completeTasks/pcrngs")
public class PcrNgsCompleteController extends BaseController
{
    
    @Autowired
    private IPcrNgsCompleteService pcrNgsService;
    
    @RequestMapping("/pcr_ngs_primer_design.do")
    public String pcrNgsPrimerDesignSheet(String id, ModelMap model)
    {
        PcrNgsPrimerDesignSheet sheet = pcrNgsService.getPcrNgsPrimerDesignSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/pcr_ngs/pcr_ngs_primer_design";
    }
    
    @RequestMapping("/pcr_ngs_test.do")
    public String pcrNgsTestSheet(String id, ModelMap model)
    {
        PcrNgsTestSheet sheet = pcrNgsService.getPcrNgsTestSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/pcr_ngs/pcr_ngs_test";
    }
    
    @RequestMapping("/pcr_ngs_data.do")
    public String pcrNgsDataSheet(String id, ModelMap model)
    {
        PcrNgsDataSheet sheet = pcrNgsService.getPcrNgsDataSheet(id);
        model.addAttribute("sheet", sheet);
        return "bpm/completeTasks/pcr_ngs/pcr_ngs_data";
    }
}
