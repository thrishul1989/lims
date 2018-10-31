package com.todaysoft.lims.technical.model;

import java.util.List;

public class TechnicalAnalysisAddVerify {

    private String id;

    private String clinicalJudgment;

    private String chrom;

    private String startLocation;

    private String endLocation;

    private String gene;

    private String nucleotide;

    private String aminoAcid;

    private String pure;

    private String verifyMethod;

    private String familyRelation;

    private String technicalAnalysisId;

    private String sampleCode;

    private String dataCode;

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

    public String getChrom() {
        return chrom;
    }

    public void setChrom(String chrom) {
        this.chrom = chrom;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public String getNucleotide() {
        return nucleotide;
    }

    public void setNucleotide(String nucleotide) {
        this.nucleotide = nucleotide;
    }

    public String getAminoAcid() {
        return aminoAcid;
    }

    public void setAminoAcid(String aminoAcid) {
        this.aminoAcid = aminoAcid;
    }

    public String getPure() {
        return pure;
    }

    public void setPure(String pure) {
        this.pure = pure;
    }

    public String getVerifyMethod() {
        return verifyMethod;
    }

    public void setVerifyMethod(String verifyMethod) {
        this.verifyMethod = verifyMethod;
    }

    public String getFamilyRelation() {
        return familyRelation;
    }

    public void setFamilyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
    }

    public String getTechnicalAnalysisId() {
        return technicalAnalysisId;
    }

    public void setTechnicalAnalysisId(String technicalAnalysisId) {
        this.technicalAnalysisId = technicalAnalysisId;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }
}