package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseRecord;

public class VerifyMethodColumnValidator implements ITechnicalAnalyRecordValidator
{
    @Override
    public void validate(TechnicalAnalyRecordValidateContext context)
    {
        TechnicalAnalyParseRecord target = context.getValidateTarget();
        String verify = target.getData().getVerify();
        String locusType = target.getData().getLocusType();
        
        if (!"验证".equals(verify) && !"验证位点".equals(locusType))
        {
            return;
        }
        
        Set<String> values = new HashSet<String>();
        values.add("Sanger");
        values.add("PCR-NGS");
        values.add("QPCR");
        values.add("MLPA");
        
        String verifyMethod = target.getData().getVerifyMethod();
        
        if (StringUtils.isEmpty(verifyMethod))
        {
            target.setValid(false);
            target.setMessage("验证方法字段为空");
            return;
        }
        
        if (!values.contains(verifyMethod))
        {
            target.setValid(false);
            target.setMessage("验证方法字段值无效");
            return;
        }
    }
}
