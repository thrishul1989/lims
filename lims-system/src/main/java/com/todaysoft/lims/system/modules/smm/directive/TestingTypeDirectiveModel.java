package com.todaysoft.lims.system.modules.smm.directive;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.system.service.freemarker.OutputDirectiveModel;
import com.todaysoft.lims.utils.FileUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Component
public class TestingTypeDirectiveModel extends OutputDirectiveModel
{
    @Autowired
    private ITestingTypeService testingTypeService;

    @Override
    protected String getOutputText(Map<String, TemplateModel> parameters, Environment env) throws TemplateException
    {
        String id = getStringParameter("testingType", parameters);
        TestingType testingType = testingTypeService.get(id);
       
        return testingType.getName();
    }
    
}
