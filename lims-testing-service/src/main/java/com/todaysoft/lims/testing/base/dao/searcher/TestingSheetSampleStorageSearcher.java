package com.todaysoft.lims.testing.base.dao.searcher;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.samplesotck.entity.TestingSheetSampleStorage;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TestingSheetSampleStorageSearcher implements ISearcher<TestingSheetSampleStorage>
{
    private String testingSheetCode;
    
    private String storageType;
    
    private Integer status;
    
    private String storageRecordId;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String semantic;
    
    private String startTime;
    
    private String endTime;
    
    public String getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    public String getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getTestingSheetCode()
    {
        return testingSheetCode;
    }

    public void setTestingSheetCode(String testingSheetCode)
    {
        this.testingSheetCode = testingSheetCode;
    }

    public String getStorageType()
    {
        return storageType;
    }
    
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getStorageRecordId()
    {
        return storageRecordId;
    }
    
    public void setStorageRecordId(String storageRecordId)
    {
        this.storageRecordId = storageRecordId;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        if (StringUtils.isEmpty(testingSheetCode) && StringUtils.isEmpty(sampleCode) && StringUtils.isEmpty(sampleName) && StringUtils.isEmpty(semantic)
            && StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime) && StringUtils.isEmpty(status))
        {
            hql.append("select  s FROM TestingSheetSampleStorage s WHERE 1 = 1 ");
            hql.append(" AND s.storageType = :storageType");
            names.add("storageType");
            values.add(storageType);
        }
        else
        {
            hql.append("select distinct s FROM TestingSheetSampleStorage s,TestingSheetTask tt,TestingTask t,TestingSample tm WHERE 1 = 1 "
                + "and s.testingSheet.id=tt.testingSheet.id and tt.testingTaskId=t.id and t.inputSample.id=tm.id");
            addFilter(hql, names, values);
        }

//        //去除荧光检测数据分析
        hql.append(" and s.testingSheet.semantic<>'FLUO-ANALYSE'");
        
        hql.append(" order by s.status asc,s.testingSheet.createTime desc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(testingSheetCode))
        {
            hql.append(" AND s.testingSheet.code LIKE :sheetCode");
            paramNames.add("sheetCode");
            parameters.add("%" + testingSheetCode + "%");
        }
        if (null != status)
        {
            hql.append(" AND s.status = :status");
            paramNames.add("status");
            parameters.add(status);
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(storageType))
        {
            hql.append(" AND s.storageType = :storageType");
            paramNames.add("storageType");
            parameters.add(storageType);
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND tm.sampleCode LIKE :sampleCode");
            paramNames.add("sampleCode");
            parameters.add(sampleCode + "%");
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(sampleName))
        {
            hql.append(" AND tm.receivedSample.sampleName Like :sampleName");
            paramNames.add("sampleName");
            parameters.add(sampleName + "%");
        }
        
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(semantic))
        {
            hql.append(" AND t.semantic = :semantic");
            paramNames.add("semantic");
            parameters.add(semantic);
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(startTime))
        {
            hql.append(" AND tt.testingSheet.assignTime   >=  STR_TO_DATE(:startTime,'%Y-%m-%d %H:%i:%s') ");
            paramNames.add("startTime");
            parameters.add(startTime);
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(endTime))
        {
            hql.append(" AND tt.testingSheet.assignTime <= STR_TO_DATE(:endTime,'%Y-%m-%d %H:%i:%s')");
            paramNames.add("endTime");
            parameters.add(endTime + " 23:59:59");
        }
        
    }
    
    @Override
    public Class<TestingSheetSampleStorage> getEntityClass()
    {
        // TODO Auto-generated method stub
        return TestingSheetSampleStorage.class;
    }
    
}
