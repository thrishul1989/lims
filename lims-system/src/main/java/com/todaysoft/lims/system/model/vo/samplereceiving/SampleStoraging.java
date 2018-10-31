package com.todaysoft.lims.system.model.vo.samplereceiving;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.persist.UuidKeyEntity;

public class SampleStoraging extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code;//''入库编号'
    
    private Integer type; // '入库类型',
    
    private String remark;//   '入库备注',
    
    private String operatorId;//  '操作人ID',
    
    private String operatorName;//'操作人姓名',
    
    private Date operateTime;//'操作时间',
    
    private List<SampleStoragingDetail> sampleStoragingDetail = new ArrayList<SampleStoragingDetail>(); //转存记录明细
    
    private boolean status;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String searchType;
    
    private Integer isOver;
    
    private String sampleCode;
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限
    
    private String storagingType; //传到前台的入库类型
    
    public String getStoragingType()
    {
        return storagingType;
    }
    
    public void setStoragingType(String storagingType)
    {
        this.storagingType = storagingType;
    }
    
    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Integer getIsOver()
    {
        return isOver;
    }
    
    public void setIsOver(Integer isOver)
    {
        this.isOver = isOver;
    }
    
    public String getSearchType()
    {
        return searchType;
    }
    
    public void setSearchType(String searchType)
    {
        this.searchType = searchType;
    }
    
    public boolean isStatus()
    {
        return status;
    }
    
    public void setStatus(boolean status)
    {
        this.status = status;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
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
    
    public List<SampleStoragingDetail> getSampleStoragingDetail()
    {
        return sampleStoragingDetail;
    }
    
    public void setSampleStoragingDetail(List<SampleStoragingDetail> sampleStoragingDetail)
    {
        this.sampleStoragingDetail = sampleStoragingDetail;
    }
    
}
