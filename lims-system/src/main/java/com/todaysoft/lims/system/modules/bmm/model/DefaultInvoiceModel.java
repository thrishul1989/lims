package com.todaysoft.lims.system.modules.bmm.model;

import com.todaysoft.lims.system.model.vo.contract.Contract;
import com.todaysoft.lims.system.model.vo.order.OrderExaminee;
import com.todaysoft.lims.system.model.vo.order.OrderProduct;
import com.todaysoft.lims.system.model.vo.payment.OrderDelay;
import com.todaysoft.lims.system.model.vo.payment.OrderReduce;
import com.todaysoft.lims.system.model.vo.payment.OrderRefund;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultInvoiceModel
{
    private String id;
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private String code; //订单编号
    
    private Integer status;//订单状态
    
    private Integer amount;//订单金额，单位（分）
    
    private String recipientName;//收件人姓名
    
    private String recipientPhone;//收件人电话
    
    private String recipientEmail;//收件人邮箱
    
    private String recipientAddress;//收件人地址
    
    private String invoiceTitle;//发票抬头
    
    private String creatorId;//创建人ID
    
    private String creatorName;//业务员
    
    private Date createTime; //创建时间
    
    private Contract contract; //归属合同
    
    private String ownerId; //订单所属客户
    
    private String ownerName;
    
    private String ownerCompany;
    
    private Integer incomingAmount;
    
    private Integer subsidiarySampleAmount = 0; //附属样本额外收费金额，单位（分）
    
    private String discountCouponId; //抵用优惠券ID
    
    private Integer discountAmount = 0; //优惠券抵用金额
    
    private Integer reduceAmount = 0; //减免金额
    
    private String payType; //付款方式
    
    private List<OrderRefund> orderRefund; //关联订单退款记录
    
    private List<OrderReduce> orderReduce; //关联订单减免记录
    
    private List<OrderDelay> orderDelay; //关联订单延迟记录
    
    private String institution;
    
    private Integer solveStatus;

    private String delayStatus;

    private BigDecimal invoiceAmount;//开票金额

    private BigDecimal actualInvoiceAmount;//实际开票金额

    private String content;
    
    private List<InvoiceInfo> infoList;

    private List<InvoiceInfo> infoNormalList; //正常票列表

    private List<InvoiceInfo> infoRedList; // 红票列表

    private List<InvoiceInfo> infoInvalidList; // 作废票列表

    private String isShowNormal;

    private String isShowRed;

    private String isShowInvalid;

    private String isAlreadySynchro; //是否已经同步
    
    private Date updateTime; //更新时间
    
    private List<OrderExaminee> orderExamineeList = new ArrayList<OrderExaminee>(); //受检人
    
    private String examinee;
    
    private BigDecimal payable;//应付款
    
    private Integer reduceStatus;//减免申请状态
    
    private BigDecimal applyAmount; //申请减免金额,
    
    private BigDecimal checkAmount; //审批减免退款金额,
    
    private BigDecimal refundAmount;//退款金额
    
    private BigDecimal receivable;//应收款
    
    private BigDecimal actualPayment;//实收款
    
    private BigDecimal fillingAmount;//需补款
    
    private BigDecimal companyRatio;//北京迈基诺 占比例

    private BigDecimal inspectionRatio;//北京检验所 占比例

    private BigDecimal cqCompanyRatio;//重庆迈基诺 占比例
    
    private BigDecimal currentReceivable;//当前所属机构应收款

    private BigDecimal currentActualPay;//当前所属机构实付款
    
    private Integer invoiceApplyType;//开票类型 1默认开票 2提前开票
    
    private Integer issueInvoice;//状态：1-待开票 2-可开票 3-已开票 4-已寄送
    
    private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
    
    private String recordKey;

    private Integer billingType;

    private Integer headerType;

    private String dutyParagraph;

    private String mailbox;

    private String isShow; //冗余 用于区分过滤南北区的订单

    private Integer synchroStatus ; //是否同步金税（针对延迟付款）

    public BigDecimal getActualInvoiceAmount() {
        return actualInvoiceAmount;
    }

    public void setActualInvoiceAmount(BigDecimal actualInvoiceAmount) {
        this.actualInvoiceAmount = actualInvoiceAmount;
    }

    public Integer getSynchroStatus() {
        return synchroStatus;
    }

    public void setSynchroStatus(Integer synchroStatus) {
        this.synchroStatus = synchroStatus;
    }

    public String getIsAlreadySynchro() {
        return isAlreadySynchro;
    }

    public void setIsAlreadySynchro(String isAlreadySynchro) {
        this.isAlreadySynchro = isAlreadySynchro;
    }

    public String getDelayStatus() {
        return delayStatus;
    }

    public void setDelayStatus(String delayStatus) {
        this.delayStatus = delayStatus;
    }

    public String getIsShowNormal() {
        return isShowNormal;
    }

    public void setIsShowNormal(String isShowNormal) {
        this.isShowNormal = isShowNormal;
    }

    public String getIsShowRed() {
        return isShowRed;
    }

    public void setIsShowRed(String isShowRed) {
        this.isShowRed = isShowRed;
    }

    public String getIsShowInvalid() {
        return isShowInvalid;
    }

    public void setIsShowInvalid(String isShowInvalid) {
        this.isShowInvalid = isShowInvalid;
    }

    public BigDecimal getInspectionRatio() {
        return inspectionRatio;
    }

    public void setInspectionRatio(BigDecimal inspectionRatio) {
        this.inspectionRatio = inspectionRatio;
    }

    public BigDecimal getCqCompanyRatio() {
        return cqCompanyRatio;
    }

    public void setCqCompanyRatio(BigDecimal cqCompanyRatio) {
        this.cqCompanyRatio = cqCompanyRatio;
    }

    public BigDecimal getCurrentActualPay() {
        return currentActualPay;
    }

    public void setCurrentActualPay(BigDecimal currentActualPay) {
        this.currentActualPay = currentActualPay;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Integer getBillingType() {
        return billingType;
    }

    public void setBillingType(Integer billingType) {
        this.billingType = billingType;
    }

    public Integer getHeaderType() {
        return headerType;
    }

    public void setHeaderType(Integer headerType) {
        this.headerType = headerType;
    }

    public String getDutyParagraph() {
        return dutyParagraph;
    }

    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getRecordKey()
    {
        return recordKey;
    }
    
    public void setRecordKey(String recordKey)
    {
        this.recordKey = recordKey;
    }
    
    public String getOwnerCompany()
    {
        return ownerCompany;
    }
    
    public void setOwnerCompany(String ownerCompany)
    {
        this.ownerCompany = ownerCompany;
    }
    
    public List<OrderProduct> getOrderProductList()
    {
        return orderProductList;
    }
    
    public void setOrderProductList(List<OrderProduct> orderProductList)
    {
        this.orderProductList = orderProductList;
    }
    
    public Integer getInvoiceApplyType()
    {
        return invoiceApplyType;
    }
    
    public void setInvoiceApplyType(Integer invoiceApplyType)
    {
        this.invoiceApplyType = invoiceApplyType;
    }
    
    public Integer getIssueInvoice()
    {
        return issueInvoice;
    }
    
    public void setIssueInvoice(Integer issueInvoice)
    {
        this.issueInvoice = issueInvoice;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    public String getRecipientName()
    {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    
    public String getRecipientPhone()
    {
        return recipientPhone;
    }
    
    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }
    
    public String getRecipientEmail()
    {
        return recipientEmail;
    }
    
    public void setRecipientEmail(String recipientEmail)
    {
        this.recipientEmail = recipientEmail;
    }
    
    public String getRecipientAddress()
    {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
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
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    public String getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
    public Integer getSubsidiarySampleAmount()
    {
        return subsidiarySampleAmount;
    }
    
    public void setSubsidiarySampleAmount(Integer subsidiarySampleAmount)
    {
        this.subsidiarySampleAmount = subsidiarySampleAmount;
    }
    
    public String getDiscountCouponId()
    {
        return discountCouponId;
    }
    
    public void setDiscountCouponId(String discountCouponId)
    {
        this.discountCouponId = discountCouponId;
    }
    
    public Integer getDiscountAmount()
    {
        return discountAmount;
    }
    
    public void setDiscountAmount(Integer discountAmount)
    {
        this.discountAmount = discountAmount;
    }
    
    public Integer getReduceAmount()
    {
        return reduceAmount;
    }
    
    public void setReduceAmount(Integer reduceAmount)
    {
        this.reduceAmount = reduceAmount;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public List<OrderRefund> getOrderRefund()
    {
        return orderRefund;
    }
    
    public void setOrderRefund(List<OrderRefund> orderRefund)
    {
        this.orderRefund = orderRefund;
    }
    
    public List<OrderReduce> getOrderReduce()
    {
        return orderReduce;
    }
    
    public void setOrderReduce(List<OrderReduce> orderReduce)
    {
        this.orderReduce = orderReduce;
    }
    
    public List<OrderDelay> getOrderDelay()
    {
        return orderDelay;
    }
    
    public void setOrderDelay(List<OrderDelay> orderDelay)
    {
        this.orderDelay = orderDelay;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public Integer getSolveStatus()
    {
        return solveStatus;
    }
    
    public void setSolveStatus(Integer solveStatus)
    {
        this.solveStatus = solveStatus;
    }
    
    public BigDecimal getInvoiceAmount()
    {
        return invoiceAmount;
    }
    
    public void setInvoiceAmount(BigDecimal invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public List<InvoiceInfo> getInfoList()
    {
        return infoList;
    }
    
    public void setInfoList(List<InvoiceInfo> infoList)
    {
        this.infoList = infoList;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public List<OrderExaminee> getOrderExamineeList()
    {
        return orderExamineeList;
    }
    
    public void setOrderExamineeList(List<OrderExaminee> orderExamineeList)
    {
        this.orderExamineeList = orderExamineeList;
    }
    
    public String getExaminee()
    {
        return examinee;
    }
    
    public void setExaminee(String examinee)
    {
        this.examinee = examinee;
    }
    
    public BigDecimal getPayable()
    {
        return payable;
    }
    
    public void setPayable(BigDecimal payable)
    {
        this.payable = payable;
    }
    
    public Integer getReduceStatus()
    {
        return reduceStatus;
    }
    
    public void setReduceStatus(Integer reduceStatus)
    {
        this.reduceStatus = reduceStatus;
    }
    
    public BigDecimal getRefundAmount()
    {
        return refundAmount;
    }
    
    public void setRefundAmount(BigDecimal refundAmount)
    {
        this.refundAmount = refundAmount;
    }
    
    public BigDecimal getReceivable()
    {
        return receivable;
    }
    
    public void setReceivable(BigDecimal receivable)
    {
        this.receivable = receivable;
    }
    
    public BigDecimal getActualPayment()
    {
        return actualPayment;
    }
    
    public void setActualPayment(BigDecimal actualPayment)
    {
        this.actualPayment = actualPayment;
    }
    
    public BigDecimal getFillingAmount()
    {
        return fillingAmount;
    }
    
    public void setFillingAmount(BigDecimal fillingAmount)
    {
        this.fillingAmount = fillingAmount;
    }
    
    public BigDecimal getCheckAmount()
    {
        return checkAmount;
    }
    
    public void setCheckAmount(BigDecimal checkAmount)
    {
        this.checkAmount = checkAmount;
    }
    
    public BigDecimal getCompanyRatio()
    {
        return companyRatio;
    }
    
    public void setCompanyRatio(BigDecimal companyRatio)
    {
        this.companyRatio = companyRatio;
    }
    
    public BigDecimal getApplyAmount()
    {
        return applyAmount;
    }
    
    public void setApplyAmount(BigDecimal applyAmount)
    {
        this.applyAmount = applyAmount;
    }
    
    public BigDecimal getCurrentReceivable()
    {
        return currentReceivable;
    }
    
    public void setCurrentReceivable(BigDecimal currentReceivable)
    {
        this.currentReceivable = currentReceivable;
    }

    public List<InvoiceInfo> getInfoNormalList() {
        return infoNormalList;
    }

    public void setInfoNormalList(List<InvoiceInfo> infoNormalList) {
        this.infoNormalList = infoNormalList;
    }

    public List<InvoiceInfo> getInfoRedList() {
        return infoRedList;
    }

    public void setInfoRedList(List<InvoiceInfo> infoRedList) {
        this.infoRedList = infoRedList;
    }

    public List<InvoiceInfo> getInfoInvalidList() {
        return infoInvalidList;
    }

    public void setInfoInvalidList(List<InvoiceInfo> infoInvalidList) {
        this.infoInvalidList = infoInvalidList;
    }
}
