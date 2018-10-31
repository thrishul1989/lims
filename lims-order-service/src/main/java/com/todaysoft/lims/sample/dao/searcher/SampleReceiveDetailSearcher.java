package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.util.Constant;

public class SampleReceiveDetailSearcher implements ISearcher<SampleReceiveDetail> {
	
	private String code;
	
	private String sampleIdentification;//样本标识
	
	private String state; //状态： 1未启动、2启动
	
	private String createUser;

	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SampleReceiveDetail s WHERE 1=1"); 
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
		 if (StringUtils.isNotEmpty(code)){
	            hql.append(" AND s.code LIKE:code");
	            paramNames.add("code");
	            parameters.add("%"+code+"%");
	      }
		 
		 if (StringUtils.isNoneEmpty(createUser)){
	            hql.append(" AND s.createUser =:createUser");
	            paramNames.add("createUser");
	            parameters.add(createUser);
	      }
		 
         hql.append(" AND s.receiveId is NULL ");
	}

	@Override
	public Class<SampleReceiveDetail> getEntityClass() {
		return SampleReceiveDetail.class;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	public String getSampleIdentification() {
		return sampleIdentification;
	}

	public void setSampleIdentification(String sampleIdentification) {
		this.sampleIdentification = sampleIdentification;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public NamedQueryer toQueryStartActiviti() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SampleReceiveDetail s WHERE 1=1 "); 
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addSQLFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

	private void addSQLFilter(StringBuffer hql, List<String> paramNames,List<Object> parameters) {
		 if (null != state && !"".equals(state)){
	            hql.append(" AND s.state =:state");
	            paramNames.add("state");
	            parameters.add(state);
	      }
		 
		 if (null != sampleIdentification && !"".equals(sampleIdentification)){
	            hql.append(" AND s.sampleIdentification =:sampleIdentification");
	            paramNames.add("sampleIdentification");
	            parameters.add(sampleIdentification);
	      }
		 
        hql.append(" AND s.isSave =:isSave");
        paramNames.add("isSave");
        parameters.add(Constant.SAMPLE_DETAIL_SAVED); //已关联父样本接收
		 
		hql.append(" order by s.id desc");
	}

	
	public NamedQueryer toQueryProductByReceiveID(Integer receiveId) {
		StringBuffer hql = new StringBuffer(512);
        hql.append("select p FROM SampleReceive s ,Project p WHERE 1=1 AND s.relatedItems = p.id "); 
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        if(receiveId!=null){
        	hql.append(" AND s.id =:ids");
        	paramNames.add("ids");
	        parameters.add(receiveId);
        }
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
	
	

}
