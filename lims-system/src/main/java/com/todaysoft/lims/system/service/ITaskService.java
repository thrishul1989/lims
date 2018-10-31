package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.searcher.TaskSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.model.vo.TaskInput;
import com.todaysoft.lims.system.model.vo.TestingScheduleActive;
import com.todaysoft.lims.system.model.vo.order.DealScheduleModel;
import com.todaysoft.lims.system.model.vo.order.OrderProductTestingScheduleRequest;
import com.todaysoft.lims.system.model.vo.order.TemproaryTestingTask;
import com.todaysoft.lims.system.service.adapter.request.TaskConfigRequest;

public interface ITaskService
{
    Pagination<Task> paging(TaskSearcher searcher, int pageNo, int pageSize);
    
    List<Task> list(TaskSearcher searcher);
    
    Task get(String id);
    
    Task getBySemantic(String semantic);
    
    List<TemproaryTestingTask> getTaskNameBySemantic(String nodesStr);
    
    void modify(TaskConfigRequest request);
    
    List<TaskInput> getTaskInputs(String taskId);
    
    Task getTaskBySemantic(String semantic);
    
    List<TestingScheduleActive> getActiveTasks(String sheduleId);
    
    void modifyShedule(DealScheduleModel scheduleModel);
    
    void cancelSheduleByOrderProduct(OrderProductTestingScheduleRequest request);
    
}
