package com.todaysoft.lims.smm.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.User;
import com.todaysoft.lims.smm.entity.enums.UserState;

public class UserSelectSearcher implements ISearcher<User>
{
    private UserState state;
    
    @Override
    public Class<User> getEntityClass()
    {
        return User.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(256);
        hql.append("select u FROM User u WHERE u.deleted = false");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        addStateFilter(hql, names, values);
        hql.append(" AND NOT EXISTS (SELECT b.id FROM BusinessInfo b WHERE b.id = u.id)");
        hql.append(" ORDER BY u.state,u.createTime DESC");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addStateFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!StringUtils.isEmpty(state))
        {
            hql.append(" AND u.state = :state");
            paramNames.add("state");
            parameters.add(state);
        }
    }
    
    public UserState getState()
    {
        return state;
    }
    
    public void setState(UserState state)
    {
        this.state = state;
    }
}
