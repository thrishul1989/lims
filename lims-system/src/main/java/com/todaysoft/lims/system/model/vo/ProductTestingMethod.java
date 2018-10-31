package com.todaysoft.lims.system.model.vo;


import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;

import java.math.BigDecimal;
import java.util.List;


public class ProductTestingMethod {

    private String id;
    private Product product;
    private TestingMethod testingMethod;
    private List<ProductProbe> productProbe;
    private BigDecimal testingDatasize;
    private String coordinate;
    private String scheduleConfigId;
    private String dataTemplateId;
    private String claimTemplateId;//分析内容
    private String coverage;       //分析要求
    private String analysisContent; //分析内容 冗余

    private String analysisRequire;//分析要求 冗余

    private String scheduleName ;//页面查看数据处理---冗余

    private String dataTemplateName; //页面查看数据处理---冗余

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getDataTemplateName() {
        return dataTemplateName;
    }

    public void setDataTemplateName(String dataTemplateName) {
        this.dataTemplateName = dataTemplateName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TestingMethod getTestingMethod() {
        return testingMethod;
    }

    public void setTestingMethod(TestingMethod testingMethod) {
        this.testingMethod = testingMethod;
    }

    public String getScheduleConfigId() {
        return scheduleConfigId;
    }

    public void setScheduleConfigId(String scheduleConfigId) {
        this.scheduleConfigId = scheduleConfigId;
    }

    public BigDecimal getTestingDatasize() {
        return testingDatasize;
    }

    public void setTestingDatasize(BigDecimal testingDatasize) {
        this.testingDatasize = testingDatasize;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public List<ProductProbe> getProductProbe() {
        return productProbe;
    }

    public void setProductProbe(List<ProductProbe> productProbe) {
        this.productProbe = productProbe;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDataTemplateId() {
        return dataTemplateId;
    }

    public void setDataTemplateId(String dataTemplateId) {
        this.dataTemplateId = dataTemplateId;
    }

    public String getClaimTemplateId() {
        return claimTemplateId;
    }

    public void setClaimTemplateId(String claimTemplateId) {
        this.claimTemplateId = claimTemplateId;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getAnalysisContent() {
        return analysisContent;
    }

    public void setAnalysisContent(String analysisContent) {
        this.analysisContent = analysisContent;
    }

    public String getAnalysisRequire() {
        return analysisRequire;
    }

    public void setAnalysisRequire(String analysisRequire) {
        this.analysisRequire = analysisRequire;
    }
}
