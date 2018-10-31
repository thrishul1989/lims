package com.todaysoft.lims.technical.service;

import com.todaysoft.lims.technical.model.request.UploadAnnotationRequest;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotate;

public interface IBioInfoAnnotateService
{
    
    Integer afterAnnotateForSave(String id, String... filePath);
    
    void saveAnnotation(UploadAnnotationRequest request);
    
    void update(BioInfoAnnotate bioInfoAnnotate);
    
    //BioInfoAnnotate getAnnotateBySampleCode(String sampleCode);
}
