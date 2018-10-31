package com.todaysoft.lims.sample.entity;

import com.todaysoft.lims.persist.AutoKeyEntity;
import com.todaysoft.lims.persist.QueryField;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by HSHY-032 on 2016/8/19.
 */
@Entity
@Table(name="LIMS_TASK_ASSIGN")
@Builder(toBuilder = true)
@AllArgsConstructor
public class TaskAssign extends AutoKeyEntity {

    @QueryField(type = QueryField.QueryType.eq)
    private Integer sampleDetailId;

    @QueryField(type = QueryField.QueryType.eq)
    private Integer productId;

    @QueryField(type = QueryField.QueryType.eq)
    private Integer probeId;

    @QueryField(type = QueryField.QueryType.eq)
    private String probeName;

    @QueryField(type = QueryField.QueryType.eq)
    private String semantic;

    @QueryField(type = QueryField.QueryType.eq)
    private String state;

    public TaskAssign() {
    }

    @Column(name="SAMPLE_DETAIL_ID")
    public Integer getSampleDetailId() {
        return sampleDetailId;
    }

    public void setSampleDetailId(Integer sampleDetailId) {
        this.sampleDetailId = sampleDetailId;
    }

    @Column(name="PRODUCT_ID")
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Column(name="PROBE_ID")
    public Integer getProbeId() {
        return probeId;
    }

    public void setProbeId(Integer probeId) {
        this.probeId = probeId;
    }
    @Column(name="PROBE_NAME")
    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    @Column(name="SEMANTIC")
    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }

    @Column(name="STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
