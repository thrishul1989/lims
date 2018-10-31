package com.todaysoft.lims.testing.ons.event;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingTask;


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
