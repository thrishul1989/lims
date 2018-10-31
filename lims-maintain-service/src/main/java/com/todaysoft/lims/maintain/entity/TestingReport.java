package com.todaysoft.lims.maintain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    
    private String orderId;
    
    private String orderCode;
    
    private String productId;
    
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
    
    private Date reportDate;
    
    private String dataUrl;
    
    private Integer productStatus;//产品状态，0：待送测；1：待数据分析；2：待验证；3：待出报告；4：待审核报告；5：待寄送报告；6：已完成；
    
    private boolean resubmit;
    
    private Integer resubmitCount;//报告重出次数RESUBMIT_COUNT
    
    private String remark;//报告审核不通过备注
    
    private Integer reportStatus;//报告状态：0-未出报告，1-待一审，2-待二审，3-待寄送，4-已寄送
    
    private List<TestingReportReview> reviewList;
    
    private boolean ifRedo;//是否打回重做
    
    private Integer redoCount;//重做次数

    private String productDutyMan;//产品负责人

    private boolean delFlag;//删除标志

    @Column(name = "DEL_FLAG")
    public boolean isDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }

    @Column(name = "PRODUCT_DUTY_MAN")
    public String getProductDutyMan() {
        return productDutyMan;
    }

    public void setProductDutyMan(String productDutyMan) {
        this.productDutyMan = productDutyMan;
    }

    //    private Integer mailStatus;//0-不需要邮寄，1-需要邮寄
//
//    @Column(name="MAIL_STATUS")
//    public Integer getMailStatus() {
//        return mailStatus;
//    }
//
//    public void setMailStatus(Integer mailStatus) {
//        this.mailStatus = mailStatus;
//    }
    
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
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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
    
    @Column(name = "PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
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

    @Column(name = "TECHNICAL_MAN_ID")
    public String getTechnicalManId() {
        return technicalManId;
    }

    public void setTechnicalManId(String technicalManId) {
        this.technicalManId = technicalManId;
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
    public boolean isResubmit()
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
}
