package com.todaysoft.lims.system.modules.report.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.lims.system.modules.report.service.IReportCancelService;
import com.todaysoft.lims.system.mvc.BaseController;

@Controller
@RequestMapping(value = "/report/reportCancel")
public class ReportCancelController extends BaseController
{
    @Autowired
    private IReportCancelService reportCancelService;
    
    @RequestMapping(value = "/cancelReport.do")
    public String view(String scheduleId, String reportId, ModelMap model)
    {
        reportCancelService.cancelReport(scheduleId,reportId);
        
        return "report/dataTemplate/dataTemplate_view";
    }
    
}
