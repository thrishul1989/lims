package com.todaysoft.lims.gateway.model.request;

import com.todaysoft.lims.gateway.model.TestingSheet;

public class TestingSheetSubmitRequest
{
	private Integer id;
    
    private TestingSheet testingSheet;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
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
