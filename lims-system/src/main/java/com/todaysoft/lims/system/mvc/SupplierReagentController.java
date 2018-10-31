package com.todaysoft.lims.system.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Reagent;
import com.todaysoft.lims.system.model.vo.ReagentKit;
import com.todaysoft.lims.system.model.vo.Supplier;
import com.todaysoft.lims.system.model.vo.SupplierReagent;
import com.todaysoft.lims.system.model.vo.SupplierReagentKit;
import com.todaysoft.lims.system.modules.bsm.service.IReagentKitService;
import com.todaysoft.lims.system.modules.bsm.service.IReagentService;
import com.todaysoft.lims.system.service.ISupplierReagentKitService;
import com.todaysoft.lims.system.service.ISupplierReagentService;
import com.todaysoft.lims.system.service.ISupplierService;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping(value = "/supplierReagent")
public class SupplierReagentController extends BaseController
{
    
    @Autowired
    private ISupplierReagentKitService supplierReagentKitService;
    
    @Autowired
    private ISupplierReagentService supplierReagentService;
    
    @Autowired
    private ISupplierService supplierService;
    
    @Autowired
    private IReagentKitService reagentKitService;
    
    @Autowired
    private IReagentService reagentService;
    
    /**
     * tab信息展示供应商的试剂试剂盒
     * */
    @RequestMapping(value = "/tab.do")
    public String tab(ModelMap model)
    {
        model.addAttribute("searcher", 1);
        return "supplierreagent/tab";
        
    }
    
    /**
     * 提供商供应的试剂盒
     * */
    @RequestMapping(value = "/kitList.do")
    public String supplierReagentList(SupplierReagentKit searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Pagination<SupplierReagentKit> pagination = supplierReagentKitService.paging(searcher, pageNo, 10);
        if (null != searcher.getSupplier())
        {
            if (StringUtils.isNotEmpty(searcher.getSupplier().getId()))
            {
                Supplier supplier = supplierService.getSupplier(searcher.getSupplier().getId());
                searcher.setSupplier(supplier);
            }
            
        }
        if (null != searcher.getReagentKit())
        {
            if (StringUtils.isNotEmpty(searcher.getReagentKit().getId()))
            {
                ReagentKit reagentKit = reagentKitService.getReagentKit(searcher.getReagentKit().getId());
                searcher.setReagentKit(reagentKit);
            }
        }
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "supplierreagent/kit_list";
        
    }
    
    /**
     * 新增供应商的试剂盒
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/createKit.do", method = RequestMethod.GET)
    public String createKit(SupplierReagentKit request, ModelMap model, HttpSession session)
    {
        
        supplierReagentKitService.createKit(request);
        
        return redirectKitList(model, session, "redirect:/supplierReagent/kitList.do");
    }
    
    /**
     * 删除厂商的试剂盒
     * 
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/deleteKit.do")
    public String deleteKit(Integer id, ModelMap model, HttpSession session)
    {
        supplierReagentKitService.delete(id);
        return redirectKitList(model, session, "redirect:/supplierReagent/kitList.do");
    }
    
    /**
     * 更改试剂盒价格
     * */
    @RequestMapping("/updateKitPrice.do")
    public void updateKitPrice(SupplierReagentKit request, ModelMap model, HttpSession session)
    {
        supplierReagentKitService.updateKitPrice(request);
    }
    
    /**
     * 提供商供应的试剂
     * */
    @RequestMapping(value = "/reagentList.do")
    public String reagentList(SupplierReagent searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model)
    {
        Pagination<SupplierReagent> pagination = supplierReagentService.paging(searcher, pageNo, 10);
        if (null != searcher.getSupplier())
        {
            if (StringUtils.isNotEmpty(searcher.getSupplier().getId()))
            {
                Supplier supplier = supplierService.getSupplier(searcher.getSupplier().getId());
                searcher.setSupplier(supplier);
            }
            
        }
        if (null != searcher.getReagent())
        {
            if (StringUtils.isNotEmpty(searcher.getReagent().getId()))
            {
                Reagent reagent = reagentService.get(searcher.getReagent().getId());
                searcher.setReagent(reagent);
            }
        }
        
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        return "supplierreagent/reagent_list";
        
    }
    
    /**
     * 新增供应商的试剂
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/createReagent.do", method = RequestMethod.GET)
    public String createReagent(SupplierReagent request, ModelMap model, HttpSession session)
    {
        
        supplierReagentService.create(request);
        
        return redirectReagentList(model, session, "redirect:/supplierReagent/reagentList.do");
    }
    
    /**
     * 删除厂商的试剂
     * 
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/deleteReagent.do")
    public String deleteReagent(Integer id, ModelMap model, HttpSession session)
    {
        supplierReagentService.delete(id);
        return redirectReagentList(model, session, "redirect:/supplierReagent/reagentList.do");
    }
    
    /**
     * 更改试剂价格
     * */
    @RequestMapping("/updateReagentPrice.do")
    public void updateReagentPrice(SupplierReagent request, ModelMap model, HttpSession session)
    {
        supplierReagentService.updateReagentPrice(request);
    }
    
    /**
     * 保存后刷新
     * 
     * @param model
     * @param session
     * @param reUrl
     * @return
     */
    private String redirectKitList(ModelMap model, HttpSession session, String reUrl)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return reUrl;
    }
    
    /**
     * 保存后刷新
     * 
     * @param model
     * @param session
     * @param reUrl
     * @return
     */
    private String redirectReagentList(ModelMap model, HttpSession session, String reUrl)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return reUrl;
    }
    
    @ResponseBody
    @RequestMapping(value = "validateReagent.do")
    public boolean validate(SupplierReagent reagent)
    {
        
        return supplierReagentService.validate(reagent);
    }
    
    @ResponseBody
    @RequestMapping(value = "validateReagentKit.do")
    public boolean validate(SupplierReagentKit reagent)
    {
        
        return supplierReagentService.validate(reagent);
    }
    
}
