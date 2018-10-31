package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_REPORT")
public class TestingReport extends UuidKeyEntity implements Serializable
{
    
    public static int REPORT_STATUS_WAIT_DATA = 0;//带数据整合
    
    public static int REPORT_STATUS_ABLE_REPORT = 1;//可出报告
    
    public static int REPORT_STATUS_COMPLETED = 2;//已出报告
    
    public static String REPORT_NO = "REPORT-NO";//已出报告
    
    private static final long serialVersionUID = -1549652317268723301L;
    
    private Order order;
    
    private Product product;
    
    private String orderCode;
    
    private String productCode;
    
    private String productName;
    
    private String sampleId;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String testUnit;
    
    private String customer;
    
    private String analType;
    
    private String businessMan;
    
    private String technicalMan;

    private String technicalManId;
    
    private String marketingCenter;
    
    private Date shouldReportDate;
    
    private Integer totalNum;
    
    private Integer completeNum;
    
    private Date createDate;
    
    private Date updateDate;
    
    private Integer status;
    
    private String reportCode;
    
    private String reportName;
    
    private String reviewerName;
    
    private String firstReviewStatus;
    
    private String secondReviewStatus;
    
    private List<TestingReportReview> reviewList;
    
    private Date reportDate;
    
    private String dataUrl;
    
    private Integer productStatus;//产品状态，0：待送测；1：待数据分析；2：待验证；3：待出报告；4：待审核报告；5：待寄送报告；6：已完成；
    
    private boolean resubmit;
    
    private Integer resubmitCount;//报告重出次数RESUBMIT_COUNT
    
    private String remark;//报告审核不通过备注
    
    private Integer reportStatus;//报告状态：0-未出报告，1-待一审，2-待二审，3-待寄送，4-已寄送
    
    private boolean pdfFile;
    
    private boolean docxFile;
    
    private String combineFileName;
    
    private String sequenceCode;
    
    private String inspectMan;
    
    private String productLeader;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private boolean ifRedo;//是否打回重做
    
    private Integer redoCount;//重做次数
    
    private String instruction;//结果说明
    
    private String reportType;//报告分类
    
    private String analyResult;//结果说明
    
    private String pictures;//关联图片
    
    private boolean delFlag;//删除标志
    
    private String mapTemplate;
    
    private Integer sendStatus;//报告处理状态:1处理中 2成功 3失败
    
    private String operateRemark;
    
    private String reportTemplateType;

    private Integer assignStatus;

    private String assignerId;

    private String assignerName;

    private Date assignDate;

    private String receiverId;

    private String receiverName;

    private Date completeDate;

    private String productDutyMan;//产品负责人

    @Column(name="PRODUCT_DUTY_MAN")
    public String getProductDutyMan() {
        return productDutyMan;
    }

    public void setProductDutyMan(String productDutyMan) {
        this.productDutyMan = productDutyMan;
    }

    @Column(name="COMPLETE_DATE")
    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    @Column(name = "TECHNICAL_MAN_ID")
    public String getTechnicalManId() {
        return technicalManId;
    }

    public void setTechnicalManId(String technicalManId) {
        this.technicalManId = technicalManId;
    }
    
    @Column(name = "IF_REDO")
    public boolean isIfRedo()
    {
        return ifRedo;
    }
    
    public void setIfRedo(boolean ifRedo)
    {
        this.ifRedo = ifRedo;
    }
    
    @Column(name = "REDO_COUNT")
    public Integer getRedoCount()
    {
        return redoCount;
    }
    
    public void setRedoCount(Integer redoCount)
    {
        this.redoCount = redoCount;
    }
    
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    @Column(name = "ORDER_CODE")
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    @Column(name = "PRODUCT_CODE")
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
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
    
