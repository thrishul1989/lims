package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseRecord;

public class ReportLocusValidator implements ITechnicalAnalyRecordValidator
{
    @Override
    public void validate(TechnicalAnalyRecordValidateContext context)
    {
        TechnicalAnalyParseRecord target = context.getValidateTarget();
        String locusType = target.getData().getLocusType();
        
        if (!"报告位点".equalsIgnoreCase(locusType))
        {
            return;
        }
        
        if (StringUtils.isEmpty(target.getData().getGeneSymbol()))
        {
            target.setValid(false);
            target.setMessage("报告位点，突变基因字段为空");
            return;
        }
        
        if (StringUtils.isEmpty(target.getData().getChrLocation()))
        {
            target.setValid(false);
            target.setMessage("报告位点，染色体位置字段为空");
            return;
        }
        
        if (StringUtils.isEmpty(target.getData().getChromosome()))
        {
            target.setValid(false);
            target.setMessage("报告位点，染色体字段为空");
            return;
        }
        
        if (StringUtils.isEmpty(target.getData().getBeginLocus()))
        {
            target.setValid(false);
            target.setMessage("报告位点，位置1字段为空");
            return;
        }
    }
}
