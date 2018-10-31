package com.todaysoft.lims.system.modules.bpm.qpcr.service.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataDataService;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.*;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.qpcr.service.IQpcrService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class QpcrService extends RestService implements IQpcrService
{
    @Autowired
    private IMlpaDataDataService dataDataService;

    @Override
    public List<QpcrTask> getQpcrAssignableList(QpcrAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/qpcr/tasks/assignable");
        ResponseEntity<List<QpcrTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<QpcrAssignableTaskSearcher>(searcher), new ParameterizedTypeReference<List<QpcrTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public QpcrAssignModel getQpcrAssignModel(QpcrAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/qpcr/tasks/assigning");
        ResponseEntity<QpcrAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<QpcrAssignArgs>(args), new ParameterizedTypeReference<QpcrAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignQpcr(QpcrAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/qpcr/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public QpcrSubmitModel getQpcrSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/qpcr/sheets/{id}");
        return template.getForObject(url, QpcrSubmitModel.class, id);
    }
    
    @Override
    public void submitQpcr(QpcrSubmitRequest request)
    {
        List<QpcrSubmitTask> successArgsList = Lists.newArrayList();
        DataAnalysisParseModel parseModel = request.getParseModel();
        if(null != parseModel) //为空的情况是没有上传东西
        {
            if(Collections3.isNotEmpty(parseModel.getQpcrRecords()))
            {
                for(QpcrSubmitTask record:parseModel.getQpcrRecords())
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
        request.setSubTasks(successArgsList);
        String url = GatewayService.getServiceUrl("/bpm/testing/qpcr/sheets/submit");
        template.postForLocation(url, request);
    }
}
