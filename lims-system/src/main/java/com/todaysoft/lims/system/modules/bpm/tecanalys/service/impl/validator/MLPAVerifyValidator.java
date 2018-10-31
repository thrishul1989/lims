package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseRecord;

public class MLPAVerifyValidator implements ITechnicalAnalyRecordValidator
{
    @Override
    public void validate(TechnicalAnalyRecordValidateContext context)
    {
        TechnicalAnalyParseRecord target = context.getValidateTarget();
        String verifyMethod = target.getData().getVerifyMethod();
        
        if (!"MLPA".equalsIgnoreCase(verifyMethod))
        {
            return;
        }
        
        if (StringUtils.isEmpty(target.getData().getGeneSymbol()))
        {
            target.setValid(false);
            target.setMessage("MLPA验证，突变基因字段为空");
            return;
        }
        
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(target.getData().getChrLocation()))
        {
            String[] location = target.getData().getChrLocation().split("-");
            if (location.length < 2 || com.todaysoft.lims.utils.StringUtils.isEmpty(target.getData().getChromosome())
                || com.todaysoft.lims.utils.StringUtils.isEmpty(target.getData().getBeginLocus()))
            {
                target.setValid(false);
                target.setMessage("染色体位置不正确");
                return;
            }
            if (!location[0].equals(target.getData().getChromosome()) || !location[1].equals(target.getData().getBeginLocus()))
            {
                target.setValid(false);
                target.setMessage("染色体位置不正确");
                return;
            }
        }
        
        
    }
}
