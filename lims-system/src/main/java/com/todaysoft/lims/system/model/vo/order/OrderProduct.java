package com.todaysoft.lims.system.model.vo.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.order.response.OrderStatusScheduleModel;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.utils.StringUtils;

/**
 * 订单检测表
 * @author admin
 *
 */

public class OrderProduct extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Order order; //订单对象
    
    private Product product;//关联产品
    
    private String productId;
    
    private Integer productPrice;
    
    private BigDecimal realProductPrice;
    
    private Integer productStatus;//产品状态，0：待送测；3：待出报告；4：待审核报告；5：待寄送报告；6：已完成； 7 :不寄送 8  已取消
    
    private Date reportTime;
    
    private Integer reportStatus;
    
    private Date emailTime;
    
    private String samplePurpose;
    
    public String getSamplePurpose()
    {
        return samplePurpose;
    }
    
    public void setSamplePurpose(String samplePurpose)
    {
        this.samplePurpose = samplePurpose;
    }
    
    public Integer getProductStatus()
    {
        return productStatus;
    }
    
    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }
    
    public String getReportNo()
    {
        return reportNo;
    }
    
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }
    
    private String reportNo;//报告编号
    
    public BigDecimal getRealProductPrice()
    {
        if (StringUtils.isNotEmpty(productPrice))
        {
            return BigDecimal.valueOf(productPrice).divide(new BigDecimal(100));
        }
        else
        {
            return new BigDecimal(0);
        }
    }
    
    public void setRealProductPrice(BigDecimal realProductPrice)
    {
        this.realProductPrice = realProductPrice;
    }
    
    public Integer getProductPrice()
    {
        return productPrice;
    }
    
    public void setProductPrice(Integer productPrice)
    {
        this.productPrice = productPrice;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    private Integer refundStatus; //产品退款状态，0:未申请；1：退款审核中；2：退款中；3：已退款；
    
    public Integer getRefundStatus()
    {
        return refundStatus;
    }
    
    public void setRefundStatus(Integer refundStatus)
    {
        this.refundStatus = refundStatus;
    }
    
/*    private List<TemporarySample> testingsamples;
    
    private List<TemporarySample> verificationSamples;
    
    public List<TemporarySample> getTestingsamples()
    {
        return testingsamples;
    }
    
    public void setTestingsamples(List<TemporarySample> testingsamples)
    {
        this.testingsamples = testingsamples;
    }
    
    public List<TemporarySample> getVerificationSamples()
    {
        return verificationSamples;
    }
    
    public void setVerificationSamples(List<TemporarySample> verificationSamples)
    {
        this.verificationSamples = verificationSamples;
    }*/
    
    //------优化后实体--------------
    private List<OrderStatusScheduleModel> testingsamples;
    private List<OrderStatusScheduleModel> verificationSamples;
    
    public List<OrderStatusScheduleModel> getTestingsamples()
    {
        return testingsamples;
    }
    public void setTestingsamples(List<OrderStatusScheduleModel> testingsamples)
    {
        this.testingsamples = testingsamples;
    }
    
    public List<OrderStatusScheduleModel> getVerificationSamples()
    {
        return verificationSamples;
    }
    public void setVerificationSamples(List<OrderStatusScheduleModel> verificationSamples)
    {
        this.verificationSamples = verificationSamples;
    }

    public Date getReportTime()
    {
        return reportTime;
    }
    
    public void setReportTime(Date reportTime)
    {
        this.reportTime = reportTime;
    }
    
    public Integer getReportStatus()
    {
        return reportStatus;
    }
    
    public void setReportStatus(Integer reportStatus)
    {
        this.reportStatus = reportStatus;
    }
    
    public Date getEmailTime()
    {
        return emailTime;
    }
    
    public void setEmailTime(Date emailTime)
    {
        this.emailTime = emailTime;
    }
    
}
