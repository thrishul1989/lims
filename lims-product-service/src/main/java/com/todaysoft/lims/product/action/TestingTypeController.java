package com.todaysoft.lims.product.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.product.entity.Product;
import com.todaysoft.lims.product.entity.TestingType;
import com.todaysoft.lims.product.service.ITestingTypeService;


@RestController
@RequestMapping("/bcm/testingType")
public class TestingTypeController {
	
	@Autowired
	private ITestingTypeService testingTypeService;
	
	  @RequestMapping(value = "/testingTypeList")
	    public List<TestingType> testingTypeList(@RequestBody TestingType request)
	    {
		  List<TestingType> list= testingTypeService.testingTypeList();
		  return list;
	    }
	  
	  @RequestMapping(value = "/testingSubtypeList")
	    public List<TestingType> testingSubtypeList(@RequestBody TestingType request)
	    {
	        return testingTypeService.testingSubtypeList(request.getParentId());
	    }
	  
	  
	  
	  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	    public TestingType get(@PathVariable String id)
	    {
	      TestingType p = testingTypeService.get(id);
	        return p;
	    }
	  
	  
	  
	  
}
