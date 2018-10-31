package com.todaysoft.lims.sample.entity.samplereceiving;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="LIMS_TESTING_SAMPLE_STORAGE")
public class TestingSampleStorage extends UuidKeyEntity implements Serializable{

    private static final long serialVersionUID = -579797743778809440L;

    private String sampleCode;

    private String locationCode;

    private BigDecimal sampleSize;

    private Integer status;//存储状态 1-入库 2-出库

    @Column(name="SAMPLE_CODE")
    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    @Column(name="LOCATION_CODE")
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @Column(name="SAMPLE_SIZE")
    public BigDecimal getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(BigDecimal sampleSize) {
        this.sampleSize = sampleSize;
    }

    @Column(name="STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
