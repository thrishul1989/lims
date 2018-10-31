package com.todaysoft.lims.testing.base.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单-受检人-临床表型
 * @author admin
 *
 */

@Entity
@Table(name = "LIMS_ORDER_EXAMINEE_PHENOTYPE")
public class OrderExamineePhenotype extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private OrderExaminee orderExaminee; //订单受检人
    
    private Phenotype phenotype; //临床表型
    
    public OrderExamineePhenotype()
    {
        super();
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OE_ID")
    @JsonIgnore
    public OrderExaminee getOrderExaminee()
    {
        return orderExaminee;
    }
    
    public void setOrderExaminee(OrderExaminee orderExaminee)
    {
        this.orderExaminee = orderExaminee;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHENOTYPE_ID")
    //@JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    public Phenotype getPhenotype()
    {
        return phenotype;
    }
    
    public void setPhenotype(Phenotype phenotype)
    {
        this.phenotype = phenotype;
    }
    
}
