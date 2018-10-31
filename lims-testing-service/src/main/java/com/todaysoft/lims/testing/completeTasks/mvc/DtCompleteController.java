package com.todaysoft.lims.testing.completeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.completeTasks.model.dt.DtDataSheet;
import com.todaysoft.lims.testing.completeTasks.service.IDtCompleteService;

@RestController
@RequestMapping("/bpm/completeTasks/dt")
public class DtCompleteController
{
    @Autowired
    private IDtCompleteService service;
    
    @RequestMapping(value = "/data/sheet/{id}", method = RequestMethod.GET)
    public DtDataSheet getDtDataSheet(@PathVariable String id)
    {
        return service.getDtDataSheet(id);
    }
}
