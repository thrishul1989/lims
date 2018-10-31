package com.todaysoft.lims.system.modules.bpm.secondpcr.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRAssignArgs;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRAssignModel;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRAssignRequest;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRTask;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SixNineInfoModel;
import com.todaysoft.lims.system.modules.bpm.secondpcr.service.ISecondPCRService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SecondPCRService extends RestService implements ISecondPCRService
{
    
    @Override
    public List<SecondPCRTask> getSecondPCRAssignableList(SecondPCRAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/secondPcr/tasks/assignable");
        ResponseEntity<List<SecondPCRTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<SecondPCRAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<SecondPCRTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public SecondPCRAssignModel getSecondPCRAssignModel(SecondPCRAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/secondPcr/tasks/assigning");
        ResponseEntity<SecondPCRAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SecondPCRAssignArgs>(args), new ParameterizedTypeReference<SecondPCRAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignSecondPCR(SecondPCRAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/secondPcr/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public SecondPCRSheetModel getSecondPCRSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/secondPcr/sheets/{id}");
        SecondPCRSheetModel model = template.getForObject(url, SecondPCRSheetModel.class, id);
        
        List<SixNineInfoModel> sixNineInfoList = Lists.newArrayList();
        List<String> columeNum = Lists.newArrayList("A", "B", "C", "D", "E", "F", "G", "H");
        for (String colume : columeNum)
        {
            SixNineInfoModel sixNineModel = new SixNineInfoModel();
            List<String> lineNumList = Lists.newArrayList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
            List<String> combineCodeList = Lists.newArrayList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
            for (SecondPCRTask task : model.getTasks())
            {
                if (StringUtils.isNotEmpty(task.getSixNineInfo()) && StringUtils.isNotEmpty(task.getSpcrTestCode()))
                {
                    if (task.getSpcrTestCode().contains(colume))
                    {
                        String line = task.getSpcrTestCode().substring(1);
                        switch (line)
                        {
                            case "1":
                                lineNumList.set(0, task.getSixNineInfo());
                                combineCodeList.set(0, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "2":
                                lineNumList.set(1, task.getSixNineInfo());
                                combineCodeList.set(1, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "3":
                                lineNumList.set(2, task.getSixNineInfo());
                                combineCodeList.set(2, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "4":
                                lineNumList.set(3, task.getSixNineInfo());
                                combineCodeList.set(3, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "5":
                                lineNumList.set(4, task.getSixNineInfo());
                                combineCodeList.set(4, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "6":
                                lineNumList.set(5, task.getSixNineInfo());
                                combineCodeList.set(5, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "7":
                                lineNumList.set(6, task.getSixNineInfo());
                                combineCodeList.set(6, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "8":
                                lineNumList.set(7, task.getSixNineInfo());
                                combineCodeList.set(7, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "9":
                                lineNumList.set(8, task.getSixNineInfo());
                                combineCodeList.set(8, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "10":
                                lineNumList.set(9, task.getSixNineInfo());
                                combineCodeList.set(9, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "11":
                                lineNumList.set(10, task.getSixNineInfo());
                                combineCodeList.set(10, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                            case "12":
                                lineNumList.set(11, task.getSixNineInfo());
                                combineCodeList.set(11, task.getCombineCode() + "_" + task.getForwardPrimerCode());
                                break;
                        }
                    }
                }
            }
            sixNineModel.setLineNum(lineNumList);
            sixNineModel.setCombineCodeList(combineCodeList);
            sixNineModel.setColumeNum(colume);
            sixNineInfoList.add(sixNineModel);
        }
        model.setSixNineModelList(sixNineInfoList);
        return model;
    }
    
    @Override
    public void submitSecondPCR(SecondPCRSubmitRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/secondPcr/sheets/submit");
        template.postForLocation(url, request);
    }
}
