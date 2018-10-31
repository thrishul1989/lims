package com.todaysoft.lims.technical.service;

import java.io.IOException;

import com.todaysoft.lims.technical.model.response.PhenoTypePointCallbackRequest;

public interface IApiCallBackService
{
    
    void phenoTypePointCallback(PhenoTypePointCallbackRequest request) throws IOException;
    
}