    @Column(name = "SAMPLE_ID")
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "SAMPLE_NAME")
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    @Column(name = "TEST_UNIT")
    public String getTestUnit()
    {
        return testUnit;
    }
    
    public void setTestUnit(String testUnit)
    {
        this.testUnit = testUnit;
    }
    
    @Column(name = "CUSTOMER")
    public String getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    @Column(name = "ANAL_TYPE")
    public String getAnalType()
    {
        return analType;
    }
    
    public void setAnalType(String analType)
    {
        this.analType = analType;
    }
    
    @Column(name = "BUSINESS_MAN")
    public String getBusinessMan()
    {
        return businessMan;
    }
    
    public void setBusinessMan(String businessMan)
    {
        this.businessMan = businessMan;
    }
    
    @Column(name = "TECHNICAL_MAN")
    public String getTechnicalMan()
    {
        return technicalMan;
    }
    
    public void setTechnicalMan(String technicalMan)
    {
        this.technicalMan = technicalMan;
    }
    
    @Column(name = "MARKETING_CENTER")
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    @Column(name = "SHOULD_REPORT_DATE")
    public Date getShouldReportDate()
    {
        return shouldReportDate;
    }
    
    public void setShouldReportDate(Date shouldReportDate)
    {
        this.shouldReportDate = shouldReportDate;
    }
    
    @Column(name = "TOTAL_NUM")
    public Integer getTotalNum()
    {
        return totalNum;
    }
    
    public void setTotalNum(Integer totalNum)
    {
        this.totalNum = totalNum;
    }
    
    @Column(name = "COMPLETE_NUM")
    public Integer getCompleteNum()
    {
        return completeNum;
    }
    
    public void setCompleteNum(Integer completeNum)
    {
        this.completeNum = completeNum;
    }
    
    @Column(name = "CREATE_DATE")
    public Date getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }
    
    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    @Column(name = "REPORT_CODE")
    public String getReportCode()
    {
        return reportCode;
    }
    
    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }
    
    @Column(name = "REPORT_NAME")
    public String getReportName()
    {
        return reportName;
    }
    
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }
    
    @Column(name = "REVIEWER_NAME")
    public String getReviewerName()
    {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
    }
    
    @Column(name = "FIRST_REVIEW_STATUS")
    public String getFirstReviewStatus()
    {
        return firstReviewStatus;
    }
    
    public void setFirstReviewStatus(String firstReviewStatus)
    {
        this.firstReviewStatus = firstReviewStatus;
    }
    
    @Column(name = "SECOND_REVIEW_STATUS")
    public String getSecondReviewStatus()
    {
        return secondReviewStatus;
    }
    
    public void setSecondReviewStatus(String secondReviewStatus)
    {
        this.secondReviewStatus = secondReviewStatus;
    }
    
    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy("reviewTime")
    public List<TestingReportReview> getReviewList()
    {
        return reviewList;
    }
    
    public void setReviewList(List<TestingReportReview> reviewList)
    {
        this.reviewList = reviewList;
    }
    
    @Transient
    public Date getReportDate()
    {
        return reportDate;
    }
    
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    
    @Transient
    public String getDataUrl()
    {
        return dataUrl;
    }
    
    public void setDataUrl(String dataUrl)
    {
        this.dataUrl = dataUrl;
    }
    
    @Transient
    public Integer getProductStatus()
    {
        return productStatus;
    }
    
    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }
    
    @Column(name = "RESUBMIT")
    public boolean getResubmit()
    {
        return resubmit;
    }

    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }
    
    @Column(name = "RESUBMIT_COUNT")
    public Integer getResubmitCount()
    {
        return resubmitCount;
    }
    
    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }
    
    @Transient
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Transient
    public Integer getReportStatus()
    {
        return reportStatus;
    }
    
    public void setReportStatus(Integer reportStatus)
    {
        this.reportStatus = reportStatus;
    }
    
    @Transient
    public boolean isPdfFile()
    {
        return pdfFile;
    }
    
    public void setPdfFile(boolean pdfFile)
    {
        this.pdfFile = pdfFile;
    }
    
    @Transient
    public boolean isDocxFile()
    {
        return docxFile;
    }

    public void setDocxFile(boolean docxFile)
    {
        this.docxFile = docxFile;
    }

    @Transient
    public String getCombineFileName()
    {
        return combineFileName;
    }
    
    public void setCombineFileName(String combineFileName)
    {
        this.combineFileName = combineFileName;
    }
    
    @Transient
    public String getSequenceCode()
    {
        return sequenceCode;
    }
    
    public void setSequenceCode(String sequenceCode)
    {
        this.sequenceCode = sequenceCode;
    }
    
    @Transient
    public String getInspectMan()
    {
        return inspectMan;
    }
    
    public void setInspectMan(String inspectMan)
    {
        this.inspectMan = inspectMan;
    }
    
    @Transient
    public String getProductLeader()
    {
        return productLeader;
    }
    
    public void setProductLeader(String productLeader)
    {
        this.productLeader = productLeader;
    }
    
    @Transient
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }
    
    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
    @Transient
    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }
    
    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }
    
    @Transient
    public String getUrgentName()
    {
        return urgentName;
    }
    
    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }
    
    @Column(name = "INSTRUCTION")
    public String getInstruction()
    {
        return instruction;
    }
    
    public void setInstruction(String instruction)
    {
        this.instruction = instruction;
    }
    
    @Column(name = "REPORT_TYPE")
    public String getReportType()
    {
        return reportType;
    }
    
    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }
    
    @Column(name = "ANALY_RESULT")
    public String getAnalyResult()
    {
        return analyResult;
    }
    
    public void setAnalyResult(String analyResult)
    {
        this.analyResult = analyResult;
    }
    
    @Column(name = "PICTURES")
    public String getPictures()
    {
        return pictures;
    }
    
    public void setPictures(String pictures)
    {
        this.pictures = pictures;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
    @Column(name = "SEND_STATUS")
    public Integer getSendStatus()
    {
        return sendStatus;
    }
    
    public void setSendStatus(Integer sendStatus)
    {
        this.sendStatus = sendStatus;
    }
    
    @Transient
    public String getMapTemplate()
    {
        return mapTemplate;
    }
    
    public void setMapTemplate(String mapTemplate)
    {
        this.mapTemplate = mapTemplate;
    }

    @Column(name="OPERATE_REMARK")
    public String getOperateRemark()
    {
        return operateRemark;
    }

    public void setOperateRemark(String operateRemark)
    {
        this.operateRemark = operateRemark;
    }

    @Column(name="REPORT_TEMPLATE_TYPE")
    public String getReportTemplateType()
    {
        return reportTemplateType;
    }

    public void setReportTemplateType(String reportTemplateType)
    {
        this.reportTemplateType = reportTemplateType;
    }

    @Column(name="RECEIVER_NAME")
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Column(name="RECEIVER_ID")
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Column(name="ASSIGN_DATE")
    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    @Column(name="ASSIGNER_NAME")
    public String getAssignerName() {
        return assignerName;
    }

    public void setAssignerName(String assignerName) {
        this.assignerName = assignerName;
    }

    @Column(name="ASSIGNER_ID")
    public String getAssignerId() {
        return assignerId;
    }

    public void setAssignerId(String assignerId) {
        this.assignerId = assignerId;
    }

    @Column(name="ASSIGN_STATUS")
    public Integer getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(Integer assignStatus) {
        this.assignStatus = assignStatus;
    }
}
