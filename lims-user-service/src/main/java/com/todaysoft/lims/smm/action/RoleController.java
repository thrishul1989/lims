package com.todaysoft.lims.smm.action;

import java.util.List;

import com.todaysoft.lims.smm.dao.searcher.RoleSearcher;
import com.todaysoft.lims.smm.entity.Role;
import com.todaysoft.lims.smm.response.UserSimpleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.RoleTemplate;
import com.todaysoft.lims.smm.request.RoleCreateRequest;
import com.todaysoft.lims.smm.request.RoleListRequest;
import com.todaysoft.lims.smm.request.RoleModifyRequest;
import com.todaysoft.lims.smm.response.RoleDetailsModel;
import com.todaysoft.lims.smm.response.RoleSimpleModel;
import com.todaysoft.lims.smm.service.IRoleService;

@RestController
@RequestMapping("/smm/role")
public class RoleController
{
    @Autowired
    private IRoleService service;
    
    @RequestMapping(value = "/paging")
    public Pagination<RoleSimpleModel> paging(@RequestBody RoleListRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping(value = "/list")
    public List<RoleSimpleModel> list()
    {
        return service.list();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RoleDetailsModel getRoleById(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/create")
    public String create(@RequestBody RoleCreateRequest request)
    {
        return service.create(request);
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody RoleSearcher request){
        return service.validate(request);
    }
    
    @RequestMapping(value = "/modify")
    public void modify(@RequestBody RoleModifyRequest request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        service.delete(id);
    }
    
    @RequestMapping(value = "/roleSelect")
    public List<RoleTemplate> roleSelect(@RequestBody RoleListRequest request)
    {
        return service.roleSelect(request);
    }

    @RequestMapping(value = "/getUserListByRole")
    public List<UserSimpleModel> getUserListByRole(@RequestBody Role role)
    {
        String id = role.getId();
        return service.getUserListByRole(id);
    }

    @RequestMapping(value = "/getRoleByName", method = RequestMethod.POST)
    public RoleSimpleModel getRoleByName(@RequestBody RoleSearcher searcher)
    {
        return  service.getRoleByName(searcher);
    }

}
