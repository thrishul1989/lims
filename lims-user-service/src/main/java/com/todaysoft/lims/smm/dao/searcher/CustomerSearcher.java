package com.todaysoft.lims.smm.dao.searcher;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.Customer;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearcher implements ISearcher<Customer> {

	private String name;
	
	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(256);
        hql.append("FROM Customer c WHERE 1=1");
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
        if (!StringUtils.isEmpty(name))
        {
            hql.append(" AND c.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
    }

	@Override
	public Class<Customer> getEntityClass() {
		return Customer.class;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
