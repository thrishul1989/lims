package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseRecord;

public class LocusTypeColumnValidator implements ITechnicalAnalyRecordValidator
{
    @Override
    public void validate(TechnicalAnalyRecordValidateContext context)
    {
        Set<String> values = new HashSet<String>();
        values.add("验证位点");
        values.add("报告位点");
        values.add("纯阴性报告");
        values.add("参考位点");
        values.add("缺失重复位点");
        
        TechnicalAnalyParseRecord target = context.getValidateTarget();
        String locusType = target.getData().getLocusType();
        
        if (StringUtils.isEmpty(locusType))
        {
            target.setValid(false);
            target.setMessage("位点类型字段为空");
            return;
        }
        
        if (!values.contains(locusType))
        {
            target.setValid(false);
            target.setMessage("位点类型字段值无效");
            return;
        }
        
        String verify = target.getData().getVerify();
        
        if ("不验证".equals(verify) && "验证位点".equals(locusType))
        {
            target.setValid(false);
            target.setMessage("是否验证/位点类型字段不匹配");
            return;
        }
        
        if ("验证".equals(verify) && !"验证位点".equals(locusType))
        {
            target.setValid(false);
            target.setMessage("是否验证/位点类型字段不匹配");
            return;
        }
    }
}
