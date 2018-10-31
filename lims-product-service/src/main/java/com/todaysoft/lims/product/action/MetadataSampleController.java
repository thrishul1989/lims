package com.todaysoft.lims.product.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.MeasureUnit;
import com.todaysoft.lims.product.entity.MetadataSample;
import com.todaysoft.lims.product.model.SamplePretestingConfigDetailModel;
import com.todaysoft.lims.product.model.SampleSimpleModel;
import com.todaysoft.lims.product.model.TestingNode;
import com.todaysoft.lims.product.model.request.SamplePagingRequest;
import com.todaysoft.lims.product.model.request.SampleSearchRequest;
import com.todaysoft.lims.product.model.request.SampleStartableConfigRequest;
import com.todaysoft.lims.product.model.request.TestingNodeSearchRequest;
import com.todaysoft.lims.product.service.IMetadataSampleService;

@RestController
@RequestMapping("/bcm/sample")
public class MetadataSampleController
{
    @Autowired
    private IMetadataSampleService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<MetadataSample> paging(@RequestBody SamplePagingRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<MetadataSample> list(@RequestBody SamplePagingRequest request)
    {
        return service.list(request);
    }
    
    @RequestMapping(value = "/getSampleByName/{name}", method = RequestMethod.GET)
    public MetadataSample getSampleByName(@PathVariable String name)
    {
        return service.getSampleByName(name);
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<SampleSimpleModel> search(@RequestBody SampleSearchRequest request)
    {
        return service.search(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MetadataSample get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody MetadataSample request)
    {
        service.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody MetadataSample request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        service.delete(id);
    }
    
    @RequestMapping(value = "/getUnitName/{id}", method = RequestMethod.GET)
    public MeasureUnit getUnitName(@PathVariable String id)
    {
        return service.getUnitName(id);
    }
    
    @RequestMapping(value = "/unitList", method = RequestMethod.GET)
    public List<MeasureUnit> unitList()
    {
        return service.unitList();
    }
    
    @RequestMapping(value = "/startable/config", method = RequestMethod.POST)
    public void startableConfig(@RequestBody SampleStartableConfigRequest request)
    {
        service.startableConfig(request);
    }
    
    @RequestMapping(value = "/pretesting/configs", method = RequestMethod.GET)
    public List<SamplePretestingConfigDetailModel> getPretestingConfigs()
    {
        return service.getPretestingConfigs();
    }
    
    @RequestMapping(value = "getPreTestingTaskNode", method = RequestMethod.POST)
    public List<TestingNode> getPreTestingTaskNode(@RequestBody TestingNodeSearchRequest request)
    {
        return service.getPreTestingTaskNode(request);
    }
    
    @RequestMapping(value = "/validata", method = RequestMethod.POST)
    public boolean validata(@RequestBody MetadataSample sample)
    {
        return service.validata(sample);
    }
    
}
