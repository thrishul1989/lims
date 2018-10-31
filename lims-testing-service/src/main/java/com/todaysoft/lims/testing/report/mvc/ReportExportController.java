package com.todaysoft.lims.testing.report.mvc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.entity.report.ReportExportDetectionResult;
import com.todaysoft.lims.testing.base.entity.report.ReportExportMethodColumnConfig;
import com.todaysoft.lims.testing.base.entity.report.ReportExportSampleBaseInfo;
import com.todaysoft.lims.testing.report.request.GetReportExportSampleBaseInfoRequest;
import com.todaysoft.lims.testing.report.request.ReportExportDetectionResultInfoRequest;
import com.todaysoft.lims.testing.report.service.IReportExportService;

@RestController
@RequestMapping("/bpm/testing/reportexport")
public class ReportExportController
{
    @Autowired
    private IReportExportService service;
    
    @RequestMapping("/getReportExportSampleBaseInfo")
    public ReportExportSampleBaseInfo getReportExportMutationInfo(@RequestBody GetReportExportSampleBaseInfoRequest request)
    {
       return service.getReportExportSampleBaseInfo(request);
    }
    
    @RequestMapping("/getReportExportDetectionResultInfo")
    public Map<String,List<ReportExportDetectionResult>> getReportExportDetectionResultInfo(@RequestBody ReportExportDetectionResultInfoRequest request)
    {
       return service.getReportExportDetectionResultInfo(request);
    }
    
    @RequestMapping("/getReportExportMethodColumnConfig")
    public List<ReportExportMethodColumnConfig>  getReportExportMethodColumnConfig(@RequestBody ReportExportDetectionResultInfoRequest request)
    {
       return service.getReportExportMethodColumnConfig(request.getMethodName());
    }
    
    
}
