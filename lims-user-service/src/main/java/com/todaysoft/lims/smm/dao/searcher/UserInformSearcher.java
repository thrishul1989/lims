package com.todaysoft.lims.smm.dao.searcher;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.UserInform;
import com.todaysoft.lims.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserInformSearcher implements ISearcher<UserInform>
{
   private  Integer id;

    @Override
    public Class<UserInform> getEntityClass()
    {
        return UserInform.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("FROM UserInform u WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(id))
        {
            hql.append(" AND u.userId  =:id");
            paramNames.add("id");
            parameters.add(id);
        }
    }
    
  

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    
  


}
