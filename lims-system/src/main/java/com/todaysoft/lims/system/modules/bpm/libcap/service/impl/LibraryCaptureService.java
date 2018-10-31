package com.todaysoft.lims.system.modules.bpm.libcap.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignArgs;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignModel;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignRequest;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSheetModel;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSubmitGroupArgs;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.libcap.model.LibraryCaptureTask;
import com.todaysoft.lims.system.modules.bpm.libcap.service.ILibraryCaptureService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class LibraryCaptureService extends RestService implements ILibraryCaptureService
{
    @Override
    public List<LibraryCaptureTask> getAssignableList(LibraryCaptureAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libcap/tasks/assignable");
        ResponseEntity<List<LibraryCaptureTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<LibraryCaptureAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<LibraryCaptureTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public LibraryCaptureAssignModel getAssignModel(LibraryCaptureAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libcap/tasks/assigning");
        ResponseEntity<LibraryCaptureAssignModel> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<LibraryCaptureAssignArgs>(args),
                new ParameterizedTypeReference<LibraryCaptureAssignModel>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void assign(LibraryCaptureAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libcap/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public LibraryCaptureSheetModel getSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libcap/sheets/{id}");
        return template.getForObject(url, LibraryCaptureSheetModel.class, id);
    }
    
    @Override
    public void submit(LibraryCaptureSubmitRequest request)
    {
        // 处理前端数据
        if (!CollectionUtils.isEmpty(request.getGroups()))
        {
            for (LibraryCaptureSubmitGroupArgs group : request.getGroups())
            {
                if (group.isQualified())
                {
                    group.setUnqualifiedRemark(null);
                }
            }
        }
        
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            for (LibraryCaptureSubmitTaskArgs task : request.getTasks())
            {
                if (task.isQualified())
                {
                    task.setUnqualifiedRemark(null);
                    task.setUnqualifiedStrategy(null);
                }
            }
        }
        
        String url = GatewayService.getServiceUrl("/bpm/testing/libcap/sheets/submit");
        template.postForLocation(url, request);
    }
    
    @Override
    public boolean validateBatchCode(String batchCode)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/libcap/validateBatchCode/{batchCode}");
        return template.getForObject(url, boolean.class, batchCode);
    }
    
    @Override
    public String probeLessFive(String probe)
    {
        String[] str = probe.split(",");
        StringBuffer probes = new StringBuffer();
        for (int i = 0; i < str.length; i++)
        {
            if (i > 0 && i < 5)
            {
                probes.append("," + str[i]);
            }
            else if (i == 0)
            {
                probes.append(str[i]);
            }
            else
            {
                break;
            }
        }
        return probes.toString();
    }
    
    @Override
    public String probeTitle(String probe)
    {
        String[] str = probe.split(",");
        StringBuffer probesTitle = new StringBuffer();
        for (int i = 0; i < str.length; i++)
        {
            if (i % 3 != 0)
            {
                if (i % 3 == 2)
                {
                    probesTitle.append(" " + str[i] + "\n");
                }
                else
                {
                    probesTitle.append(" " + str[i]);
                }
            }
            else
            {
                probesTitle.append(str[i]);
            }
        }
        return probesTitle.toString();
    }
}
