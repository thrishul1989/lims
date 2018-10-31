package com.todaysoft.lims.system.modules.report.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.order.Order;

public class TestingReport
{
    
    private String id;
    
    private Order order;
    
    private String orderCode;
    
    private Product product;
    
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
    
    private String marketingCenter;
    
    private Date shouldReportDate;
    
    private Integer totalNum;
    
    private Integer completeNum;
    
    private Date createDate;
    
    private Date updateDate;
    
    private int status;
    
    private String reportCode;
    
    private String reportName;
    
    private String reviewerName;
    
    private String firstReviewStatus;
    
    private String secondReviewStatus;
    
    private List<TestingReportReview> reviewList;
    
    private String reviewResult;
    
    private String reviewOpinion;
    
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
    
    private String productLeader;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getTestUnit()
    {
        return testUnit;
    }
    
    public void setTestUnit(String testUnit)
    {
        this.testUnit = testUnit;
    }
    
    public String getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    public String getAnalType()
    {
        return analType;
    }
    
    public void setAnalType(String analType)
    {
        this.analType = analType;
    }
    
    public String getBusinessMan()
    {
        return businessMan;
    }
    
    public void setBusinessMan(String businessMan)
    {
        this.businessMan = businessMan;
    }
    
    public String getTechnicalMan()
    {
        return technicalMan;
    }
    
    public void setTechnicalMan(String technicalMan)
    {
        this.technicalMan = technicalMan;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public Date getShouldReportDate()
    {
        return shouldReportDate;
    }
    
    public void setShouldReportDate(Date shouldReportDate)
    {
        this.shouldReportDate = shouldReportDate;
    }
    
    public Integer getTotalNum()
    {
        return totalNum;
    }
    
    public void setTotalNum(Integer totalNum)
    {
        this.totalNum = totalNum;
    }
    
    public Integer getCompleteNum()
    {
        return completeNum;
    }
    
    public void setCompleteNum(Integer completeNum)
    {
        this.completeNum = completeNum;
    }
    
    public Date getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }
    
    public Date getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public String getReportCode()
    {
        return reportCode;
    }
    
    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }
    
    public String getReportName()
    {
        return reportName;
    }
    
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }
    
    public String getReviewerName()
    {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
    }
    
    public String getFirstReviewStatus()
    {
        return firstReviewStatus;
    }
    
    public void setFirstReviewStatus(String firstReviewStatus)
    {
        this.firstReviewStatus = firstReviewStatus;
    }
    
    public String getSecondReviewStatus()
    {
        return secondReviewStatus;
    }
    
    public void setSecondReviewStatus(String secondReviewStatus)
    {
        this.secondReviewStatus = secondReviewStatus;
    }
    
    public List<TestingReportReview> getReviewList()
    {
        return reviewList;
    }
    
    public void setReviewList(List<TestingReportReview> reviewList)
    {
        this.reviewList = reviewList;
    }
    
    public String getReviewResult()
    {
        return reviewResult;
    }
    
    public void setReviewResult(String reviewResult)
    {
        this.reviewResult = reviewResult;
    }
    
    public String getReviewOpinion()
    {
        return reviewOpinion;
    }
    
    public void setReviewOpinion(String reviewOpinion)
    {
        this.reviewOpinion = reviewOpinion;
    }
    
    public Date getReportDate()
    {
        return reportDate;
    }
    
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    
    public String getDataUrl()
    {
        return dataUrl;
    }
    
    public void setDataUrl(String dataUrl)
    {
        this.dataUrl = dataUrl;
    }
    
    public Integer getProductStatus()
    {
        return productStatus;
    }
    
    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }
    
    public boolean isResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }
    
    public Integer getResubmitCount()
    {
        return resubmitCount;
    }
    
    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getReportStatus()
    {
        return reportStatus;
    }
    
    public void setReportStatus(Integer reportStatus)
    {
        this.reportStatus = reportStatus;
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
    
    public boolean isPdfFile()
    {
        return pdfFile;
    }
    
    public void setPdfFile(boolean pdfFile)
    {
        this.pdfFile = pdfFile;
    }
    
    public String getCombineFileName()
    {
        return combineFileName;
    }
    
    public void setCombineFileName(String combineFileName)
    {
        this.combineFileName = combineFileName;
    }
    
    public String getProductLeader()
    {
        return productLeader;
    }
    
    public void setProductLeader(String productLeader)
    {
        this.productLeader = productLeader;
    }

    public boolean isDocxFile()
    {
        return docxFile;
    }

    public void setDocxFile(boolean docxFile)
    {
        this.docxFile = docxFile;
    }
}
