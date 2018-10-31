package com.todaysoft.lims.system.mvc;

import java.io.File;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.vo.ExperimentProduct;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Project;
import com.todaysoft.lims.system.service.IExperimentProductService;
import com.todaysoft.lims.system.service.IProjectService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.util.ConfigManage;

/**项目管理*/
@Controller
@RequestMapping( value = "/project")
public class ProjectController extends BaseController{
	@Autowired
	private IExperimentProductService experimentProductService;
	
	@Autowired
	private IProjectService projectService;
	
	@Autowired
	private IUserService userService;
	

	/**
	 * 项目管理列表
	 * */
	@RequestMapping(value = "/list.do")
	public String getDeviceList(
			Project searcher,
			@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
			ModelMap model,HttpSession session) {
		Pagination<Project> pagination = projectService.paging(searcher, pageNo,
				10);
		model.addAttribute("searcher", searcher);
		model.addAttribute("pagination", pagination);
		session.setAttribute("s-pageNo", pageNo);
		session.setAttribute("s-searcher", searcher);
	
		return "project/project_list";

	}
	
	
	/**
	 * 新增项目
	 * */
	@RequestMapping(value = "/createProject.do", method = RequestMethod.GET)
	public String createPage(ExperimentProduct searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model) {
		Project data = new Project();
		
		model.addAttribute("project", data);
		model.addAttribute("flag", "新增");
		
		return "project/project_form";
	}
	
	
	/**
	 * 修改项目
	 * */
	@RequestMapping(value = "/projectModify.do", method = RequestMethod.GET)
	public String modifyPage(Integer id, ModelMap model) {
		Project data = projectService.getProject(id);
	
		model.addAttribute("project", data);
		model.addAttribute("flag", "修改");
		return "project/project_form";
	}
	
	/**
	 * 查看
	 * */
	@RequestMapping(value = "/projectShow.do", method = RequestMethod.GET)
	public String projectShow(Integer id, ModelMap model) {
		Project data = projectService.getProject(id);
		model.addAttribute("project", data);
		return "project/project_show";
	}
	
	
	/**
	 * 为多选自动匹配框赋值
	 * */
	@ResponseBody
	@RequestMapping(value = "/getProject.do", method = RequestMethod.GET)
	public Project getProject(Integer id, ModelMap model) {
		Project data = projectService.getProject(id);
	
		return data;
	}
	
	
	
	
	
	
	/**
	 * 删除项目
	 * 
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/delete.do")
	public String deleteProject(Integer id, ModelMap model, HttpSession session) {
		projectService.deleteProject(id);
		//return redirectList(model, session, "redirect:/project/list.do");
		return "redirect:/project/list.do";
	}
	
	/**
	 * 修改项目
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/modifyProjecr.do", method = RequestMethod.GET)
	public String modifyProject(Project request, ModelMap model,
			HttpSession session) {
		if(request.getId()==null){
			projectService.createProject(request);
		}
		else{
			projectService.modify(request);
		}
		
		//return redirectList(model, session, "redirect:/project/list.do");
		return "redirect:list.do";
	}
	
	
	

	
	
	/**
	 * 唯一性教研
	 */
	@ResponseBody
	@RequestMapping("/validate.do")
	public boolean validate(Project request) {
		 return projectService.validate(request);
	
	}
	
	
	 @RequestMapping("/upload.do")
	 public String upload(HttpServletRequest request, HttpServletResponse response,Integer id){
		projectService.upload(request, response,id);
		return "redirect:/project/list.do";
	 }
		
		
	 @RequestMapping("/download.do")
	 public void dowload(HttpServletRequest request,HttpServletResponse response,Integer id ,String fileName){
		try{
			StringBuffer path = new StringBuffer(ConfigManage.getkey("uploadPath"));
			path.append(File.separator).append(id).append(File.separator).append(fileName);
	        download(response, path.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	 }
 
}
