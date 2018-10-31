package com.todaysoft.lims.system.modules.bcm.directive;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bcm.model.TestingNodeUserGroupConfig;
import com.todaysoft.lims.system.modules.bcm.model.TestingNodeUserGroupConfigSearcher;
import com.todaysoft.lims.system.modules.bcm.service.ITestingNodeConfigService;
import com.todaysoft.lims.system.service.freemarker.VariablesDirectiveModel;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.Version;

@Component
public class TestingNodeUserGroupConfigsDirectiveModel extends VariablesDirectiveModel
{
    @Autowired
    private ITestingNodeConfigService service;
    
    @Override
    protected Map<String, TemplateModel> getVariables(Map<String, TemplateModel> parameters, Environment env)
        throws TemplateException
    {
        Map<String, TemplateModel> variables = super.getVariables(parameters, env);
        String semantic = getStringParameter("semantic", parameters);
        if (StringUtils.isEmpty(semantic))
        {
            throw new TemplateException("Node value can not be empty.", env);
        }
        
        TestingNodeUserGroupConfigSearcher searcher = new TestingNodeUserGroupConfigSearcher();
        searcher.setSemantic(semantic);
        List<TestingNodeUserGroupConfig> configs;
        
        try
        {
            configs = service.List(searcher);
        }
        catch (Exception e)
        {
            configs = Collections.emptyList();
        }
        
        variables.put("members", new DefaultObjectWrapperBuilder(new Version("2.3.23")).build().wrap(configs));
        return variables;
    }
}
