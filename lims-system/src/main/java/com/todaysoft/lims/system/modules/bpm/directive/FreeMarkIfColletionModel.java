package com.todaysoft.lims.system.modules.bpm.directive;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.request.DoctorIfColletionRequest;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.ITechnicalAnalyService;
import com.todaysoft.lims.system.service.freemarker.OutputDirectiveModel;

import freemarker.core.Environment;
import freemarker.template.TemplateModel;

@Component
public class FreeMarkIfColletionModel extends OutputDirectiveModel
{
    
    @Autowired
    private ITechnicalAnalyService technicalAnalyService;
    
    @Override
    @SuppressWarnings("rawtypes")
    public String getOutputText(Map<String, TemplateModel> parameters, Environment env)
    {
        
        String id = ((TemplateModel)parameters.get("id")).toString();
        String tag = ((TemplateModel)parameters.get("tag")).toString();
       
        
        DoctorIfColletionRequest req = new DoctorIfColletionRequest();
        req.setId(id);
        req.setTag(tag);
       
        boolean flag = technicalAnalyService.ifColletion(req);
        if (flag)
        {
            return "/collection2.png";
        }
        
        return "/collection.png";
        
    }
}
