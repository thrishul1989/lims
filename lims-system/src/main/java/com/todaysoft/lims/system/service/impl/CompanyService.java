package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.model.searcher.CompanySearcher;
import com.todaysoft.lims.system.model.vo.Area;
import com.todaysoft.lims.system.model.vo.Company;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.ICompanyService;
import com.todaysoft.lims.system.util.Pinyin4jUtil;
import com.todaysoft.lims.utils.excel.ImportExcel;

@Service
public class CompanyService extends RestService implements ICompanyService
{
    
    @Override
    public Pagination<Company> paging(CompanySearcher searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/company/paging");
        ResponseEntity<Pagination<Company>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CompanySearcher>(searcher), new ParameterizedTypeReference<Pagination<Company>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void modify(Company request)
    {
        
        String url = GatewayService.getServiceUrl("/bsm/company/modify");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public Company get(String id)
    {
        // TODO Auto-generated method stub
        String url = GatewayService.getServiceUrl("/bsm/company/{id}");
        return template.getForObject(url, Company.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/company/{id}");
        template.delete(url, Collections.singletonMap("id", id));
        
    }
    
    @Override
    public String create(Company request)
    {
        String url = GatewayService.getServiceUrl("/bsm/company/create");
        return template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public boolean validate(CompanySearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bsm/company/validate");
        return template.postForObject(url, searcher, boolean.class);
    }
    
    @Override
    public Pagination<Company> selectCompany(CompanySearcher entity, int pageNo, int pageSize)
    {
        entity.setPageNo(pageNo);
        entity.setPageSize(pageSize);
        
        return exchange("/company/selectCompany", entity, new ParameterizedTypeReference<Pagination<Company>>()
        {
        });
    }
    
    @Override
    public List<Company> getCompanys(CompanySearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bsm/company/getCompanys");
        ResponseEntity<List<Company>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<CompanySearcher>(searcher), new ParameterizedTypeReference<List<Company>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Long countCustomers(String id)
    {
        // TODO Auto-generated method stub
        String url = GatewayService.getServiceUrl("/bsm/company/countCustomers/{id}");
        return template.getForObject(url, Long.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public List<Area> getAreas(Area area)
    {
        String url = GatewayService.getServiceUrl("/bsm/company/getAreas");
        ResponseEntity<List<Area>> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<Area>(area), new ParameterizedTypeReference<List<Area>>()
        {
        });
        return exchange.getBody();
    }
    
    @Override
    public Area getAreaById(String areaId)
    {
        String url = GatewayService.getServiceUrl("/bsm/company/getAreaById/{areaId}");
        return template.getForObject(url, Area.class, Collections.singletonMap("areaId", areaId));
    }
    
    @Override
    public void upload(MultipartFile uploadData)
    {
        List<Company> companys = Lists.newArrayList();
        try
        {
            ImportExcel ei = new ImportExcel(uploadData, 0, 0);
            List<Company> list = ei.getDataList(Company.class);
            for (Company company : list)
            {
                Company c = getAreaBycolColumn(company);
                c.setCreateTime(new Date());
                c.setCreator("9048b78e49ed4847b5c52fc0a004d1ad");
                c.setDeleted(false);
                c.setPinyin(Pinyin4jUtil.getPinYin(c.getName()));
                c.setPy(Pinyin4jUtil.getPinYinHeadChar(c.getName()));
                c.setUpdateTime(new Date());
                companys.add(c);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for (Company c : companys)
        {
            create(c);
        }
    }
    
    @Override
    public Company getAreaBycolColumn(Company request)
    {
        String url = GatewayService.getServiceUrl("/bsm/company/getAreaBycolColumn");
        return template.postForObject(url, request, Company.class);
    }
}
