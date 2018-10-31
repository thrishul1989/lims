package com.todaysoft.lims.smm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.Customer;
import com.todaysoft.lims.smm.request.UserCreateRequest;
import com.todaysoft.lims.smm.request.UserListRequest;
import com.todaysoft.lims.smm.request.UserModifyRequest;
import com.todaysoft.lims.smm.request.UserPasswordModifyRequest;
import com.todaysoft.lims.smm.request.UserPasswordResetRequest;
import com.todaysoft.lims.smm.response.UserDetailsModel;
import com.todaysoft.lims.smm.response.UserMinimalModel;
import com.todaysoft.lims.smm.response.UserSimpleModel;
import com.todaysoft.lims.smm.service.IUserService;

@RestController
@RequestMapping("/smm/user")
public class UserController
{
    @Autowired
    private IUserService service;
    
    @RequestMapping("/paging")
    public Pagination<UserSimpleModel> paging(@RequestBody UserListRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping("/list")
    public List<UserSimpleModel> list(@RequestBody UserListRequest request)
    {
        return service.list(request);
    }
    
    @RequestMapping("/no_salesman_list")
    public List<UserSimpleModel> noSalesmanList(@RequestBody UserListRequest request)
    {
        return service.noSalesmanList(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDetailsModel getUserByID(@PathVariable String id)
    {
        UserDetailsModel model = service.getUserByID(id);
        return model;
    }
    
    @RequestMapping(value = "/{id}/minimal", method = RequestMethod.GET)
    public UserMinimalModel getMinimalUserByID(@PathVariable String id)
    {
        return service.getMinimalUserByID(id);
    }
    
    @RequestMapping("/unique")
    public boolean unique(@RequestBody UserListRequest request)
    {
        return service.unique(request.getUsername());
    }
    
    @RequestMapping("/create")
    public synchronized String create(@RequestBody UserCreateRequest request)
    {
        String id = service.create(request);
        return String.valueOf(id);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody UserModifyRequest request)
    {
        service.modify(request);
    }
    
    @RequestMapping("/enable/{id}")
    public void setEnable(@PathVariable String id)
    {
        service.enable(id);
    }
    
    @RequestMapping("/disable/{id}")
    public void setDisable(@PathVariable String id)
    {
        service.disable(id);
    }
    
    @RequestMapping("/lock/{id}")
    public void setLock(@PathVariable String id)
    {
        service.lock(id);
    }
    
    @RequestMapping("/password/reset")
    public void resetPassword(@RequestBody UserPasswordResetRequest request)
    {
        service.resetPassword(request);
    }
    
    @RequestMapping("/password/modify")
    public void modifyPassword(@RequestBody UserPasswordModifyRequest request)
    {
        service.modifyPassword(request);
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Integer detele(@PathVariable String id)
    {
        return service.delete(id);
    }
    
    @RequestMapping("/contact_users")
    public List<UserDetailsModel> getContactUsers(@RequestBody List<Integer> userIds)
    {
        return service.getContactUsers(userIds);
    }
    
    @RequestMapping("/userSelect")
    public List<UserSimpleModel> userSelect(@RequestBody UserListRequest request)
    {
        return service.userSelect(request);
    }
    
    @RequestMapping("/validate")
    public boolean validate(@RequestBody UserListRequest request)
    {
        return service.validate(request);
    }
    
    @RequestMapping("/selectList")
    public List<UserSimpleModel> selectList(@RequestBody UserListRequest request)
    {
        return service.userSelectForTest(request);
    }
    
    @RequestMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable String id)
    {
        return service.getCustomer(id);
    }
    
    @RequestMapping(value = "/validate/{id}", method = RequestMethod.GET)
    public boolean validate(@PathVariable String id)
    {
        return service.validate(id);
    }
    
    @RequestMapping(value = "/uniqueNum", method = RequestMethod.POST)
    public boolean uniqueNum(@RequestBody UserListRequest request)
    {
        return service.uniqueNum(request.getArchive().getEmployeeNo(), request.getId());
    }
}
