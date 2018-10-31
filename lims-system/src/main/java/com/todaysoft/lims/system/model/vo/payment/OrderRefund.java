package com.todaysoft.lims.system.model.vo.payment;

import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
public class OrderRefund
{
    private String id;
    
    private Order orderId; //订单ID,
    
    private String orderProductId;//'订单产品关联表IDS，采用主键id1,id2,...',
    
    private String applyReason;//'申请退款原因',
    
    private Integer replyAmount; //'实际允许退款金额',
    
    private String remark;// '审核备注',
    
    private Integer status;// '退款申请状态：0：待审核:1：审核通过；2：审核未通过；',
    
    private String creatorId;// '申请人ID',
    
    private String creatorName;// '申请人姓名',
    
    private Date applyTime;//'申请时间',
    
    private Date applyEndTime;//'额外字段',
    
    private Date updateTime;// '更新时间',
    
    private List<OrderRefundCheck> orderRefundCheckList;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private String examineeName;//额外字段
    
    private List<OrderProduct> orderProduct;
    
    private Double applyAmount; //退款申请额度
    
    private String handler; //是否处理
    
    private OrderRefundRecord refundRecord; //处理信息
    
    private String orderCode;//额外字段
    
    private String orderType;//额外字段
    
    private String ownerId;//额外字段
    
    private String bankType; //银行名称
    
    private String bankNo; //银行卡号
    
    private String bankOwnerName; //姓名

    private String productName;//产品名称

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public OrderRefund()
    {
        
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Order getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(Order orderId)
    {
        this.orderId = orderId;
    }
    
    public String getOrderProductId()
    {
        return orderProductId;
    }
    
    public void setOrderProductId(String orderProductId)
    {
        this.orderProductId = orderProductId;
    }
    
    public String getApplyReason()
    {
        return applyReason;
    }
    
    public void setApplyReason(String applyReason)
    {
        this.applyReason = applyReason;
    }
    
    public Integer getReplyAmount()
    {
        return replyAmount;
    }
    
    public void setReplyAmount(Integer replyAmount)
    {
        this.replyAmount = replyAmount;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public List<OrderRefundCheck> getOrderRefundCheckList()
    {
        return orderRefundCheckList;
    }
    
    public void setOrderRefundCheckList(List<OrderRefundCheck> orderRefundCheckList)
    {
        this.orderRefundCheckList = orderRefundCheckList;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public Date getApplyEndTime()
    {
        return applyEndTime;
    }
    
    public void setApplyEndTime(Date applyEndTime)
    {
        this.applyEndTime = applyEndTime;
    }
    
    public List<OrderProduct> getOrderProduct()
    {
        return orderProduct;
    }
    
    public void setOrderProduct(List<OrderProduct> orderProduct)
    {
        this.orderProduct = orderProduct;
    }
    
    public Double getApplyAmount()
    {
        return applyAmount;
    }
    
    public void setApplyAmount(Double applyAmount)
    {
        this.applyAmount = applyAmount;
    }
    
    public OrderRefundRecord getRefundRecord()
    {
        return refundRecord;
    }
    
    public void setRefundRecord(OrderRefundRecord refundRecord)
    {
        this.refundRecord = refundRecord;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
    public String getBankType()
    {
        return bankType;
    }
    
    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }
    
    public String getBankNo()
    {
        return bankNo;
    }
    
    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
    }
    
    public String getBankOwnerName()
    {
        return bankOwnerName;
    }
    
    public void setBankOwnerName(String bankOwnerName)
    {
        this.bankOwnerName = bankOwnerName;
    }
    
    public String getHandler()
    {
        return handler;
    }
    
    public void setHandler(String handler)
    {
        this.handler = handler;
    }
    
}
