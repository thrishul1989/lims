package com.todaysoft.lims.system.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.LimsOperationLog;
import com.todaysoft.lims.system.service.IOpertaionLogService;

@Service
public class OpertaionLogService extends RestService implements IOpertaionLogService
{
    
    @Override
    public void create(LimsOperationLog request)
    {
        String url = GatewayService.getServiceUrl("/smm/opertaionLog/create");
        template.postForObject(url, request, String.class);
    }
    
}
