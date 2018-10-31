package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.CompanySearcher;
import com.todaysoft.lims.reagent.entity.Area;
import com.todaysoft.lims.reagent.entity.Company;
import com.todaysoft.lims.reagent.service.ICompanyService;

@RestController
@RequestMapping("/bsm/company")
public class CompanyController
{
    
    @Autowired
    private ICompanyService companyService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<Company> paging(@RequestBody CompanySearcher request)
    {
        return companyService.paging(request);
    }
    
    @RequestMapping(value = "/getCompanys", method = RequestMethod.POST)
    public List<Company> getCompanys(@RequestBody CompanySearcher request)
    {
        return companyService.getCompanys(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Company get(@PathVariable String id)
    {
        return companyService.get(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody Company request)
    {
        return companyService.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody Company request)
    {
        companyService.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        companyService.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody CompanySearcher request)
    {
        return companyService.validate(request);
    }
    
    @RequestMapping("/selectCompany")
    public Pagination<Company> selectCompany(@RequestBody CompanySearcher request)
    {
        return companyService.selectCompany(request);
    }
    
    @RequestMapping(value = "/countCustomers/{id}", method = RequestMethod.GET)
    public Long countCustomers(@PathVariable String id)
    {
        return companyService.countCustomers(id);
    }
    
    @RequestMapping("/getAreas")
    public List<Area> getAreas(@RequestBody Area request)
    {
        return companyService.getAreas(request);
    }
    
    @RequestMapping(value = "/getAreaById/{areaId}", method = RequestMethod.GET)
    public Area getAreaById(@PathVariable String areaId)
    {
        return companyService.getAreaById(areaId);
    }
    
    @RequestMapping(value = "/getAreaBycolColumn", method = RequestMethod.POST)
    public Company getAreaBycolColumn(@RequestBody Company request)
    {
        return companyService.getAreaBycolColumn(request);
    }
}
