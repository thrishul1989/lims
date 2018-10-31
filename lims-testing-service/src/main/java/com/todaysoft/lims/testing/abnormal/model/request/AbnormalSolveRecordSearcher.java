package com.todaysoft.lims.testing.abnormal.model.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.abnormal.model.AbnormalSolveSheduleRecord;
import com.todaysoft.lims.testing.base.entity.AbnormalSolveRecord;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.utils.StringUtils;

public class AbnormalSolveRecordSearcher implements ISearcher<AbnormalSolveRecord>
{
    public List<AbnormalSolveSheduleRecord> getSheduleList()
    {
        return sheduleList;
    }
    
    public void setSheduleList(List<AbnormalSolveSheduleRecord> sheduleList)
    {
        this.sheduleList = sheduleList;
    }
    
    private String id;
    
    private String taskId;
    
    private String strategy;
    
    private String sheetCode;
    
    private String strategyName;
    
    public String getSheetCode()
    {
        return sheetCode;
    }
    
    public void setSheetCode(String sheetCode)
    {
        this.sheetCode = sheetCode;
    }
    
    public String getStrategyName()
    {
        return strategyName;
    }
    
    public void setStrategyName(String strategyName)
    {
        this.strategyName = strategyName;
    }
    
    private String remark;
    
    private String solverId;
    
    private String solverName;
    
    private Date solveTime;
    
    public Date getSolveTime()
    {
        return solveTime;
    }
    
    public void setSolveTime(Date solveTime)
    {
        this.solveTime = solveTime;
    }
    
    private TestingTask testingTask;
    
    private String sampleCode;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private List<AbnormalSolveSheduleRecord> sheduleList;
    
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
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getSolverId()
    {
        return solverId;
    }
    
    public void setSolverId(String solverId)
    {
        this.solverId = solverId;
    }
    
    public String getSolverName()
    {
        return solverName;
    }
    
    public void setSolverName(String solverName)
    {
        this.solverName = solverName;
    }
    
    public TestingTask getTestingTask()
    {
        return testingTask;
    }
    
    public void setTestingTask(TestingTask testingTask)
    {
        this.testingTask = testingTask;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select s FROM AbnormalRecord s where 1 =1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" order by s.solveTime desc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != sampleCode && StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND s.sampleCode LIKE :sampleCode");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        if (StringUtils.isNotEmpty(strategy))
        {
            hql.append(" AND s.strategy = :strategy");
            paramNames.add("strategy");
            parameters.add(strategy);
        }
        if (StringUtils.isNotEmpty(solverName))
        {
            hql.append(" AND s.solverName LIKE :solverName");
            paramNames.add("solverName");
            parameters.add("%" + solverName + "%");
        }
        
    }
    
    @Override
    public Class<AbnormalSolveRecord> getEntityClass()
    {
        // TODO Auto-generated method stub
        return AbnormalSolveRecord.class;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
}
