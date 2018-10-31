package com.todaysoft.lims.testing.abnormal.service.core;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.todaysoft.lims.testing.base.model.TaskSemantic;

@Component
public class AbnormalSolverFactory
{
    @Resource(name = "resamplingSolver")
    private IAbnormalSolver resamplingSolver;
    
    @Resource(name = "cancelSolver")
    private IAbnormalSolver cancelSolver;
    
    @Resource(name = "specifyNodeSolver")
    private IAbnormalSolver specifyNodeSolver;

    @Resource(name = "reExperimentSolver")
    private IAbnormalSolver reExperimentSolver;
    
    public IAbnormalSolver getSolver(String strategy)
    {
        if (TaskSemantic.RESAMPLING.equals(strategy))
        {
            return resamplingSolver;
        }
        else if (TaskSemantic.EXPERIMENT_CANCER.equals(strategy))
        {
            return cancelSolver;
        }
        else if (TaskSemantic.RE_EXPERIMENT.equals(strategy))
        {
            return reExperimentSolver;
        }
        else
        {
            return specifyNodeSolver;
        }
    }
}
