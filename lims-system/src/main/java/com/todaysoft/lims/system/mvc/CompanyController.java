package com.todaysoft.lims.system.mvc;

import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.searcher.CompanySearcher;
import com.todaysoft.lims.system.model.vo.Area;
import com.todaysoft.lims.system.model.vo.Company;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.AuthorizedUser;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.service.ICompanyService;
import com.todaysoft.lims.system.util.Pinyin4jUtil;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController
{
    
    @Autowired
    private ICompanyService companyService;
    
    @Autowired
    private IUserService userService;
    
    /**
     * 客户单位数据
     * */
    @RequestMapping(value = "/list.do")
    public String getCompanyList(CompanySearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Pagination<Company> pagination = companyService.paging(searcher, pageNo, 10);
        if (null != pagination)
        {
            for (Company entity : pagination.getRecords())
            {
                if (StringUtils.isNotEmpty(entity.getProvince()))
                {
                    Area a = companyService.getAreaById(entity.getProvince().getId());
                    if (null != a)
                        entity.setProvinceName(a.getName());
                }
                if (StringUtils.isNotEmpty(entity.getCity()))
                {
                    Area a = companyService.getAreaById(entity.getCity().getId());
                    if (null != a)
                        entity.setCityName(a.getName());
                }
                if (StringUtils.isNotEmpty(entity.getCounty()))
                {
                    Area a = companyService.getAreaById(entity.getCounty().getId());
                    if (null != a)
                        entity.setCountyName(companyService.getAreaById(entity.getCounty().getId()).getName());
                }
            }
        }
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        model.addAttribute("areaList", companyService.getAreas(new Area()));
        return "company/company_list";
        
    }
    
    @RequestMapping(value = "/getCompanys")
    @ResponseBody
    public Map getCompanys(CompanySearcher searcher)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        List<Company> list = companyService.getCompanys(searcher);
        map.put("value", list);
        return map;
    }
    
    /**
     * 新增单位page
     * */
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        Company data = new Company();
        
        model.addAttribute("company", data);
        model.addAttribute("flag", "新增");
        model.addAttribute("areaList", companyService.getAreas(new Area()));
        return "company/company_form";
    }
    
    /**
     * 修改探单位page
     * */
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        Company data = companyService.get(id);
        Area cityArea = new Area();
        if (null != data.getProvince())
        {
            cityArea.setParentId(data.getProvince().getId());
        }
        
        if (StringUtils.isNotEmpty(data.getProvince()))
        {
            List<Area> citys = companyService.getAreas(cityArea);
            model.addAttribute("citys", citys);
        }
        Area countyArea = new Area();
        if (null != data.getCity())
        {
            countyArea.setParentId(data.getCity().getId());
        }
        
        if (StringUtils.isNotEmpty(data.getCity()))
        {
            List<Area> countys = companyService.getAreas(countyArea);
            model.addAttribute("countys", countys);
        }
        
        model.addAttribute("company", data);
        model.addAttribute("flag", "修改");
        model.addAttribute("areaList", companyService.getAreas(new Area()));
        return "company/company_form";
    }
    
    /**
     * 修改 单位
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/companyModify.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modifyCompany(Company request, ModelMap model, HttpSession session)
    {
        request.setAddress(request.getAddress().trim());
        AuthorizedUser user = userService.getByToken();
        request.setPy(Pinyin4jUtil.getPinYinHeadChar(request.getName()));
        request.setPinyin(Pinyin4jUtil.getPinYin(request.getName()));
        if (StringUtils.isNotEmpty(request.getId()))
        {
            request.setUpdateTime(new Date());
            request.setUpdater(user.getId());
            companyService.modify(request);
        }
        else
        {
            request.setCreateTime(new Date());
            request.setUpdateTime(new Date());
            request.setCreator(user.getId());
            companyService.create(request);
        }
        
        return redirectList(model, session, "redirect:/company/list.do");
    }
    
    /**
     * 删除单位
     * 
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/delete.do")
    @ResponseBody
    public boolean delete(String id)
    {
        boolean flag = false;
        if (companyService.countCustomers(id) == 0)
        {
            companyService.delete(id);
            flag = true;
        }
        return flag;
    }
    
    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping("/validate.do")
    public boolean validate(CompanySearcher searcher)
    {
        return companyService.validate(searcher);
        
    }
    
    /**
     * 查看客户单位
     */
    @RequestMapping("/view.do")
    public String view(String id, ModelMap model)
    {
        Company entity = companyService.get(id);
        if (StringUtils.isNotEmpty(entity.getProvince()))
        {
            entity.setProvinceName(companyService.getAreaById(entity.getProvince().getId()).getName());
        }
        if (StringUtils.isNotEmpty(entity.getCity()))
        {
            entity.setCityName(companyService.getAreaById(entity.getCity().getId()).getName());
        }
        if (StringUtils.isNotEmpty(entity.getCounty()))
        {
            entity.setCountyName(companyService.getAreaById(entity.getCounty().getId()).getName());
        }
        model.addAttribute("company", entity);
        return "company/company_view";
    }
    
    /**
     * 模糊匹配单位
     * */
    @RequestMapping(value = "/companySelect.do", method = RequestMethod.GET)
    @ResponseBody
    public Map containerSelect(CompanySearcher entity)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        
        Pagination<Company> containers = companyService.selectCompany(entity, 1, 10);
        for (Company Company : containers.getRecords())
        {
            if (StringUtils.isNotEmpty(Company.getOtherName()))
            {
                
                Company.setName(Company.getName() + "(" + Company.getOtherName() + ")");
            }
            else
            {
                Company.setName(Company.getName());
            }
        }
        
        map.put("value", containers.getRecords());
        
        return map;
    }
    
    @ResponseBody
    @RequestMapping("/toLetter.do")
    public String toLetter(String name)
    {
        return Pinyin4jUtil.getPinYin(name);
    }
    
    @RequestMapping("/getAreas.do")
    @ResponseBody
    public List<Area> getAreas(Area area)
    {
        return companyService.getAreas(area);
    }
    
    @RequestMapping("/upload.do")
    public String upload(@RequestParam MultipartFile uploadData, ModelMap model, HttpSession session)
    {
        companyService.upload(uploadData);
        return redirectList(model, session, "redirect:/company/list.do");
    }
}
