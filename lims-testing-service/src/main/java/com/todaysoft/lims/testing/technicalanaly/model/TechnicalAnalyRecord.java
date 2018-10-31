package com.todaysoft.lims.testing.technicalanaly.model;

import com.google.common.collect.Maps;

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

    private String upRefGene;

    private String downRefGene;

    private String refSample;

    private Map<String,String> map = Maps.newHashMap();//其他多余字段不涉及代码的放map里面

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClinicalJudgment() {
        return clinicalJudgment;
    }

    public void setClinicalJudgment(String clinicalJudgment) {
        this.clinicalJudgment = clinicalJudgment;
    }

    public String getMutationSource() {
        return mutationSource;
    }

    public void setMutationSource(String mutationSource) {
        this.mutationSource = mutationSource;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getLocusType() {
        return locusType;
    }

    public void setLocusType(String locusType) {
        this.locusType = locusType;
    }

    public String getVerifyMethod() {
        return verifyMethod;
    }

    public void setVerifyMethod(String verifyMethod) {
        this.verifyMethod = verifyMethod;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public String getChrLocation() {
        return chrLocation;
    }

    public void setChrLocation(String chrLocation) {
        this.chrLocation = chrLocation;
    }

    public String getRefTranscript() {
        return refTranscript;
    }

    public void setRefTranscript(String refTranscript) {
        this.refTranscript = refTranscript;
    }

    public String getExon() {
        return exon;
    }

    public void setExon(String exon) {
        this.exon = exon;
    }

    public String getNucleotideChanges() {
        return nucleotideChanges;
    }

    public void setNucleotideChanges(String nucleotideChanges) {
        this.nucleotideChanges = nucleotideChanges;
    }

    public String getAminoAcidChanges() {
        return aminoAcidChanges;
    }

    public void setAminoAcidChanges(String aminoAcidChanges) {
        this.aminoAcidChanges = aminoAcidChanges;
    }

    public String getGeneType() {
        return geneType;
    }

    public void setGeneType(String geneType) {
        this.geneType = geneType;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getBeginLocus() {
        return beginLocus;
    }

    public void setBeginLocus(String beginLocus) {
        this.beginLocus = beginLocus;
    }

    public String getEndLocus() {
        return endLocus;
    }

    public void setEndLocus(String endLocus) {
        this.endLocus = endLocus;
    }

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }

    public String getUpRefGene() {
        return upRefGene;
    }

    public void setUpRefGene(String upRefGene) {
        this.upRefGene = upRefGene;
    }

    public String getDownRefGene() {
        return downRefGene;
    }

    public void setDownRefGene(String downRefGene) {
        this.downRefGene = downRefGene;
    }

    public String getRefSample() {
        return refSample;
    }

    public void setRefSample(String refSample) {
        this.refSample = refSample;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
