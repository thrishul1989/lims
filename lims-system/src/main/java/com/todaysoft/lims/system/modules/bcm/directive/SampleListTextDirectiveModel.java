package com.todaysoft.lims.system.modules.bcm.directive;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.service.freemarker.OutputDirectiveModel;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class SampleListTextDirectiveModel extends OutputDirectiveModel
{
    @Autowired
    private IMetadataSampleService service;
    
    @Override
    protected String getOutputText(Map<String, TemplateModel> parameters, Environment env) throws TemplateException
    {
        
        String value = getStringParameter("id", parameters);
        
        try
        {
            MetadataSample entry = service.get(value);
            return null == entry ? (StringUtils.isEmpty(value) ? "" : value) : entry.getName();
        }
        catch (Exception e)
        {
            return StringUtils.isEmpty(value) ? "" : value;
        }
    }
}
