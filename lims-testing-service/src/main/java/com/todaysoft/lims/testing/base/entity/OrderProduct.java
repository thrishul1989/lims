package com.todaysoft.lims.testing.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单检测表
 * @author admin
 *
 */

@Entity
@Table(name = "LIMS_ORDER_PRODUCT")
public class OrderProduct extends UuidKeyEntity
{
    public static final Integer REPORT_STATUS_NO_REPORT = 0;//未出报告
    
    public static final Integer REPORT_STATUS_TODO_FIRST_REVIEW = 1;//待一审
    
    public static final Integer REPORT_STATUS_TODO_SECOND_REVIEW = 2;//待二审
    
    public static final Integer REPORT_STATUS_TODO_SEND = 3;//待寄送
    
    public static final Integer REPORT_STATUS_SENDED = 4;//已寄送
    
    public static final Integer REPORT_STATUS_UNSEND = 5;//不寄送
    
    private static final long serialVersionUID = 1L;
    
    private Order order; //订单对象
    
    private Product product;//关联产品
    
    private String productId;
    
    private String productName;
    
    private Integer productPrice;
    
    private Integer productStatus;//产品状态，0：待送测；1：待数据分析；2：待验证；3：待出报告；4：待审核报告；5：待寄送报告；6：已完成 ;8 已取消；
    
    private Integer refundStatus; //产品退款状态，0:未申请；1：退款审核中；2：退款中；3：已退款；
    
    private String reportNo;//报告编号
    
    private Date reportTime;
    
    private String transportType;//运输方式
    
    private String transportName;//运输人
    
    private String courierNumber;//报告物流号
    
    private String transportPhone;//运输人电话
    
    private String dataUrl;//数据链接地址
    
    private Integer reportStatus;//报告状态：0-未出报告，1-待一审，2-待二审，3-待寄送，4-已寄送 5-不寄送
    
    @Column(name = "TRANSPORT_PHONE")
    public String getTransportPhone()
    {
        return transportPhone;
    }
    
    public void setTransportPhone(String transportPhone)
    {
        this.transportPhone = transportPhone;
    }
    
    @Column(name = "TRANSPORT_NAME")
    public String getTransportName()
    {
        return transportName;
    }
    
    public void setTransportName(String transportName)
    {
        this.transportName = transportName;
    }
    
    @Column(name = "TRANSPORT_TYPE")
    public String getTransportType()
    {
        return transportType;
    }
    
    public void setTransportType(String transportType)
    {
        this.transportType = transportType;
    }
    
    @Column(name = "REPORT_TIME")
    public Date getReportTime()
    {
        return reportTime;
    }
    
    public void setReportTime(Date reportTime)
    {
        this.reportTime = reportTime;
    }
    
    @Column(name = "REFUND_STATUS")
    public Integer getRefundStatus()
    {
        return refundStatus;
    }
    
    public void setRefundStatus(Integer refundStatus)
    {
        this.refundStatus = refundStatus;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    @JsonIgnore
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    //@JsonIgnore
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    @Transient
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    @Column(name = "PRODUCT_NAME")
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    @Column(name = "PRODUCT_PRICE")
    public Integer getProductPrice()
    {
        return productPrice;
    }
    
    public void setProductPrice(Integer productPrice)
    {
        this.productPrice = productPrice;
    }
    
    @Column(name = "PRODUCT_STATUS")
    public Integer getProductStatus()
    {
        return productStatus;
    }
    
    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }
    
    @Column(name = "REPORT_NO")
    public String getReportNo()
    {
        return reportNo;
    }
    
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }
    
    @Column(name = "COURIER_NUMBER")
    public String getCourierNumber()
    {
        return courierNumber;
    }
    
    public void setCourierNumber(String courierNumber)
    {
        this.courierNumber = courierNumber;
    }
    
    @Column(name = "DATA_URL")
    public String getDataUrl()
    {
        return dataUrl;
    }
    
    public void setDataUrl(String dataUrl)
    {
        this.dataUrl = dataUrl;
    }
    
    @Column(name = "REPORT_STATUS")
    public Integer getReportStatus()
    {
        return reportStatus;
    }
    
    public void setReportStatus(Integer reportStatus)
    {
        this.reportStatus = reportStatus;
    }
}
