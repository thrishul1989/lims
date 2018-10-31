package com.todaysoft.lims.system.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.vo.Firm;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.IFirmService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/firm")
public class FirmController extends BaseController
{
    
    @Autowired
    private IFirmService firmService;
    
    /**
     * 客户单位数据
     * */
    @RequestMapping(value = "/list.do")
    public String getDeviceList(Firm searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Pagination<Firm> pagination = firmService.paging(searcher, pageNo, 10);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "firm/firm_list";
        
    }
    
    /**
     * 新增单位page
     * */
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    public String create(ModelMap model)
    {
        Firm data = new Firm();
        
        model.addAttribute("firm", data);
        model.addAttribute("flag", "新增");
        
        return "firm/firm_form";
    }
    
    /**
     * 修改探单位page
     * */
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    public String modify(Integer id, ModelMap model)
    {
        Firm data = firmService.get(id);
        
        model.addAttribute("firm", data);
        model.addAttribute("flag", "修改");
        return "firm/firm_form";
    }
    
    /**
     * 修改 单位
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/firmModify.do", method = RequestMethod.POST)
    public String modifyFirm(Firm request, ModelMap model, HttpSession session)
    {
        if (request.getId() == null)
        {
            request.setState("0");
            firmService.create(request);
        }
        else
        {
            firmService.modify(request);
        }
        
        return redirectList(model, session, "redirect:/firm/list.do");
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
    public String delete(Integer id, ModelMap model, HttpSession session)
    {
        firmService.delete(id);
        return redirectList(model, session, "redirect:/firm/list.do");
    }
    
    /**
     * 唯一性校验
     */
    @ResponseBody
    @RequestMapping("/validate.do")
    public boolean validate(Firm search)
    {
        return firmService.validate(search);
        
    }
    
    /**
     *启用禁用
     */
    
    @RequestMapping(value = "/enableFirm.do", method = RequestMethod.GET)
    public String enableFirm(Firm request, ModelMap model, HttpSession session)
    {
        
        firmService.enableFirm(request);
        
        return redirectList(model, session, "redirect:/firm/list.do");
    }
    
    /**
     * 模糊匹配单位
     * */
    @RequestMapping(value = "/firmSelect.do", method = RequestMethod.GET)
    @ResponseBody
    public Map containerSelect(Firm key)
    {
        Map map = new HashMap<>();
        map.put("message", "");
        
        Pagination<Firm> containers = firmService.selectFirm(key, 1, 10);
        for (Firm firm : containers.getRecords())
        {
            if (StringUtils.isNotEmpty(firm.getOtherName()))
            {
                
                firm.setName(firm.getName() + "(" + firm.getOtherName() + ")");
            }
            else
            {
                firm.setName(firm.getName());
            }
        }
        
        map.put("value", containers.getRecords());
        
        return map;
    }
    
}
