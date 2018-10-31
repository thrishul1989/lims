package com.todaysoft.lims.biology.model.request;


public class FamlilySampleData {

    private String dataCode;

    private String vcfFilePath;

    private String cnvFilePath;

    private String snpFilePath;

    private String statistics;

    private String svJson;

    private String regioncount;

    private String phenotype;

    private BamData bam;

    private String relation;

    private String symptom;
    //relation:1:本人,2:父亲,3:母亲,4:丈夫,5:妻子,6:爷爷,7:奶奶,8:兄弟,9:姐妹,10:儿子,11:女儿,12:外公,13:外婆,14:叔叔,15:姑姑,16:舅舅,17:姨妈,18:表兄弟,19:表姐妹,20:堂兄弟,21:堂姐妹,22:大表哥,23:其他
    //symptom: 0:正常，1:确诊，2:与受检人类似，3:未知
   // sex: 0:男，1:女，2:未知

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }
}
