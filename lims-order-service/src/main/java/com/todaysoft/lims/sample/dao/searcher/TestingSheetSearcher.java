package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.sample.entity.TestingSheet;

public class TestingSheetSearcher implements ISearcher<TestingSheet> {

	private String type;
	
	private String activitiTaskId;
	
	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingSheet ts WHERE 1=1"); 
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
	}
	
	private void addFilter(StringBuffer hql, List<String> paramNames,List<Object> parameters) {
		if (!StringUtils.isEmpty(type)){
            hql.append(" AND ts.type LIKE :type");
            paramNames.add("type");
            parameters.add("%" + type + "%");
        }
		
		if (!StringUtils.isEmpty(activitiTaskId)){
            hql.append(" AND ts.activitiTaskId = :activitiTaskId");
            paramNames.add("activitiTaskId");
            parameters.add(activitiTaskId);
        }
	}

	@Override
	public Class<TestingSheet> getEntityClass() {
		return TestingSheet.class;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

}
