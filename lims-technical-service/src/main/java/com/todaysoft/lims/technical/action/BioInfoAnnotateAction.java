package com.todaysoft.lims.technical.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.technical.model.request.UploadAnnotationRequest;
import com.todaysoft.lims.technical.service.IBioInfoAnnotateService;

@RestController
@RequestMapping("/bioInfoAnnotate")
public class BioInfoAnnotateAction
{
    
    @Autowired
    private IBioInfoAnnotateService service;
    
    @RequestMapping("/saveAnnotation")
    public void saveAnnotation(@RequestBody UploadAnnotationRequest request)
    {
        service.saveAnnotation(request);
    }
    
}
