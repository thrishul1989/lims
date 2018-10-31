package com.todaysoft.lims.sample.model.request;

import com.todaysoft.lims.sample.entity.TestingSheet;

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
