package com.todaysoft.lims.system.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Probe;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.service.ITestingTypeService;


@Controller
@RequestMapping(value = "/testingType")
public class TestingTypeController extends BaseController {
	
	@Autowired
	private ITestingTypeService testingTypeService;
	
	@RequestMapping(value = "/testingTypeList")
	@ResponseBody
	public List<TestingType> testingTypeList(ModelMap model) {

		 List<TestingType> list= testingTypeService.testingTypeList();
		 return list;
	}
	
	
	@RequestMapping(value = "/testingSubtypeList")
	@ResponseBody
	public List<TestingType> testingSubtypeList(TestingType requset,ModelMap model) {

		 List<TestingType> list= testingTypeService.testingSubtypeList(requset);
		 return list;
	}
	
	
	
}
