package com.todaysoft.lims.testing.abnormal.service.core;

import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskSolveRequest;
import com.todaysoft.lims.testing.abnormal.service.impl.AbnormalSolveContext;
import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class ReExperimentSolver extends AbstractAbnormalSolver
{
    @Autowired
    private ITestingScheduleService testingScheduleService;

    @Autowired
    private ITestingTaskService testingTaskService;
    
    @Override
    protected String process(AbnormalTaskSolveRequest request, List<String> scheduleIds, String token)
    {
        //重新实验逻辑跟风险实验一样
        String remark = request.getRemark();

        TestingTask abnormalTask = baseDaoSupport.get(TestingTask.class, request.getId());

        if (null == abnormalTask)
        {
            return "";
        }

        String abnormalTaskSemantic = abnormalTask.getSemantic();

        List<TestingSchedule> schedules = testingScheduleService.getRelatedSchedulesByTestingTask(abnormalTask.getId());

        if (CollectionUtils.isEmpty(schedules))
        {
            return "";
        }

        TestingTask lastTask;
        TestingTask restartTask = null;
        AbnormalSolveContext context = new AbnormalSolveContext();
        for (TestingSchedule schedule : schedules) {
            if (!scheduleIds.contains(schedule.getId())) {
                scheduleIds.add(schedule.getId());
            }else {
                continue;
            }
            String lastnNodes = getRiskTestingNode(schedule.getScheduleNodes(), abnormalTaskSemantic);
            lastTask = testingScheduleService.getScheduleNodeLastTestingTask(schedule.getId(), lastnNodes);

            if (null == lastTask)
            {
                //1.有可能是引物设计不存在历史表 因为有时不需要做引物设计直接到一次PCR了
                throw new IllegalStateException();
            }

            restartTask = context.getSolvedTask(lastTask);

            if (null == restartTask)
            {
                restartTask = new TestingTask();
                restartTask.setName(lastTask.getName());
                restartTask.setSemantic(lastTask.getSemantic());
                restartTask.setStatus(TestingTask.STATUS_ASSIGNABLE);
                restartTask.setResubmit(true);
                restartTask.setResubmitCount(null == lastTask.getResubmitCount() ? 1 : (lastTask.getResubmitCount() + 1));
                restartTask.setStartTime(new Date());
                restartTask.setInputSample(lastTask.getInputSample());
                restartTask.setTestingInputArgs(lastTask.getTestingInputArgs());
                baseDaoSupport.insert(restartTask);

                TestingTaskRunVariable variables = new TestingTaskRunVariable();
                variables.setTestingTaskId(restartTask.getId());
                Map<String, String> runText = new HashMap<>();
                runText.put("remark", remark);
                if(TaskSemantic.SANGER_PCR_ONE.equals(lastTask.getSemantic()))
                {
                    variables.setText(lastTask.getTestingInputArgs());
                }else{
                    variables.setText(JsonUtils.asJson(runText));
                }
                baseDaoSupport.insert(variables);
                context.solve(lastTask, restartTask);
            }

            TestingScheduleActive active = new TestingScheduleActive();
            active.setSchedule(schedule);
            active.setTaskId(restartTask.getId());
            baseDaoSupport.insert(active);

            TestingScheduleHistory history = new TestingScheduleHistory();
            history.setScheduleId(schedule.getId());
            history.setTaskId(restartTask.getId());
            history.setTimestamp(new Date());
            baseDaoSupport.insert(history);

            testingScheduleService.setScheduleActiveName(schedule);
            schedule.setEndTime(null);
            schedule.setEndType(null);
            baseDaoSupport.update(schedule);
            scheduleIds.add(schedule.getId());
        }

        //更新冗余字段
        testingTaskService.updateTaskRedundantColumn(Arrays.asList(restartTask), 0);

        return "";
    }

    private String getRiskTestingNode(String nodes, String abnormalNode)
    {
        String[] array = nodes.split("\\/");

        Set<String> valids = new HashSet<String>();
        valids.add(TaskSemantic.DNA_EXTRACT);
        valids.add(TaskSemantic.CDNA_EXTRACT);
        valids.add(TaskSemantic.LIBRARY_CRE);
        valids.add(TaskSemantic.LIBRARY_CAP);
        valids.add(TaskSemantic.QPCR);
        valids.add(TaskSemantic.MLPA);
        valids.add(TaskSemantic.PCR_ONE);
        valids.add(TaskSemantic.PCR_NGS);
        valids.add(TaskSemantic.MULTI_PCR);
        valids.add(TaskSemantic.FLUO_TEST);
        valids.add(TaskSemantic.SANGER_PCR_ONE);
        valids.add(TaskSemantic.DT);
        valids.add(TaskSemantic.LONG_PCR);

        String lastValidNode = null;

        if ((TaskSemantic.DNA_QC.equals(abnormalNode) || TaskSemantic.CDNA_QC.equals(abnormalNode)) && array[0].equals(abnormalNode))
        {
            lastValidNode = abnormalNode;
            return lastValidNode;
        }
        else
        {
            for (String node : array)
            {
                if (node.equals(abnormalNode))
                {
                    if (valids.contains(node))
                    {
                        lastValidNode = node;
                    }

                    break;
                }
                else
                {
                    if (valids.contains(node))
                    {
                        lastValidNode = node;
                    }
                }
            }
        }

        return lastValidNode;
    }


}
