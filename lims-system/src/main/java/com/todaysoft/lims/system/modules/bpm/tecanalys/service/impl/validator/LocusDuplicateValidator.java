package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseRecord;

public class LocusDuplicateValidator implements ITechnicalAnalyRecordValidator
{
    @Override
    public void validate(TechnicalAnalyRecordValidateContext context)
    {
        TechnicalAnalyParseRecord target = context.getValidateTarget();
        String locusType = target.getData().getLocusType();
        
        if ("纯阴性报告".equals(locusType))
        {
            return;
        }
        
        String sample = target.getData().getSample();
        String verifyMethod = target.getData().getVerifyMethod();
        String geneSymbol = target.getData().getGeneSymbol();
        String location = target.getData().getChrLocation();
        
        StringBuffer key = new StringBuffer(128);
        key.append(StringUtils.isEmpty(sample) ? "" : sample);
        key.append("_");
        key.append(StringUtils.isEmpty(locusType) ? "" : locusType);
        key.append("_");
        key.append(StringUtils.isEmpty(verifyMethod) ? "" : verifyMethod);
        key.append("_");
        key.append(StringUtils.isEmpty(geneSymbol) ? "" : geneSymbol);
        key.append("_");
        key.append(StringUtils.isEmpty(location) ? "" : location);
        
        if (context.isDuplicate(DigestUtils.md5Hex(key.toString())))
        {
            target.setValid(false);
            target.setMessage("重复记录");
            return;
        }
    }
}
