package com.todaysoft.lims.system.modules.report.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.ReportTemplate;

public interface IReportTemplateService
{
    Pagination<ReportTemplate> paging(ReportTemplate searcher);
    
    ReportTemplate get(String id);
    
    void create(ReportTemplate request);
    
    void modify(ReportTemplate request);
    
    void delete(String id);
    
    boolean validate(ReportTemplate request);
    
    String uploadTemplate(MultipartFile file, String rootPath);
    
    String uploadImage(MultipartFile file, String rootPath);
    
    List<ReportTemplate> reportTemplateList(ReportTemplate searcher);
}
