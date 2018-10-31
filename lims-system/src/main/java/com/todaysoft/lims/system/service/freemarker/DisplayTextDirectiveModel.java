package com.todaysoft.lims.system.service.freemarker;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.todaysoft.lims.utils.StringUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class DisplayTextDirectiveModel extends OutputDirectiveModel
{
    @Override
    protected String getOutputText(Map<String, TemplateModel> parameters, Environment env)
        throws TemplateException
    {
        String text = getStringParameter("text", parameters);
        Integer length = getIntegerParameter("length", parameters);
        String appends = getStringParameter("appends", parameters);
        
        length = null == length ? 20 : length;
        appends = StringUtils.isEmpty(appends) ? "..." : appends;
        
        if (StringUtils.isEmpty(text))
        {
            return "";
        }
        
        if (text.length() <= length)
        {
            return text;
        }
        
        String value = text.substring(0, length);
        return value + appends;
    }
}
