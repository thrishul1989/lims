package com.todaysoft.lims.system.modules.bpm.libqc.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.libqc.model.LibraryQcSubmitModel;
import com.todaysoft.lims.system.modules.bpm.libqc.model.LibraryQcSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.libqc.service.ILibraryQcService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class LibraryQcService extends RestService implements ILibraryQcService
{
    @Override
    public LibraryQcSubmitModel getSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libqc/sheets/{id}");
        return template.getForObject(url, LibraryQcSubmitModel.class, id);
    }
    
    @Override
    public void submit(LibraryQcSubmitRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libqc/sheets/submit");
        template.postForLocation(url, request);
    }
}
