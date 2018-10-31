package com.todaysoft.lims.system.modules.smm.directive;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.system.service.IResourceService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class SecurityResourceDirectiveModel implements TemplateDirectiveModel
{
    @Autowired
    private IResourceService service;
    
    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map parameters, TemplateModel[] loopVars, TemplateDirectiveBody body)
        throws TemplateException, IOException
    {
        String resource = ((TemplateModel)parameters.get("resource")).toString();
        boolean authorized = service.isGrantedResource(resource);
        
        if (authorized)
        {
            body.render(env.getOut());
        }
    }
}
