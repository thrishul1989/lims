package com.todaysoft.lims.maintain.testing.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.maintain.testing.service.ITestingTecanalysService;

@RestController
@RequestMapping("/maintain/testing/tecanalys")
public class TestingTecanalysController
{
    @Autowired
    private ITestingTecanalysService service;
    
    @RequestMapping(value = "/generateData", method = RequestMethod.GET)
    public void generateData()
    {
        service.generateData();
    }
}
