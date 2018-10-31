package com.todaysoft.lims.testing.report.model;


import com.google.common.collect.Maps;
import com.todaysoft.lims.testing.base.entity.DataTemplate;

import java.util.Map;

public class MlpaDataInfo
{
    private String sampleCode;

    private String gene;

    private String chromosomeLocation;

    private String mutationSource;//突变来源

    private String exon;

    private String nucleotideChange;//核苷酸变化

    private String pureHeteroNucleus;//纯核/杂核

    private String remark;

    private String negaOrPositive;//阴性/阳性

    private String conclusion;

    private Map<String,String> map = Maps.newHashMap();

    private DataTemplate dataTemplate;

    public DataTemplate getDataTemplate() {
        return dataTemplate;
    }

    public void setDataTemplate(DataTemplate dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public String getChromosomeLocation() {
        return chromosomeLocation;
    }

    public void setChromosomeLocation(String chromosomeLocation) {
        this.chromosomeLocation = chromosomeLocation;
    }

    public String getMutationSource() {
        return mutationSource;
    }

    public void setMutationSource(String mutationSource) {
        this.mutationSource = mutationSource;
    }

    public String getExon() {
        return exon;
    }

    public void setExon(String exon) {
        this.exon = exon;
    }

    public String getNucleotideChange() {
        return nucleotideChange;
    }

    public void setNucleotideChange(String nucleotideChange) {
        this.nucleotideChange = nucleotideChange;
    }

    public String getPureHeteroNucleus() {
        return pureHeteroNucleus;
    }

    public void setPureHeteroNucleus(String pureHeteroNucleus) {
        this.pureHeteroNucleus = pureHeteroNucleus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNegaOrPositive() {
        return negaOrPositive;
    }

    public void setNegaOrPositive(String negaOrPositive) {
        this.negaOrPositive = negaOrPositive;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
