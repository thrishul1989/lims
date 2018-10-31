package com.todaysoft.lims.reagent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.CompanySearcher;
import com.todaysoft.lims.reagent.entity.Area;
import com.todaysoft.lims.reagent.entity.Company;
import com.todaysoft.lims.reagent.entity.CompanyAlias;
import com.todaysoft.lims.reagent.exception.ServiceException;
import com.todaysoft.lims.reagent.service.ICompanyService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class CompanyService implements ICompanyService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Company> paging(CompanySearcher request)
    {
        
        Pagination<Company> paginations = baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), Company.class);
        if (paginations != null)
        {
            for (Company c : paginations.getRecords())
            {
                c.setOtherName(getOtherName(c.getId()));
            }
        }
        return paginations;
    }
    
    @Override
    public List<Company> getCompanys(CompanySearcher request)
    
    {
        request.setName(null == request.getName() ? "" : request.getName());
        return baseDaoSupport.find(Company.class, "from Company c where c.name like '%" + request.getName() + "%' and c.deleted = false");
    }
    
    @Override
    public Company get(String id)
    {
        Company company = baseDaoSupport.get(Company.class, id);
        company.setOtherName(getOtherName(id));
        return company;
    }
    
    @Override
    @Transactional
    public String create(Company request)
    {
        if (StringUtils.isEmpty(request.getName()))
        {
            // TODO: 错误码设置
            throw new ServiceException("名称不能为空");
        }
        request.setDeleted(false);
        baseDaoSupport.insert(request);
        
        List<String> list = getOtherNames(request.getOtherName());
        for (String string : list)
        {
            CompanyAlias companyAlias = new CompanyAlias();
            companyAlias.setCompanyId(request.getId());
            companyAlias.setOtherName(string);
            baseDaoSupport.insert(companyAlias);
        }
        return request.getId();
    }
    
    @Override
    @Transactional
    public void modify(Company request)
    {
        
        List<CompanyAlias> CompanyAlias = getCompanyAlias(request.getId());
        for (CompanyAlias companyAlias2 : CompanyAlias)
        {
            baseDaoSupport.delete(companyAlias2);
        }
        List<String> list = getOtherNames(request.getOtherName());
        for (String string : list)
        {
            CompanyAlias companyAlias = new CompanyAlias();
            companyAlias.setCompanyId(request.getId());
            companyAlias.setOtherName(string);
            baseDaoSupport.insert(companyAlias);
        }
        baseDaoSupport.update(request);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        Company entity = get(id);
        entity.setDeleted(true);
        baseDaoSupport.update(entity);
        List<CompanyAlias> companyAlias = getCompanyAlias(id);
        if (Collections3.isNotEmpty(companyAlias))
        {
            for (CompanyAlias companyAlias2 : companyAlias)
            {
                baseDaoSupport.delete(companyAlias2);
            }
        }
        
    }
    
    @Override
    public boolean validate(CompanySearcher request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Company.class, request, "code")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public Pagination<Company> selectCompany(CompanySearcher request)
    {
        String hql = "FROM Company u WHERE (u.name LIKE :name or u.otherName LIKE :names)";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Lists.newArrayList("name", "names"))
                .values(Lists.newArrayList((Object)("%" + request.getName() + "%"), (Object)("%" + request.getName() + "%")))
                .build();
        Pagination<Company> pageCompany = baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), Company.class);
        
        return pageCompany;
    }
    
    public List<CompanyAlias> getCompanyAlias(String companyId)
    {
        
        String hql = "FROM CompanyAlias c WHERE c.companyId = :companyId";
        return baseDaoSupport.findByNamedParam(CompanyAlias.class, hql, new String[] {"companyId"}, new Object[] {companyId});
    }
    
    @Override
    public Long countCustomers(String companyId)
    {
        String sql = "select count(*) from Customer c where c.company.id =? and c.delFlag = 0 ";
        return baseDaoSupport.count(sql, new String[] {companyId});
    }
    
    public List<String> getOtherNames(String otherNames)
    {
        List<String> list = Lists.newArrayList();
        if (otherNames.contains(","))
        {
            String[] strings = otherNames.split(",");
            for (String string : strings)
            {
                list.add(string);
            }
        }
        else if (otherNames.contains("，"))
        {
            String[] strings = otherNames.split("，");
            for (String string : strings)
            {
                list.add(string);
            }
        }
        else
        {
            
            list.add(otherNames);
        }
        return list;
    }
    
    @Override
    public List<Area> getAreas(Area request)
    {
        String hql = null;
        List<Area> list = Lists.newArrayList();
        if (StringUtils.isEmpty(request.getParentId()))
        {
            hql = "FROM Area a WHERE a.parentId = null";
            list = baseDaoSupport.find(Area.class, hql);
        }
        else
        {
            hql = "FROM Area a WHERE a.parentId = :parenntId";
            list = baseDaoSupport.findByNamedParam(Area.class, hql, new String[] {"parenntId"}, new Object[] {request.getParentId()});
        }
        return list;
    }
    
    @Override
    public Area getAreaById(String areaId)
    {
        return baseDaoSupport.get(Area.class, areaId);
    }
    
    @Override
    public Company getAreaBycolColumn(Company request)
    {
        String hql = "FROM Area a WHERE a.name = :name";
        List<Area> list = Lists.newArrayList();
        if (!StringUtils.isEmpty(request.getProvince()))
        {
            list = baseDaoSupport.findByNamedParam(Area.class, hql, new String[] {"name"}, new Object[] {request.getProvince()});
            Area a = Collections3.isNotEmpty(list) ? list.get(0) : null;
            if (null != a)
                request.setProvince(a);
        }
        if (!StringUtils.isEmpty(request.getCity()))
        {
            list = baseDaoSupport.findByNamedParam(Area.class, hql, new String[] {"name"}, new Object[] {request.getCity()});
            Area a = Collections3.isNotEmpty(list) ? list.get(0) : null;
            if (null != a)
                request.setCity(a);
        }
        if (!StringUtils.isEmpty(request.getCounty()))
        {
            list = baseDaoSupport.findByNamedParam(Area.class, hql, new String[] {"name"}, new Object[] {request.getCounty()});
            Area a = Collections3.isNotEmpty(list) ? list.get(0) : null;
            if (null != a)
                request.setCounty(a);
        }
        return request;
    }
    
    public String getOtherName(String id)
    {
        List<CompanyAlias> companyAlias = getCompanyAlias(id);
        String str = "";
        String otherName = "";
        if (Collections3.isNotEmpty(companyAlias))
        {
            for (CompanyAlias companyAlias2 : companyAlias)
            {
                str += companyAlias2.getOtherName() + ",";
            }
            if (str.endsWith(","))
            {
                otherName = str.substring(0, str.lastIndexOf(","));
            }
        }
        return otherName;
    }
}
