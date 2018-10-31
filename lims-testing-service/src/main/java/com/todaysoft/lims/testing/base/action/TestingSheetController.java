package com.todaysoft.lims.testing.base.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.dao.searcher.TestingSheetSearcher;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingSheetView;
import com.todaysoft.lims.testing.base.service.ITestingSheetService;

@RestController
@RequestMapping("/bpm/testingSheet")
public class TestingSheetController
{
    @Autowired
    private ITestingSheetService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<TestingSheetView> paging(@RequestBody TestingSheetSearcher searcher)
    {
        return service.paging(searcher);
    }
    
}
