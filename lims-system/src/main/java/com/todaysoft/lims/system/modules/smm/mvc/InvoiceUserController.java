package com.todaysoft.lims.system.modules.smm.mvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.form.FormInputView;
import com.todaysoft.lims.system.form.FormSubmitHandler;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUser;
import com.todaysoft.lims.system.modules.smm.model.InvoiceUserModel;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.service.IInvoiceUserService;
import com.todaysoft.lims.system.modules.smm.service.IUserService;
import com.todaysoft.lims.system.mvc.BaseController;
import com.todaysoft.lims.utils.StringUtils;

@Controller
@RequestMapping("/smm/invoiceUser")
public class InvoiceUserController extends BaseController
{
    
    @Autowired
    private IInvoiceUserService service;
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping(value = "/list.do")
    public String paging(InvoiceUser searcher, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, ModelMap model, HttpSession session)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        Pagination<InvoiceUser> pagination = service.paging(searcher);
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-pageNo", pageNo);
        session.setAttribute("s-searcher", searcher);
        return "smm/invoiceUser/invoiceUser_list";
    }
    
    @RequestMapping(value = "/view.do")
    public String view(String id, ModelMap model)
    {
        InvoiceUser data = service.get(id);
        model.addAttribute("data", data);
        return "smm/invoiceUser/invoiceUser_view";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.GET)
    @FormInputView
    public String createForward(ModelMap model)
    {
        return "smm/invoiceUser/invoiceUser_form";
    }
    
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(InvoiceUser request, ModelMap model, HttpSession session)
    {
        List<String> ids = Arrays.asList(request.getInvoiceUserIds().split(","));
        List<InvoiceUserModel> userList = Lists.newArrayList();
        for (String id : ids)
        {
            if (StringUtils.isNotEmpty(id))
            {
                UserDetailsModel userModel = userService.getUserByID(id);
                User user = new User();
                BeanUtils.copyProperties(userModel, user);
                InvoiceUserModel invoiceUserModel = new InvoiceUserModel();
                invoiceUserModel.setId(id);
                invoiceUserModel.setUser(user);
                invoiceUserModel.setName(user.getArchive().getName());
                userList.add(invoiceUserModel);
            }
        }
        request.setUserList(userList);
        service.create(request);
        return redirectList(model, session, "redirect:/smm/invoiceUser/list.do");
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.GET)
    @FormInputView
    public String modifyForward(String id, ModelMap model)
    {
        InvoiceUser data = service.get(id);
        Map<String, List<InvoiceUserModel>> map = new HashMap<String, List<InvoiceUserModel>>();
        for (InvoiceUserModel userModel : data.getUserList())
        {
            List<InvoiceUserModel> user = Lists.newArrayList();
            user.add(userModel);
            map.put(userModel.getId(), user);
        }
        
        model.addAttribute("data", data);
        model.addAttribute("map", JSONObject.toJSON(map).toString());
        return "smm/invoiceUser/invoiceUser_form";
    }
    
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(InvoiceUser request, ModelMap model, HttpSession session)
    {
        List<String> ids = Arrays.asList(request.getInvoiceUserIds().split(","));
        List<InvoiceUserModel> userList = Lists.newArrayList();
        for (String id : ids)
        {
            if (StringUtils.isNotEmpty(id))
            {
                UserDetailsModel userModel = userService.getUserByID(id);
                User user = new User();
                BeanUtils.copyProperties(userModel, user);
                InvoiceUserModel invoiceUserModel = new InvoiceUserModel();
                invoiceUserModel.setId(id);
                invoiceUserModel.setUser(user);
                invoiceUserModel.setName(user.getArchive().getName());
                userList.add(invoiceUserModel);
            }
        }
        request.setUserList(userList);
        service.modify(request);
        return redirectList(model, session, "redirect:/smm/invoiceUser/list.do");
    }
    
    @RequestMapping(value = "/delete.do")
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session, "redirect:/smm/invoiceUser/list.do");
    }
    
    @ResponseBody
    @RequestMapping(value = "/validateUser.do", method = RequestMethod.POST)
    public boolean validateUser(InvoiceUser request)
    {
        List<String> ids = Arrays.asList(request.getInvoiceUserIds().split(","));
        List<InvoiceUserModel> userList = Lists.newArrayList();
        for (String id : ids)
        {
            if (StringUtils.isNotEmpty(id))
            {
                UserDetailsModel userModel = userService.getUserByID(id);
                InvoiceUserModel invoiceUserModel = new InvoiceUserModel();
                invoiceUserModel.setId(id);
                userList.add(invoiceUserModel);
            }
        }
        request.setUserList(userList);
        return service.validateUser(request);
    }
    
    @ResponseBody
    @RequestMapping(value = "/validateInstitution.do", method = RequestMethod.GET)
    public boolean validateInstitution(String testInstitution)
    {
        return service.validateInstitution(testInstitution);
    }
}
