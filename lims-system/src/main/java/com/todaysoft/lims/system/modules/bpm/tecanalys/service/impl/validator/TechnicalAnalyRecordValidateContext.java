package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseRecord;

public class TechnicalAnalyRecordValidateContext
{
    private TechnicalAnalyParseRecord target;
    
    private Set<String> validSamples;
    
    private Set<String> validKeys = new HashSet<String>();

    private List<String> combineStrList = Lists.newArrayList();

    private Map<String,Boolean> map = Maps.newHashMap();
    
    public boolean isDuplicate(String key)
    {
        return validKeys.contains(key);
    }
    
    public TechnicalAnalyRecordValidateContext(Set<String> validSamples,List<String> combineStrList,Map<String,Boolean> map)
    {
        this.validSamples = validSamples;
        this.combineStrList = combineStrList;
        this.map = map;
    }

    public List<String> getValidateDataCode()
    {
        return combineStrList;
    }

    public Map<String,Boolean> getValidateSubmitted()
    {
        return map;
    }

    
    public TechnicalAnalyParseRecord getValidateTarget()
    {
        return target;
    }
    
    public Set<String> getValidSamples()
    {
        return validSamples;
    }
    
    public void setTarget(TechnicalAnalyParseRecord target)
    {
        this.target = target;
    }
    
    public void valid()
    {
        if (null == target)
        {
            return;
        }
        
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
        
        validKeys.add(DigestUtils.md5Hex(key.toString()));
    }
}
