package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.sample.entity.SampleOrder;

public class SampleOrderSearcher  implements ISearcher<SampleOrder> {
	
	private String orderCode; //订单编号
	
	private String testProduct;//检测产品
	
	private String chargeState;//检测产品
	

	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SampleOrder s WHERE 1=1"); 
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
	}
	
	public NamedQueryer toListQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM SampleOrder s WHERE 1=1"); 
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter1(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
	}
	
	private void addFilter1(StringBuffer hql, List<String> paramNames,List<Object> parameters) {
		if (!StringUtils.isEmpty(orderCode)){
            hql.append(" AND s.orderCode LIKE :orderCode");
            paramNames.add("orderCode");
            parameters.add("%" + orderCode + "%");
        }
        
        if (!StringUtils.isEmpty(testProduct)){
            hql.append(" AND s.testProduct LIKE :testProduct");
            paramNames.add("testProduct");
            parameters.add("%" + testProduct + "%");
        }
        
        if (!StringUtils.isEmpty(chargeState)){
            hql.append(" AND s.chargeState =:chargeState");
            paramNames.add("chargeState");
            parameters.add(chargeState);
        }
        
        hql.append(" order by s.id desc");
	}

	

	private void addFilter(StringBuffer hql, List<String> paramNames,List<Object> parameters) {
		if (!StringUtils.isEmpty(orderCode)){
            hql.append(" AND s.orderCode LIKE :orderCode");
            paramNames.add("orderCode");
            parameters.add("%" + orderCode + "%");
        }
        
        if (!StringUtils.isEmpty(testProduct)){
            hql.append(" AND s.testProduct LIKE :testProduct");
            paramNames.add("testProduct");
            parameters.add("%" + testProduct + "%");
        }
        hql.append(" and not exists (select r.orderId from SampleReceive r where r.orderId = s.id ) ");
        hql.append(" order by s.id desc");
	}

	@Override
	public Class<SampleOrder> getEntityClass() {
		return SampleOrder.class;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getTestProduct() {
		return testProduct;
	}

	public void setTestProduct(String testProduct) {
		this.testProduct = testProduct;
	}

	public String getChargeState() {
		return chargeState;
	}

	public void setChargeState(String chargeState) {
		this.chargeState = chargeState;
	}
	
	

}
