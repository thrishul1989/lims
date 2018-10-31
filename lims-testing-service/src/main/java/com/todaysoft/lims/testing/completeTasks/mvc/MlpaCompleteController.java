package com.todaysoft.lims.testing.completeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.completeTasks.model.mlpa.MlpaDataSheet;
import com.todaysoft.lims.testing.completeTasks.service.IMlpaCompleteService;

@RestController
@RequestMapping("/bpm/completeTasks/mlpa")
public class MlpaCompleteController
{
    @Autowired
    private IMlpaCompleteService service;
    
    @RequestMapping(value = "/data/sheet/{id}", method = RequestMethod.GET)
    public MlpaDataSheet getMlpaDataSheet(@PathVariable String id)
    {
        return service.getMlpaDataSheet(id);
    }
}
