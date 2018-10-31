package com.todaysoft.lims.persist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.todaysoft.lims.utils.Collections3;

public abstract class IDataAuthoritySearcher
{
//    @Autowired
//    private BaseDaoSupport baseDaoSupport;
    
    public List<DataAuthoritySearcher> dataAuthoritySearcher;
    
    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    public abstract NamedQueryer toAuthQuery();
    
    public void addAuthFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters, String creatParam)
    {
        if (null == dataAuthoritySearcher || Collections3.isEmpty(dataAuthoritySearcher))
        {
            hql.append(" AND (1=2");
        }
        
        else if (null != dataAuthoritySearcher && Collections3.isNotEmpty(dataAuthoritySearcher))
        {
            for (int i = 0; i <= dataAuthoritySearcher.size() - 1; i++)
            {
                if (i == 0)
                {
                    switch (dataAuthoritySearcher.get(i).getConfig())
                    {
                        case 1://本人数据
                            hql.append(" and (   (s." + creatParam + " =:userId)");
                            paramNames.add("userId");
                            parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            break;
                        
                        case 2://本人及下属数据
                            hql.append(" and (s." + creatParam + " in (:archives)");
                            
                            paramNames.add("archives");
                            parameters.add(dataAuthoritySearcher.get(i).getUserAndSons());
                            break;
                        
                        case 3://所在机构数据
                            if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                            {
                                hql.append(" and (  (s." + creatParam + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and "
                                    + " ua.deptId= (select uu.archive.deptId from UserConfig uu where uu.id=:userId ) ))");
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
                                hql.append("  and ( ( s." + creatParam
                                    + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and " + " ua.deptId in (:list))  )");
                                paramNames.add("list");
                                parameters.add(dataAuthoritySearcher.get(i).getDeptAndSons());
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
                            hql.append(" and (   (s." + creatParam + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and "
                                + " ua.deptId in (:list ) ))");
                            paramNames.add("list");
                            parameters.add(dataAuthoritySearcher.get(i).getDepts());
                            break;
                    
                    }
                }
                else
                {
                    switch (dataAuthoritySearcher.get(i).getConfig())
                    {
                        case 1://本人数据
                            hql.append("  or (s." + creatParam + " =:userId)");
                            paramNames.add("userId");
                            parameters.add(dataAuthoritySearcher.get(i).getUserId());
                            break;
                        
                        case 2://本人及下属数据
                            hql.append(" or (s." + creatParam + " in (:archives))");
                            paramNames.add("archives");
                            parameters.add(dataAuthoritySearcher.get(i).getUserAndSons());
                            break;
                        
                        case 3://所在机构数据
                            if (Collections3.isNotEmpty(dataAuthoritySearcher.get(i).getDeptAndSons()))
                            {
                                hql.append("  or (s." + creatParam + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and "
                                    + " ua.deptId= (select uu.archive.deptId from UserConfig uu where uu.id=:userId ) ))");
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
                                hql.append("  or ( s." + creatParam + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and "
                                    + " ua.deptId in  (:list))   )");
                                paramNames.add("list");
                                parameters.add(dataAuthoritySearcher.get(i).getDeptAndSons());
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
                            hql.append("  or  (s." + creatParam + " in( select u.id from UserConfig u,UserArchiveConfig ua where u.archive.id=ua.id and "
                                + " ua.deptId in (:list ) ))");
                            paramNames.add("list");
                            parameters.add(dataAuthoritySearcher.get(i).getDepts());
                            break;
                    
                    }
                }
                
            }
            
        }
        hql.append(" ) ");
        
    }
}
