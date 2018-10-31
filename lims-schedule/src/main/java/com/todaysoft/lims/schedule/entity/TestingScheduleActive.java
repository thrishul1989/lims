package com.todaysoft.lims.schedule.entity;

import javax.persistence.*;

import com.todaysoft.lims.persist.UuidKeyEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "LIMS_TESTING_SCHEDULE_ACTIVE")
public class TestingScheduleActive extends UuidKeyEntity
{
    private static final long serialVersionUID = 5436722698980660012L;
    
    public static final int STATUS_NORMAL = 0;// 正常
    
    public static final int STATUS_CANCER = 3;// 已取消
    
    private TestingSchedule schedule;
    
    private String taskId;
    
    private Integer runningStatus;// 任务运行状态0-正常 3-实验取消 1-暂停
    
    private String taskRefer;// 任务id从属 表
    
    @Column(name = "TASK_REFER")
    public String getTaskRefer()
    {
        return taskRefer;
    }
    
    public void setTaskRefer(String taskRefer)
    {
        this.taskRefer = taskRefer;
    }
    
    @ManyToOne
    @JoinColumn(name = "SCHEDULE_ID")
    public TestingSchedule getSchedule()
    {
        return schedule;
    }
    
    public void setSchedule(TestingSchedule schedule)
    {
        this.schedule = schedule;
    }
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @Column(name = "RUNNING_STATUS", columnDefinition = "tinyint(1)default 0")
    public Integer getRunningStatus()
    {
        return runningStatus;
    }
    
    public void setRunningStatus(Integer runningStatus)
    {
        this.runningStatus = runningStatus;
    }
    
}
