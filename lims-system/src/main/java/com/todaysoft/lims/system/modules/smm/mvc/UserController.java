package com.todaysoft.lims.system.modules.smm.mvc;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.Department;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.enums.Gender;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.model.RoleDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.RoleSimpleModel;
import com.todaysoft.lims.system.modules.smm.model.TemplateEntity;
import com.todaysoft.lims.system.modules.smm.model.UserArchive;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.UserSearcher;
import com.todaysoft.lims.system.modules.smm.model.UserSimpleModel;
import com.todaysoft.lims.system.modules.smm.service.IRoleService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.modules.smm.service.request.UserCreateRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserListRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserModifyRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserPasswordResetRequest;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;
import com.todaysoft.lims.system.service.ICustomerService;
import com.todaysoft.lims.system.service.IDepartmentService;
import com.todaysoft.lims.system.util.AppToken;
import com.todaysoft.lims.system.util.Base64;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/smm/user")
public class UserController extends BaseController
{
    @Autowired
    private IUserService service;
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IDepartmentService deptService;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private AppToken appToken;
    
    @RequestMapping("/list.do")
    public String list(UserSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<UserSimpleModel> pagination = service.paging(searcher, pageNo, 10);
        if (null != searcher.getArchive())
        {
            String deptId = searcher.getArchive().getDeptId();
            if (!StringUtils.isEmpty(deptId))
            {
                Department departmenet = deptService.get(deptId);
                model.addAttribute("departmenet", JSONObject.toJSON(departmenet).toString());
            }
        }
        String roleId = searcher.getRoleId();
        if (!StringUtils.isEmpty(roleId))
        {
            RoleDetailsModel role = roleService.get(roleId);
            model.addAttribute("roleDetailsModel", JSONObject.toJSON(role).toString());
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "smm/user/user_list";
    }
    
    @RequestMapping("/unique.do")
    @ResponseBody
    public boolean unique(String username)
    {
        return service.unique(username);
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        List<RoleSimpleModel> roles = roleService.list();
        model.addAttribute("roles", roles);
        return "smm/user/user_form";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(UserCreateRequest request, ModelMap model, HttpSession session)
    {
        request.setPassword(Base64.getBase64(request.getPassword()));
        service.create(request);
        return redirectList(model, session);
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        UserDetailsModel data = service.getUserByID(id);
        String leadId = data.getArchive().getLeaderId();
        if (StringUtils.isNotEmpty(leadId))
        {
            UserDetailsModel usermodel = service.getUserByID(leadId);
            if (null != usermodel)
            {
                usermodel.setName(usermodel.getArchive().getName());
                usermodel.setUserName_name(usermodel.getName() + "-" + usermodel.getUsername());
                model.addAttribute("leader", JSONObject.toJSON(usermodel).toString());
            }
        }
        String deptId = data.getArchive().getDeptId();
        if (StringUtils.isNotEmpty(deptId))
        {
            Department departmenet = deptService.get(deptId);
            model.addAttribute("departmenet", departmenet);
        }
        List<RoleSimpleModel> roles = roleService.list();
        model.addAttribute("user", data);
        model.addAttribute("roles", roles);
        return "smm/user/user_form";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(UserModifyRequest request, ModelMap model, HttpSession session)
    {
        service.modify(request);
        return redirectList(model, session);
    }
    
    @RequestMapping(value = "/password/reset.do", method = RequestMethod.GET)
    public String resetPasswordPage(String id, ModelMap model)
    {
        UserDetailsModel user = service.getUserByID(id);
        UserDetailsModel data = service.getUserByID(user.getId());
        model.addAttribute("user", data);
        return "smm/user/password_reset";
    }
    
    @RequestMapping(value = "/password/reset.do", method = RequestMethod.POST)
    public String passwordReset(UserPasswordResetRequest request, ModelMap model, HttpSession session)
    {
        request.setPassword(Base64.getBase64(request.getPassword()));
        service.resetPassword(request);
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        AuthorizedUser user = service.getByToken();
        UserDetailsModel userdetailsmodel = service.getUserByID(user.getId());
        List<String> roles = userdetailsmodel.getRoles();
        boolean flag = false;
        if (Collections3.isNotEmpty(roles))
        {
            for (String role : roles)
            {
                RoleDetailsModel rolemodel = roleService.get(role);
                if (null != rolemodel)
                {
                    if ("系统管理员".equals(rolemodel.getName()))
                    {
                        flag = true;
                    }
                }
            }
        }
        if (flag)
        {
            return "redirect:/smm/user/list.do";
        }
        else
        {
            new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
            return "redirect:/login";
            
        }
    }
    
    @RequestMapping("/enable.do")
    public String setEnable(String id, ModelMap model, HttpSession session)
    {
        service.setEnable(id);
        return redirectList(model, session);
    }
    
    @RequestMapping("/disable.do")
    public String setDisable(String id, ModelMap model, HttpSession session)
    {
        service.setDisable(id);
        appToken.clearAppToken(id);
        return redirectList(model, session);
    }
    
    @RequestMapping("/delete.do")
    @ResponseBody
    public Integer delete(String id, ModelMap model, HttpSession session)
    {
        AppToken AppToken = new AppToken();
        //        AppToken.clearAppToken(id);
        Integer a = service.delete(id);
        return a;
    }
    
    @RequestMapping("/stop.do")
    public String stop(String id, ModelMap model, HttpSession session)
    {
        service.setDisable(id);
        return "redirect:/smm/user/list.do";
    }
    
    @RequestMapping("/display.do")
    public String display(String id, ModelMap model)
    {
        UserDetailsModel data = service.getUserByID(id);
        List<String> roleIds = data.getRoles();
        List<RoleDetailsModel> roleModels = Lists.newArrayList();
        if (Collections3.isNotEmpty(roleIds))
        {
            for (String roleId : roleIds)
            {
                roleModels.add(roleService.get(roleId));
            }
        }
        UserArchive u = data.getArchive();
        if (null != u)
        {
            if (StringUtils.isNotEmpty(u.getDeptId()))
            {
                u.setDeptName(deptService.get(u.getDeptId()).getText());
                
            }
            if (StringUtils.isNotEmpty(u.getLeaderId()))
            {
                if (null != service.getUserByID(u.getLeaderId())) {
                    u.setLeadName(service.getUserByID(u.getLeaderId()).getArchive().getName());
                }
            }
        }
        data.setArchive(u);
        model.addAttribute("user", data);
        model.addAttribute("roles", roleModels);
        return "smm/user/user_details";
    }
    
    @RequestMapping("/getUser.do")
    @ResponseBody
    public UserDetailsModel getById(String id)
    {
        if (StringUtils.isNotEmpty(id))
        {
            UserDetailsModel data = service.getUserByID(id);
            
            if (data.getArchive().getGender().name().equals("MALE"))
            {
                data.getArchive().setSex("男");
            }
            else if (data.getArchive().getGender().name().equals("FEMALE"))
            {
                data.getArchive().setSex("女");
            }
            else
            {
                data.getArchive().setSex("未知");
            }
            List<String> roleIds = data.getRoles();
            List<RoleDetailsModel> roleModels = Lists.newArrayList();
            for (String roleId : roleIds)
            {
                roleModels.add(roleService.get(roleId));
            }
            UserArchive u = data.getArchive();
            if (null != u)
            {
                if (StringUtils.isNotEmpty(u.getDeptId()))
                {
                    u.setDeptName(deptService.get(u.getDeptId()).getText());
                    
                }
                if (StringUtils.isNotEmpty(u.getLeaderId()))
                {
                    
                    u.setLeadName(service.getUserByID(u.getLeaderId()).getArchive().getName());
                }
            }
            data.setArchive(u);
            return data;
        }
        else
        {
            return null;
        }
        
    }
    
    public static boolean contains(String type)
    {
        for (Gender gender : Gender.values())
        {
            if (gender.name().equals(type))
            {
                return true;
            }
        }
        return false;
    }
    
    @RequestMapping("/list_json.do")
    @ResponseBody
    public List<UserSimpleModel> list(String key)
    {
        UserSearcher searcher = new UserSearcher();
        searcher.setName(key);
        return service.list(searcher);
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/smm/user/list.do";
    }
    
    @RequestMapping("/UserSelect.do")
    @ResponseBody
    public List<UserSimpleModel> userSelect(TemplateEntity data)
    {
        UserListRequest request = new UserListRequest();
        request.setUsername(data.getKey());
        if (StringUtils.isNotEmpty(data.getDeptId()))
        {
            request.setTemplateId(data.getTemplateId());
            UserArchive u = new UserArchive();
            u.setDeptId(data.getDeptId());
            request.setArchive(u);
        }
        
        List<UserSimpleModel> list = service.userSelect(request);
        if (!Collections3.isEmpty(list))
        {
            for (UserSimpleModel u : list)
            {
                u.setUserName_name(u.getUsername() + "-" + u.getName());
            }
        }
        if (Collections3.isEmpty(list))
        {
            UserSimpleModel u = new UserSimpleModel();
            u.setId("1");
            u.setName("该部门目前没有人");
            list.add(u);
        }
        return list;
    }
    
    @RequestMapping("/validate.do")
    @ResponseBody
    public boolean validate(UserSearcher searcher)
    {
        Customer c = new Customer();
        c.setPhoneNum(searcher.getPhone());
        if (!customerService.validate(c))
        {
            return false;
        }
        return service.validate(searcher);
    }
    
    @RequestMapping("/no_salesman_list.do")
    @ResponseBody
    public List<UserSimpleModel> noSalesManList(String key)
    {
        UserSearcher searcher = new UserSearcher();
        searcher.setName(key);
        return service.noSalesManList(searcher);
    }
    
    @RequestMapping("/validata.do")
    @ResponseBody
    public boolean validata(String id)
    {
        return service.validate(id);
    }
    
    @RequestMapping("/uniqueNum.do")
    @ResponseBody
    public boolean uniqueNum(String employeeNo, String id)
    {
        return service.uniqueNum(employeeNo, id);
    }
}
