package com.todaysoft.lims.sample.dao.searcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleTransferring;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleTransferringDetail;
import com.todaysoft.lims.utils.StringUtils;

public class SampleTransferringDetailSearcher implements ISearcher<SampleTransferringDetail>
{
    
    private String sampleCode;//    '样本编号',
    
    private String sampleSize;// '接收样本量',
    
    private Integer lsSize; //'长期存储量',
    
    private Integer tsSize;//'临时存储量',
    
    private String sizeUnit;//'收样量、存储量单位名称',
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String operatorName; //查询条件  操作人
    
    private String start;//查询条件  开始时间
    
    private String end;//查询条件  结束时间
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select distinct s FROM SampleTransferring s , SampleTransferringDetail d WHERE 1=1 and s.id = d.sampleTransferring.id ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" order by s.operateTime desc ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND d.sampleCode LIKE :sampleCode");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        
        if (StringUtils.isNotEmpty(operatorName))
        {
            hql.append(" AND s.operatorName LIKE :operatorName");
            paramNames.add("operatorName");
            parameters.add("%" + operatorName + "%");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            
            if (StringUtils.isNotEmpty(start))
            {
                hql.append(" AND s.operateTime > :start");
                paramNames.add("start");
                parameters.add(sdf.parse(start));
            }
            if (StringUtils.isNotEmpty(end))
            {
                hql.append(" AND s.operateTime < :end");
                paramNames.add("end");
                parameters.add(sdf.parse(end));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public Class<SampleTransferringDetail> getEntityClass()
    {
        return SampleTransferringDetail.class;
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
    
    private SampleTransferring sampleTransferring; //转存记录
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(String sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    public Integer getLsSize()
    {
        return lsSize;
    }
    
    public void setLsSize(Integer lsSize)
    {
        this.lsSize = lsSize;
    }
    
    public Integer getTsSize()
    {
        return tsSize;
    }
    
    public void setTsSize(Integer tsSize)
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
