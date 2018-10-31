package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.sample.entity.TestingSheetTask;

public class TestingSheetTaskSearcher implements ISearcher<TestingSheetTask> {

	private Integer testingSheetId;
	
	private String semantic;
	
	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingSheetTask tst WHERE 1=1"); 
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
		 if (!StringUtils.isEmpty(testingSheetId)){
	            hql.append(" AND tst.testingSheetId = :testingSheetId");
	            paramNames.add("testingSheetId");
	            parameters.add(testingSheetId);
	      }
		 
		 if (!StringUtils.isEmpty(semantic)){
	            hql.append(" AND tst.semantic = :semantic");
	            paramNames.add("semantic");
	            parameters.add(semantic);
	      }
	}

	@Override
	public Class<TestingSheetTask> getEntityClass() {
		return TestingSheetTask.class;
	}

	public Integer getTestingSheetId() {
		return testingSheetId;
	}

	public void setTestingSheetId(Integer testingSheetId) {
		this.testingSheetId = testingSheetId;
	}

	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}
}
