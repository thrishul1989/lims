package com.todaysoft.lims.system.model.vo;

import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.model.vo.order.OrderBaseReuqest;

public class PaymentSearchRequest extends OrderBaseReuqest
{
    private String id;
    
    private int pageNo;
    
    private int pageSize;
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private String code; //订单编号
    
    private Integer status;//订单状态
    
    private String ownerId; //客户
    
    private String creatorName;//业务员
    
    private String contractName;//合同名称
    
    /*****************************************/
    
    private String paymentType; //1付款 2补款 3退款
    
    private String protoId; //视图原生记录ID
    
    private String start;
    
    private String end;
    
    private String examineeName; //受检人姓名
    
    private Integer submitSource;
    
    private String customer;
    
    private String amount; //冗余--页面传钱 code
    
    private String settleBillId; //账单id
    
    /*****************************************/
    private String refundApplyId; //退款申请id
    
    private Integer testingStatus; //订单主状态
    
    private Integer paymentStatus; //支付状态
    
    private Integer settlementType;
    
    private Integer notTestingStatus; //订单主状态
    
    public Integer getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(Integer testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    public String getRefundApplyId()
    {
        return refundApplyId;
    }
    
    public void setRefundApplyId(String refundApplyId)
    {
        this.refundApplyId = refundApplyId;
    }
    
    public String getSettleBillId()
    {
        return settleBillId;
    }
    
    public void setSettleBillId(String settleBillId)
    {
        this.settleBillId = settleBillId;
    }
    
    public String getAmount()
    {
        return amount;
    }
    
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    public String getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    public Integer getSubmitSource()
    {
        return submitSource;
    }
    
    public void setSubmitSource(Integer submitSource)
    {
        this.submitSource = submitSource;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getStart()
    {
        return start;
    }
    
    public void setStart(String start)
    {
        this.start = start;
    }
    
    public String getEnd()
    {
        return end;
    }
    
    public void setEnd(String end)
    {
        this.end = end;
    }
    
    public String getProtoId()
    {
        return protoId;
    }
    
    public void setProtoId(String protoId)
    {
        this.protoId = protoId;
    }
    
    public String getPaymentType()
    {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
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
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public String getContractName()
    {
        return contractName;
    }
    
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限
    
    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getSettlementType()
    {
        return settlementType;
    }
    
    public void setSettlementType(Integer settlementType)
    {
        this.settlementType = settlementType;
    }
    
    public Integer getNotTestingStatus()
    {
        return notTestingStatus;
    }
    
    public void setNotTestingStatus(Integer notTestingStatus)
    {
        this.notTestingStatus = notTestingStatus;
    }
    
}
