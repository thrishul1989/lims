package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.CompanySearcher;
import com.todaysoft.lims.reagent.entity.Area;
import com.todaysoft.lims.reagent.entity.Company;

public interface ICompanyService
{
    
    Pagination<Company> paging(CompanySearcher request);
    
    List<Company> getCompanys(CompanySearcher request);
    
    Company get(String id);
    
    String create(Company request);
    
    void modify(Company request);
    
    void delete(String id);
    
    boolean validate(CompanySearcher request);
    
    Pagination<Company> selectCompany(CompanySearcher request);
    
    Long countCustomers(String id);
    
    List<Area> getAreas(Area request);
    
    Area getAreaById(String areaId);
    
    Company getAreaBycolColumn(Company request);
    
}
