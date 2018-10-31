package com.todaysoft.lims.maintain.model;


import com.google.common.collect.Maps;

import java.util.Map;

public class FluoDataSubmitTaskSuccessArgs {

    private String sampleCode;

    private String gene;

    private String chromosomeLocation;

    private String transcriptCode;//转卢本号

    private String exon;

    private String nucleotideChange;//核苷酸变化

    private String aminoAcidChange;//氨基酸号

    private String pureHeteroNucleus;//纯核/杂核

    private String remark;

    private String negaOrPositive;//阴性/阳性

    private String conclusion;

    private Map<String,String> map = Maps.newHashMap();//其他多余字段不涉及代码的放map里面

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

    public String getTranscriptCode() {
        return transcriptCode;
    }

    public void setTranscriptCode(String transcriptCode) {
        this.transcriptCode = transcriptCode;
    }

    public String getAminoAcidChange() {
        return aminoAcidChange;
    }

    public void setAminoAcidChange(String aminoAcidChange) {
        this.aminoAcidChange = aminoAcidChange;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
