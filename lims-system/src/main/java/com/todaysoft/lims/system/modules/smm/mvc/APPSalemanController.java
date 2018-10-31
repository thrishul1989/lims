package com.todaysoft.lims.system.modules.smm.mvc;

import com.alibaba.fastjson.JSONObject;

import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.TestingType;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderFormRequest;
import com.todaysoft.lims.system.model.vo.order.OrderSearchRequest;
import com.todaysoft.lims.system.modules.smm.model.APPSaleman;
import com.todaysoft.lims.system.modules.smm.model.APPSalemanSearcher;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.model.UserSimpleModel;
import com.todaysoft.lims.system.modules.smm.service.IAPPSalemanService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.system.mvc.ModelResolver;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.ITestingTypeService;
import com.todaysoft.lims.utils.Collections3;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/smm/appsaleman")
public class APPSalemanController extends BaseController
{
    @Autowired
    private IAPPSalemanService service;
    
    @Autowired
    private ITestingTypeService testingTypeService;
    
    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/list.do")
    public String list(APPSalemanSearcher searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, ModelMap model, HttpSession session)
    {
        Pagination<BusinessInfo> pagination = service.paging(searcher, pageNo, 10);
        for (BusinessInfo b : pagination.getRecords())
        {
            if (!StringUtils.isEmpty(b.getTestingType()))
            {
                b.setTestingTypeName(testingTypeService.get(b.getTestingType()).getName());
            }
            //列表展示项目管理人姓名
            if (!StringUtils.isEmpty(b.getProjectManager()))
            {
                UserDetailsModel user  = userService.getUserByID(b.getProjectManager());
                b.setProjectManagerName(user.getArchive().getName());
            }
        }

        List<TestingType> testingTypes = testingTypeService.testingTypeList();
        model.addAttribute("testingTypes", testingTypes);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "smm/appsaleman/appsaleman_list";
    }
    
    @RequestMapping("/modify.do")
    @FormSubmitHandler
    public String modify(APPSaleman entity, ModelMap model, HttpSession session)
    {
        APPSaleman appSaleman = service.get(entity.getUserId());//原有项目管理人
        //之前已有项目管理人，并且进行修改了（修改之后，该业务员的所有订单都跟着业务员整体迁移到新的项目管理人）
        if (!StringUtils.isEmpty(appSaleman.getProjectManager())&&(!(appSaleman.getProjectManager()).equals(entity.getProjectManager())))
        {
            //查询修改项目管理人之前该业务员下的所有订单列表
            OrderSearchRequest searcher = new OrderSearchRequest();
            searcher.setCreatorId(appSaleman.getUserId());
            List<Order> orderList =  orderService.getOrderByAppSaleman(searcher);
            if (Collections3.isNotEmpty(orderList))
            {
                for (Order order: orderList )
                {
                    if (!StringUtils.isEmpty(order.getProjectManager()))
                    {

                        if(order.getProjectManager().equals(appSaleman.getProjectManager()))//订单的管理人为业务员之前默认的管理人
                        {
                            order.setProjectManager(entity.getProjectManager());//只更改订单业务员默认的项目管理人
                            OrderFormRequest request = new OrderFormRequest();
                            BeanUtils.copyProperties(order,request);
                            orderService.updateOrder(request);
                        }
                    }
                }
            }
        }
        service.modify(entity);
        return redirectList(model, session);
    }
    
    @RequestMapping("/create.do")
    @FormSubmitHandler
    public String create(APPSaleman entity, ModelMap model, HttpSession session)
    {
        service.create(entity);
        return redirectList(model, session);
    }
    
