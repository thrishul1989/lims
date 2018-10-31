package com.todaysoft.lims.system.model.vo.samplereceiving;

import java.math.BigDecimal;
import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.persist.UuidKeyEntity;

public class SampleTransferringDetail extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String sampleCode;//    '样本编号',
    
    private BigDecimal sampleSize;// '接收样本量',
    
    private BigDecimal lsSize; //'长期存储量',
    
    private BigDecimal tsSize;//'临时存储量',
    
    private String sizeUnit;//'收样量、存储量单位名称',
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private Integer sort;
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限
    
    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
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
    
    //视图部分--前台传入
    private String sampleId; //样本id
    
    private String sampleName;//样本名称
    
    private String typeId;//样本类型
    
    private String typeName;
    
    private Integer sampleBtype;//传给前台显示并传入后台  ---业务类型：1-非科研主样本 2-非科研附属样本 3-科研样本
    
    private Integer purpose;//传给前台显示并传入后台  ---业务类型：1-2-3
    
    private SampleTransferring sampleTransferring; //转存记录
    
    public Integer getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(Integer purpose)
    {
        this.purpose = purpose;
    }
    
    public Integer getSampleBtype()
    {
        return sampleBtype;
    }
    
    public void setSampleBtype(Integer sampleBtype)
    {
        this.sampleBtype = sampleBtype;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getTypeId()
    {
        return typeId;
    }
    
    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    public BigDecimal getLsSize()
    {
        return lsSize;
    }
    
    public void setLsSize(BigDecimal lsSize)
    {
        this.lsSize = lsSize;
    }
    
    public BigDecimal getTsSize()
    {
        return tsSize;
    }
    
    public void setTsSize(BigDecimal tsSize)
    {
        this.tsSize = tsSize;
    }
    
    public String getSizeUnit()
    {
        return sizeUnit;
    }
    
    public void setSizeUnit(String sizeUnit)
    {
        this.sizeUnit = sizeUnit;
    }
    
    public SampleTransferring getSampleTransferring()
    {
        return sampleTransferring;
    }
    
    public void setSampleTransferring(SampleTransferring sampleTransferring)
    {
        this.sampleTransferring = sampleTransferring;
    }
    
    private String operatorName; //查询条件  操作人
    
    private String start;//查询条件  开始时间
    
    private String end;//查询条件  结束时间
    
    public String getOperatorName()
    {
        return operatorName;
    }
    
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }
    
    public String getStart()
    {
        return start;
    }
    
    public void setStart(String start)
    {
        this.start = start;
    }
    
    public String getEnd()
    {
        return end;
    }
    
    public void setEnd(String end)
    {
        this.end = end;
    }
    
}
