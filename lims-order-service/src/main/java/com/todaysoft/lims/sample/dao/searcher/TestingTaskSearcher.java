package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import org.springframework.util.StringUtils;
import com.todaysoft.lims.sample.entity.TestingTask;
public class TestingTaskSearcher implements ISearcher<TestingTask> {

	
	private String semantic;
	
	private Integer orderId;    //订单 
	
	private Integer productId;  //检测项目
	
	private Integer methodId;   //检测方法
	
	private Integer status;
	
	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingTask tt WHERE 1=1"); 
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
		 
		 if (!StringUtils.isEmpty(semantic)){
	            hql.append(" AND tt.semantic = :semantic");
	            paramNames.add("semantic");
	            parameters.add(semantic);
	      }
		 if (!StringUtils.isEmpty(orderId)){
	            hql.append(" AND tt.orderId = :orderId");
	            paramNames.add("orderId");
	            parameters.add(orderId);
	      }
		 if (!StringUtils.isEmpty(productId)){
	            hql.append(" AND tt.productId = :productId");
	            paramNames.add("productId");
	            parameters.add(productId);
	      }
		 if (!StringUtils.isEmpty(methodId)){
	            hql.append(" AND tt.methodId = :methodId");
	            paramNames.add("methodId");
	            parameters.add(methodId);
	      }
		 if (!StringUtils.isEmpty(status)){
	            hql.append(" AND tt.status = :status");
	            paramNames.add("status");
	            parameters.add(status);
	      }
	}

	@Override
	public Class<TestingTask> getEntityClass() {
		return TestingTask.class;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getMethodId() {
		return methodId;
	}

	public void setMethodId(Integer methodId) {
		this.methodId = methodId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}
}
