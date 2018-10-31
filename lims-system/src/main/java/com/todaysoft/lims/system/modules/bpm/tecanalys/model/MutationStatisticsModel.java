package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

public class MutationStatisticsModel
{
    private String sample;
    
    private String rawDataBases;//原始数据产量
    
    private String cleanDataBases;//质控后合格数据量
    
    private String alignedBases;//比对到基因组的数据量
    
    private String aligned;//比对比例
    
    private String initialBases;//目标区覆盖大小
    
    private String baseCovered;//目标区域覆盖大小
    
    private String coverageRegion;//覆盖度
    
    private String effectiveBases;//有效数据量
    
    private String averageSequencingDepth;//平均测序深度
    
    private String fractionEffectiveBases;//捕获效率
    
    private String duplicationRate;//PCR冗余度
    
    private String least4X;
    
    private String least10X;
    
    private String least20X;
    
    private String least50X;
    
    private String least100X;
    
    private String chip;//产品坐标
    
    private String q30;//Q30
    
    private String leastOne;//最多3个覆盖度
    
    private String leastTwo;
    
    private String leastThree;
    
    public String getSample()
    {
        return sample;
    }
    
    public void setSample(String sample)
    {
        this.sample = sample;
    }
    
    public String getRawDataBases()
    {
        return rawDataBases;
    }
    
    public void setRawDataBases(String rawDataBases)
    {
        this.rawDataBases = rawDataBases;
    }
    
    public String getCleanDataBases()
    {
        return cleanDataBases;
    }
    
    public void setCleanDataBases(String cleanDataBases)
    {
        this.cleanDataBases = cleanDataBases;
    }
    
    public String getAlignedBases()
    {
        return alignedBases;
    }
    
    public void setAlignedBases(String alignedBases)
    {
        this.alignedBases = alignedBases;
    }
    
    public String getAligned()
    {
        return aligned;
    }
    
    public void setAligned(String aligned)
    {
        this.aligned = aligned;
    }
    
    public String getInitialBases()
    {
        return initialBases;
    }
    
    public void setInitialBases(String initialBases)
    {
        this.initialBases = initialBases;
    }
    
    public String getBaseCovered()
    {
        return baseCovered;
    }
    
    public void setBaseCovered(String baseCovered)
    {
        this.baseCovered = baseCovered;
    }
    
    public String getCoverageRegion()
    {
        return coverageRegion;
    }
    
    public void setCoverageRegion(String coverageRegion)
    {
        this.coverageRegion = coverageRegion;
    }
    
    public String getEffectiveBases()
    {
        return effectiveBases;
    }
    
    public void setEffectiveBases(String effectiveBases)
    {
        this.effectiveBases = effectiveBases;
    }
    
    public String getFractionEffectiveBases()
    {
        return fractionEffectiveBases;
    }
    
    public void setFractionEffectiveBases(String fractionEffectiveBases)
    {
        this.fractionEffectiveBases = fractionEffectiveBases;
    }
    
    public String getAverageSequencingDepth()
    {
        return averageSequencingDepth;
    }
    
    public void setAverageSequencingDepth(String averageSequencingDepth)
    {
        this.averageSequencingDepth = averageSequencingDepth;
    }
    
    public String getLeast4X()
    {
        return least4X;
    }
    
    public void setLeast4X(String least4x)
    {
        least4X = least4x;
    }
    
    public String getLeast10X()
    {
        return least10X;
    }
    
    public void setLeast10X(String least10x)
    {
        least10X = least10x;
    }
    
    public String getLeast20X()
    {
        return least20X;
    }
    
    public void setLeast20X(String least20x)
    {
        least20X = least20x;
    }
    
    public String getDuplicationRate()
    {
        return duplicationRate;
    }
    
    public void setDuplicationRate(String duplicationRate)
    {
        this.duplicationRate = duplicationRate;
    }
    
    public String getChip()
    {
        return chip;
    }
    
    public void setChip(String chip)
    {
        this.chip = chip;
    }
    
    public String getQ30()
    {
        return q30;
    }
    
    public void setQ30(String q30)
    {
        this.q30 = q30;
    }
    
    public String getLeastOne()
    {
        return leastOne;
    }
    
    public void setLeastOne(String leastOne)
    {
        this.leastOne = leastOne;
    }
    
    public String getLeastTwo()
    {
        return leastTwo;
    }
    
    public void setLeastTwo(String leastTwo)
    {
        this.leastTwo = leastTwo;
    }
    
    public String getLeastThree()
    {
        return leastThree;
    }
    
    public void setLeastThree(String leastThree)
    {
        this.leastThree = leastThree;
    }
    
    public String getLeast50X()
    {
        return least50X;
    }
    
    public void setLeast50X(String least50x)
    {
        least50X = least50x;
    }
    
    public String getLeast100X()
    {
        return least100X;
    }
    
    public void setLeast100X(String least100x)
    {
        least100X = least100x;
    }
    
}
