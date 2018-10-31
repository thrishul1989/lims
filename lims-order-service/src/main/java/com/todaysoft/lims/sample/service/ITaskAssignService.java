package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.sample.entity.TaskAssign;

import java.util.List;

/**
 * Created by HSHY-032 on 2016/8/19.
 */
public interface ITaskAssignService {

    void save(TaskAssign taskAssign);

    void saveList(List<TaskAssign> list);

    void update(TaskAssign taskAssign);

    List<TaskAssign> list(TaskAssign taskAssign);

    TaskAssign getOneTaskAssign(TaskAssign taskAssign);

    boolean taskAssignComplete(Integer sampleDetailId,Integer productId,String semantic);

}
