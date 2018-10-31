package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseRecord;

public class SampleColumnValidator implements ITechnicalAnalyRecordValidator
{
    private static Logger log = LoggerFactory.getLogger(SampleColumnValidator.class);
    
    @Override
    public void validate(TechnicalAnalyRecordValidateContext context)
    {
        TechnicalAnalyParseRecord target = context.getValidateTarget();
        Set<String> validSamples = context.getValidSamples();
        
        String sample = target.getData().getSample();
        
        if (log.isInfoEnabled())
        {
            log.info("Validate sample code {}, valid samples {}", sample, validSamples);
        }
        
        if (StringUtils.isEmpty(sample))
        {
            target.setValid(false);
            target.setMessage("样本编号字段为空");
            return;
        }
        
        if (CollectionUtils.isEmpty(validSamples) || !validSamples.contains(sample))
        {
            target.setValid(false);
            target.setMessage("数据-样本编号在任务单中不存在或数据校验不合格");
            return;
        }
    }
}
