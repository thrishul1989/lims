package com.todaysoft.lims.testing.abnormal.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskSemantic;

public class AbnormalSolveContext
{
    private Map<String, TestingTask> lastTaskSampleSolveTasks = new HashMap<String, TestingTask>();
    
    public void solve(TestingTask lastTask, TestingTask solveTask)
    {
        String lastTaskSample = lastTask.getInputSample().getId();
        lastTaskSampleSolveTasks.put(lastTaskSample, solveTask);
    }
    
    public TestingTask getSolvedTask(TestingTask lastTask)
    {
        List<String> ignoreNodes = Arrays.asList(TaskSemantic.DNA_EXTRACT, TaskSemantic.CDNA_EXTRACT, TaskSemantic.DNA_QC,TaskSemantic.PCR_NGS_PRIMER_DESIGN,TaskSemantic.PRIMER_DESIGN);//DNACDNA 引物设计提取只需生成一条任务
        if (ignoreNodes.contains(lastTask.getSemantic()))
        {
            String lastTaskSample = lastTask.getInputSample().getId();
            return lastTaskSampleSolveTasks.get(lastTaskSample);
        }
        else
        {
            return null;
        }
        
    }
}
