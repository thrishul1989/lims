package com.todaysoft.lims.system.modules.bpm.dpcr.service.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.*;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.dpcr.service.IDataPcrService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class DataPcrService extends RestService implements IDataPcrService
{
    @Autowired
    private IMlpaDataDataService dataDataService;
    
    @Override
    public List<DataPcrTask> getDataPcrAssignableList(DataPcrAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcr/tasks/assignable");
        ResponseEntity<List<DataPcrTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<DataPcrAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<DataPcrTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public DataPcrAssignModel getDataPcrAssignModel(DataPcrAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcr/tasks/assigning");
        ResponseEntity<DataPcrAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataPcrAssignArgs>(args), new ParameterizedTypeReference<DataPcrAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignDataPcr(DataPcrAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcr/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public DataPcrSheetModel getDataPcrSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcr/sheets/{id}");
        return template.getForObject(url, DataPcrSheetModel.class, id);
    }
    
    @Override
    public void submitDataPcr(DataPcrSubmitRequest request)
    {
        List<DataPcrSubmitTaskArgs> successArgsList = Lists.newArrayList();
        DataAnalysisParseModel parseModel = request.getParseModel();
        if(null != parseModel) //为空的情况是没有上传东西
        {
            if(Collections3.isNotEmpty(parseModel.getDpcrRecords()))
            {
                for(DataPcrSubmitTaskArgs record:parseModel.getDpcrRecords())
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

        String url = GatewayService.getServiceUrl("/bpm/testing/dataPcr/sheets/submit");
        template.postForLocation(url, request);
    }
}
