package com.todaysoft.lims.sample.entity.order;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.disease.Gene;

/**
 * 订单-受检人-重点关注基因
 * @author admin
 *
 */

@Entity
@Table(name = "LIMS_ORDER_EXAMINEE_GENE")
public class OrderExamineeGene extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private OrderExaminee orderExaminee; //订单受检人
    
    private Gene gene; //重点关注基因
    
    public OrderExamineeGene()
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
    @JoinColumn(name = "GENE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Gene getGene()
    {
        return gene;
    }
    
    public void setGene(Gene gene)
    {
        this.gene = gene;
    }
    
}
