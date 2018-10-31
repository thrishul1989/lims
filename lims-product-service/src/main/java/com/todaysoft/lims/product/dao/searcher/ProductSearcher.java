package com.todaysoft.lims.product.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.product.entity.Department;
import com.todaysoft.lims.product.entity.Product;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

public class ProductSearcher extends IDataAuthoritySearcher implements ISearcher<Product>
{
    
    private Product product;
    
    private boolean likeSearch;
    
    private String productDutyName;
    
    private String testingSubtype;//二级分类
    
    private String principalName;//技术负责人
    
    private String productProbe;
    
    public String getProductProbe()
    {
        return productProbe;
    }
    
    public void setProductProbe(String productProbe)
    {
        this.productProbe = productProbe;
    }
    
    public String getTestingSubtype()
    {
        return testingSubtype;
    }
    
    public void setTestingSubtype(String testingSubtype)
    {
        this.testingSubtype = testingSubtype;
    }
    
    public String getPrincipalName()
    {
        return principalName;
    }
    
    public void setPrincipalName(String principalName)
    {
        this.principalName = principalName;
    }
    
    public String getProductDutyName()
    {
        return productDutyName;
    }
    
    public void setProductDutyName(String productDutyName)
    {
        this.productDutyName = productDutyName;
    }
    
    private BaseDaoSupport baseDaoSupport;
    
