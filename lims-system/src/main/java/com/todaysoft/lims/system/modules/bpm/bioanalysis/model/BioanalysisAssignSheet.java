package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

import java.math.BigDecimal;
import java.util.List;

public class BioanalysisAssignSheet
{
    private String testerId;
    
    private BigDecimal fourHtOne;
    
    private BigDecimal oneEightHtOne;
    
    private List<BioanalysisAssignSheetTask> tasks;
    
    public String getTesterId()
    {
        return testerId;
    }
    
    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }
    
    public BigDecimal getFourHtOne()
    {
        return fourHtOne;
    }
    
    public void setFourHtOne(BigDecimal fourHtOne)
    {
        this.fourHtOne = fourHtOne;
    }
    
    public BigDecimal getOneEightHtOne()
    {
        return oneEightHtOne;
    }
    
    public void setOneEightHtOne(BigDecimal oneEightHtOne)
    {
        this.oneEightHtOne = oneEightHtOne;
    }
    
    public List<BioanalysisAssignSheetTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<BioanalysisAssignSheetTask> tasks)
    {
        this.tasks = tasks;
    }
}
