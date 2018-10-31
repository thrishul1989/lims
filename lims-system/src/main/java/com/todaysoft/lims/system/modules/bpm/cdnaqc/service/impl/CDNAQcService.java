package com.todaysoft.lims.system.modules.bpm.cdnaqc.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcAssignArgs;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcAssignModel;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcAssignSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcTask;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.model.CDNAQcTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.cdnaqc.service.ICDNAQcService;
import com.todaysoft.lims.system.modules.bpm.model.test.args.TestArgs;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class CDNAQcService extends RestService implements ICDNAQcService
{
    
    @Override
    public List<CDNAQcTask> cdnaQcTasks(CDNAQcTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/cdnaqc/tasks/assignable");
        ResponseEntity<List<CDNAQcTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CDNAQcTaskSearcher>(searcher), new ParameterizedTypeReference<List<CDNAQcTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public CDNAQcAssignModel getCDNAQcAssignModel(CDNAQcAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/cdnaqc/tasks/assigning");
        ResponseEntity<CDNAQcAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CDNAQcAssignArgs>(args), new ParameterizedTypeReference<CDNAQcAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void cdnaQcAssign(CDNAQcAssignSheet request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/cdnaqc/tasks/assign");
        template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public CDNAQcSheet getCDNAQcTestModel(TestArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/cdnaqc/sheets/{id}");
        return template.getForObject(url, CDNAQcSheet.class, args.getId());
    }
    
    @Override
    public void submitSheet(CDNAQcSubmitSheetModel request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/cdnaqc/sheets/submit");
        template.postForObject(url, request, Integer.class);
    }
    
}
