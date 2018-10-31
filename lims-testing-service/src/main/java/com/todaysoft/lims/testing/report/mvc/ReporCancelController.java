package com.todaysoft.lims.testing.report.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.report.service.IReportCancelService;

@RestController
@RequestMapping("/bpm/report/reportCancel")
public class ReporCancelController
{
    @Autowired
    private IReportCancelService reportCancelService;
    
}
