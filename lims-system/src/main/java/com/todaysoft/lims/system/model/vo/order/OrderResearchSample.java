package com.todaysoft.lims.system.model.vo.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.utils.excel.annotation.ExcelField;

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
    
    private String samplingDate;//采样时间
    
    private String diagnosis;
    
    private String focusGene;
    
    private String familyRelation;
    
    private String remark;
    
    private OrderSampleGroup orderSampleGroup; //样本组
    
    private Order order; //订单对象
    
    private List<OrderResearchProduct> orderResearchProduct = new ArrayList<OrderResearchProduct>();
    
    private String sampleTypeName;//类型名称
    
    private String samplereceivingUnit;//收样单位
    
    private Integer samplePackageStatus;
    
    private Integer sampleErrorStatus; //异常样本处理状态,0：未处理；1：已处理；
    
    private String productUploadString; //上传接收产品字符串
    
    private String groupName;
    
    private Date receiveTime;
    
    public Integer getSampleErrorStatus()
    {
        return sampleErrorStatus;
    }
    
    public void setSampleErrorStatus(Integer sampleErrorStatus)
    {
        this.sampleErrorStatus = sampleErrorStatus;
    }
    
    public OrderResearchSample()
    {
        super();
    }
    
    public Integer getSamplePackageStatus()
    {
        return samplePackageStatus;
    }
    
    public void setSamplePackageStatus(Integer samplePackageStatus)
    {
        this.samplePackageStatus = samplePackageStatus;
    }
    
    @ExcelField(title = "样本编号", align = 1, sort = 1)
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    @ExcelField(title = "样本类型", align = 1, sort = 2)
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    @ExcelField(title = "样本名称", align = 1, sort = 3)
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    @ExcelField(title = "样本量", align = 1, sort = 4)
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    @ExcelField(title = "采样时间", align = 1, sort = 5)
    public String getSamplingDate()
    {
        return samplingDate;
    }
    
    public void setSamplingDate(String samplingDate)
    {
        this.samplingDate = samplingDate;
    }
    
    @ExcelField(title = "临床诊断", align = 1, sort = 6)
    public String getDiagnosis()
    {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }
    
    @ExcelField(title = "家系关系", align = 1, sort = 7)
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    @ExcelField(title = "重点关注基因", align = 1, sort = 8)
    public String getFocusGene()
    {
        return focusGene;
    }
    
    public void setFocusGene(String focusGene)
    {
        this.focusGene = focusGene;
    }
    
    @ExcelField(title = "检测产品", align = 1, sort = 9)
    public String getProductUploadString()
    {
        return productUploadString;
    }
    
    public void setProductUploadString(String productUploadString)
    {
        this.productUploadString = productUploadString;
    }
    
    @ExcelField(title = "备注", align = 1, sort = 10)
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @ExcelField(title = "组名", align = 1, sort = 11)
    public String getGroupName()
    {
        return groupName;
    }
    
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
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
    
    public String getSamplereceivingUnit()
    {
        return samplereceivingUnit;
    }
    
    public void setSamplereceivingUnit(String samplereceivingUnit)
    {
        this.samplereceivingUnit = samplereceivingUnit;
    }
    
    private String activeTask;//页面显示
    
    private Date updateTime;//页面显示
    
    private String scheduleId;//页面显示
    
    private String productId;//页面显示
    
    private String testMethodId;//页面显示
    
    private String testMethodName;//页面显示
    
    private String productName;//页面显示
    
    private String verifyKey;//页面显示
    
    public String getVerifyKey()
    {
        return verifyKey;
    }
    
    public void setVerifyKey(String verifyKey)
    {
        this.verifyKey = verifyKey;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getTestMethodId()
    {
        return testMethodId;
    }
    
    public void setTestMethodId(String testMethodId)
    {
        this.testMethodId = testMethodId;
    }
    
    public String getTestMethodName()
    {
        return testMethodName;
    }
    
    public void setTestMethodName(String testMethodName)
    {
        this.testMethodName = testMethodName;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getActiveTask()
    {
        return activeTask;
    }
    
    public void setActiveTask(String activeTask)
    {
        this.activeTask = activeTask;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getScheduleId()
    {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }

    public Date getReceiveTime()
    {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }
    
}
