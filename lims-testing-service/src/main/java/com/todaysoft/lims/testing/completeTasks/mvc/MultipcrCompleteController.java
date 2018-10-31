package com.todaysoft.lims.testing.completeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.completeTasks.model.multipcr.MultipcrQcSheet;
import com.todaysoft.lims.testing.completeTasks.service.IMultipcrCompleteService;

@RestController
@RequestMapping("/bpm/completeTasks/multipcr")
public class MultipcrCompleteController
{
    @Autowired
    private IMultipcrCompleteService service;
    
    @RequestMapping(value = "/sheet/{id}", method = RequestMethod.GET)
    public MultipcrQcSheet getMultipcrQcSheet(@PathVariable String id)
    {
        return service.getMultipcrQcSheet(id);
    }
}
