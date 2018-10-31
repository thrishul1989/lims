package com.todaysoft.lims.testing.base.dao.searcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingSheet;

public class TestingSheetSearcher extends IDataAuthoritySearcher implements ISearcher<TestingSheet>
{
    private String taskName;
    
    private String code;
    
    private String assignerName;
    
    private String testerName;
    
    private int pageNo;
    
    private int pageSize;
    
    private String assignTimeStart;
    
    private String assignTimeEnd;
    
    private String sampleCode;
    
    private Boolean completed;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingSheet s WHERE s.assignTime is not null");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" ORDER BY s.submitTime DESC,s.assignTime DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(sampleCode))
        {
            hql.append(" AND ( s.id IN (SELECT tSheetTask.testingSheet.id FROM TestingSheetTask tSheetTask WHERE (tSheetTask.testingTaskId IN (SELECT tsh.taskId FROM TestingScheduleHistory tsh WHERE tsh.scheduleId IN (SELECT ts.id FROM TestingSchedule ts WHERE (ts.sampleId IN (SELECT rs.sampleId FROM ReceivedSample rs WHERE rs.sampleCode = :sampleCode))"
                    + " OR (ts.sampleId IN (SELECT tsample.id FROM TestingSample tsample WHERE tsample.sampleCode = :sampleCode)))))"
                    + " OR (tSheetTask.testingTaskId IN (SELECT tsa.taskId FROM TestingScheduleActive tsa WHERE tsa.schedule.id IN (SELECT ts.id FROM TestingSchedule ts WHERE (ts.sampleId IN (SELECT rs.sampleId FROM ReceivedSample rs WHERE rs.sampleCode = :sampleCode)) "
                    + "OR (ts.sampleId IN (SELECT tsample.id FROM TestingSample tsample WHERE tsample.sampleCode = :sampleCode))))))  or "
                    + "s.id IN ( SELECT s.id FROM NgsSequecingSheet s WHERE ( s.ngsTaskId IN ( SELECT tsh.taskId FROM TestingScheduleHistory tsh WHERE tsh.scheduleId IN ( SELECT ts.id FROM TestingSchedule ts WHERE ( ts.sampleId IN ( SELECT rs.id FROM TestingSample rs WHERE rs.sampleCode = :sampleCode))))))"
                    + " or s.id IN ( SELECT s.id FROM BiologyDivisionSheet s WHERE ( s.taskId IN ( SELECT tsh.taskId FROM TestingScheduleHistory tsh WHERE tsh.scheduleId IN ( SELECT ts.id FROM TestingSchedule ts WHERE ( ts.sampleId IN ( SELECT rs.id FROM TestingSample rs WHERE rs.sampleCode = :sampleCode))))))"
                    + " or s.id IN( SELECT dd.id FROM TechnicalSheet dd WHERE( dd.taskId in ( SELECT tt.familyGroupId FROM TechnicalAnalysisTask tt WHERE ( tt.id IN ( SELECT tsh.taskId FROM TestingScheduleHistory tsh WHERE tsh.scheduleId IN ( SELECT ts.id FROM TestingSchedule ts WHERE ( ts.sampleId IN ( SELECT rs.id FROM TestingSample rs WHERE rs.sampleCode = :sampleCode))))))))"
            +")");

            paramNames.add("sampleCode");
            parameters.add(sampleCode);
        }
        if (!StringUtils.isEmpty(taskName))
        {
            hql.append(" AND s.taskName = :taskName");
            paramNames.add("taskName");
            parameters.add(taskName);
        }
        if (!StringUtils.isEmpty(code))
        {
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        if (!StringUtils.isEmpty(assignerName))
        {
            hql.append(" AND s.assignerName LIKE :assignerName");
            paramNames.add("assignerName");
            parameters.add("%" + assignerName + "%");
        }
        if (!StringUtils.isEmpty(testerName))
        {
            hql.append(" AND s.testerName LIKE :testerName");
            paramNames.add("testerName");
            parameters.add("%" + testerName + "%");
        }
        if (!StringUtils.isEmpty(completed))
        {
            if (completed)
            {
                hql.append(" AND s.submitTime is not null");
            }
            else
            {
                hql.append(" AND s.submitTime is null");
            }
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try
        {
            if (!StringUtils.isEmpty(assignTimeStart))
            {
                hql.append(" AND s.assignTime > :assignTimeStart");
                paramNames.add("assignTimeStart");
                parameters.add(sdf.parse(assignTimeStart));
            }
            if (!StringUtils.isEmpty(assignTimeEnd))
            {
                hql.append(" AND s.assignTime < :assignTimeEnd");
                paramNames.add("assignTimeEnd");
                parameters.add(sdf.parse(assignTimeEnd));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public Class<TestingSheet> getEntityClass()
    {
        return TestingSheet.class;
    }
    
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getAssignerName()
    {
        return assignerName;
    }
    
    public void setAssignerName(String assignerName)
    {
        this.assignerName = assignerName;
    }
    
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getAssignTimeStart()
    {
        return assignTimeStart;
    }
    
    public void setAssignTimeStart(String assignTimeStart)
    {
        this.assignTimeStart = assignTimeStart;
    }
    
    public String getAssignTimeEnd()
    {
        return assignTimeEnd;
    }
    
    public void setAssignTimeEnd(String assignTimeEnd)
    {
        this.assignTimeEnd = assignTimeEnd;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Boolean getCompleted()
    {
        return completed;
    }
    
    public void setCompleted(Boolean completed)
    {
        this.completed = completed;
    }
    
    @Override
    public NamedQueryer toAuthQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingSheetView s WHERE s.assignTime is not null");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addAuthFilter(hql, paramNames, parameters, "testerId");
        addFilter(hql, paramNames, parameters);
        hql.append(" ORDER BY s.submitTime DESC,s.assignTime DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
}
