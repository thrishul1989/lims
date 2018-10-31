package com.todaysoft.lims.gateway.model.request.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单-主样本
 * @author admin
 *
 */

public class OrderResearchSample extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String sampleCode; //样本编号
    
    private String sampleTypeId;//样本类型ID
    
    private String sampleName; //样本名称
    
    private BigDecimal sampleSize;//样本量
    
    private String diagnosis;
    
    private String focusGene;
    
    private String familyRelation;
    
    private String remark;
    
    private OrderSampleGroup orderSampleGroup; //样本组
    
    private Order order; //订单对象
    
    private List<OrderResearchProduct> orderResearchProduct = new ArrayList<OrderResearchProduct>();
    
    public OrderResearchSample()
    {
        super();
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getDiagnosis()
    {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }
    
    public String getFocusGene()
    {
        return focusGene;
    }
    
    public void setFocusGene(String focusGene)
    {
        this.focusGene = focusGene;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    public OrderSampleGroup getOrderSampleGroup()
    {
        return orderSampleGroup;
    }
    
    public void setOrderSampleGroup(OrderSampleGroup orderSampleGroup)
    {
        this.orderSampleGroup = orderSampleGroup;
    }
    
    public List<OrderResearchProduct> getOrderResearchProduct()
    {
        return orderResearchProduct;
    }
    
    public void setOrderResearchProduct(List<OrderResearchProduct> orderResearchProduct)
    {
        this.orderResearchProduct = orderResearchProduct;
    }
    
}
