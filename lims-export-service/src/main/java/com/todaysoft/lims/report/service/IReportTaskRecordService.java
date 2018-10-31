package com.todaysoft.lims.report.service;


import com.todaysoft.lims.report.entity.ReportTaskRecord;

public interface IReportTaskRecordService
{
    void create(ReportTaskRecord reportTaskRecord);

    ReportTaskRecord get(String id);

}
