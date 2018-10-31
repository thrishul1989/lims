package com.todaysoft.lims.system.modules.report.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.ReportTemplate;
import com.todaysoft.lims.system.modules.report.service.IReportTemplateService;
import com.todaysoft.lims.system.service.handler.UploadFileHandler;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ReportTemplateService extends RestService implements IReportTemplateService
{
    
    @Override
    public Pagination<ReportTemplate> paging(ReportTemplate searcher)
    {
        String url = GatewayService.getServiceUrl("/report/reportTemplate/paging");
        ResponseEntity<Pagination<ReportTemplate>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ReportTemplate>(searcher), new ParameterizedTypeReference<Pagination<ReportTemplate>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public ReportTemplate get(String id)
    {
        String url = GatewayService.getServiceUrl("/report/reportTemplate/{id}");
        return template.getForObject(url, ReportTemplate.class, id);
    }
    
    @Override
    public void create(ReportTemplate request)
    {
        String url = GatewayService.getServiceUrl("/report/reportTemplate/create");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void modify(ReportTemplate request)
    {
        String url = GatewayService.getServiceUrl("/report/reportTemplate/modify");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/report/reportTemplate/{id}");
        template.delete(url, id);
    }
    
    @Override
    public boolean validate(ReportTemplate request)
    {
        String url = GatewayService.getServiceUrl("/report/reportTemplate/validate");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public String uploadTemplate(MultipartFile file, String rootPath)
    {
        UploadFileHandler handler = new UploadFileHandler(rootPath, "/upload/reportTemplate", file);
        handler.upload();
        File resultFile = handler.getResultFile();
        String filePath = resultFile.getAbsolutePath();
        return filePath;
    }
    
    @Override
    public String uploadImage(MultipartFile file, String rootPath)
    {
        UploadFileHandler handler = new UploadFileHandler(rootPath, "/upload/image/reportTemplate", file);
        handler.upload();
        File resultFile = handler.getResultFile();
        String filePath = resultFile.getAbsolutePath();
        return filePath;
    }
    
    @Override
    public List<ReportTemplate> reportTemplateList(ReportTemplate searcher)
    {
        String url = GatewayService.getServiceUrl("/report/reportTemplate/list");
        ResponseEntity<List<ReportTemplate>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ReportTemplate>(searcher), new ParameterizedTypeReference<List<ReportTemplate>>()
            {
            });
        return exchange.getBody();
    }
}
