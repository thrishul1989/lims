package com.todaysoft.lims.testing.completeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.completeTasks.model.pcrngs.PcrNgsDataSheet;
import com.todaysoft.lims.testing.completeTasks.model.pcrngs.PcrNgsPrimerDesignSheet;
import com.todaysoft.lims.testing.completeTasks.model.pcrngs.PcrNgsTestSheet;
import com.todaysoft.lims.testing.completeTasks.service.IPcrNgsCompleteService;

@RestController
@RequestMapping("/bpm/completeTasks/pcrngs")
public class PcrNgsCompleteAction
{
    @Autowired
    private IPcrNgsCompleteService PcrNgsService;
    
    @RequestMapping(value = "/pcrngsprimerdesign/sheet/{id}", method = RequestMethod.GET)
    public PcrNgsPrimerDesignSheet getPcrNgsPrimerDesignSheet(@PathVariable String id)
    {
        return PcrNgsService.getPcrNgsPrimerDesignSheet(id);
    }
    
    @RequestMapping(value = "/pcrngstest/sheet/{id}", method = RequestMethod.GET)
    public PcrNgsTestSheet getPcrNgsTestSheet(@PathVariable String id)
    {
        return PcrNgsService.getPcrNgsTestSheet(id);
    }
    
    @RequestMapping(value = "/pcrngsdata/sheet/{id}", method = RequestMethod.GET)
    public PcrNgsDataSheet getPcrNgsDataSheet(@PathVariable String id)
    {
        return PcrNgsService.getPcrNgsDataSheet(id);
    }
}
