package com.todaysoft.lims.system.mvc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.system.service.IExperimentProductService;


@RestController
@RequestMapping("/experimentProduct")
public class ExperimentProductController extends BaseController{
	@Autowired
	private IExperimentProductService experimentProductService;
	
//	@RequestMapping("/list.do")
//	 public String list(ExperimentProduct searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
//    {
//		
//		 Pagination<ExperimentProduct> pagination = experimentProductService.paging(searcher, pageNo, 10);
//	        model.addAttribute("searcher", searcher);
//	        model.addAttribute("pagination", pagination);
//	        
//	        return "equipment/experiment_list";
//	}
	
//	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
//	public ExperimentProduct getEquipment(@PathVariable Integer id){
//		return experimentProductService.getExperimentProduct(id);
//	}
//	
//	@RequestMapping(value = "/create")
//	public Integer create(@RequestBody ExperimentProductCreateRequest request){
//		return experimentProductService.create(request);
//	}
//	
//	@RequestMapping(value = "/modify")
//	public void modify(@RequestBody ExperimentProductModifyRequest request){
//		experimentProductService.modify(request);
//	}
//	
//	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
//	public void delete(@PathVariable Integer id){
//		experimentProductService.delete(id);
//	}
}
