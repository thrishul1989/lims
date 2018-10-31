package com.todaysoft.lims.system.mvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.service.IDepartmentService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController extends BaseController
{
    @Autowired
    private IDepartmentService departmentService;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping(value = "/list.do")
    public String getList(Department searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Pagination<Department> pagination = departmentService.paging(searcher, pageNo, 10);
        
        //查询每个机构负责人
        searchPrincipal(pagination.getRecords(), null);
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        model.addAttribute("data", JSONObject.toJSON(pagination.getRecords()).toString());
        return "department/department";
    }
    
    /**
     * 模糊匹配机构
     */
    @RequestMapping(value = "/departmentSelect.do", method = RequestMethod.GET)
    @ResponseBody
    public List departmentSelect(Department searcher)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        
        List<Department> list = departmentService.departmentSelect(searcher);
        return list;
    }
    
    /**
     * 新增机构
     */
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @ResponseBody
    public Integer create(@RequestBody Department searcher)
    {
        Integer res = 0;
        if (null != searcher.getId())
        {
            res = departmentService.update(searcher);
            
        }
        else
        {
            res = departmentService.create(searcher);
        }
        
        return res;
        
    }
    
    /**
     * 删除机构
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Integer delete(@RequestBody Department searcher)
    {
        Integer res = 0;
        res = departmentService.delete(searcher);
        
        return res;
        
    }
    
    void searchPrincipal(List<Department> dep, Department father)
    {
        
        for (Department d : dep)
        {
            if (Collections3.isNotEmpty(d.getNodes()))
            {
                searchPrincipal(d.getNodes(), d);
                
            }
            
            //查询
            if (StringUtils.isNotEmpty(d.getPrincipalId()))
            {
                UserDetailsModel userDetail = userService.getUserByID(d.getPrincipalId());
                if (null != userDetail)
                {
                    userDetail.getArchive().setId(userDetail.getId());
                    d.setUserDetail(userDetail);
                    d.setTags(Arrays.asList("" + userDetail.getArchive().getName()));
                    
                }
                
            }
            if (father != null)
            {
                Department fatherClone = new Department();
                fatherClone.setId(father.getId());
                fatherClone.setText(father.getText());
                fatherClone.setTags(father.getTags());
                
                d.setPp(fatherClone);
            }
            
        }
        
    }
    
    @RequestMapping(value = "/getDepartment.do", method = RequestMethod.POST)
    @ResponseBody
    public Department getDepartment(String id)
    {
        return departmentService.get(id);
    }
    
    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping(value = "/validate.do", method = RequestMethod.POST)
    public boolean validate(Department request)
    {
        return departmentService.validate(request);
    }
    
    @ResponseBody
    @RequestMapping(value = "/selectParentD", method = RequestMethod.GET)
    public List<Department> selectParentD()
    {
        return departmentService.selectParentD();
    }
}
