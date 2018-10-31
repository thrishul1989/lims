package com.todaysoft.lims.system.model.vo.order;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class OrderResearchSampleForUpload
{
    
    private String sampleCode; //样本编号
    
    private String sampleTypeId;//样本类型ID
    
    private String sampleName; //样本名称
    
    private String sampleSize;//样本量
    
    private String samplingDate;//采样时间
    
    private String diagnosis;
    
    private String focusGene;
    
    private String familyRelation;
    
    private String remark;
    
    private List<Product> orderResearchProduct = new ArrayList<Product>();
    
    private String sampleTypeName;//类型名称
    
    private String productUploadString; //上传接收产品字符串
    
    private String groupName;
    
    public OrderResearchSampleForUpload()
    {
        super();
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
    public String getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(String sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
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
    
    public List<Product> getOrderResearchProduct()
    {
        return orderResearchProduct;
    }
    
    public void setOrderResearchProduct(List<Product> orderResearchProduct)
    {
        this.orderResearchProduct = orderResearchProduct;
    }
    
}
