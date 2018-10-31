package com.todaysoft.lims.system.modules.bcm.directive;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bcm.model.TestingNodeReagentKitConfig;
import com.todaysoft.lims.system.modules.bcm.model.TestingNodeReagentKitConfigSearcher;
import com.todaysoft.lims.system.modules.bcm.service.ITestingNodeConfigService;
import com.todaysoft.lims.system.service.freemarker.VariablesDirectiveModel;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.Version;

@Component
public class TestingNodeReagentKitConfigsDirectiveModel extends VariablesDirectiveModel
{
    @Autowired
    private ITestingNodeConfigService service;
    
    @Override
    protected Map<String, TemplateModel> getVariables(Map<String, TemplateModel> parameters, Environment env)
        throws TemplateException
    {
        Map<String, TemplateModel> variables = super.getVariables(parameters, env);
        String node = getStringParameter("node", parameters);
        String sample = getStringParameter("sample", parameters);
        String prenode = getStringParameter("pre_node", parameters);
        
        if (StringUtils.isEmpty(node))
        {
            throw new TemplateException("Node value can not be empty.", env);
        }
        
        if (StringUtils.isEmpty(sample) && StringUtils.isEmpty(prenode))
        {
            throw new TemplateException("Sample value and prenode value can not be empty at the same time.", env);
        }
        
        TestingNodeReagentKitConfigSearcher searcher = new TestingNodeReagentKitConfigSearcher();
        searcher.setTestingNode(node);
        searcher.setTestingSample(sample);
        searcher.setTestingPrenode(prenode);
        
        List<TestingNodeReagentKitConfig> configs;
        
        try
        {
            configs = service.search(searcher);
            //去除任务配置中空置的试剂盒。
            Iterator<TestingNodeReagentKitConfig> testingNodeReagentKitConfigs = configs.iterator();
            while (testingNodeReagentKitConfigs.hasNext())
            {
                TestingNodeReagentKitConfig t = testingNodeReagentKitConfigs.next();
                if (StringUtils.isEmpty(t.getId()))
                {
                    testingNodeReagentKitConfigs.remove();
                }
            }
        }
        catch (Exception e)
        {
            configs = Collections.emptyList();
        }
        
        variables.put("kits", new DefaultObjectWrapperBuilder(new Version("2.3.23")).build().wrap(configs));
        return variables;
    }
}
