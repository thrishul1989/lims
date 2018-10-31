package com.todaysoft.lims.system.service.adapter.request;

import com.todaysoft.lims.system.model.vo.TestingSheet;



public class TestingSheetSubmitRequest
{
	private String id;
    
    private TestingSheet testingSheet;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }

	public TestingSheet getTestingSheet() {
		return testingSheet;
	}

	public void setTestingSheet(TestingSheet testingSheet) {
		this.testingSheet = testingSheet;
	}
}
