package com.todaysoft.lims.system.modules.smm.directive;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.todaysoft.lims.system.service.freemarker.OutputDirectiveModel;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class AmountToYuanDirectiveModal extends OutputDirectiveModel
{
    
    @Override
    protected String getOutputText(Map<String, TemplateModel> parameters, Environment env) throws TemplateException
    {
        
        String amount = getStringParameter("amount", parameters) == null ? "0" : getStringParameter("amount", parameters);
        
        Double f = Double.valueOf(amount == null ? "0" : amount);
        
        BigDecimal ff = new BigDecimal(f).divide(new BigDecimal(100));
        
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(ff);
        
    }
}
