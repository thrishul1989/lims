package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "LIMS_TESTING_POOLING_DIVISION_SAMPLE")
public class PoolingDivisionSample extends UuidKeyEntity
{
    private String sequencingCode;
    
    private String sampleId;
    
    private String sampleCode;

    private String dataCode;

    private String indexCode;

    @Column(name="SEQUENCING_CODE")
    public String getSequencingCode() {
        return sequencingCode;
    }

    public void setSequencingCode(String sequencingCode) {
        this.sequencingCode = sequencingCode;
    }

    @Column(name="SAMPLE_ID")
    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    @Column(name="SAMPLE_CODE")
    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    @Column(name="DATA_CODE")
    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    @Column(name="INDEX_CODE")
    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }
}
