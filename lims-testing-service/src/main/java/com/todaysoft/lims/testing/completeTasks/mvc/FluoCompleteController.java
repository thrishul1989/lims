package com.todaysoft.lims.testing.completeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.completeTasks.model.fluo.FluoAnalyseSheet;
import com.todaysoft.lims.testing.completeTasks.service.IFluoCompleteService;

@RestController
@RequestMapping("/bpm/completeTasks/fluo")
public class FluoCompleteController
{
    @Autowired
    private IFluoCompleteService service;
    
    @RequestMapping(value = "/fluoAnalyse/sheet/{id}", method = RequestMethod.GET)
    public FluoAnalyseSheet getFluoAnalyseSheet(@PathVariable String id)
    {
        return service.getFluoAnalyseSheet(id);
    }
}
