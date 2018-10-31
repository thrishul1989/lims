package com.todaysoft.lims.sample.service.activiti;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import com.todaysoft.lims.sample.config.RootContext;

public class NodeScheduleProcessHandler implements ProcessEventHandler
{
    private RuntimeService runtimeService = RootContext.getBean(RuntimeService.class);
    
    @Override
    public void start(ExecutionEntity entity)
    {
        
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void end(ExecutionEntity entity)
    {
        String id = entity.getCurrentActivityId();
        
        if ("end-success".equals(id))
        {
            // 正常结束，通知testing_schedule
            Map<String, Object> variables = entity.getVariables(Arrays.asList("testing_schedules",
                "task_semantic",
                "output_sample_instance_code",
                "output_sample_type",
                "output_sample_id",
                "sheet_task_id",
                "sample_detail_id",
                "product_id",
                "sheet_code",
                "started",
                "wkbh_code",
                "test_method_name"));
            List<String> schedules = (List<String>)variables.get("testing_schedules");
            String outputSampleInstanceId = (String)variables.get("output_sample_instance_code");
            String outputSampleType = (String)variables.get("output_sample_type");
            Integer outputSampleId = (Integer)variables.get("output_sample_id");
            Integer sheetTaskId = (Integer)variables.get("sheet_task_id");
            Integer sampleDetailId = (Integer)variables.get("sample_detail_id");
            Integer productId = (Integer)variables.get("product_id");
            String semantic = (String)variables.get("task_semantic");
            String sheetCode = (String)variables.get("sheet_code");
            Boolean started = (Boolean)variables.get("started");
            String wkbhCode = (String)variables.get("wkbh_code");
            String testMethod = (String)variables.get("test_method_name");
            Map<String, Object> args = new HashMap<>();
            args.put("newly_sample_instance", outputSampleInstanceId);
            args.put("output_sample_type", outputSampleType);
            args.put("output_sample_id", outputSampleId);
            args.put("sheet_task_id", sheetTaskId);
            args.put("sample_detail_id", sampleDetailId);
            args.put("product_id", productId);
            args.put("sheet_code", sheetCode);
            args.put("started", started);
            args.put("wkbh_code", wkbhCode);
            args.put("testMethod", testMethod);
            for (String schedule : schedules)
            {
                runtimeService.signal(schedule, args);
                args.put("started", true);
            }
        }
        else if ("end-exception".equals(id))
        {
            
        }
    }
}
