package com.todaysoft.lims.technical.model;

import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;

public class TechnicalAnalySubmitTaskContext
{
    private TechnicalAnalysisTask testingTaskEntity;
    
    public TechnicalAnalysisTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TechnicalAnalysisTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
}
