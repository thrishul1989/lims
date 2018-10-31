package com.todaysoft.lims.testing.abnormal.service.core;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskSolveRequest;
import com.todaysoft.lims.testing.base.entity.AbnormalSolveRecord;
import com.todaysoft.lims.testing.base.service.adapter.smm.SMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public abstract class AbstractAbnormalSolver implements IAbnormalSolver
{
    @Autowired
    protected BaseDaoSupport baseDaoSupport;
    
    @Autowired
    protected SMMAdapter smmadapter;
    
    @Override
    @Transactional
    public String solve(AbnormalTaskSolveRequest request, String token, List<String> scheduleIds)
    {
        // 1、业务处理
        String orderId = process(request, scheduleIds, token);
        
        // 2、记录异常任务处理事件
        recrod(request, token);
        
        return orderId;
    }
    
    protected abstract String process(AbnormalTaskSolveRequest request, List<String> scheduleIds, String token);
    
    protected void recrod(AbnormalTaskSolveRequest request, String token)
    {
        UserMinimalModel solver = smmadapter.getLoginUser(token);
        AbnormalSolveRecord solveRecord = new AbnormalSolveRecord();
        solveRecord.setRemark(request.getRemark());
        solveRecord.setTaskId(request.getId());
        solveRecord.setStrategy(request.getStrategy());
        solveRecord.setSolverId(solver.getId());
        solveRecord.setSolverName(solver.getName());
        solveRecord.setSolveTime(new Date());
        baseDaoSupport.insert(solveRecord);
    }
}
