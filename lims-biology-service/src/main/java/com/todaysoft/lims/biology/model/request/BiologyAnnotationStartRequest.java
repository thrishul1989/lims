package com.todaysoft.lims.biology.model.request;


import java.util.ArrayList;
import java.util.List;

public class BiologyAnnotationStartRequest {

    private String sequencingCode;

    private String sampleCode;

    private String dataCode;

    private String productCode;

    private String sex;

    private String genomeVersion;

    private String analysisContent;

    private String phenotype;

    private String analysisRequire;

    private List<FastqFile> fastqFile=new ArrayList<>();

    public String getSequencingCode() {
        return sequencingCode;
    }

    public void setSequencingCode(String sequencingCode) {
        this.sequencingCode = sequencingCode;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGenomeVersion() {
        return genomeVersion;
    }

    public void setGenomeVersion(String genomeVersion) {
        this.genomeVersion = genomeVersion;
    }

    public String getAnalysisContent() {
        return analysisContent;
    }

    public void setAnalysisContent(String analysisContent) {
        this.analysisContent = analysisContent;
    }

    public String getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(String phenotype) {
        this.phenotype = phenotype;
    }

    public String getAnalysisRequire() {
        return analysisRequire;
    }

    public void setAnalysisRequire(String analysisRequire) {
        this.analysisRequire = analysisRequire;
    }

    public List<FastqFile> getFastqFile() {
        return fastqFile;
    }

    public void setFastqFile(List<FastqFile> fastqFile) {
        this.fastqFile = fastqFile;
    }
}
