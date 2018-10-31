package com.todaysoft.lims.system.model.vo.materialsApply;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.model.vo.enums.MaterialsStatus;

public class MaterialsApply
{
    private String id;
    
    private String code;//物资申请编号
    
    private Integer materialsNum;//物资明细数量
    
    private MaterialsStatus status;//状态：0-未处理，1-处理中，2-已完成
    
    private String recipientName;//收件人姓名
    
    private String recipientTel;//收件人电话
    
    private String recipientAddress;//收件人地址（省市区）,按照逗号隔开
    
    private String recipientAddressDetail;//收件人详细地址
    
    private String creatorId;//申请人ID
    
    private Date applyTime;//申请时间 
    
    private String counterman;//页面显示
    
    private String testingType;//页面显示
    
    private List<String> materialsType;//页面显示
    
    private List<MaterialsApplyDetail> materialsApplyDetail;
    
    private String fullName;//页面显示
    
    private Date sendTime;
    
    public Date getSendTime()
    {
        return sendTime;
    }
    
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getFullName()
    {
        return fullName;
    }
    
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    
    public List<MaterialsApplyDetail> getMaterialsApplyDetail()
    {
        return materialsApplyDetail;
    }
    
    public void setMaterialsApplyDetail(List<MaterialsApplyDetail> materialsApplyDetail)
    {
        this.materialsApplyDetail = materialsApplyDetail;
    }
    
    public List<String> getMaterialsType()
    {
        return materialsType;
    }
    
    public void setMaterialsType(List<String> materialsType)
    {
        this.materialsType = materialsType;
    }
    
    public String getCounterman()
    {
        return counterman;
    }
    
    public void setCounterman(String counterman)
    {
        this.counterman = counterman;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getMaterialsNum()
    {
        return materialsNum;
    }
    
    public void setMaterialsNum(Integer materialsNum)
    {
        this.materialsNum = materialsNum;
    }
    
    public MaterialsStatus getStatus()
    {
        return status;
    }
    
    public void setStatus(MaterialsStatus status)
    {
        this.status = status;
    }
    
    public String getRecipientName()
    {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    
    public String getRecipientTel()
    {
        return recipientTel;
    }
    
    public void setRecipientTel(String recipientTel)
    {
        this.recipientTel = recipientTel;
    }
    
    public String getRecipientAddress()
    {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }
    
    public String getRecipientAddressDetail()
    {
        return recipientAddressDetail;
    }
    
    public void setRecipientAddressDetail(String recipientAddressDetail)
    {
        this.recipientAddressDetail = recipientAddressDetail;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
}
