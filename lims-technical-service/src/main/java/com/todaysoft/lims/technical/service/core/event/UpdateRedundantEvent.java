package com.todaysoft.lims.technical.service.core.event;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.TestingTask;

public class UpdateRedundantEvent
{
    private List<TestingTask> list;

    public List<TestingTask> getList()
    {
        return list;
    }

    public void setList(List<TestingTask> list)
    {
        this.list = list;
    }
}
