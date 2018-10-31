package com.todaysoft.lims.system.modules.bpm.dnaqc.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcAssignModel;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcAssignSheet;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcSheet;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcTask;
import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dnaqc.service.IDNAQcService;
import com.todaysoft.lims.system.modules.bpm.model.test.args.TestArgs;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class DNAQcService extends RestService implements IDNAQcService
{
    @Override
    public List<DNAQcTask> dnaQcTasks(DNAQcTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dnaqc/tasks/assignable");
        ResponseEntity<List<DNAQcTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DNAQcTaskSearcher>(searcher), new ParameterizedTypeReference<List<DNAQcTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public DNAQcAssignModel getDNAQcAssignModel(DNAQcAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dnaqc/tasks/assigning");
        ResponseEntity<DNAQcAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DNAQcAssignArgs>(args), new ParameterizedTypeReference<DNAQcAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void dnaQcAssign(DNAQcAssignSheet request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dnaqc/tasks/assign");
        template.postForObject(url, request, Integer.class);
    }
    
    @Override
    public DNAQcSheet getDNAQcTestModel(TestArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dnaqc/sheets/{id}");
        return template.getForObject(url, DNAQcSheet.class, args.getId());
    }
    
    @Override
    public void submitSheet(DNAQcSubmitSheetModel request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dnaqc/sheets/submit");
        template.postForObject(url, request, Integer.class);
    }
}
