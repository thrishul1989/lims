package com.todaysoft.lims.system.modules.bcm.directive;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.system.modules.bcm.model.SampleSearcher;
import com.todaysoft.lims.system.modules.bcm.model.SampleSimpleModel;
import com.todaysoft.lims.system.modules.bcm.service.IMetadataSampleService;
import com.todaysoft.lims.system.service.freemarker.VariablesDirectiveModel;
import com.todaysoft.lims.utils.StringUtils;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.Version;

@Component
public class SampleListDirectiveModel extends VariablesDirectiveModel
{
    @Autowired
    private IMetadataSampleService service;
    
    @Override
    protected Map<String, TemplateModel> getVariables(Map<String, TemplateModel> parameters, Environment env) throws TemplateException
    {
        Map<String, TemplateModel> variables = super.getVariables(parameters, env);
        String type = getStringParameter("type", parameters);
        String isProduct = getStringParameter("isProduct", parameters);
        Boolean startable = getBooleanParameter("startable", parameters);
        SampleSearcher searcher = new SampleSearcher();
        searcher.setType(type);
        searcher.setStartable(startable);
        List<SampleSimpleModel> samples = service.search(searcher);
        
        if (null == samples)
        {
            samples = Collections.emptyList();
        }
        //如果是产品推荐样本，过滤掉CDNA
        if (StringUtils.isNotEmpty(isProduct))
        {
            Iterator<SampleSimpleModel> it = samples.iterator();
            while (it.hasNext())
            {
                if ("CDNA".equals(it.next().getName()))
                {
                    it.remove();
                    
                }
            }
        }
        
        variables.put("samples", new DefaultObjectWrapperBuilder(new Version("2.3.23")).build().wrap(samples));
        return variables;
    }
}
