package com.todaysoft.lims.smm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.DataAuthorityRoleSearcher;
import com.todaysoft.lims.smm.dao.searcher.DataAuthoritySearcher;
import com.todaysoft.lims.smm.dao.searcher.DataAuthorityUserSearcher;
import com.todaysoft.lims.smm.entity.DataAuthority;
import com.todaysoft.lims.smm.entity.DataAuthorityRole;
import com.todaysoft.lims.smm.entity.DataAuthorityUser;
import com.todaysoft.lims.smm.service.IDataAuthorityService;

@RestController
@RequestMapping("/smm/dataAuthority")
public class DataAuthorityController
{
    
    @Autowired
    private IDataAuthorityService dataAuthotiryService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<DataAuthority> paging(@RequestBody DataAuthoritySearcher request)
    {
        return dataAuthotiryService.paging(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<DataAuthority> list(@RequestBody DataAuthoritySearcher request)
    {
        return dataAuthotiryService.list(request);
    }
    
    @RequestMapping(value = "/dataAuthorityRolePaging", method = RequestMethod.POST)
    public Pagination<DataAuthorityRole> dataAuthorityRolePaging(@RequestBody DataAuthorityRoleSearcher request)
    {
        return dataAuthotiryService.dataAuthorityRolePaging(request);
    }
    
    @RequestMapping(value = "/dataAuthorityRoleByRoleId", method = RequestMethod.POST)
    public List<DataAuthorityRole> dataAuthorityRoleByRoleId(@RequestBody DataAuthorityRoleSearcher request)
    {
        return dataAuthotiryService.dataAuthorityRoleByRoleId(request);
    }
    
    @RequestMapping(value = "/dataAuthorityUserByUserId", method = RequestMethod.POST)
    public List<DataAuthorityUser> dataAuthorityUserByUserId(@RequestBody DataAuthorityUserSearcher request)
    {
        return dataAuthotiryService.dataAuthorityUserByUserId(request);
    }
    
    @RequestMapping(value = "/createRoleDataAuthority", method = RequestMethod.POST)
    public Integer createRoleDataAuthority(@RequestBody DataAuthorityRole request)
    {
        return dataAuthotiryService.createRoleDataAuthority(request);
    }
    
    @RequestMapping(value = "/deleteRoleAuthority", method = RequestMethod.POST)
    public Integer deleteRoleAuthority(@RequestBody DataAuthorityRole request)
    {
        return dataAuthotiryService.deleteRoleAuthority(request);
    }
    
    @RequestMapping(value = "/dataAuthorityUserPaging", method = RequestMethod.POST)
    public Pagination<DataAuthorityUser> dataAuthorityUserPaging(@RequestBody DataAuthorityUserSearcher request)
    {
        return dataAuthotiryService.dataAuthorityUserPaging(request);
    }
    
    @RequestMapping(value = "/createUserDataAuthority", method = RequestMethod.POST)
    public Integer createUserDataAuthority(@RequestBody DataAuthorityUser request)
    {
        return dataAuthotiryService.createUserDataAuthority(request);
    }
    
    @RequestMapping(value = "/deleteUserAuthority", method = RequestMethod.POST)
    public Integer deleteUserAuthority(@RequestBody DataAuthorityUser request)
    {
        return dataAuthotiryService.deleteUserAuthority(request);
    }
    
    @RequestMapping(value = "/getSubordinates/{leaders}", method = RequestMethod.GET)
    public List<String> getSubordinates(@PathVariable String leaders)
    {
        return dataAuthotiryService.getSubordinates(leaders);
    }
    
}
