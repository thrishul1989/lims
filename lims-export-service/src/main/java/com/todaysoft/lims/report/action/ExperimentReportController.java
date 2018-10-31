package com.todaysoft.lims.report.action;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.model.OrderSamplePageModel;
import com.todaysoft.lims.report.model.request.SampleExperimentRequest;
import com.todaysoft.lims.report.service.IExperimentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/export/experiment")
public class ExperimentReportController
{
    @Autowired
    private IExperimentReportService experimentReportService;

    @RequestMapping(value = "/sampleExperimentPaging")
    public OrderSamplePageModel paging(@RequestBody SampleExperimentRequest request)
    {
        return experimentReportService.paging(request);
    }

    @RequestMapping(value = "/sheetSamplePaging")
    public OrderSamplePageModel sheetSamplePaging(@RequestBody SampleExperimentRequest request)
    {
        return experimentReportService.sheetSamplePaging(request);
    }

    @RequestMapping(value = "/sheetSampleSuccessRatePaging")
    public OrderSamplePageModel sheetSampleSuccessRatePaging(@RequestBody SampleExperimentRequest request)
    {
        return experimentReportService.sheetSampleSuccessRatePaging(request);
    }

    @RequestMapping(value = "/taskFailSolvePaging")
    public OrderSamplePageModel taskFailSolvePaging(@RequestBody SampleExperimentRequest request)
    {
        return experimentReportService.taskFailSolvePaging(request);
    }

}
