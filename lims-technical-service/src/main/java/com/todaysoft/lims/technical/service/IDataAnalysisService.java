package com.todaysoft.lims.technical.service;

import com.todaysoft.lims.technical.model.request.UploadEvidenceRequest;
import com.todaysoft.lims.technical.model.response.UploadEvidenceRespModel;

public interface IDataAnalysisService
{
    
    String getEvidenceByMongoId(String mongoId);
    
    UploadEvidenceRespModel uploadEvidence(UploadEvidenceRequest request);
    
    String getMutationDetail(String objectId);
    
}
