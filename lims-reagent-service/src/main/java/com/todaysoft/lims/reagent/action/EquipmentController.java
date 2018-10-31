package com.todaysoft.lims.reagent.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.Equipment;
import com.todaysoft.lims.reagent.model.request.EquipmentCreateRequest;
import com.todaysoft.lims.reagent.model.request.EquipmentModifyRequest;
import com.todaysoft.lims.reagent.model.request.EquipmentPagingRequest;
import com.todaysoft.lims.reagent.service.IEquipmentService;

@RestController
@RequestMapping("/bsm/equipment")

public class EquipmentController {
	@Autowired
	private IEquipmentService equipmentService;
	
	@RequestMapping(value = "/paging")
	public Pagination<Equipment> paging(@RequestBody EquipmentPagingRequest request){
		return equipmentService.paging(request);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public Equipment getEquipment(@PathVariable String id){
		return equipmentService.getEquipment(id);
	}
	
	@RequestMapping(value = "/create")
	public String create(@RequestBody EquipmentCreateRequest request){
		return equipmentService.create(request);
	}
	
	@RequestMapping(value = "/modify")
	public void modify(@RequestBody EquipmentModifyRequest request){
		equipmentService.modify(request);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public void delete(@PathVariable String id){
		equipmentService.delete(id);
	}
	
	@RequestMapping(value = "/checkName")
	@ResponseBody
	public Boolean checkName(@RequestBody EquipmentCreateRequest request){
		return equipmentService.checkName(request);
	}
	
	@RequestMapping(value = "/checkEquipmentNo")
	@ResponseBody
	public Boolean checkEquipmentNo(@RequestBody EquipmentCreateRequest request){
		return equipmentService.checkEquipmentNo(request);
	}

}
