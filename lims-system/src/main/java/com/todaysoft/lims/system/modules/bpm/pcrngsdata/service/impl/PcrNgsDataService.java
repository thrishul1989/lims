package com.todaysoft.lims.system.modules.bpm.pcrngsdata.service.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSubmitTaskSuccessArgs;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.service.IPcrNgsDataService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.*;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class PcrNgsDataService extends RestService implements IPcrNgsDataService
{

    @Autowired
    private IMlpaDataDataService dataDataService;

    @Override
    public List<PcrNgsDataTask> getDataPcrAssignableList(PcrNgsDataAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgsData/tasks/assignable");
        ResponseEntity<List<PcrNgsDataTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PcrNgsDataAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<PcrNgsDataTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public PcrNgsDataAssignModel getDataPcrAssignModel(PcrNgsDataAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgsData/tasks/assigning");
        ResponseEntity<PcrNgsDataAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PcrNgsDataAssignArgs>(args), new ParameterizedTypeReference<PcrNgsDataAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignDataPcr(PcrNgsDataAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgsData/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public PcrNgsDataSheetModel getDataPcrSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgsData/sheets/{id}");
        return template.getForObject(url, PcrNgsDataSheetModel.class, id);
    }
    
    @Override
    public void submitDataPcr(PcrNgsDataSubmitRequest request)
    {
        List<PcrNgsDataSubmitTaskArgs> successArgsList = Lists.newArrayList();
        DataAnalysisParseModel parseModel = request.getParseModel();
        if(null != parseModel) //为空的情况是没有上传东西
        {
            if(Collections3.isNotEmpty(parseModel.getPcrNgsRecords()))
            {
                for(PcrNgsDataSubmitTaskArgs record:parseModel.getPcrNgsRecords())
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
        request.setTasks(successArgsList);

        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgsData/sheets/submit");
        template.postForLocation(url, request);
    }
}
