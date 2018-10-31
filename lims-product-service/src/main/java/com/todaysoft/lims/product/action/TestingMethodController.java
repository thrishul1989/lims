package com.todaysoft.lims.product.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.dao.searcher.TestingMethodSearcher;
import com.todaysoft.lims.product.entity.TestingMethod;
import com.todaysoft.lims.product.model.request.TestingMethodListRequest;
import com.todaysoft.lims.product.service.ITestingMethodService;

@RestController
@RequestMapping("/bcm/testing/methods")
public class TestingMethodController
{
    @Autowired
    private ITestingMethodService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<TestingMethod> paging(@RequestBody TestingMethodListRequest request)
    {
        TestingMethodSearcher searcher = new TestingMethodSearcher();
        searcher.setName(request.getName());
        Pagination<TestingMethod> t = service.paging(searcher, request.getPageNo(), request.getPageSize());
        return t;
    }
    
    @RequestMapping(value = "/list")
    public List<TestingMethod> list(@RequestBody TestingMethodListRequest request)
    {
        TestingMethodSearcher searcher = new TestingMethodSearcher();
        searcher.setName(request.getName());
        searcher.setType(request.getType());
        return service.list(searcher);
    }
    
    @RequestMapping(value = "/{id}")
    public TestingMethod get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/getByName", method = RequestMethod.POST)
    public TestingMethod getByName(@RequestBody TestingMethod method)
    {
        TestingMethod tm = service.getByName(method);
        return tm;
    }
    
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public List<TestingMethod> listAll()
    {
        return service.listAll();
    }
}
