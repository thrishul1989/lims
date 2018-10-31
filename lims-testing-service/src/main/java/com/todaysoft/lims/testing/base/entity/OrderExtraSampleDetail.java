package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;

/**
 * 订单-追加附属样本明细
 */

@Entity
@Table(name = "LIMS_ORDER_EXTRA_SAMPLE_DETAIL")
public class OrderExtraSampleDetail extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;

    private String sampleId; //附属样本id
    
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
