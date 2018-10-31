package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.SampleReceive;


public class SampleReceiveSearcher  implements ISearcher<SampleReceive> {
	
	
	private String state; 
	private String acceptPerson; 
	

	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SampleReceive s WHERE 1=1"); 
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
	}

	private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters) {
		 if (null != acceptPerson && !"".equals(acceptPerson)){
	            hql.append(" AND s.acceptPerson LIKE:acceptPerson");
	            paramNames.add("acceptPerson");
	            parameters.add("%"+acceptPerson+"%");
	      }
		 if (null != state && !"".equals(state)){
	            hql.append(" AND s.state =:state");
	            paramNames.add("state");
	            parameters.add(state);
	      }
		 
		 hql.append(" order by s.acceptDate desc");
	}

	@Override
	public Class<SampleReceive> getEntityClass() {
		return SampleReceive.class;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAcceptPerson() {
		return acceptPerson;
	}

	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}

	
	
	
}
