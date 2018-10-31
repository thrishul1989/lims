package com.todaysoft.lims.system.modules.smm.mvc;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.TreeNode;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.modules.smm.model.*;
import com.todaysoft.lims.system.modules.smm.service.IRoleService;
import com.todaysoft.lims.system.modules.smm.service.request.RoleCreateRequest;
import com.todaysoft.lims.system.modules.smm.service.request.RoleModifyRequest;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;
import com.todaysoft.lims.system.service.IResourceService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import org.hibernate.validator.constraints.ModCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/smm/role")
public class RoleController extends BaseController
{

    static final String PROJECT_MANAGER_ROLE = "项目管理人";
    @Autowired
    private IRoleService service;
    
    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IUserService userService;
    
    @RequestMapping("/list.do")
    public String list(RoleSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<RoleSimpleModel> pagination = service.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "smm/role/role_list";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    public String createPage()
    {
        return "smm/role/role_form";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public String create(RoleCreateRequest data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session);
    }

    /**
     * 唯一性验证
     *
     * @param searcher
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/validate.do",method = RequestMethod.POST)
    public boolean validate(RoleSearcher searcher)
    {
        return service.validate(searcher);
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    public String modifyPage(String id, ModelMap model)
    {
        RoleDetailsModel data = service.get(id);
        model.addAttribute("data", data);
       
        return "smm/role/role_form";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    public String modify(RoleModifyRequest data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session);
    }

    @RequestMapping(value = "/details.do", method = RequestMethod.GET)
    public String details(String id, ModelMap model)
    {
        RoleDetailsModel data = service.get(id);
        List<UserSimpleModel> userList = service.getUserListByRole(id);
        model.addAttribute("userList", userList);
        model.addAttribute("data", data);
        model.addAttribute("roleId", id);
        return "smm/role/role_details";
    }

    @RequestMapping("/roleAuthorities.do")
    @ResponseBody
    public List<TreeNode> roleAuthorities(String roleId)
    {
        List<String> roleIds = Arrays.asList(roleId);
        List<TreeNode> list= resourceService.getHierarchyAuthoritiesByRole(roleIds);
        return list;
    }


    @RequestMapping("/delete.do")
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session);
    }
    
    @RequestMapping("/authorities.do")
    @ResponseBody
    public List<TreeNode> authorities()
    {
        List<TreeNode> list= resourceService.getHierarchyAuthorities();
        return list;
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/smm/role/list.do";
    }
    
    @RequestMapping("/roleSelect.do")
    @ResponseBody
    public List<RoleTemplate> roleSelect(RoleSearcher searcher)
    {
        return service.roleSelect(searcher);
        
    }
   //获取角色为项目管理人的用户列表
    @RequestMapping("/getPrjManagerList.do")
    @ResponseBody
    public List<UserSimpleModel> getPrjManagerList(String key)
    {
        UserSearcher searcher = new UserSearcher();
        searcher.setName(key);
        RoleSearcher rs = new RoleSearcher();
        rs.setName(PROJECT_MANAGER_ROLE);
        RoleSimpleModel role = service.getRoleByName(rs);
        return service.getUserListByRole(role.getId());
    }

    //绑定项目管理人筛选框
    @RequestMapping(value = "/getSelectList.do")
    @ResponseBody
    public Map<String, Object> getSelectList(UserSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        RoleSearcher rs = new RoleSearcher();
        rs.setName(PROJECT_MANAGER_ROLE);
        RoleSimpleModel role = service.getRoleByName(rs);
        searcher.setRoleId(role.getId());
        Pagination<UserSimpleModel> pagination = userService.paging(searcher, pageNo, 1000);
        map.put("value", pagination.getRecords());
        return map;

    }



}
