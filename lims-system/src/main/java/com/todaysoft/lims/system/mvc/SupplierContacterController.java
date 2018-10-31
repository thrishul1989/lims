package com.todaysoft.lims.system.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Supplier;
import com.todaysoft.lims.system.model.vo.SupplierContacter;
import com.todaysoft.lims.system.service.ISupplierContacterService;
import com.todaysoft.lims.system.service.ISupplierService;



@Controller
@RequestMapping(value = "/supplierContacter")
public class SupplierContacterController extends BaseController{

	
	@Autowired
	private ISupplierContacterService supplerContacterService;
		
	@Autowired
	private ISupplierService supplierService;
		/**
		 * 根据供应商获取联系人
		 * */
		@RequestMapping(value = "/list.do")
		public String getSupplierContacterList(
				SupplierContacter searcher,
				@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
				ModelMap model) {
			Pagination<SupplierContacter> pagination = supplerContacterService.paging(searcher,
					pageNo, 10);
			Supplier supplier=	supplierService.getSupplier(searcher.getSupplier().getId());
			model.addAttribute("supplier", supplier);
			model.addAttribute("searcher", searcher);
			model.addAttribute("pagination", pagination);
			return "supplier/supplierContacter_list";

		}
		
		/**
		 * 修改活修改供应商联系人
		 * 
		 * @param id
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/supplierModify.do", method = RequestMethod.GET)
		public String supplierContacterModify(SupplierContacter request, ModelMap model,
				HttpSession session) {
			if (request.getId() == null) {
				request.getSupplier().setName("123123");
				supplerContacterService.create(request);
			} 
			else {
				supplerContacterService.modify(request);
			}

			return redirectList(model, session, "redirect:/supplierContacter/list.do?supplier.id="+request.getSupplier().getId());
		}
		
		
		/**
		 * 删除厂商联系人
		 * 
		 * @param id
		 * @param model
		 * @param session
		 * @return
		 */
		@RequestMapping("/delete.do")
		public String delete(SupplierContacter request, ModelMap model, HttpSession session) {
			
			supplerContacterService.delete(request.getId());
			return redirectList(model, session, "redirect:/supplierContacter/list.do?supplier.id="+request.getSupplier().getId());
		}
		
		
	
		
		
		
		
		
}
