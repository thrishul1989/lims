package com.todaysoft.lims.testing.completeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.completeTasks.model.longpcr.LongpcrSheet;
import com.todaysoft.lims.testing.completeTasks.model.longpcr.LongqcSheet;
import com.todaysoft.lims.testing.completeTasks.service.ILongpcrCompleteService;

@RestController
@RequestMapping("/bpm/completeTasks/longpcr")
public class LongpcrCompleteAction
{
    @Autowired
    private ILongpcrCompleteService longpcrCompleteService;
    
    @RequestMapping(value = "/longpcr/sheet/{id}", method = RequestMethod.GET)
    public LongpcrSheet getLongpcrSheet(@PathVariable String id)
    {
        return longpcrCompleteService.getLongpcrSheet(id);
    }
    
    @RequestMapping(value = "/longqc/sheet/{id}", method = RequestMethod.GET)
    public LongqcSheet getLongqcSheet(@PathVariable String id)
    {
        return longpcrCompleteService.getLongqcSheet(id);
    }
}
