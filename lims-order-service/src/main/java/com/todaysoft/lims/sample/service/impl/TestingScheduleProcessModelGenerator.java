package com.todaysoft.lims.sample.service.impl;

import java.util.Collections;
import java.util.List;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.ReceiveTask;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;

import com.todaysoft.lims.sample.model.TestingTask;

public class TestingScheduleProcessModelGenerator
{
    private String name;
    
    private List<TestingTask> tasks;
    
    public TestingScheduleProcessModelGenerator(String name, List<TestingTask> tasks)
    {
        this.name = name;
        this.tasks = tasks;
    }
    
    public BpmnModel generate()
    {
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);
        process.setId(name);
        
        process.addFlowElement(createStartEvent());
        
        String id;
        String last = "start";
        
        for (TestingTask task : tasks)
        {
            id = "task-" + task.getId();
            process.addFlowElement(createReceiveTask(id, task.getName()));
            process.addFlowElement(createSequenceFlow(last, id));
            last = id;
        }
        
        process.addFlowElement(createEndEvent());
        process.addFlowElement(createSequenceFlow(last, "end"));
        
        return model;
    }
    
    protected ReceiveTask createReceiveTask(String id, String name)
    {
        ReceiveTask task = new ReceiveTask();
        task.setId(id);
        task.setName(name);
        ActivitiListener listener = new ActivitiListener();
        listener.setEvent("start");
        listener.setImplementationType("class");
        listener.setImplementation("com.todaysoft.lims.activiti.listener.TestingScheduleListener");
        task.setExecutionListeners(Collections.singletonList(listener));
        return task;
    }
    
    protected SequenceFlow createSequenceFlow(String from, String to)
    {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }
    
    protected StartEvent createStartEvent()
    {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }
    
    protected EndEvent createEndEvent()
    {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }
}
