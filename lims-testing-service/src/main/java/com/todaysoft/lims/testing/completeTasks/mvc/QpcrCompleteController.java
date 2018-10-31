package com.todaysoft.lims.testing.completeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.completeTasks.model.qpcr.QpcrSheet;
import com.todaysoft.lims.testing.completeTasks.service.IQpcrCompleteService;

@RestController
@RequestMapping("/bpm/completeTasks/qpcr")
public class QpcrCompleteController
{
    @Autowired
    private IQpcrCompleteService service;
    
    @RequestMapping(value = "/sheet/{id}", method = RequestMethod.GET)
    public QpcrSheet getQpcrSheet(@PathVariable String id)
    {
        return service.getQpcrSheet(id);
    }
}
