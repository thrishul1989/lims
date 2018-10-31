package com.todaysoft.lims.sample.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单-追加附属样本明细
 * @author 
 *
 */

@Entity
@Table(name = "LIMS_ORDER_EXTRA_SAMPLE_DETAIL")
public class OrderExtraSampleDetail extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private OrderExtraSample extraSample; //附属样本id
    
    private String sampleId; //附属样本id
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECORD_ID")
    @JsonIgnore
    public OrderExtraSample getExtraSample()
    {
        return extraSample;
    }
    
    public void setExtraSample(OrderExtraSample extraSample)
    {
        this.extraSample = extraSample;
    }
    
    @Column(name = "SAMPLE_ID")
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
}
