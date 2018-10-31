package com.todaysoft.lims.testing.qt.context;

import java.util.Set;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class QtSubmitTestingTaskContext
{
    private TestingTask testingTaskEntity;
    
    private Set<String> captureTasks;
    
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String unqualifiedStrategy;
    
    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
    
    public Set<String> getCaptureTasks()
    {
        return captureTasks;
    }
    
    public void setCaptureTasks(Set<String> captureTasks)
    {
        this.captureTasks = captureTasks;
    }
    
    public boolean isQualified()
    {
        return qualified;
    }
    
    public void setQualified(boolean qualified)
    {
        this.qualified = qualified;
    }
    
    public String getUnqualifiedRemark()
    {
        return unqualifiedRemark;
    }
    
    public void setUnqualifiedRemark(String unqualifiedRemark)
    {
        this.unqualifiedRemark = unqualifiedRemark;
    }
    
    public String getUnqualifiedStrategy()
    {
        return unqualifiedStrategy;
    }
    
    public void setUnqualifiedStrategy(String unqualifiedStrategy)
    {
        this.unqualifiedStrategy = unqualifiedStrategy;
    }
}
