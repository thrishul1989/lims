package com.todaysoft.lims.system.service.adapter.request;

import com.todaysoft.lims.system.model.vo.*;
import com.todaysoft.lims.system.modules.report.model.ReportTemplate;

import java.util.List;

/**
 * Created by HSHY-032 on 2016/6/8.
 */
public class ProductCreateRequest {
    private String id;

    private String code; // 产品编号

    private String name; // 产品名称

    private String price; // 产品定价￥

    private TestingType testingSubtype; // 产品分类

    private TestingType testingType; // 检测方法

    private ReportTemplate reportTemplate;

    private User productDuty;

    private String productLeader;

    private Integer testingDuration; // 产品周期 天

    private String enSample; // 推荐样本

    private String description;// 产品描述

    private String excelPath;//请求字段

    private String remark;// 产品备注

    private List<ProductDisease> productDiseaseList;

    private String productDisease;//请求字段

    private String productGenes;//请求字段

    private List<ProductGene> productGeneList;

    private String experimentSamples;//实验样本

    private String coordinates;//请求字段

    private String testingDatasize;//请求字段

    private String scheduleTestingCongigs;//检测配置 请求字段

    private String dataTemplateCongigs;

    private String claimTemplateCongigs;

    private String coverageCongigs;

    private String testingStartSample;

    private Integer delFlag;

    private Integer ifMade;//是否定制 0 否 1是

    private String testInstitution;//检测机构：0 检验所 1麦诺基公司

    private String updateTime;

    private String samplePurpose;

    private String ProductAmountRule;

    private List<ProductAmountRule> productAmountRuleList;

    public List<ProductAmountRule> getProductAmountRuleList() {
        return productAmountRuleList;
    }

    public void setProductAmountRuleList(List<ProductAmountRule> productAmountRuleList) {
        this.productAmountRuleList = productAmountRuleList;
    }

    public String getProductAmountRule() {
        return ProductAmountRule;
    }

    public void setProductAmountRule(String productAmountRule) {
        ProductAmountRule = productAmountRule;
    }

    public User getProductDuty() {
        return productDuty;
    }

    public void setProductDuty(User productDuty) {
        this.productDuty = productDuty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductDisease() {
        return productDisease;
    }

    public void setProductDisease(String productDisease) {
        this.productDisease = productDisease;
    }

    public String getProductGenes() {
        return productGenes;
    }

    public void setProductGenes(String productGenes) {
        this.productGenes = productGenes;
    }

    public String getDataTemplateCongigs() {
        return dataTemplateCongigs;
    }

    public void setDataTemplateCongigs(String dataTemplateCongigs) {
        this.dataTemplateCongigs = dataTemplateCongigs;
    }

    public String getClaimTemplateCongigs() {
        return claimTemplateCongigs;
    }

    public void setClaimTemplateCongigs(String claimTemplateCongigs) {
        this.claimTemplateCongigs = claimTemplateCongigs;
    }

    public String getCoverageCongigs() {
        return coverageCongigs;
    }

    public void setCoverageCongigs(String coverageCongigs) {
        this.coverageCongigs = coverageCongigs;
    }

    public String getTestingDatasize() {
        return testingDatasize;
    }

    public void setTestingDatasize(String testingDatasize) {
        this.testingDatasize = testingDatasize;
    }

    public String getSamplePurpose() {
        return samplePurpose;
    }

    public void setSamplePurpose(String samplePurpose) {
        this.samplePurpose = samplePurpose;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public Integer getIfMade() {
        return ifMade;
    }

    public void setIfMade(Integer ifMade) {
        this.ifMade = ifMade;
    }

    public String getTestInstitution() {
        return testInstitution;
    }

    public void setTestInstitution(String testInstitution) {
        this.testInstitution = testInstitution;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getTestingStartSample() {
        return testingStartSample;
    }

    public void setTestingStartSample(String testingStartSample) {
        this.testingStartSample = testingStartSample;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getExperimentSamples() {
        return experimentSamples;
    }

    public void setExperimentSamples(String experimentSamples) {
        this.experimentSamples = experimentSamples;
    }

    public List<ProductDisease> getProductDiseaseList() {
        return productDiseaseList;
    }

    public void setProductDiseaseList(List<ProductDisease> productDiseaseList) {
        this.productDiseaseList = productDiseaseList;
    }

    public List<ProductGene> getProductGeneList() {
        return productGeneList;
    }

    public void setProductGeneList(List<ProductGene> productGeneList) {
        this.productGeneList = productGeneList;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public List<ProductPrincipal> getProductPrincipalList() {
        return productPrincipalList;
    }

    public void setProductPrincipalList(List<ProductPrincipal> productPrincipalList) {
        this.productPrincipalList = productPrincipalList;
    }

    private String probes;// 探针

    private String testMethodArray;// 冗余字段

    private List<ProductProbeRequest> productProbeList;// 请求字段

    private List<ProductPrincipal> productPrincipalList;

    private String principalArray;//请求自选

    public String getPrincipalArray() {
        return principalArray;
    }

    public void setPrincipalArray(String principalArray) {
        this.principalArray = principalArray;
    }

    public List<ProductProbeRequest> getProductProbeList() {
        return productProbeList;
    }

    public void setProductProbeList(List<ProductProbeRequest> productProbeList) {
        this.productProbeList = productProbeList;
    }

    public String getTestMethodArray() {
        return testMethodArray;
    }

    public void setTestMethodArray(String testMethodArray) {
        this.testMethodArray = testMethodArray;
    }

    private List<ProductProbe> probeList;

    private User creater;// 创建人

    private String createTime;// 创建时间

    private String enabled;// 状态

    private List<ProductSample> productSampleList;

    private List<ProductTestingMethod> productTestingMethodList;

    public List<ProductProbe> getProbeList() {
        return probeList;
    }

    public void setProbeList(List<ProductProbe> probeList) {
        this.probeList = probeList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getTestingDuration() {
        return testingDuration;
    }

    public void setTestingDuration(Integer testingDuration) {
        this.testingDuration = testingDuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnSample() {
        return enSample;
    }

    public void setEnSample(String enSample) {
        this.enSample = enSample;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TestingType getTestingSubtype() {
        return testingSubtype;
    }

    public void setTestingSubtype(TestingType testingSubtype) {
        this.testingSubtype = testingSubtype;
    }

    public TestingType getTestingType() {
        return testingType;
    }

    public void setTestingType(TestingType testingType) {
        this.testingType = testingType;
    }

    public String getProbes() {
        return probes;
    }

    public void setProbes(String probes) {
        this.probes = probes;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ProductSample> getProductSampleList() {
        return productSampleList;
    }

    public void setProductSampleList(List<ProductSample> productSampleList) {
        this.productSampleList = productSampleList;
    }

    public List<ProductTestingMethod> getProductTestingMethodList() {
        return productTestingMethodList;
    }

    public void setProductTestingMethodList(List<ProductTestingMethod> productTestingMethodList) {
        this.productTestingMethodList = productTestingMethodList;
    }

    public ReportTemplate getReportTemplate() {
        return reportTemplate;
    }

    public void setReportTemplate(ReportTemplate reportTemplate) {
        this.reportTemplate = reportTemplate;
    }

    public String getScheduleTestingCongigs() {
        return scheduleTestingCongigs;
    }

    public void setScheduleTestingCongigs(String scheduleTestingCongigs) {
        this.scheduleTestingCongigs = scheduleTestingCongigs;
    }

    public String getProductLeader() {
        return productLeader;
    }

    public void setProductLeader(String productLeader) {
        this.productLeader = productLeader;
    }

}
