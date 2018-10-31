package com.todaysoft.lims.system.modules.bpm.directive;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.system.modules.bpm.tecanalys.service.ITechnicalAnalyService;
import com.todaysoft.lims.system.service.freemarker.OutputDirectiveModel;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class FreeMarkCollectionCountModel extends OutputDirectiveModel
{
    
    @Autowired
    private ITechnicalAnalyService technicalAnalyService;
    
    @Override
    protected String getOutputText(Map<String, TemplateModel> parameters, Environment env) throws TemplateException
    {
        String analysisSampleId = parameters.get("analysisSampleId").toString();
        TemplateModel model=parameters.get("technicalTaskId");
        Integer Count =0;
        if(model!=null) {
            String technicalTaskId=parameters.get("technicalTaskId").toString();
             Count = technicalAnalyService.myCollectionCount(analysisSampleId, technicalTaskId); 
        }
        
        return Count.toString();
    }
    
}
