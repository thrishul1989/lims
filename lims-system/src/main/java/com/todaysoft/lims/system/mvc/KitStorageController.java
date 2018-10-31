package com.todaysoft.lims.system.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.KitStorage;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Probe;
import com.todaysoft.lims.system.service.IKitStorageService;

@Controller
@RequestMapping(value = "/kitStorage")
public class KitStorageController extends BaseController{
@Autowired
private IKitStorageService kitStorageService;
	
	/**
	 * 试剂盒入库数据
	 * */
	@RequestMapping(value = "/list.do")
	public String getList(
			KitStorage searcher,
			@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
			ModelMap model) {

		Pagination<KitStorage> pagination = kitStorageService.paging(searcher, pageNo,
				10);
		model.addAttribute("searcher", searcher);
		model.addAttribute("pagination", pagination);
		return "kitstorage/kitstorage_list";

	}
	
	
	/**
	 * 新增试剂盒入库page
	 * */
	@RequestMapping(value = "/createPage.do", method = RequestMethod.GET)
	public String createPage(ModelMap model) {
		KitStorage data = new KitStorage();
		//获取探针编号
		
		model.addAttribute("kitStorage", data);
		model.addAttribute("flag", "新增");

		return "kitstorage/kitstorage_form";
	}
	
	
	/**
	 * 新增试剂盒入库
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create.do", method = RequestMethod.GET)
	public String create(KitStorage request, ModelMap model,
			HttpSession session) {
		
		kitStorageService.create(request);
		

		return redirectList(model, session, "redirect:/kitStorage/list.do");
	}
	
	
	/**
	 * 修改反应数
	 * */
	@RequestMapping(value = "/modifyReaction.do", method = RequestMethod.GET)
	public String modifyReaction(KitStorage request, ModelMap model,
			HttpSession session) {
		kitStorageService.modifyReaction(request);
		return redirectList(model, session, "redirect:/kitStorage/list.do");
	}
	
	/**
	 * 删除试剂盒入库
	 * 
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/delete.do")
	public String delete(Integer id, ModelMap model, HttpSession session) {
		kitStorageService.delete(id);
		return redirectList(model, session, "redirect:/kitStorage/list.do");
	}
	
	
}
