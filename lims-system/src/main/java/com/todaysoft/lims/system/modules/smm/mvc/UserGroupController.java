package com.todaysoft.lims.system.modules.smm.mvc;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.todaysoft.lims.system.model.vo.Department;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.modules.smm.model.UserGroup;
import com.todaysoft.lims.system.modules.smm.model.UserGroupMember;
import com.todaysoft.lims.system.modules.smm.model.UserSearcher;
import com.todaysoft.lims.system.modules.smm.model.UserSimpleModel;
import com.todaysoft.lims.system.modules.smm.service.IUserGroupService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.modules.smm.service.request.UserGroupCreateRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserGroupModifyRequest;
import com.todaysoft.lims.system.modules.smm.service.request.UserGroupPagingRequest;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.service.IDepartmentService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/userGroup")
public class UserGroupController extends BaseController
{
    @Autowired
    private IUserGroupService service;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IDepartmentService deptService;
    
    @RequestMapping("/list.do")
    public String paging(UserGroupPagingRequest request,
        @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model,
        HttpSession session)
    {
        Pagination<UserGroup> pagination = service.paging(request, pageNo, 10);
        model.addAttribute("pagination", pagination).addAttribute("searcher", request);
        return "smm/userGroup/userGroup_list";
    }
    
    //详情页面
    @RequestMapping("/show.do")
    public String view(String id, ModelMap model)
    {
        UserGroup userGroup = service.getById(id);
        String deptId = userGroup.getGroupLeader().getArchive().getDeptId();
        userGroup.getGroupLeader().getArchive().setDeptName(getDeptName(deptId));
        getSex(userGroup.getGroupLeader());//转换性别
        for (UserGroupMember ugm : userGroup.getUserGroupMembers())
        {
            String deptIds = ugm.getUser().getArchive().getDeptId();
            if (StringUtils.isNotEmpty(deptIds))
            {
                ugm.getUser().getArchive().setDeptName(getDeptName(deptIds));
            }
            getSex(ugm.getUser());//转换性别
        }
        model.addAttribute("userGroup", userGroup);
        return "smm/userGroup/userGroup_show";
        
    }
    
    //获取部门名称
    private String getDeptName(String id)
    {
        if (StringUtils.isNotEmpty(id))
        {
            Department d = deptService.get(id);
            return d.getText();
        }
        else
        {
            return "";
        }
    }
    
    //性别转换为汉字
    private void getSex(User user)
    {
        if (user.getArchive().getGender().name().equals("MALE"))
        {
            user.getArchive().setSex("男");
        }
        else if (user.getArchive().getGender().name().equals("FEMALE"))
        {
            user.getArchive().setSex("女");
        }
        else
        {
            user.getArchive().setSex("未知");
        }
    }
    
    //新建页面
    @RequestMapping(value = "/create_form.do", method = RequestMethod.GET)
    public String createForm(UserSearcher userSearcher, ModelMap model)
    {
        List<UserSimpleModel> userList = userService.list(userSearcher);
        model.addAttribute("userList", userList);
        return "smm/userGroup/userGroup_create_form";
    }
    
    //修改页面
    @RequestMapping(value = "/modify_form.do", method = RequestMethod.GET)
    public String modiyForm(String id, ModelMap model)
    {
        UserGroup userGroup = service.getById(id);
        String deptId = userGroup.getGroupLeader().getArchive().getDeptId();
        userGroup.getGroupLeader().getArchive().setDeptName(getDeptName(deptId));
        getSex(userGroup.getGroupLeader());//转换性别
        //获取组员信息
        Map<String, List<UserSimpleModel>> members = new HashMap<String, List<UserSimpleModel>>();
        for (UserGroupMember ugm : userGroup.getUserGroupMembers())
        {
            String deptIds = ugm.getUser().getArchive().getDeptId();
            if (StringUtils.isNotEmpty(deptIds))
            {
                ugm.getUser().getArchive().setDeptName(getDeptName(deptIds));
            }
            getSex(ugm.getUser());//转换性别
            List<UserSimpleModel> member = new ArrayList<UserSimpleModel>();//组员集合
            UserSimpleModel m = new UserSimpleModel();
            m.setName(ugm.getUser().getArchive().getName());
            member.add(m);
            members.put(ugm.getUser().getId(), member);
        }
        //获取组长信息
        List<UserSimpleModel> leaders = new ArrayList<UserSimpleModel>();
        UserSimpleModel usm = new UserSimpleModel();
        usm.setName(userGroup.getGroupLeader().getArchive().getName());
        leaders.add(usm);
        model.addAttribute("leaders", JSONObject.toJSON(leaders).toString()).addAttribute("members",
            JSONObject.toJSON(members).toString());
        model.addAttribute("userGroup", userGroup);
        return "smm/userGroup/userGroup_modify_form";
    }
    
    //新建组
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public String create(UserGroupCreateRequest request)
    {
        service.create(request);
        return "redirect:/userGroup/list.do";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    public String modify(UserGroupModifyRequest request)
    {
        service.modify(request);
        return "redirect:/userGroup/list.do";
    }
    
    @RequestMapping("/delete.do")
    @ResponseBody
    public boolean delete(String id)
    {
        boolean a = service.delete(id);
        return a;
    }
    
    @RequestMapping("/validate.do")
    @ResponseBody
    public boolean validate(UserGroupCreateRequest request)
    {
        boolean a = service.validate(request);//组名不存在FALSE，存在TRUE；
        return a;
    }
    
    @RequestMapping("/getUserGroup.do")
    @ResponseBody
    public UserGroup getById(String id)
    {
        if (StringUtils.isNotEmpty(id))
        {
            UserGroup data = service.getById(id);
            
            return data;
        }
        else
        {
            return null;
        }
        
    }
    
    @RequestMapping("/list_json.do")
    @ResponseBody
    public List<UserGroup> list(String key)
    {
        UserGroupPagingRequest searcher = new UserGroupPagingRequest();
        searcher.setGroupName(key);
        List<UserGroup> groups = service.list(searcher);
        for (UserGroup u : groups)
        {
            u.setName(u.getGroupName());
        }
        return groups;
    }
}
