package com.todaysoft.lims.sample.entity.materialsApply;

import java.util.Date;
import java.util.List;

public class MaterialsApplyTransportRequest
{
    private String id;
    
    private String applyId;//申请ID
    
    private String transportType;//运输方式（字典：0:人工运输、1:快递、...）
    
    private String transportName;//运输人姓名
    
    private String transportPhone;//运输人电话
    
    private String courierNumber;//快递号
    
    private Date sendTime;//寄送时间
    
    private List<MaterialsApplyTransportRelation> relations;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getApplyId()
    {
        return applyId;
    }
    
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }
    
    public String getTransportType()
    {
        return transportType;
    }
    
    public void setTransportType(String transportType)
    {
        this.transportType = transportType;
    }
    
    public String getTransportName()
    {
        return transportName;
    }
    
    public void setTransportName(String transportName)
    {
        this.transportName = transportName;
    }
    
    public String getTransportPhone()
    {
        return transportPhone;
    }
    
    public void setTransportPhone(String transportPhone)
    {
        this.transportPhone = transportPhone;
    }
    
    public String getCourierNumber()
    {
        return courierNumber;
    }
    
    public void setCourierNumber(String courierNumber)
    {
        this.courierNumber = courierNumber;
    }
    
    public Date getSendTime()
    {
        return sendTime;
    }
    
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }
    
    public List<MaterialsApplyTransportRelation> getRelations()
    {
        return relations;
    }
    
    public void setRelations(List<MaterialsApplyTransportRelation> relations)
    {
        this.relations = relations;
    }
}
