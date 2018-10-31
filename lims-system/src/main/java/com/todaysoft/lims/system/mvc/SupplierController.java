package com.todaysoft.lims.system.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Probe;
import com.todaysoft.lims.system.model.vo.ReagentKit;
import com.todaysoft.lims.system.model.vo.Supplier;
import com.todaysoft.lims.system.model.vo.Task;
import com.todaysoft.lims.system.service.ISupplierService;
import com.todaysoft.lims.system.service.adapter.request.TaskListRequest;

@Controller
@RequestMapping(value = "/supplier")
public class SupplierController extends BaseController{
@Autowired
private ISupplierService supplerService;
	
	/**
	 * 试剂盒主数据
	 * */
	@RequestMapping(value = "/list.do")
	public String getSupplierList(
			Supplier searcher,
			@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
			ModelMap model) {
		Pagination<Supplier> pagination = supplerService.paging(searcher,
				pageNo, 10);
		model.addAttribute("searcher", searcher);
		model.addAttribute("pagination", pagination);
		return "supplier/supplier_list";

	}
	
	/**
	 * 新增供应商page
	 * */
	@RequestMapping(value = "/createPage.do", method = RequestMethod.GET)
	public String createPage(ModelMap model) {
		Supplier supplier=new Supplier();
		model.addAttribute("supplier", supplier);
		return "supplier/supplier_form";
	}

	/**
	 * 编辑供应商page
	 * */
	@RequestMapping(value = "/modifyPage.do", method = RequestMethod.GET)
	public String modifyPage(Integer id, ModelMap model) {
	
		Supplier data = supplerService.getSupplier(id);

		model.addAttribute("supplier", data);
		return "supplier/supplier_form";
	}
	
	
	/**
	 * 修改活修改供应商
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/supplierModify.do", method = RequestMethod.POST)
	public String supplierModify(Supplier request, ModelMap model,
			HttpSession session) {
		if (request.getId() == null) {
			supplerService.create(request);
		} else {
			supplerService.modify(request);
		}

		return redirectList(model, session, "redirect:/supplier/list.do");
	}
	
	
	/**
	 * 删除供应商
	 * 
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/delete.do")
	public String delete(Integer id, ModelMap model, HttpSession session) {
		supplerService.delete(id);
		return redirectList(model, session, "redirect:/supplier/list.do");
	}
	

	 /**
		 * 模糊匹配提供商
		 * */
		@RequestMapping(value = "/supplierSelect.do", method = RequestMethod.GET)
		 @ResponseBody
		    public Map supplierSelect(Supplier key)
		    {
			 Map map=new HashMap<>();
			 map.put("message", "");
		
			 Pagination<Supplier> supplier = supplerService.selectSupplier(key, 1, 10);
			
			 
			 map.put("value", supplier.getRecords());
		      
		        return map;
		    }
		
		
	
	
	
	/**
	 * 唯一性教研
	 */
	@ResponseBody
	@RequestMapping("/validate.do")
	public boolean validate(Supplier search) {
		return supplerService.validate(search);

	}
	
	
}
