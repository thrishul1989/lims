package com.todaysoft.lims.system.modules.bcm.service;

import com.todaysoft.lims.system.model.vo.TaskConfig;
import com.todaysoft.lims.system.modules.bcm.model.TaskConfigSimpleModel;

import java.util.List;

public interface ITaskConfigService
{
    List<TaskConfigSimpleModel> getPretestingTaskConfigsByInputSample(String inputSampleId);

    List<TaskConfig> getTaskList();//获取DNA提取、文库构建、文库捕获任务列表

    TaskConfig getTaskBySemantic(String semantic);

}
