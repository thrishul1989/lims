package com.todaysoft.lims.testing.completeTasks.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.completeTasks.model.sangerTest.DataPcrSangerSheet;
import com.todaysoft.lims.testing.completeTasks.model.sangerTest.FirstPCRSangerSheet;
import com.todaysoft.lims.testing.completeTasks.service.ISangerTestCompleteService;

@RestController
@RequestMapping("/bpm/completeTasks/sangerTest")
public class SangerTestCompleteAction
{
    @Autowired
    private ISangerTestCompleteService service;
    
    @RequestMapping(value = "/firstpcrSanger/sheet/{id}", method = RequestMethod.GET)
    public FirstPCRSangerSheet getFirstPCRSangerSheet(@PathVariable String id)
    {
        return service.getFirstPCRSangerSheet(id);
    }
    
    @RequestMapping(value = "/datapcrSanger/sheet/{id}", method = RequestMethod.GET)
    public DataPcrSangerSheet getDataPcrSangerSheet(@PathVariable String id)
    {
        return service.getDataPcrSangerSheet(id);
    }
}
