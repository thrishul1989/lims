package com.todaysoft.lims.system.modules.bpm.dpcrsanger.service.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.*;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.dpcrsanger.service.IDataPcrSangerService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class DataPcrSangerService extends RestService implements IDataPcrSangerService
{
    @Autowired
    private IMlpaDataDataService dataDataService;
    
    @Override
    public List<DataPcrSangerTask> getDataPcrAssignableList(DataPcrSangerAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcrSanger/tasks/assignable");
        ResponseEntity<List<DataPcrSangerTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<DataPcrSangerAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<DataPcrSangerTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public DataPcrSangerAssignModel getDataPcrAssignModel(DataPcrSangerAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcrSanger/tasks/assigning");
        ResponseEntity<DataPcrSangerAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataPcrSangerAssignArgs>(args), new ParameterizedTypeReference<DataPcrSangerAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignDataPcr(DataPcrSangerAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcrSanger/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public DataPcrSangerSheetModel getDataPcrSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcrSanger/sheets/{id}");
        return template.getForObject(url, DataPcrSangerSheetModel.class, id);
    }
    
    @Override
    public void submitDataPcr(DataPcrSangerSubmitRequest request)
    {
        List<DataPcrSangerSubmitTaskArgs> successArgsList = Lists.newArrayList();
        DataAnalysisParseModel parseModel = request.getParseModel();
        if(null != parseModel) //为空的情况是没有上传东西
        {
            if(Collections3.isNotEmpty(parseModel.getDpcrSangerRecords()))
            {
                for(DataPcrSangerSubmitTaskArgs record:parseModel.getDpcrSangerRecords())
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

        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcrSanger/sheets/submit");
        template.postForLocation(url, request);
    }
    
    @Override
    public DataPcrSangerSheetModel exportAnalySheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcrSanger/exportAnalySheet/{id}");
        return template.getForObject(url, DataPcrSangerSheetModel.class, id);
    }
}
