package com.todaysoft.lims.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.ReportTemplateSearcher;
import com.todaysoft.lims.report.entity.ReportTemplate;

public interface IReportTemplateService
{
    Pagination<ReportTemplate> paging(ReportTemplateSearcher searcher);
    
    ReportTemplate get(String id);
    
    void create(ReportTemplate request);
    
    void modify(ReportTemplate request);
    
    void delete(String id);
    
    boolean validate(ReportTemplate request);
    
    List<ReportTemplate> list(ReportTemplateSearcher searcher);
}