    @RequestMapping("/view.do")
    public String view(String id, ModelMap model)
    {
        BusinessInfo data = service.getBusinessInfo(id);
        data.setUser(userService.getUserByID(data.getId()));
        data.setTestingTypeName(testingTypeService.get(data.getTestingType()).getName());
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(data.getParentId()))
        {
            data.setParentName(userService.getUserByID(data.getParentId()).getArchive().getName());
        }
        if (!StringUtils.isEmpty(data.getProjectManager()))
        {
            data.setProjectManagerName(userService.getUserByID(data.getProjectManager()).getArchive().getName());
        }
        model.addAttribute("businessInfo", data);
        return "smm/appsaleman/appsaleman_view";
    }
    
    @RequestMapping("/form.do")
    @FormInputView
    public String form(String id, ModelMap model)
    {
        APPSaleman entity = null;
        if (!StringUtils.isEmpty(id))
        {
            entity = service.get(id);
            UserDetailsModel user = userService.getUserByID(id);
            UserSimpleModel usm = new UserSimpleModel();
            usm.setName(user.getArchive().getName());
            usm.setPhone(user.getArchive().getPhone());
            usm.setUsername(user.getUsername());
            usm.setId(user.getId());
            usm.setSex(user.getArchive().getGender());
            usm.setLeaderId(user.getArchive().getLeaderId());
            
            entity.setUser(user);
            model.addAttribute("appsaleman", entity);
            model.addAttribute("usm", JSONObject.toJSON(usm).toString());

            //回显选中项目管理人
            if (!StringUtils.isEmpty(entity.getProjectManager()))
            {
                UserDetailsModel prjManagerUser = userService.getUserByID(entity.getProjectManager());
                UserSimpleModel pmu = new UserSimpleModel();
                pmu.setId(prjManagerUser.getId());
                pmu.setName(prjManagerUser.getArchive().getName());
                pmu.setPhone(prjManagerUser.getArchive().getPhone());
                model.addAttribute("pmu", JSONObject.toJSON(pmu).toString());
            }
        }

        List<TestingType> testingTypes = testingTypeService.testingTypeList();
        model.addAttribute("testingTypes", testingTypes);
        return "smm/appsaleman/appsaleman_form";
    }

    /**
     * 批量修改业务员的项目管理人
     *
     * @param keys
     * @param model
     * @return
     */
    @RequestMapping("/prjManagerChange.do")
    @FormInputView
    public String prjManagerChange(String keys, String projectManagerUser,ModelMap model, HttpSession session)
    {
        List<APPSaleman> appSalemens = service.getAppSaleByids(keys);//通过业务员id（多个以逗号分隔）获取业务员列表
        if (Collections3.isNotEmpty(appSalemens))
        {
            for (APPSaleman a : appSalemens)
            {
                //之前已有项目管理人，并且进行修改了（修改之后，该业务员的订单都跟着业务员迁移到新的项目管理人，只改默认的）
                if (!StringUtils.isEmpty(a.getProjectManager())&&(!(a.getProjectManager()).equals(projectManagerUser)))
                {
                    //查询修改项目管理人之前该业务员下的所有订单列表（待检测下放，2-检测中 ，3-已暂停 状态下的订单）
                    OrderSearchRequest searcher = new OrderSearchRequest();
                    searcher.setCreatorId(a.getUserId());
                    List<Order> orderList =  orderService.getOrderByAppSaleman(searcher);
                    if (Collections3.isNotEmpty(orderList))
                    {
                        for (Order order: orderList )
                        {
                            if (!StringUtils.isEmpty(order.getProjectManager()))
                            {

                                if(order.getProjectManager().equals(a.getProjectManager()))//订单的管理人为业务员之前默认的管理人
                                {
                                    order.setProjectManager(projectManagerUser);//只更改订单业务员默认的项目管理人
                                    OrderFormRequest request = new OrderFormRequest();
                                    BeanUtils.copyProperties(order,request);
                                    orderService.updateOrder(request);
                                }
                            }
                        }
                    }

                }
                a.setProjectManager(projectManagerUser);
                service.modify(a);
            }
        }
        return redirectList(model, session);
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
        return redirectList(model, session);
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/smm/appsaleman/list.do";
    }
    
    @RequestMapping("/getDataById")
    @ResponseBody
    public boolean getDataById(String userId)
    {
        return service.getDataById(userId);
        
    }
    
    @RequestMapping("/getCustomerByAppId")
    @ResponseBody
    public boolean getCustomerByAppId(String appId)
    {
        return service.getCustomerByAppId(appId);
    }
    
    @RequestMapping("/getTestingType")
    @ResponseBody
    public String getTestingType(String id)
    {
        BusinessInfo b = service.getBusinessInfo(id);
        return null == b ? null : b.getTestingType();
    }
    
    @RequestMapping("/getBusinessInfo")
    @ResponseBody
    public boolean getBusinessInfo(String id)
    {
        return null != service.getBusinessInfo(id);
        
    }
   //获取业务员对应的项目管理人
    @RequestMapping("/getPrjManager")
    @ResponseBody
    public UserSimpleModel getPrjManager(String id)
    {
        APPSaleman appSaleman = service.get(id);
        UserDetailsModel udm = null;
        UserSimpleModel usm  = new UserSimpleModel();
        if (null!=appSaleman)
        {
            if (appSaleman.getProjectManager()!=null && appSaleman.getProjectManager()!="")
            {
                udm = userService.getUserByID(appSaleman.getProjectManager());
                usm  = new UserSimpleModel();
                usm.setId(udm.getId());
                usm.setName(udm.getArchive().getName());
                usm.setPhone(udm.getArchive().getPhone());
            }
        }
        return usm;
    }
}
