package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.sample.entity.OrderExperimentProduct;

public class OrderExperimentProductSearcher implements ISearcher<OrderExperimentProduct> {

	private Integer orderId;
	private Integer sampleDetailId;
	private String locationCode;
	
	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM OrderExperimentProduct oep WHERE 1=1"); 
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
		if (!StringUtils.isEmpty(orderId)){
            hql.append(" AND oep.orderId = :orderId");
            paramNames.add("orderId");
            parameters.add(orderId);
        }
		
		if (!StringUtils.isEmpty(sampleDetailId)){
            hql.append(" AND oep.sampleDetailId = :sampleDetailId");
            paramNames.add("sampleDetailId");
            parameters.add(sampleDetailId);
        }
        
        if (!StringUtils.isEmpty(locationCode)){
            hql.append(" AND oep.locationCode LIKE :locationCode");
            paramNames.add("locationCode");
            parameters.add("%" + locationCode + "%");
        }
        
        //hql.append(" ORDER BY oep.id ASC");
	}

	@Override
	public Class<OrderExperimentProduct> getEntityClass() {
		return OrderExperimentProduct.class;
	}

	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

}
