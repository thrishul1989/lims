package com.todaysoft.lims.system.modules.smm.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.system.model.vo.Department;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.DataAuthority;
import com.todaysoft.lims.system.modules.smm.model.DataAuthorityRole;
import com.todaysoft.lims.system.modules.smm.model.DataAuthorityUser;
import com.todaysoft.lims.system.modules.smm.model.Role;
import com.todaysoft.lims.system.modules.smm.model.RoleDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.service.IDataAuthorityService;
import com.todaysoft.lims.system.modules.smm.service.IRoleService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;
import com.todaysoft.lims.system.service.IDepartmentService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/dataAuthority")
public class DataAuthorityController extends BaseController
{
    
    @Autowired
    private IDataAuthorityService dataAuthorityService;
    
    @Autowired
    private IDepartmentService departmentService;
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping("/list.do")
    public String list(DataAuthority searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<DataAuthority> pagination = dataAuthorityService.paging(searcher, pageNo, 10);
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "smm/dataAuthority/dataAuthority_list";
    }
    
    @RequestMapping("/tab.do")
    public String tab(String resourcekey, String resourceName, ModelMap model)
    {
        model.addAttribute("resourcekey", resourcekey);
        model.addAttribute("resourceName", resourceName);
        return "smm/dataAuthority/tab";
        
    }
    
    @RequestMapping("/dataAuthorityRole.do")
    public String dataAuthorityRole(DataAuthorityRole searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<DataAuthorityRole> pagination = dataAuthorityService.dataAuthorityRole(searcher, pageNo, 10);
        //查询角色
        for (DataAuthorityRole dr : pagination.getRecords())
        {
            RoleDetailsModel role = roleService.get(dr.getRoleId());
            dr.setRole(role);
            //查询机构
            if (dr.getConfig() == 5 && StringUtils.isNotEmpty(dr.getSpecialDepts()))
            {
                String[] depts = dr.getSpecialDepts().split("\\,");
                for (String dep : depts)
                {
                    Department department = departmentService.get(dep);
                    dr.setDeptNames((dr.getDeptNames() == null ? "" : dr.getDeptNames()) + "\t" + department.getText());
                }
            }
            
        }
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        //展现树列表
        Pagination<Department> tree = departmentService.paging(new Department(), pageNo, 10);
        
        model.addAttribute("tree", JSONObject.toJSON(tree.getRecords()).toString());
        
        return "smm/dataAuthority/dataAuthorityRole";
        
    }
    
    @RequestMapping(value = "/createRoleDataAuthority.do", method = RequestMethod.POST)
    @ResponseBody
    public Integer createRoleDataAuthority(@RequestBody DataAuthorityRole request, ModelMap model)
    {
        
        Integer res = dataAuthorityService.createRoleDataAuthority(request);
        return res;
    }
    
    @RequestMapping("/deleteRoleAuthority.do")
    public String deleteRoleAuthority(DataAuthorityRole request, ModelMap model, HttpSession session)
    {
        dataAuthorityService.deleteRoleAuthority(request);
        return redirectRoleList(request, model, session);
    }
    
    private String redirectRoleList(DataAuthorityRole request, ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        model.addAttribute("resourceKey.resourceKey", request.getResourceKey().getResourceKey());
        model.addAttribute("resourceKey.resourceName", request.getResourceKey().getResourceName());
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/dataAuthority/dataAuthorityRole.do";
    }
    
    private String redirectUserList(DataAuthorityUser request, ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        model.addAttribute("resourceKey.resourceKey", request.getResourceKey().getResourceKey());
        model.addAttribute("resourceKey.resourceName", request.getResourceKey().getResourceName());
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/dataAuthority/dataAuthorityUser.do";
    }
    
    
    
    @RequestMapping("/dataAuthorityUser.do")
    public String dataAuthorityUser(DataAuthorityUser searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<DataAuthorityUser> pagination = dataAuthorityService.dataAuthorityUser(searcher, pageNo, 10);
        //查询用户
        for (DataAuthorityUser du : pagination.getRecords())
        {
            UserDetailsModel user = userService.getUserByID(du.getUserId());
            du.setUser(user);
            
            //查询机构
            if (du.getConfig() == 5 && StringUtils.isNotEmpty(du.getSpecialDepts()))
            {
                String[] depts = du.getSpecialDepts().split("\\,");
                for (String dep : depts)
                {
                    Department department = departmentService.get(dep);
                    du.setDeptNames((du.getDeptNames() == null ? "" : du.getDeptNames()) + "\t" + department.getText());
                    
                }
                
            }
            
        }
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        
        //展现树列表
        Pagination<Department> tree = departmentService.paging(new Department(), pageNo, 10);
        
        model.addAttribute("tree", JSONObject.toJSON(tree.getRecords()).toString());
        
        return "smm/dataAuthority/dataAuthorityUser";
        
    }
    
    @RequestMapping(value = "/createUserDataAuthority.do", method = RequestMethod.POST)
    @ResponseBody
    public Integer createUserDataAuthority(@RequestBody DataAuthorityUser request, ModelMap model)
    {
        
        Integer res = dataAuthorityService.createUserDataAuthority(request);
        return res;
    }
    
    @RequestMapping("/deleteUserAuthority.do")
    public String deleteUserAuthority(DataAuthorityUser request, ModelMap model, HttpSession session)
    {
        dataAuthorityService.deleteUserAuthority(request);
        return redirectUserList(request, model, session);
    }
    
}
