package com.todaysoft.lims.technical.export.mybatis.model;

public class ReportExportMutationInfo
{
    private String id;

    private String taskId;

    private String objectId;

    private String clinicalJudgment;

    private String geneSymbol;

    private String chromosomalLocation;

    private String exon;

    private String nucleotideChanges;

    private String aminoAcidChanges;

    private String geneType;

    private String highestMafName;

    private String effect;

    private String pathogenicAnalysis;

    private String inhert;

    private String diseasePhenotype;

    private String inhertSourceName;
    
    private ReportExportMutationInfoExplain reportExportMutationInfoExplain;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
    }

    public String getClinicalJudgment() {
        return clinicalJudgment;
    }

    public void setClinicalJudgment(String clinicalJudgment) {
        this.clinicalJudgment = clinicalJudgment == null ? null : clinicalJudgment.trim();
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol == null ? null : geneSymbol.trim();
    }

    public String getChromosomalLocation() {
        return chromosomalLocation;
    }

    public void setChromosomalLocation(String chromosomalLocation) {
        this.chromosomalLocation = chromosomalLocation == null ? null : chromosomalLocation.trim();
    }

    public String getExon() {
        return exon;
    }

    public void setExon(String exon) {
        this.exon = exon == null ? null : exon.trim();
    }

    public String getNucleotideChanges() {
        return nucleotideChanges;
    }

    public void setNucleotideChanges(String nucleotideChanges) {
        this.nucleotideChanges = nucleotideChanges == null ? null : nucleotideChanges.trim();
    }

    public String getAminoAcidChanges() {
        return aminoAcidChanges;
    }

    public void setAminoAcidChanges(String aminoAcidChanges) {
        this.aminoAcidChanges = aminoAcidChanges == null ? null : aminoAcidChanges.trim();
    }

    public String getGeneType() {
        return geneType;
    }

    public void setGeneType(String geneType) {
        this.geneType = geneType == null ? null : geneType.trim();
    }

    public String getHighestMafName() {
        return highestMafName;
    }

    public void setHighestMafName(String highestMafName) {
        this.highestMafName = highestMafName == null ? null : highestMafName.trim();
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect == null ? null : effect.trim();
    }

    public String getPathogenicAnalysis() {
        return pathogenicAnalysis;
    }

    public void setPathogenicAnalysis(String pathogenicAnalysis) {
        this.pathogenicAnalysis = pathogenicAnalysis == null ? null : pathogenicAnalysis.trim();
    }

    public String getInhert() {
        return inhert;
    }

    public void setInhert(String inhert) {
        this.inhert = inhert == null ? null : inhert.trim();
    }

    public String getDiseasePhenotype() {
        return diseasePhenotype;
    }

    public void setDiseasePhenotype(String diseasePhenotype) {
        this.diseasePhenotype = diseasePhenotype == null ? null : diseasePhenotype.trim();
    }

    public String getInhertSourceName() {
        return inhertSourceName;
    }

    public void setInhertSourceName(String inhertSourceName) {
        this.inhertSourceName = inhertSourceName == null ? null : inhertSourceName.trim();
    }

    public ReportExportMutationInfoExplain getReportExportMutationInfoExplain()
    {
        return reportExportMutationInfoExplain;
    }

    public void setReportExportMutationInfoExplain(ReportExportMutationInfoExplain reportExportMutationInfoExplain)
    {
        this.reportExportMutationInfoExplain = reportExportMutationInfoExplain;
    }
}
