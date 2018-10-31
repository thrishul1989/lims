package com.todaysoft.lims.smm.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.UserGroup;
import com.todaysoft.lims.smm.request.UserGroupCreateRequest;
import com.todaysoft.lims.smm.request.UserGroupModifyRequest;
import com.todaysoft.lims.smm.request.UserGroupPagingRequest;
import com.todaysoft.lims.smm.service.IUserGroupService;

@RestController
@RequestMapping("/smm/userGroup")
public class UserGroupController
{
    @Autowired
    private IUserGroupService service;
    
    @RequestMapping("/paging")
    public Pagination<UserGroup> paging(@RequestBody UserGroupPagingRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody UserGroupCreateRequest request)
    {
        service.create(request);
    }
    
    @RequestMapping("modify")
    public void modify(@RequestBody UserGroupModifyRequest request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserGroup getById(@PathVariable String id)
    {
        return service.getById(id);
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    //获取布尔值改用get
    public boolean delete(@PathVariable String id)
    {
        return service.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validata(@RequestBody UserGroupModifyRequest request)
    {
        return service.getByName(request.getGroupName());
    }
}
