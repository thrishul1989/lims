package com.todaysoft.lims.system.modules.smm.directive;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.todaysoft.lims.system.service.freemarker.OutputDirectiveModel;
import com.todaysoft.lims.utils.FileUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class FileDownloadDirectiveModel  extends OutputDirectiveModel
{

    @Override
    protected String getOutputText(Map<String, TemplateModel> parameters, Environment env) throws TemplateException
    {
        String fileName = getStringParameter("fileName", parameters);
        String downloadUrl=FileUtils.getDownloadUrl(fileName);
        return downloadUrl;
    }

  
    
}
