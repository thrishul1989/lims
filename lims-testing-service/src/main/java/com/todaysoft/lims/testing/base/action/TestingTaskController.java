package com.todaysoft.lims.testing.base.action;

import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.model.SangerVerifyRecordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.service.ITestingTaskService;

@RestController
@RequestMapping("/bpm/testingTask")
public class TestingTaskController
{
    @Autowired
    private ITestingTaskService testingTaskService;
    
    @RequestMapping(value = "/getTestingTask/{id}", method = RequestMethod.GET)
    public TestingTask getTestingTask(@PathVariable String id)
    {
        return testingTaskService.get(id);
    }
    
    @RequestMapping(value = "/getTestingMethod/{semantic}", method = RequestMethod.GET)
    public TestingMethod getTestingMethodBySemantic(@PathVariable String semantic)
    {
        return testingTaskService.getTestingMethodBySemantic(semantic);
    }

    @RequestMapping(value = "/getSangerRecordByVerifyKey/{verifyKey}", method = RequestMethod.GET)
    public SangerVerifyRecordModel getSangerRecordByVerifyKey(@PathVariable String verifyKey)
    {
        return testingTaskService.getSangerRecordByVerifyKey(verifyKey);
    }
}
