package com.todaysoft.lims.product.service.impl.wrapper;

import com.todaysoft.lims.persist.IEntityWrapper;
import com.todaysoft.lims.product.entity.TaskConfig;
import com.todaysoft.lims.product.model.TaskConfigSimpleModel;

public class TaskConfigSimpleModelWrapper implements IEntityWrapper<TaskConfig, TaskConfigSimpleModel>
{
    @Override
    public TaskConfigSimpleModel wrap(TaskConfig entity)
    {
        if (null == entity)
        {
            return null;
        }
        
        TaskConfigSimpleModel model = new TaskConfigSimpleModel();
        model.setSemantic(entity.getSemantic());
        model.setName(entity.getName());
        model.setOutputSampleId(entity.getOutputSampleId());
        return model;
    }
}
