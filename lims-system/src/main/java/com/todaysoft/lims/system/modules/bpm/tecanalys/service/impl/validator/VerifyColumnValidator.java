package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseRecord;

public class VerifyColumnValidator implements ITechnicalAnalyRecordValidator
{
    @Override
    public void validate(TechnicalAnalyRecordValidateContext context)
    {
        Set<String> values = new HashSet<String>();
        values.add("验证");
        values.add("不验证");
        
        TechnicalAnalyParseRecord target = context.getValidateTarget();
        String verify = target.getData().getVerify();
        
        if (StringUtils.isEmpty(verify))
        {
            target.setValid(false);
            target.setMessage("是否验证字段为空");
            return;
        }
        
        if (!values.contains(verify))
        {
            target.setValid(false);
            target.setMessage("是否验证字段值无效");
            return;
        }
    }
}
