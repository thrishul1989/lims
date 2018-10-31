package com.todaysoft.lims.technical.mybatis.entity;

public class ReportExportCapcnvResult {
    private String id;

    private String taskId;

    private String cnvAnalysisPicId;

    private String gene;

    private String missingArea;

    private String exon;

    private String inheritSource;

    private String relatedDisease;
    
    private String areaSize;
    
    private String pathogenicLevel;
    
    private String mutationComment;    
    
    private String missingType;
    
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

    public String getCnvAnalysisPicId() {
        return cnvAnalysisPicId;
    }

    public void setCnvAnalysisPicId(String cnvAnalysisPicId) {
        this.cnvAnalysisPicId = cnvAnalysisPicId == null ? null : cnvAnalysisPicId.trim();
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene == null ? null : gene.trim();
    }

    public String getMissingArea() {
        return missingArea;
    }

    public void setMissingArea(String missingArea) {
        this.missingArea = missingArea == null ? null : missingArea.trim();
    }

    public String getExon() {
        return exon;
    }

    public void setExon(String exon) {
        this.exon = exon == null ? null : exon.trim();
    }

    public String getInheritSource() {
        return inheritSource;
    }

    public void setInheritSource(String inheritSource) {
        this.inheritSource = inheritSource == null ? null : inheritSource.trim();
    }

    public String getRelatedDisease() {
        return relatedDisease;
    }

    public void setRelatedDisease(String relatedDisease) {
        this.relatedDisease = relatedDisease == null ? null : relatedDisease.trim();
    }

    public String getAreaSize()
    {
        return areaSize;
    }

    public void setAreaSize(String areaSize)
    {
        this.areaSize = areaSize;
    }

    public String getPathogenicLevel()
    {
        return pathogenicLevel;
    }

    public void setPathogenicLevel(String pathogenicLevel)
    {
        this.pathogenicLevel = pathogenicLevel;
    }

    public String getMutationComment()
    {
        return mutationComment;
    }

    public void setMutationComment(String mutationComment)
    {
        this.mutationComment = mutationComment;
    }

    public String getMissingType()
    {
        return missingType;
    }

    public void setMissingType(String missingType)
    {
        this.missingType = missingType;
    }
    
    
}