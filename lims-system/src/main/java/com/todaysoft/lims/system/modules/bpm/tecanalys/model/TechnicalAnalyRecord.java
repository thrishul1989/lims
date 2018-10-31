package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.ExcelColumnField;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.ExcelColumnValueType;
import com.todaysoft.lims.utils.StringUtils;

import java.util.Map;

public class TechnicalAnalyRecord
{
    
    private String id;
    
    private String clinicalJudgment;//临床相关性判断
    
    private String mutationSource;//突变来源
    
    private String verify; //下一步-验证/不验证
    
    private String locusType; //位点类型-验证位点/纯阴性报告/参考位点
    
    private String verifyMethod; // 验证方法
    
    private String dataCode; //数据编号
    
    private String sample; // 样本编号
    
    private String geneSymbol; //突变基因
    
    private String chrLocation; //染色体位置
    
    private String refTranscript; //转录本号
    
    private String exon; //外显子 EXON
    
    private String nucleotideChanges; //核苷酸变化
    
    private String aminoAcidChanges; //氨基酸变化
    
    private String geneType; //纯合/杂合
    
    private String chromosome; //染色体
    
    private String beginLocus; //位置1
    
    private String endLocus; //位置2
    
    private String combineCode;//额外字段
    
    private String upRefGene;//上游基因
    
    private String downRefGene;//下游基因
    
    private String refSample;//参考样本
    
    // 页面判断用--
    private String temporaryId;
    
    private boolean valid;
    
    private String message;
    
    private TechnicalAnalySheetReceivedSample relatedSample;
    
    // --
    
    private Map<String, String> map = Maps.newHashMap();//其他多余字段不涉及代码的放map里面
    
    private String technicalFamilyGroupId;
    
    private String sourceRef;
    
    private String mutationObjectId;
    
    private String cnvResultId;
    
    public String getTechnicalFamilyGroupId()
    {
        return technicalFamilyGroupId;
    }
    
    public void setTechnicalFamilyGroupId(String technicalFamilyGroupId)
    {
        this.technicalFamilyGroupId = technicalFamilyGroupId;
    }
    
    public String getSourceRef()
    {
        return sourceRef;
    }
    
    public void setSourceRef(String sourceRef)
    {
        this.sourceRef = sourceRef;
    }
    
    public String getMutationObjectId()
    {
        return mutationObjectId;
    }
    
    public void setMutationObjectId(String mutationObjectId)
    {
        this.mutationObjectId = mutationObjectId;
    }
    
    public String getCnvResultId()
    {
        return cnvResultId;
    }
    
    public void setCnvResultId(String cnvResultId)
    {
        this.cnvResultId = cnvResultId;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getVerify()
    {
        return verify;
    }
    
    public void setVerify(String verify)
    {
        this.verify = verify;
    }
    
    public String getLocusType()
    {
        return locusType;
    }
    
    public void setLocusType(String locusType)
    {
        this.locusType = locusType;
    }
    
    public String getVerifyMethod()
    {
        return verifyMethod;
    }
    
    public void setVerifyMethod(String verifyMethod)
    {
        this.verifyMethod = verifyMethod;
    }
    
    public String getDataCode()
    {
        return dataCode;
    }
    
    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }
    
    public String getSample()
    {
        return sample;
    }
    
    public void setSample(String sample)
    {
        this.sample = sample;
    }
    
    public String getGeneSymbol()
    {
        return geneSymbol;
    }
    
    public void setGeneSymbol(String geneSymbol)
    {
        this.geneSymbol = geneSymbol;
    }
    
    public String getChrLocation()
    {
        return chrLocation;
    }
    
    public void setChrLocation(String chrLocation)
    {
        this.chrLocation = chrLocation;
    }
    
    public String getRefTranscript()
    {
        return refTranscript;
    }
    
    public void setRefTranscript(String refTranscript)
    {
        this.refTranscript = refTranscript;
    }
    
    public String getExon()
    {
        return exon;
    }
    
    public void setExon(String exon)
    {
        this.exon = exon;
    }
    
    public String getNucleotideChanges()
    {
        return nucleotideChanges;
    }
    
    public void setNucleotideChanges(String nucleotideChanges)
    {
        this.nucleotideChanges = nucleotideChanges;
    }
    
    public String getAminoAcidChanges()
    {
        return aminoAcidChanges;
    }
    
    public void setAminoAcidChanges(String aminoAcidChanges)
    {
        this.aminoAcidChanges = aminoAcidChanges;
    }
    
    public String getChromosome()
    {
        return chromosome;
    }
    
    public void setChromosome(String chromosome)
    {
        this.chromosome = chromosome;
    }
    
    public String getBeginLocus()
    {
        return beginLocus;
    }
    
    public void setBeginLocus(String beginLocus)
    {
        this.beginLocus = beginLocus;
    }
    
    public String getEndLocus()
    {
        return endLocus;
    }
    
    public void setEndLocus(String endLocus)
    {
        this.endLocus = endLocus;
    }
    
    public String getClinicalJudgment()
    {
        return clinicalJudgment;
    }
    
    public void setClinicalJudgment(String clinicalJudgment)
    {
        this.clinicalJudgment = clinicalJudgment;
    }
    
    public String getMutationSource()
    {
        return mutationSource;
    }
    
    public void setMutationSource(String mutationSource)
    {
        this.mutationSource = mutationSource;
    }
    
    public Map<String, String> getMap()
    {
        return map;
    }
    
    public void setMap(Map<String, String> map)
    {
        this.map = map;
    }
    
    public String getGeneType()
    {
        return geneType;
    }
    
    public void setGeneType(String geneType)
    {
        this.geneType = geneType;
    }
    
    public String getTemporaryId()
    {
        return temporaryId;
    }
    
    public void setTemporaryId(String temporaryId)
    {
        this.temporaryId = temporaryId;
    }
    
    public boolean isVerify()
    {
        return StringUtils.isNotEmpty(verify) && "验证".equals(verify);
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public TechnicalAnalySheetReceivedSample getRelatedSample()
    {
        return relatedSample;
    }
    
    public void setRelatedSample(TechnicalAnalySheetReceivedSample relatedSample)
    {
        this.relatedSample = relatedSample;
    }
    
    public boolean isValid()
    {
        return valid;
    }
    
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
    
    public String getUpRefGene()
    {
        return upRefGene;
    }
    
    public void setUpRefGene(String upRefGene)
    {
        this.upRefGene = upRefGene;
    }
    
    public String getDownRefGene()
    {
        return downRefGene;
    }
    
    public void setDownRefGene(String downRefGene)
    {
        this.downRefGene = downRefGene;
    }
    
    public String getRefSample()
    {
        return refSample;
    }
    
    public void setRefSample(String refSample)
    {
        this.refSample = refSample;
    }
}
