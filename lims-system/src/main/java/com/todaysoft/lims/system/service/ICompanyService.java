package com.todaysoft.lims.system.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.model.searcher.CompanySearcher;
import com.todaysoft.lims.system.model.vo.Area;
import com.todaysoft.lims.system.model.vo.Company;
import com.todaysoft.lims.system.model.vo.Pagination;

public interface ICompanyService
{
    Pagination<Company> paging(CompanySearcher searcher, int pageNo, int pageSize);
    
    List<Company> getCompanys(CompanySearcher searcher);
    
    void modify(Company request);
    
    Company get(String id);
    
    void delete(String id);
    
    String create(Company request);
    
    boolean validate(CompanySearcher searcher);
    
    Pagination<Company> selectCompany(CompanySearcher searcher, int pageNo, int pageSize);
    
    Long countCustomers(String id);
    
    List<Area> getAreas(Area area);
    
    Area getAreaById(String areaId);
    
    void upload(MultipartFile uploadData);
    
    Company getAreaBycolColumn(Company request);
}
