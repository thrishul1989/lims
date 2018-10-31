package com.todaysoft.lims.system.modules.smm.directive;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.smm.model.Dict;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.service.freemarker.OutputDirectiveModel;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class DictEntryTextDirectiveModel extends OutputDirectiveModel
{
    @Autowired
    private IDictService service;
    
    @Override
    protected String getOutputText(Map<String, TemplateModel> parameters, Environment env)
        throws TemplateException
    {
        String category = getStringParameter("category", parameters);
        String value = getStringParameter("value", parameters);
        
        if (StringUtils.isEmpty(category))
        {
            throw new TemplateException("Categogy Can not be empty.", env);
        }
        
        if (StringUtils.isEmpty(value))
        {
            return "";
        }
        
        try
        {
            Dict entry = service.getEntry(category, value);
            return null == entry ? (StringUtils.isEmpty(value) ? "" : value) : entry.getText();
        }
        catch (Exception e)
        {
            return StringUtils.isEmpty(value) ? "" : value;
        }
    }
}
