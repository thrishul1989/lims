package com.todaysoft.lims.sample.model;

import java.util.Date;

/**
 * Created by HSHY-032 on 2016/9/2.
 */
public class TestingSheetJddlModel {

    private Integer id;

    private String testCode;//测序编号

    private Integer fragmentLength;//片段长度 280bp

    private Double concentrationPc;//上机浓度

    private Double cluster;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public Integer getFragmentLength() {
        return fragmentLength;
    }

    public void setFragmentLength(Integer fragmentLength) {
        this.fragmentLength = fragmentLength;
    }

    public Double getConcentrationPc() {
        return concentrationPc;
    }

    public void setConcentrationPc(Double concentrationPc) {
        this.concentrationPc = concentrationPc;
    }

    public Double getCluster() {
        return cluster;
    }

    public void setCluster(Double cluster) {
        this.cluster = cluster;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
