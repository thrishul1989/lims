package com.todaysoft.lims.report.service;


import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.model.OrderSamplePageModel;
import com.todaysoft.lims.report.model.SampleExperimentModel;
import com.todaysoft.lims.report.model.request.SampleExperimentRequest;

import java.util.List;
import java.util.Map;

public interface IExperimentReportService
{

    OrderSamplePageModel paging(SampleExperimentRequest request);

    OrderSamplePageModel sheetSamplePaging(SampleExperimentRequest request);

    OrderSamplePageModel sheetSampleSuccessRatePaging(SampleExperimentRequest request);

    OrderSamplePageModel taskFailSolvePaging(SampleExperimentRequest request);
}
