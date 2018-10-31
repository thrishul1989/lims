package com.todaysoft.lims.maintain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "LIMS_ORDER_SUBSIDIARY_SAMPLE")
public class OrderSubsidiarySample extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String sampleCode; //样本编号
    
    private String familyRelation;

    @Column(name = "SAMPLE_CODE")
    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    @Column(name = "FAMILY_RELATION")
    public String getFamilyRelation() {
        return familyRelation;
    }

    public void setFamilyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
    }
}
