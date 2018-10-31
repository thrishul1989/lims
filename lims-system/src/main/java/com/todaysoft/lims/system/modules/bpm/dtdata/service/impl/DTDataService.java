package com.todaysoft.lims.system.modules.bpm.dtdata.service.impl;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataAssignArgs;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataAssignModel;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataAssignRequest;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.dtdata.model.DTDataTask;
import com.todaysoft.lims.system.modules.bpm.dtdata.service.IDTDataService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class DTDataService extends RestService implements IDTDataService
{

    @Autowired
    private IMlpaDataDataService dataDataService;
    
    @Override
    public List<DTDataTask> getDataPcrAssignableList(DTDataAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dtdata/tasks/assignable");
        ResponseEntity<List<DTDataTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DTDataAssignableTaskSearcher>(searcher), new ParameterizedTypeReference<List<DTDataTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public DTDataAssignModel getDataPcrAssignModel(DTDataAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dtdata/tasks/assigning");
        ResponseEntity<DTDataAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DTDataAssignArgs>(args), new ParameterizedTypeReference<DTDataAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignDataPcr(DTDataAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dtdata/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public DTDataSheetModel getDataPcrSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dtdata/sheets/{id}");
        return template.getForObject(url, DTDataSheetModel.class, id);
    }
    
    @Override
    public void submitDataPcr(DTDataSubmitRequest request)
    {
        List<DTDataSubmitTaskSuccessArgs> successArgsList = Lists.newArrayList();
        DataAnalysisParseModel parseModel = request.getParseModel();
        if(null != parseModel)//为空的情况是没有上传东西
        {
            if(Collections3.isNotEmpty(parseModel.getDtRecords()))
            {
                for(DTDataSubmitTaskSuccessArgs record:parseModel.getDtRecords())
                {
                    if(record.isValid())
                    {
                        successArgsList.add(record);
                    }
                }
            }
            //组装保存图片的数据
            List<TestingDataPic> list = dataDataService.uploadFileAndWrapData(parseModel,request.getId());
            request.setPicList(list);
        }

        List<DTDataSubmitTaskArgs> tasks = request.getTasks();
        Map<String, List<DTDataSubmitTaskSuccessArgs>> map = Maps.newHashMap();
        if (Collections3.isNotEmpty(successArgsList))
        {
            for (DTDataSubmitTaskSuccessArgs dt : successArgsList)
            {
                if (map.containsKey(dt.getSampleCode()))
                {
                    List<DTDataSubmitTaskSuccessArgs> list = map.get(dt.getSampleCode());
                    list.add(dt);
                    map.put(dt.getSampleCode(), list);
                    
                }
                else
                {
                    List<DTDataSubmitTaskSuccessArgs> list = Lists.newArrayList();
                    list.add(dt);
                    map.put(dt.getSampleCode(), list);
                }
            }
            
            for (DTDataSubmitTaskArgs taskArgs : tasks)
            {
                String sampleCode = taskArgs.getSampleCode();
                if (taskArgs.isQualified())
                {
                    taskArgs.setSuccessArgs(map.get(sampleCode));
                }
            }
        }

        String url = GatewayService.getServiceUrl("/bpm/testing/dtdata/sheets/submit");
        template.postForLocation(url, request);
    }
    
    @Override
    public DTDataSheetModel exportAnalySheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dtdata/exportAnalySheet/{id}");
        return template.getForObject(url, DTDataSheetModel.class, id);
    }
}
