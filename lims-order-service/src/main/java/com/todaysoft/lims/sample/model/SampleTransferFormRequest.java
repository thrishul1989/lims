package com.todaysoft.lims.sample.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.model.sampleReceiving.SampleTransferringDetailModel;

public class SampleTransferFormRequest extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code;//'接收编号'
    
    private String remark;// '接收备注',
    
    private String operatorId; // '操作人ID',
    
    private String operatorName;//      '操作人姓名',
    
    private Date operateTime;//  '操作时间',
    
    private String sampleTransferringString; //共前台接收JSON串
    
    public String getSampleTransferringString()
    {
        return sampleTransferringString;
    }
    
    public void setSampleTransferringString(String sampleTransferringString)
    {
        this.sampleTransferringString = sampleTransferringString;
    }
    
    private List<SampleTransferringDetailModel> sampleTransferringDetail = new ArrayList<SampleTransferringDetailModel>(); //转存记录明细
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getOperatorId()
    {
        return operatorId;
    }
    
    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }
    
    public String getOperatorName()
    {
        return operatorName;
    }
    
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }
    
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    public List<SampleTransferringDetailModel> getSampleTransferringDetail()
    {
        return sampleTransferringDetail;
    }
    
    public void setSampleTransferringDetail(List<SampleTransferringDetailModel> sampleTransferringDetail)
    {
        this.sampleTransferringDetail = sampleTransferringDetail;
    }
    
}
