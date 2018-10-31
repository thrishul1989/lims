package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Connect;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.ConnectRequest;
import com.todaysoft.lims.gateway.service.IConnectService;


@RestController
@RequestMapping("/connect")
public class ConnectController {
	@Autowired
	private IConnectService connectService;
	
	@RequestMapping(value = "/paging")
	public Pagination<Connect> paging(@RequestBody ConnectRequest request){
		return connectService.paging(request);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public Connect getconnect(@PathVariable String id){
		return connectService.getConnect(id);
	}
	
	@RequestMapping(value = "/create")
	public String create(@RequestBody Connect request){
		return connectService.create(request);
	}
	
	@RequestMapping(value = "/modify")
	public void modify(@RequestBody Connect request){
		connectService.modify(request);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public void delete(@PathVariable String id){
		connectService.delete(id);
	}
	 
	 @RequestMapping("/chechedConnectNum")
	 public boolean checkedconnectNum(@RequestBody ConnectRequest request){
		
		 return connectService.checkedconnectNum(request);
	 }
	 
	 @RequestMapping("/getConnectListById/{ids}")
	 public List<Connect> getConnectListById(@PathVariable String ids){
		
		 return connectService.getConnectListById(ids);
	 }
	 
	 @RequestMapping("/getConnectList")
	 public List<Connect> getConnectList(@RequestBody ConnectRequest request){
		 
		 return connectService.getConnectList(request);
	 }
	 
	 @RequestMapping("/ConnectListForWkcreate")
	 public List<Connect> ConnectListForWkcreate(@RequestBody ConnectRequest request){
		 
		 return connectService.ConnectListForWkcreate(request);
	 }
	 
	 
}
