package com.todaysoft.lims.system.modules.bpm.mlpadata.service.impl;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.*;
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
import com.todaysoft.lims.system.modules.bpm.mlpadata.service.IMlpaDataService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class MlpaDataService extends RestService implements IMlpaDataService
{

    @Autowired
    private IMlpaDataDataService dataDataService;
    
    @Override
    public List<MlpaDataTask> getMlpaDataAssignableList(MlpaDataAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaData/tasks/assignable");
        ResponseEntity<List<MlpaDataTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<MlpaDataAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<MlpaDataTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<MlpaDataTask> getMlpaDataAssignModel(MlpaDataAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaData/tasks/assigning");
        ResponseEntity<List<MlpaDataTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<MlpaDataAssignArgs>(args), new ParameterizedTypeReference<List<MlpaDataTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignMlpaData(MlpaDataAssignRequest data)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaData/tasks/assign");
        template.postForLocation(url, data);
        
    }
    
    @Override
    public MlpaDataSheetModel getMlpaDataSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaData/sheets/{id}");
        return template.getForObject(url, MlpaDataSheetModel.class, id);
    }
    
    @Override
    public void submitSheet(MlpaDataSubmitSheetModel request)
    {
        List<MlpaDataSubmitTaskSuccessArgs> successArgsList = Lists.newArrayList();
        DataAnalysisParseModel parseModel = request.getParseModel();
        if(null != parseModel) //为空的情况是没有上传东西
        {
            if(Collections3.isNotEmpty(parseModel.getRecords()))
            {
                for(MlpaDataSubmitTaskSuccessArgs record:parseModel.getRecords())
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

        List<MlpaDataSubmitTaskModel> tasks = request.getTasks();
        Map<String, List<MlpaDataSubmitTaskSuccessArgs>> map = Maps.newHashMap();
        if (Collections3.isNotEmpty(successArgsList))
        {
            for (MlpaDataSubmitTaskSuccessArgs mdst : successArgsList)
            {
                if (map.containsKey(mdst.getSampleCode()))
                {
                    List<MlpaDataSubmitTaskSuccessArgs> list = map.get(mdst.getSampleCode());
                    list.add(mdst);
                    map.put(mdst.getSampleCode(), list);
                    
                }
                else
                {
                    List<MlpaDataSubmitTaskSuccessArgs> list = Lists.newArrayList();
                    list.add(mdst);
                    map.put(mdst.getSampleCode(), list);
                }
            }
            
            for (MlpaDataSubmitTaskModel taskArgs : tasks)
            {
                String sampleCode = taskArgs.getSampleCode();
                if (taskArgs.isQualified())
                {
                    taskArgs.setSuccessArgs(map.get(sampleCode));
                }
            }
        }
        //MLPA特殊处理 要判断出来是检测还是验证
        MlpaDataSheetModel model = getMlpaDataSubmitModel(request.getId());
        if(null != model)
        {
            if(Collections3.isNotEmpty(model.getTasks()))
            {
                request.setType(model.getTasks().get(0).getType());
            }
        }

        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaData/sheets/submit");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public MlpaDataSheetModel exportAnalySheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaData/exportAnalySheet/{id}");
        return template.getForObject(url, MlpaDataSheetModel.class, id);
    }
    
}
