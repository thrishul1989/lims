package com.todaysoft.lims.gateway.service.impl;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.TestingSchedule;
import com.todaysoft.lims.gateway.model.TestingTaskConfig;
import com.todaysoft.lims.gateway.model.TodoModel;
import com.todaysoft.lims.gateway.model.request.TestingNodeTodoRequest;
import com.todaysoft.lims.gateway.model.request.TestingScheduleStartRequest;
import com.todaysoft.lims.gateway.model.request.TestingSheetCreateRequest;
import com.todaysoft.lims.gateway.model.request.TestingSheetSubmitRequest;
import com.todaysoft.lims.gateway.service.ITestingScheduleService;
import com.todaysoft.lims.gateway.service.adapter.TestingSheduleAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestingSheduleService implements ITestingScheduleService
{
    @Autowired
    private TestingSheduleAdapter adapter;
    
    @Override
	public TestingSchedule get(Integer id) {
		return adapter.get(id);
	}

    @Override
    public void start(TestingScheduleStartRequest request) {
        adapter.start(request);
    }

    @Override
    public TestingTaskConfig getTestingTaskConfig(String id) {
        return null;
    }

    @Override
    public List<TodoModel> todo(TestingNodeTodoRequest request) {
        return adapter.todo(request);
    }

    @Override
    public void assign(TestingSheetCreateRequest request) {
        adapter.assign(request);
    }

    @Override
    public void submit(TestingSheetSubmitRequest request) {
        adapter.submit(request);
    }

    @Override
    public Pagination<TodoModel> processJoin(TestingNodeTodoRequest request) {
        return adapter.processJoin(request);
    }

    @Override
    public List<TodoModel> executeTodo(TestingNodeTodoRequest request) {
        return adapter.executeTodo(request);
    }

    @Override
    public void assignDnaQt(TestingSheetCreateRequest request) {
        adapter.assignDnaQt(request);
    }

    @Override
    public void submitDnaQt(TestingSheetSubmitRequest request) {
        adapter.submitDnaQt(request);
    }

    @Override
    public void assignWkCreate(TestingSheetCreateRequest request) {
        adapter.assignWkCreate(request);
    }

    @Override
    public void submitWk(TestingSheetSubmitRequest request) {
        adapter.submitWk(request);
    }

    @Override
    public void assignWkQt(TestingSheetCreateRequest request) {
        adapter.assignWkQt(request);
    }

    @Override
    public void submitWkQt(TestingSheetSubmitRequest request) {
        adapter.submitWkQt(request);
    }

    @Override
    public void assignWKCatch(TestingSheetCreateRequest request) {
        adapter.assignWKCatch(request);
    }

    @Override
    public void submitWKCatch(TestingSheetSubmitRequest request) {
        adapter.submitWKCatch(request);
    }

    @Override
    public void assignXddl(TestingSheetCreateRequest request) {
        adapter.assignXddl(request);
    }

    @Override
    public void submitXddl(TestingSheetSubmitRequest request) {
        adapter.submitXddl(request);
    }

    @Override
    public void assignAbsoluteQ(TestingSheetCreateRequest request) {
        adapter.assignAbsoluteQ(request);
    }

    @Override
    public void submitAbsoluteQ(TestingSheetSubmitRequest request) {
        adapter.submitAbsoluteQ(request);
    }

    @Override
    public void assignOntest(TestingSheetCreateRequest request) {
        adapter.assignOntest(request);
    }

    @Override
    public void submitOntest(TestingSheetSubmitRequest request) {
        adapter.submitOntest(request);
    }
    
    @Override
	public void assignBioInfo(TestingSheetCreateRequest request) {
    	adapter.assignBioInfo(request);
	}

	@Override
	public void submitBioInfo(TestingSheetSubmitRequest request) {
		adapter.submitBioInfo(request);
	}
	
	@Override
	public void assignTecAnalysis(TestingSheetCreateRequest request) {
		adapter.assignTecAnalysis(request);
	}

	@Override
	public void submitTecAnalysis(TestingSheetSubmitRequest request) {
		adapter.submitTecAnalysis(request);
	}
	
	@Override
	public void assignReportCreate(TestingSheetCreateRequest request) {
		adapter.assignReportCreate(request);
	}

	@Override
	public void submitReportCreate(TestingSheetSubmitRequest request) {
		adapter.submitReportCreate(request);
	}

	@Override
	public void assignReportCheck(TestingSheetCreateRequest request) {
		adapter.assignReportCheck(request);
	}

	@Override
	public void submitReportCheck(TestingSheetSubmitRequest request) {
		adapter.submitReportCheck(request);
	}

	@Override
	public void assignReportMail(TestingSheetCreateRequest request) {
		adapter.assignReportMail(request);
	}

	@Override
	public void submitReportMail(TestingSheetSubmitRequest request) {
		adapter.submitReportMail(request);
	}

	@Override
	public TestingSheetSubmitRequest dataProcess(TestingSheetSubmitRequest request) {
		return adapter.dataProcess(request);
	}

	@Override
	public boolean validateCatchCode(TestingSheetCreateRequest request) {
		return adapter.validateCatchCode(request);
	}
}
