package com.todaysoft.lims.system.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Firm;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Probe;
import com.todaysoft.lims.system.model.vo.Sysconfig;
import com.todaysoft.lims.system.service.ISysconfigService;


@Controller
@RequestMapping(value = "/sysconfig")
public class SysConfigController extends BaseController{

	
	@Autowired
	private ISysconfigService sysconfigService;

	/**
	 * 系统配置
	 * */
	@RequestMapping(value = "/list.do")
	public String getSysconfigList(
			Sysconfig searcher,
			@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
			ModelMap model) {
		Pagination<Sysconfig> pagination = sysconfigService.paging(searcher, pageNo,
				10);
		model.addAttribute("searcher", searcher);
		model.addAttribute("pagination", pagination);
		
		
		return "sysConfig/sysconfig_list";

	}
	
	
	/**
	 * 新增配置page
	 * */
	@RequestMapping(value = "/create.do", method = RequestMethod.GET)
	public String createPage(ModelMap model) {
		Sysconfig data = new Sysconfig();

		model.addAttribute("sysconfig", data);
		model.addAttribute("flag", "新增");

		return "sysConfig/sysconfig_form";
	}

	/**
	 * 修改配置page
	 * */
	@RequestMapping(value = "/modify.do", method = RequestMethod.GET)
	public String modifyPage(Integer id, ModelMap model) {
		Sysconfig data = sysconfigService.getSysconfig(id);

		model.addAttribute("sysconfig", data);
		model.addAttribute("flag", "修改");
		return "sysConfig/sysconfig_form";
	}
	
	
	/**
	 * 修改配置项
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sysconfigModify.do", method = RequestMethod.POST)
	public String modifyProbe(Sysconfig request, ModelMap model,
			HttpSession session) {
		if (request.getId() == null) {
			sysconfigService.create(request);
		} else {
			sysconfigService.modify(request);
		}

		return redirectList(model, session, "redirect:/sysconfig/list.do");
	}

	/**
	 * 删除配置项
	 * 
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/delete.do")
	public String delete(Integer id, ModelMap model, HttpSession session) {
		sysconfigService.delete(id);
		return redirectList(model, session, "redirect:/sysconfig/list.do");
	}
	
	/**
	 * 唯一性校验
	 */
	@ResponseBody
	@RequestMapping("/validate.do")
	public boolean validate(Sysconfig search) {
		return sysconfigService.validate(search);

	}
	
	
	
}
