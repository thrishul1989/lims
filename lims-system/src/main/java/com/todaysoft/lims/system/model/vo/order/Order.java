package com.todaysoft.lims.system.model.vo.order;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.contract.Contract;
import com.todaysoft.lims.system.model.vo.payment.OrderDelay;
import com.todaysoft.lims.system.model.vo.payment.OrderReduce;
import com.todaysoft.lims.system.model.vo.payment.OrderRefund;
import com.todaysoft.lims.system.modules.bmm.model.DefaultInvoiceModel;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.report.model.TestingReport;
import com.todaysoft.lims.utils.StringUtils;

public class Order extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private String code; //订单编号
    
    private int status;//订单状态
    
    private int amount;//订单金额，单位（分）
    
    private String realPrice;//真是价格分转元
    
    private BigDecimal replenishPrice;//需补款
    
    private Double applyAmount; //退款申请额度
    
    private String recipientName;//收件人姓名
    
    private String recipientPhone;//收件人电话
    
    private String recipientEmail;//收件人邮箱
    
    private String recipientAddress;//收件人地址
    
    private String doctorAssist;//客户参与（X/X）
    
    private String invoiceTitle;//发票抬头
    
    private String creatorId;//创建人ID
    
    private String creatorName;//创建人姓名
    
    private Date createTime; //创建时间
    
    private Integer sheduleStatus;//流程状态初始0不可启动1可启动2已启动
    
    private String remark;
    
    private Contract contract; //归属合同
    
    private String contractPartB;
    
    private String contractsettlementMode;
    
    private String examineeName; //受检人 多个用，
    
    private List<OrderExaminee> orderExamineeList = new ArrayList<OrderExaminee>(); //受检人
    
    private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>(); //产品
    
    private List<OrderPrimarySample> orderPrimarySample = new ArrayList<OrderPrimarySample>(); //主样本
    
    private List<OrderSubsidiarySample> orderSubsidiarySample = new ArrayList<OrderSubsidiarySample>(); //附属样本
    
    private List<OrderSampleGroup> orderSampleGroup = new ArrayList<OrderSampleGroup>();
    
    private List<OrderSampleView> view = new ArrayList<OrderSampleView>();
    
    private String ownerId; //订单所属客户
    
    private Customer owner;
    
    private String submitterId;
    
    private String submitterName;
    
    private Integer submitSource; //1--app  2--业务后台
    
    private Integer freeCount;
    
    private Integer extraMoney;
    
    private Date submitTime;
    
    private Integer incomingAmount;//实际到账金额
    
    private String payType; //付款方式
    
    private Integer subsidiarySampleAmount; //附属样本额外收费金额，单位（分）
    
    private String flag;
    
    private Integer issueInvoice;
    
    private String discountCouponId; //抵用优惠券ID
    
    private Integer discountAmount = 0; //优惠券抵用金额
    
    private Integer reduceAmount; //减免金额
    
    private List<OrderRefund> orderRefund; //关联订单退款记录
    
    private List<OrderReduce> orderReduce; //关联订单减免记录
    
    private List<OrderDelay> orderDelay; //关联订单延迟记录
    
    private Date updateTime;
    
    private Integer samplingAmount; //减免金额
    
    private String sequenceCodes;
    
    private Date startTime;
    
    private Date payTime; //后台接收支付时间
    
    private Date paymentTime;
    
    private Date checkTime;
    
    private List<FinanceInvoiceTask> taskList;
    
    private List<TestingReport> reportList;
    
    private Integer testingStatus;
    
    private Integer paymentStatus;
    
    private Integer schedulePaymentStatus;
    
    private Integer settlementType;
    
    private Integer paymentDelayStatus;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Integer receivedSampleStatus;//样本是否入库完成 0-未入库1-已入库
    
    private String cancelName;
    
    private String cancelRemark;
    
    private Date cancelTime;
    
    private Integer viewStatus;
    
    private DefaultInvoiceModel invoiceModel;
    
    private List<OrderProductForStatusScheduleModel> productScheduleModel;
    
    private String projectManager;//项目管理人
    
    private Boolean isAppendSample;//是否可追加样本
    
    private String showCopyOrder;

    private Integer billingType; // 开票形式:0-不需要;1-电子;2-纸质

    private Integer headerType; // 抬头类型:0-个人;1-企业

    private String dutyParagraph; // 税号

    private String mailbox; // 邮箱

    private String isShow;

    private Integer belongArea; //南北区


    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Integer getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(Integer belongArea) {
        this.belongArea = belongArea;
    }

    /********************set/get******************************/


    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
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
    public String getProjectManager()
    {
        return projectManager;
    }
    
    public void setProjectManager(String projectManager)
    {
        this.projectManager = projectManager;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public List<OrderProductForStatusScheduleModel> getProductScheduleModel()
    {
        return productScheduleModel;
    }
    
    public void setProductScheduleModel(List<OrderProductForStatusScheduleModel> productScheduleModel)
    {
        this.productScheduleModel = productScheduleModel;
    }
    
    public Integer getViewStatus()
    {
        return viewStatus;
    }
    
    public void setViewStatus(Integer viewStatus)
    {
        this.viewStatus = viewStatus;
    }
    
    public Integer getSettlementType()
    {
        return settlementType;
    }
    
    public String getCancelName()
    {
        return cancelName;
    }
    
    public void setCancelName(String cancelName)
    {
        this.cancelName = cancelName;
    }
    
    public String getCancelRemark()
    {
        return cancelRemark;
    }
    
    public void setCancelRemark(String cancelRemark)
    {
        this.cancelRemark = cancelRemark;
    }
    
    public Date getCancelTime()
    {
        return cancelTime;
    }
    
    public void setCancelTime(Date cancelTime)
    {
        this.cancelTime = cancelTime;
    }
    
    public Integer getReceivedSampleStatus()
    {
        return receivedSampleStatus;
    }
    
    public void setReceivedSampleStatus(Integer receivedSampleStatus)
    {
        this.receivedSampleStatus = receivedSampleStatus;
    }
    
    public Integer getPaymentDelayStatus()
    {
        return paymentDelayStatus;
    }
    
    public void setPaymentDelayStatus(Integer paymentDelayStatus)
    {
        this.paymentDelayStatus = paymentDelayStatus;
    }
    
    public void setSettlementType(Integer settlementType)
    {
        this.settlementType = settlementType;
    }
    
    public Integer getSchedulePaymentStatus()
    {
        return schedulePaymentStatus;
    }
    
    public void setSchedulePaymentStatus(Integer schedulePaymentStatus)
    {
        this.schedulePaymentStatus = schedulePaymentStatus;
    }
    
    public List<OrderRefund> getOrderRefund()
    {
        return orderRefund;
    }
    
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    public Integer getSamplingAmount()
    {
        return samplingAmount;
    }
    
    public void setSamplingAmount(Integer samplingAmount)
    {
        this.samplingAmount = samplingAmount;
    }
    
    public Integer getIssueInvoice()
    {
        return issueInvoice;
    }
    
    public void setIssueInvoice(Integer issueInvoice)
    {
        this.issueInvoice = issueInvoice;
    }
    
    public String getContractPartB()
    {
        return contractPartB;
    }
    
    public void setContractPartB(String contractPartB)
    {
        this.contractPartB = contractPartB;
    }
    
    public String getContractsettlementMode()
    {
        return contractsettlementMode;
    }
    
    public void setContractsettlementMode(String contractsettlementMode)
    {
        this.contractsettlementMode = contractsettlementMode;
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
    
    public BigDecimal getReplenishPrice()
    {
        return replenishPrice;
    }
    
    public void setReplenishPrice(BigDecimal replenishPrice)
    {
        this.replenishPrice = replenishPrice;
    }
    
    DecimalFormat fnum = new DecimalFormat("##0.00");
    
    public String getRealPrice()
    {
        if (StringUtils.isNotEmpty(amount) && StringUtils.isNotEmpty(subsidiarySampleAmount) && StringUtils.isNotEmpty(discountAmount))
        {
            BigDecimal realMoney =
                BigDecimal.valueOf(Double.valueOf(amount) + Double.valueOf(subsidiarySampleAmount) - Double.valueOf(discountAmount))
                    .divide(new BigDecimal(100));
            return fnum.format(realMoney);
        }
        else
        {
            return "0";
        }
        
    }
    
    public void setRealPrice(String realPrice)
    {
        this.realPrice = realPrice;
    }
    
    public BigDecimal getReduceAmount()
    {
        if (StringUtils.isNotEmpty(reduceAmount))
        {
            return BigDecimal.valueOf(reduceAmount).divide(new BigDecimal(100));
        }
        else
        {
            return new BigDecimal(0);
        }
    }
    
    public void setReduceAmount(Integer reduceAmount)
    {
        this.reduceAmount = reduceAmount;
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
    
    public String getIncomingAmount()
    {
        if (StringUtils.isNotEmpty(incomingAmount))
        {
            BigDecimal realAmount = BigDecimal.valueOf(incomingAmount).divide(new BigDecimal(100));
            return fnum.format(realAmount);
        }
        else
        {
            return "0.00";
        }
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
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public Integer getFreeCount()
    {
        return freeCount;
    }
    
    public void setFreeCount(Integer freeCount)
    {
        this.freeCount = freeCount;
    }
    
    public Integer getExtraMoney()
    {
        return extraMoney;
    }
    
    public void setExtraMoney(Integer extraMoney)
    {
        this.extraMoney = extraMoney;
    }
    
    public Customer getOwner()
    {
        return owner;
    }
    
    public void setOwner(Customer owner)
    {
        this.owner = owner;
    }
    
    public String getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
    public String getSubmitterId()
    {
        return submitterId;
    }
    
    public void setSubmitterId(String submitterId)
    {
        this.submitterId = submitterId;
    }
    
    public String getSubmitterName()
    {
        return submitterName;
    }
    
    public void setSubmitterName(String submitterName)
    {
        this.submitterName = submitterName;
    }
    
    public Integer getSubmitSource()
    {
        return submitSource;
    }
    
    public void setSubmitSource(Integer submitSource)
    {
        this.submitSource = submitSource;
    }
    
    public List<OrderSampleView> getView()
    {
        return view;
    }
    
    public void setView(List<OrderSampleView> view)
    {
        this.view = view;
    }
    
    public List<OrderSampleGroup> getOrderSampleGroup()
    {
        return orderSampleGroup;
    }
    
    public void setOrderSampleGroup(List<OrderSampleGroup> orderSampleGroup)
    {
        this.orderSampleGroup = orderSampleGroup;
    }
    
    public Order()
    {
        super();
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public List<OrderProduct> getOrderProductList()
    {
        return orderProductList;
    }
    
    public void setOrderProductList(List<OrderProduct> orderProductList)
    {
        this.orderProductList = orderProductList;
    }
    
    public List<OrderPrimarySample> getOrderPrimarySample()
    {
        return orderPrimarySample;
    }
    
    public void setOrderPrimarySample(List<OrderPrimarySample> orderPrimarySample)
    {
        this.orderPrimarySample = orderPrimarySample;
    }
    
    public List<OrderSubsidiarySample> getOrderSubsidiarySample()
    {
        return orderSubsidiarySample;
    }
    
    public void setOrderSubsidiarySample(List<OrderSubsidiarySample> orderSubsidiarySample)
    {
        this.orderSubsidiarySample = orderSubsidiarySample;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public int getAmount()
    {
        return amount;
    }
    
    public void setAmount(int amount)
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
    
    public String getDoctorAssist()
    {
        return doctorAssist;
    }
    
    public void setDoctorAssist(String doctorAssist)
    {
        this.doctorAssist = doctorAssist;
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
    
    public List<OrderExaminee> getOrderExamineeList()
    {
        return orderExamineeList;
    }
    
    public void setOrderExamineeList(List<OrderExaminee> orderExamineeList)
    {
        this.orderExamineeList = orderExamineeList;
    }
    
    public Integer getSheduleStatus()
    {
        return sheduleStatus;
    }
    
    public void setSheduleStatus(Integer sheduleStatus)
    {
        this.sheduleStatus = sheduleStatus;
    }
    
    public String getFlag()
    {
        return flag;
    }
    
    public void setFlag(String flag)
    {
        this.flag = flag;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public Double getApplyAmount()
    {
        return applyAmount;
    }
    
    public void setApplyAmount(Double applyAmount)
    {
        this.applyAmount = applyAmount;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    private List<OrderResearchProduct> orderResearchProduct;
    
    public List<OrderResearchProduct> getOrderResearchProduct()
    {
        return orderResearchProduct;
    }
    
    public void setOrderResearchProduct(List<OrderResearchProduct> orderResearchProduct)
    {
        this.orderResearchProduct = orderResearchProduct;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getSequenceCodes()
    {
        return sequenceCodes;
    }
    
    public void setSequenceCodes(String sequenceCodes)
    {
        this.sequenceCodes = sequenceCodes;
    }
    
    public String getAlreadyGeneratedPrice()
    {
        if (StringUtils.isNotEmpty(amount) && StringUtils.isNotEmpty(subsidiarySampleAmount) && StringUtils.isNotEmpty(discountAmount)
            && StringUtils.isNotEmpty(reduceAmount))
        {
            BigDecimal alreadygeneratedprice =
                BigDecimal.valueOf(Double.valueOf(amount) + Double.valueOf(subsidiarySampleAmount) - Double.valueOf(discountAmount)
                    - Double.valueOf(reduceAmount)).divide(new BigDecimal(100));
            return fnum.format(alreadygeneratedprice);
        }
        else
        {
            return "0";
        }
        
    }
    
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public Date getPaymentTime()
    {
        return paymentTime;
    }
    
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }
    
    public Date getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }
    
    public List<FinanceInvoiceTask> getTaskList()
    {
        return taskList;
    }
    
    public void setTaskList(List<FinanceInvoiceTask> taskList)
    {
        this.taskList = taskList;
    }
    
    public List<TestingReport> getReportList()
    {
        return reportList;
    }
    
    public void setReportList(List<TestingReport> reportList)
    {
        this.reportList = reportList;
    }
    
    public Integer getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(Integer testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }
    
    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }
    
    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }
    
    public String getUrgentName()
    {
        return urgentName;
    }
    
    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }
    
    public DefaultInvoiceModel getInvoiceModel()
    {
        return invoiceModel;
    }
    
    public void setInvoiceModel(DefaultInvoiceModel invoiceModel)
    {
        this.invoiceModel = invoiceModel;
    }
    
    public Date getPayTime()
    {
        return payTime;
    }
    
    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }
    
    public Boolean getIsAppendSample()
    {
        return isAppendSample;
    }
    
    public void setIsAppendSample(Boolean isAppendSample)
    {
        this.isAppendSample = isAppendSample;
    }
    
    public String getShowCopyOrder()
    {
        return showCopyOrder;
    }
    
    public void setShowCopyOrder(String showCopyOrder)
    {
        this.showCopyOrder = showCopyOrder;
    }
}
