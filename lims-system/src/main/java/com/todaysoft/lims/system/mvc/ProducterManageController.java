package com.todaysoft.lims.system.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.searcher.ProducterManageSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.ProducterManage;
import com.todaysoft.lims.system.service.IProducterManageService;

@Controller
@RequestMapping("/producterManage")
public class ProducterManageController extends BaseController {

	@Autowired
	IProducterManageService producterManageService;

	@RequestMapping("/list.do")
	public String pagging(
			ProducterManageSearcher searcher,
			@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
			ModelMap model) {
		Pagination<ProducterManage> pagination = producterManageService.paging(
				searcher, pageNo, 10);
		for (ProducterManage data : pagination.getRecords()) {
			producterManageService.doSome(data, "list");
		}
		model.addAttribute("searcher", searcher);
		model.addAttribute("pagination", pagination);

		return "/producterManage/producterManage_list";

	}

	@RequestMapping("/modify.do")
	public String getProducterManageById(ProducterManage request,
			ModelMap model, HttpSession session) {

		producterManageService.modify(request);
		return redirectList(model, session, "redirect:/producterManage/list.do");
	}

	@RequestMapping("/delete.do")
	public String delete(String id, ModelMap model, HttpSession session) {

		producterManageService.delete(id);
		return redirectList(model, session, "redirect:/producterManage/list.do");
	}

	@RequestMapping("/create.do")
	public String create(ProducterManage request, ModelMap model,
			HttpSession session) {

		producterManageService.create(request);
		return redirectList(model, session, "redirect:/producterManage/list.do");
	}

	@RequestMapping("/view.do")
	public String view(String id, ModelMap model, HttpSession session) {

		ProducterManage producterManage = producterManageService
				.getProducterManage(id);
		producterManageService.doSome(producterManage, "view");
		model.addAttribute("producterManage", producterManage);
		return "/producterManage/producterManage_view";
	}

	@RequestMapping("/form.do")
	public String form(String id, ModelMap model) {
		ProducterManage data = new ProducterManage();
		if (id != null) {
			data = producterManageService.getProducterManage(id);
			producterManageService.doSome(data, "view");
		}
		model.addAttribute("producterManage", data);

		return "producterManage/producterManage_form";
	}

	@ResponseBody
	@RequestMapping("/validate.do")
	public boolean validate(ProducterManage search) {
		return producterManageService.validate(search);

	}

	@ResponseBody
	@RequestMapping("/deleteEmail.do")
	public void deleteEmail(ProducterManage data) {
		producterManageService.deleteEmail(data);
	}

}
