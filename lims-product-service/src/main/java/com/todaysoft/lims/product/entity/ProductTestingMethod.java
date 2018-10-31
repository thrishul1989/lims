package com.todaysoft.lims.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "LIMS_PRODUCT_TESTING_METHOD")
public class ProductTestingMethod extends UuidKeyEntity {
    private Product product;

    private TestingMethod testingMethod;

    private List<ProductProbe> productProbe;
    private String coordinate;

    private BigDecimal testingDatasize;

    private String scheduleConfigId;

    private String dataTemplateId;

    private String claimTemplateId; //分析内容

    private String coverage;    //分析要求

    private String analysisContent; //分析内容 冗余

    private String analysisRequire;//分析要求 冗余



    @Column(name = "SCHEDULE_CONFIG_ID")
    public String getScheduleConfigId() {
        return scheduleConfigId;
    }

    public void setScheduleConfigId(String scheduleConfigId) {
        this.scheduleConfigId = scheduleConfigId;
    }

    @Column(name = "TESTING_DATASIZE")
    public BigDecimal getTestingDatasize() {
        return testingDatasize;
    }

    public void setTestingDatasize(BigDecimal testingDatasize) {
        this.testingDatasize = testingDatasize;
    }

    @JoinColumn(name = "COORDINATE")
    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    @OneToMany(mappedBy = "productTestingMethod", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ProductProbe> getProductProbe() {
        return productProbe;
    }

    public void setProductProbe(List<ProductProbe> productProbe) {
        this.productProbe = productProbe;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    @JsonIgnore
    public Product getProduct() {
        return product;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TESTING_METHOD_ID")
    public TestingMethod getTestingMethod() {
        return testingMethod;
    }

    public void setTestingMethod(TestingMethod testingMethod) {
        this.testingMethod = testingMethod;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "DATA_TEMPLATE_ID")
    public String getDataTemplateId() {
        return dataTemplateId;
    }

    public void setDataTemplateId(String dataTemplateId) {
        this.dataTemplateId = dataTemplateId;
    }

    @Column(name = "CLAIM_TEMPLATE_ID")
    public String getClaimTemplateId() {
        return claimTemplateId;
    }

    public void setClaimTemplateId(String claimTemplateId) {
        this.claimTemplateId = claimTemplateId;
    }

    @Column(name = "COVERAGE")
    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    @Column(name = "ANALYSIS_CONTENT")
    public String getAnalysisContent() {
        return analysisContent;
    }

    public void setAnalysisContent(String analysisContent) {
        this.analysisContent = analysisContent;
    }

    @Column(name = "ANALYSIS_REQUIRE")
    public String getAnalysisRequire() {
        return analysisRequire;
    }

    public void setAnalysisRequire(String analysisRequire) {
        this.analysisRequire = analysisRequire;
    }
}
