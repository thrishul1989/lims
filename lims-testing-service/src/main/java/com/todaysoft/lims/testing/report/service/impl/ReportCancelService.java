package com.todaysoft.lims.testing.report.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.entity.TestingReport;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.report.service.IReportCancelService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class ReportCancelService implements IReportCancelService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    @Transactional
    public void cancel(String scheduleId, String reportId, String remark)
    {
        //取该流程最后一个节点数据分析为异常上报
        TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, scheduleId);
        schedule.setEndType(null);
        schedule.setActiveTask("异常-上报");
        baseDaoSupport.update(schedule);
        String[] nodes = schedule.getScheduleNodes().split("\\/");
        String semantic = nodes[nodes.length - 1];
        List<TestingScheduleHistory> historys =
            baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, "select his from TestingScheduleHistory his,TestingTask t where his.taskId=t.id and "
                + "his.scheduleId=:scheduleId and t.semantic=:semantic order by his.timestamp desc", new String[] {"scheduleId", "semantic"}, new Object[] {
                scheduleId, semantic});
        if (Collections3.isEmpty(historys))
        {
            throw new IllegalStateException();
        }
        TestingTask lastTask = baseDaoSupport.get(TestingTask.class, Collections3.getFirst(historys).getTaskId());
        lastTask.setEndType(lastTask.END_FAILURE);
        lastTask.setEndTime(new Date());
        baseDaoSupport.update(lastTask);
        List<TestingTaskResult> results = baseDaoSupport.find(TestingTaskResult.class, "from TestingTaskResult t where t.taskId='" + lastTask.getId() + "'");
        if (Collections3.isNotEmpty(results))
        {
            TestingTaskResult result = Collections3.getFirst(results);
            result.setResult(result.RESULT_FAILURE_REPORT);
            result.setRemark(remark);
            baseDaoSupport.update(result);
            
            TestingReport report = baseDaoSupport.get(TestingReport.class, reportId);
            report.setIfRedo(true);
            report.setStatus(0);
            report.setRedoCount(null == report.getRedoCount() ? 1 : report.getRedoCount() + 1);
            baseDaoSupport.update(report);
        }
        
        
    }
}