    public ProductSearcher(BaseDaoSupport baseDaoSupport2)
    {
        this.baseDaoSupport = baseDaoSupport2;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM Product s WHERE 1=1 and s.delFlag = 0");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCodeFilter(hql, paramNames, parameters);
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public NamedQueryer toAuthQuery()//产品数据权限特殊处理，机构名称即产品营销中心名称
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select distinct s FROM Product s left join s.productTestingMethodList p ,TestingType t  WHERE s.testingType = t.id and  1=1 and s.delFlag = 0  ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        
        if (null == dataAuthoritySearcher || Collections3.isEmpty(dataAuthoritySearcher))
        {
            hql.append(" AND (1=2");
        }
        
        else if (null != dataAuthoritySearcher && Collections3.isNotEmpty(dataAuthoritySearcher))
        {
            for (int i = 0; i <= dataAuthoritySearcher.size() - 1; i++)
            {
                List<String> depAndSonNames = new ArrayList<>();
                List<String> depNames = new ArrayList<>();
                //查询机构名称
                if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                {
                    for (String deptId : dataAuthoritySearcher.get(i).getDeptAndSons())
                    {
                        Department department = baseDaoSupport.get(Department.class, deptId);
                        if (null != department)
                        {
                            depAndSonNames.add(department.getText());
                        }
                    }
                }
                
                if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDepts()))
                {
                    for (String deptId : dataAuthoritySearcher.get(i).getDepts())
                    {
                        Department department = baseDaoSupport.get(Department.class, deptId);
                        if (null != department)
                        {
                            depNames.add(department.getText());
                        }
                    }
                    
                }
                
                if (i == 0)
                {
                    switch (dataAuthoritySearcher.get(i).getConfig())
                    {
                        case 1://本人数据
                            hql.append(" and (   (s." + "creater" + " =:userId)");
                            paramNames.add("userId");
                            parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            break;
                        
                        case 2://本人及下属数据
                            hql.append(" and ( (s." + "creater" + " in( select u.id from User u,UserArchive ua where u.archive.id=ua.id and "
                                + "(ua.leaderId =:userId or u.id = :useid)))");
                            paramNames.add("userId");
                            parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            paramNames.add("useid");
                            parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            break;
                        
                        case 3://所在机构数据
                            if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                            {
                                hql.append(" and (  (t." + "name"
                                    + " = ( select d.text from Department d where d.id =(select a.deptId from UserArchive a where a.id = ("
                                    + " select u.archive.id from User u where u.id=:userId))))");
                                paramNames.add("userId");
                                parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            }
                            else
                            {
                                hql.append(" and (1=2");
                            }
                            break;
                        
                        case 4://所在（机构及下属机构）数据
                            if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                            {
                                hql.append("  and ( ( t." + "name" + " in (:depAndSonNames )  )");
                                paramNames.add("depAndSonNames");
                                parameters.add(depAndSonNames);
                            }
                            else
                            {
                                hql.append(" and (1=2 ");
                            }
                            
                            break;
                        
                        case 6://所有数据
                            hql.append(" and ( 1=1 ");
                            break;
                        
                        case 5://所选机构
                            hql.append(" and (   (t." + "name" + " in ( :depNames ))");
                            paramNames.add("depNames");
                            parameters.add(depNames);
                            break;
                    
                    }
                }
                else
                {
                    switch (dataAuthoritySearcher.get(i).getConfig())
                    {
                        case 1://本人数据
                            hql.append("  or (s." + "creater" + " =:userId)");
                            paramNames.add("userId");
                            parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            break;
                        
                        case 2://本人及下属数据
                            hql.append(" or (s." + "creater" + " in( select u.id from User u,UserArchive ua where u.archive.id=ua.id and "
                                + "(ua.leaderId =:userId or u.id = :useid)))");
                            paramNames.add("userId");
                            parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            paramNames.add("useid");
                            parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            break;
                        
                        case 3://所在机构数据
                            if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                            {
                                hql.append("  or (t." + "name"
                                    + " = ( select d.text from Department d where d.id =(select a.deptId from UserArchive a where a.id = ("
                                    + " select u.archive.id from User u where u.id=:userId))))");
                                paramNames.add("userId");
                                parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            }
                            else
                            {
                                hql.append(" or (1=2)");
                            }
                            
                            break;
                        
                        case 4://所在机构及下属机构数据
                            if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                            {
                                hql.append("  or ( t." + "name" + " in (:depAndSonNames )  )");
                                paramNames.add("depAndSonNames");
                                parameters.add(depAndSonNames);
                            }
                            else
                            {
                                hql.append(" or (1=2)");
                            }
                            break;
                        
                        case 6://所有数据
                            hql.append(" or (1=1)");
                            break;
                        
                        case 5://所选机构
                            hql.append("  or  (t." + "name" + " in ( :depNames ))");
                            paramNames.add("depNames");
                            parameters.add(depNames);
                            break;
                    
                    }
                }
                
            }
            
        }
        hql.append(" ) ");
        addCodeFilter(hql, paramNames, parameters);
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<Product> getEntityClass()
    {
        return Product.class;
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (product != null && !StringUtils.isEmpty(product.getCode()))
        {
            hql.append(" AND s.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + product.getCode() + "%");
        }
        if (product != null && !StringUtils.isEmpty(product.getName()))
        {
            
            if (!likeSearch)
            {
                hql.append(" AND s.name LIKE :name");
                paramNames.add("name");
                parameters.add("%" + product.getName() + "%");
            }
            else
            {
                hql.append(" AND s.name = :name");
                paramNames.add("name");
                parameters.add(product.getName());
            }
            
        }
        
        if (product != null && null != product.getEnabled())
        {
            hql.append(" AND s.enabled = :enabled");
            paramNames.add("enabled");
            parameters.add(product.getEnabled());
        }
        if (product != null && null != product.getTestingType() && !StringUtils.isEmpty(product.getTestingType().getId()))
        {
            hql.append(" AND s.testingType.id = :testingType");
            paramNames.add("testingType");
            parameters.add(product.getTestingType().getId());
        }
        
        if (product != null && StringUtils.isNotEmpty(product.getTestInstitution()))
        {
            hql.append(" AND s.testInstitution LIKE :testInstitution");
            paramNames.add("testInstitution");
            parameters.add("%" + product.getTestInstitution() + "%");
        }
        
        if (StringUtils.isNotEmpty(productDutyName))
        {
            hql.append(" AND s.productDuty.archive.name LIKE :productDutyName");
            paramNames.add("productDutyName");
            parameters.add("%" + productDutyName + "%");
        }
        
        if (testingSubtype != null && StringUtils.isNotEmpty(testingSubtype))
        {
            hql.append(" AND s.testingSubtype.id = :testingSubtype");
            paramNames.add("testingSubtype");
            parameters.add(testingSubtype);
        }
        
        if (principalName != null && StringUtils.isNotEmpty(principalName))
        {
            hql.append(" AND EXISTS (SELECT pp.id FROM ProductPrincipal pp WHERE pp.product.id = s.id AND pp.principal.archive.name LIKE :principalName )");
            paramNames.add("principalName");
            parameters.add("%" + principalName + "%");
        }
        
        if (StringUtils.isNotEmpty(productProbe))
        {
            hql.append(" AND EXISTS (SELECT pe.id FROM ProductProbe pe WHERE pe.productTestingMethod.id = p.id AND pe.probeId = :productProbe )");
            paramNames.add("productProbe");
            parameters.add(productProbe);
        }
        
        hql.append(" order by s.enabled desc, s.createTime desc");
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    public boolean isLikeSearch()
    {
        return likeSearch;
    }
    
    public void setLikeSearch(boolean likeSearch)
    {
        this.likeSearch = likeSearch;
    }
    
}
