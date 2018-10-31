package com.todaysoft.lims.biology.model.request;


import com.google.common.collect.Lists;

import java.util.List;

public class FamilyAnnotationRequest {

    private String dataCode;

    private String vcfFilePath;

    private String cnvFilePath;

    private String snpFilePath;

    private String statistics;

    private String svJson;

    private String regioncount;

    private String sex;

    private String phenotype;

    private BamData bam;

    private List<FamlilySampleData> family = Lists.newArrayList();

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getVcfFilePath() {
        return vcfFilePath;
    }

    public void setVcfFilePath(String vcfFilePath) {
        this.vcfFilePath = vcfFilePath;
    }

    public String getCnvFilePath() {
        return cnvFilePath;
    }

    public void setCnvFilePath(String cnvFilePath) {
        this.cnvFilePath = cnvFilePath;
    }

    public String getSnpFilePath() {
        return snpFilePath;
    }

    public void setSnpFilePath(String snpFilePath) {
        this.snpFilePath = snpFilePath;
    }

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public String getSvJson() {
        return svJson;
    }

    public void setSvJson(String svJson) {
        this.svJson = svJson;
    }

    public String getRegioncount() {
        return regioncount;
    }

    public void setRegioncount(String regioncount) {
        this.regioncount = regioncount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(String phenotype) {
        this.phenotype = phenotype;
    }

    public BamData getBam() {
        return bam;
    }

    public void setBam(BamData bam) {
        this.bam = bam;
    }

    public List<FamlilySampleData> getFamily() {
        return family;
    }

    public void setFamily(List<FamlilySampleData> family) {
        this.family = family;
    }
}
