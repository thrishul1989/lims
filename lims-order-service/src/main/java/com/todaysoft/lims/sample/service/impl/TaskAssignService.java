package com.todaysoft.lims.sample.service.impl;

import com.todaysoft.lims.sample.dao.TaskAssignDao;
import com.todaysoft.lims.sample.entity.TaskAssign;
import com.todaysoft.lims.sample.service.ITaskAssignService;
import com.todaysoft.lims.utils.Collections3;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by HSHY-032 on 2016/8/19.
 */
@Service
@Transactional(readOnly = true)
public class TaskAssignService implements ITaskAssignService {

    @Autowired
    private TaskAssignDao taskAssignDao;

    @Override
    @Transactional
    public void save(TaskAssign taskAssign) {
        taskAssignDao.save(taskAssign);
    }

    @Override
    public void saveList(List<TaskAssign> list) {
        taskAssignDao.save(list);
    }

    @Override
    @Transactional
    public void update(TaskAssign taskAssign) {
        taskAssignDao.merge(taskAssign);
    }

    @Override
    public List<TaskAssign> list(TaskAssign taskAssign) {
        DetachedCriteria detachedCriteria = taskAssignDao.createDetachedCriterias().wrapCriterion(taskAssign);
        List<TaskAssign> list = taskAssignDao.find(detachedCriteria);
        return list;
    }

    @Override
    public TaskAssign getOneTaskAssign(TaskAssign taskAssign) {
        DetachedCriteria detachedCriteria = taskAssignDao.createDetachedCriterias().wrapCriterion(taskAssign);
        List<TaskAssign> list = taskAssignDao.find(detachedCriteria);
        return Collections3.getOne(list);
    }

    @Override
    public boolean taskAssignComplete(Integer sampleDetailId, Integer productId, String semantic) {
        List<TaskAssign> list = taskAssignDao.find(taskAssignDao.createDetachedCriterias()
                                                                .wrapCriterion(TaskAssign.builder()
                                                                 .sampleDetailId(sampleDetailId)
                                                                 .productId(productId).semantic(semantic).state("1")
                                                                 .build()));
        return Collections3.isEmpty(list);
    }
}
