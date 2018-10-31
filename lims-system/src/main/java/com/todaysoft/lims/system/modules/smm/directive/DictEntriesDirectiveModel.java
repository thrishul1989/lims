package com.todaysoft.lims.system.modules.smm.directive;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.smm.model.Dict;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.service.freemarker.VariablesDirectiveModel;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.Version;

@Component
public class DictEntriesDirectiveModel extends VariablesDirectiveModel
{
    @Autowired
    private IDictService service;
    
    @Override
    protected Map<String, TemplateModel> getVariables(Map<String, TemplateModel> parameters, Environment env)
        throws TemplateException
    {
        Map<String, TemplateModel> variables = super.getVariables(parameters, env);
        String category = getStringParameter("category", parameters);
        
        if (!StringUtils.isEmpty(category))
        {
            try
            {
                List<Dict> entries = service.getEntries(category);
                variables.put("entries", new DefaultObjectWrapperBuilder(new Version("2.3.23")).build().wrap(entries));
            }
            catch (Exception e)
            {
                variables.put("entries", new DefaultObjectWrapperBuilder(new Version("2.3.23")).build().wrap(Collections.emptyList()));
            }
        }
        
        return variables;
    }
}
