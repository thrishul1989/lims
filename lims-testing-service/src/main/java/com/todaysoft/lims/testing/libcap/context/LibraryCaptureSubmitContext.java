package com.todaysoft.lims.testing.libcap.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.entity.TestingCaptureGroup;
import com.todaysoft.lims.testing.base.entity.TestingCaptureSample;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSubmitGroupArgs;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSubmitRequest;
import com.todaysoft.lims.testing.libcap.model.LibraryCaptureSubmitTaskArgs;

public class LibraryCaptureSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private Map<String, LibraryCaptureSubmitTaskArgs> taskArgs = new HashMap<String, LibraryCaptureSubmitTaskArgs>();
    
    private Map<String, LibraryCaptureSubmitGroupArgs> groupArgs = new HashMap<String, LibraryCaptureSubmitGroupArgs>();
    
    private Map<String, LibraryCaptureSubmitGroupContext> groups = new HashMap<String, LibraryCaptureSubmitGroupContext>();
    
    private Map<String, List<LibraryCaptureSubmitGroupContext>> libraryGroupMapping = new HashMap<String, List<LibraryCaptureSubmitGroupContext>>();
    
    private Map<String, LibraryCaptureSubmitSolveTaskContext> solveTasks = new HashMap<String, LibraryCaptureSubmitSolveTaskContext>();
    
    private Map<String, LibraryCaptureSubmitTestingTaskContext> testingTasks = new HashMap<String, LibraryCaptureSubmitTestingTaskContext>();
    
    private Map<String, LibraryCaptureSubmitNextTaskContext> nextTasks = new HashMap<String, LibraryCaptureSubmitNextTaskContext>();
    
    private Map<String, LibraryCaptureSubmitNextTaskContext> nextTasksByIds = new HashMap<String, LibraryCaptureSubmitNextTaskContext>();
    
    private Map<String, LibraryCaptureSubmitScheduleContext> gotoNextSchedules = new HashMap<String, LibraryCaptureSubmitScheduleContext>();
    
    private Map<String, LibraryCaptureSubmitScheduleContext> gotoSolveSchedules = new HashMap<String, LibraryCaptureSubmitScheduleContext>();
    
    private Map<String, LibraryCaptureSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, LibraryCaptureSubmitScheduleContext>();
    
    public Map<String, LibraryCaptureSubmitNextTaskContext> getNextTasksByIds()
    {
        return nextTasksByIds;
    }

    public void setNextTasksByIds(Map<String, LibraryCaptureSubmitNextTaskContext> nextTasksByIds)
    {
        this.nextTasksByIds = nextTasksByIds;
    }

    public Set<LibraryCaptureSubmitTestingTaskContext> getRelatedTasks()
    {
        return new HashSet<LibraryCaptureSubmitTestingTaskContext>(testingTasks.values());
    }
    
    public Set<LibraryCaptureSubmitGroupContext> getRelatedCaptureGroups()
    {
        return new HashSet<LibraryCaptureSubmitGroupContext>(groups.values());
    }
    
    public Set<LibraryCaptureSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<LibraryCaptureSubmitNextTaskContext>(nextTasks.values());
    }
    
    public Set<LibraryCaptureSubmitSolveTaskContext> getSolveUnqualifiedTasks()
    {
        return new HashSet<LibraryCaptureSubmitSolveTaskContext>(solveTasks.values());
    }
    
    public Set<LibraryCaptureSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<LibraryCaptureSubmitScheduleContext>(gotoNextSchedules.values());
    }
    
    public Set<LibraryCaptureSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<LibraryCaptureSubmitScheduleContext>(gotoErrorSchedules.values());
    }
    
    public String getNextTaskCreatedId(String scheduleId, String inputSampleId)
    {
        LibraryCaptureSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
        return nextTaskContext.getTestingTaskEntity().getId();
    }
    
    public TestingSheet getSheetEntity()
    {
        return this.sheetEntity;
    }
    
    public UserMinimalModel getSubmiter()
    {
        return this.submiter;
    }
    
    public void setContextForSubmiter(UserMinimalModel submiter)
    {
        this.submiter = submiter;
    }
    
    public void setContextForTestingSheet(TestingSheet sheet)
    {
        this.sheetEntity = sheet;
    }
    
    public void setContextForSubmitRequest(LibraryCaptureSubmitRequest request)
    {
        if (!CollectionUtils.isEmpty(request.getTasks()))
        {
            for (LibraryCaptureSubmitTaskArgs task : request.getTasks())
            {
                taskArgs.put(task.getId(), task);
            }
        }
        
        if (!CollectionUtils.isEmpty(request.getGroups()))
        {
            for (LibraryCaptureSubmitGroupArgs group : request.getGroups())
            {
                groupArgs.put(group.getId(), group);
            }
        }
    }
    
    public void setContextForLibraryCaptureGroup(TestingCaptureGroup group)
    {
        LibraryCaptureSubmitGroupArgs result = groupArgs.get(group.getId());
        
        if (null == result)
        {
            throw new IllegalStateException();
        }
        
        LibraryCaptureSubmitGroupContext context = new LibraryCaptureSubmitGroupContext();
        context.setGroup(group);
        context.setConcn(result.getConcn());
        context.setQualified(result.isQualified());
        context.setUnqualifiedRemark(result.getUnqualifiedRemark());
        groups.put(group.getId(), context);
        
        // 设置捕获组和文库样本映射
        List<TestingCaptureSample> libraries = group.getSamples();
        
        String libraryKey;
        List<LibraryCaptureSubmitGroupContext> libraryGroups;
        
        if (!CollectionUtils.isEmpty(libraries))
        {
            for (TestingCaptureSample library : libraries)
            {
                libraryKey = library.getSample().getId();
                
                libraryGroups = libraryGroupMapping.get(libraryKey);
                
                if (null == libraryGroups)
                {
                    libraryGroups = new ArrayList<LibraryCaptureSubmitGroupContext>();
                    libraryGroupMapping.put(libraryKey, libraryGroups);
                }
                
                libraryGroups.add(context);
            }
        }
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        LibraryCaptureSubmitTaskArgs result = taskArgs.get(task.getId());
        
        if (null == result)
        {
            throw new IllegalStateException();
        }
        
        LibraryCaptureSubmitTestingTaskContext testingTaskContext = new LibraryCaptureSubmitTestingTaskContext();
        testingTaskContext.setQualified(result.isQualified());
        testingTaskContext.setUnqualifiedRemark(result.getUnqualifiedRemark());
        testingTaskContext.setUnqualifiedStrategy(result.getUnqualifiedStrategy());
        testingTaskContext.setTestingTaskEntity(task);
        testingTasks.put(task.getId(), testingTaskContext);
    }
    
    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TaskConfig nextNodeConfig)
    {
        LibraryCaptureSubmitTestingTaskContext taskContext = testingTasks.get(task.getId());
        
        LibraryCaptureSubmitScheduleContext scheduleContext = new LibraryCaptureSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        
        if (taskContext.isQualified())
        {
            scheduleContext.setNextTasks(new ArrayList<LibraryCaptureSubmitNextTaskContext>());
            gotoNextSchedules.put(schedule.getId(), scheduleContext);
            
            String library = task.getInputSample().getId();
            List<LibraryCaptureSubmitGroupContext> groups = libraryGroupMapping.get(library);
            
            if (!CollectionUtils.isEmpty(groups))
            {
                String groupId;
                String nextTaskSemantic;
                
                LibraryCaptureSubmitNextTaskContext nextTaskContext;
                
                for (LibraryCaptureSubmitGroupContext groupContext : groups)
                {
                    groupId = groupContext.getGroup().getId();
                    nextTaskSemantic = nextNodeConfig.getSemantic();
                    nextTaskContext = nextTasks.get(groupId + "_" + nextTaskSemantic);
                    
                    if (null == nextTaskContext)
                    {
                        nextTaskContext = new LibraryCaptureSubmitNextTaskContext();
                        nextTaskContext.setGroupContext(groupContext);
                        nextTaskContext.setTaskSemantic(nextTaskSemantic);
                        nextTaskContext.setTaskName(nextNodeConfig.getName());
                        nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
                    }
                    
                    scheduleContext.getNextTasks().add(nextTaskContext);
                }
            }
        }
        else
        {
            if ("1".equals(taskContext.getUnqualifiedStrategy()))
            {
                gotoErrorSchedules.put(schedule.getId(), scheduleContext);
            }
            else
            {
                LibraryCaptureSubmitSolveTaskContext solveTaskContext = solveTasks.get(task.getId());
                
                if (null == solveTaskContext)
                {
                    solveTaskContext = new LibraryCaptureSubmitSolveTaskContext();
                    solveTaskContext.setUnqualifiedTask(task);
                    solveTaskContext.setStrategy(taskContext.getUnqualifiedStrategy());
                    solveTaskContext.setRelatedSchedules(new HashMap<String, LibraryCaptureSubmitScheduleContext>());
                    solveTasks.put(task.getId(), solveTaskContext);
                }
                
                if (!solveTaskContext.getRelatedSchedules().containsKey(schedule.getId()))
                {
                    solveTaskContext.getRelatedSchedules().put(schedule.getId(), scheduleContext);
                }
                
                gotoSolveSchedules.put(schedule.getId(), scheduleContext);
            }
        }
    }
    
    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        LibraryCaptureSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }
    
    public void setContextForCreateCaptureGroupOutputSample(String groupId, TestingSample outputSample)
    {
        LibraryCaptureSubmitGroupContext groupContext = groups.get(groupId);
        
        if (null == groupContext)
        {
            throw new IllegalStateException();
        }
        
        groupContext.setOutputSample(outputSample);
    }
}
