package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.sample.entity.TestingSchedule;
import com.todaysoft.lims.sample.entity.TestingTask;
public class TestingScheduleSearcher implements ISearcher<TestingSchedule> {

	
	
	private Integer orderId;    //订单 
	
	private Integer productId;  //检测项目
	
	private Integer methodId;   //检测方法
	
	private Integer sampleId;
	
	private Integer activeTask;
	
	private Integer verifyTarget;
	
	@Override
	public NamedQueryer toQuery() {
		StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingSchedule ts WHERE 1=1"); 
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
		 
		 if (!StringUtils.isEmpty(sampleId)){
	            hql.append(" AND ts.sampleId = :sampleId");
	            paramNames.add("sampleId");
	            parameters.add(sampleId);
	      }
		 if (!StringUtils.isEmpty(orderId)){
	            hql.append(" AND ts.orderId = :orderId");
	            paramNames.add("orderId");
	            parameters.add(orderId);
	      }
		 if (!StringUtils.isEmpty(productId)){
	            hql.append(" AND ts.productId = :productId");
	            paramNames.add("productId");
	            parameters.add(productId);
	      }
		 if (!StringUtils.isEmpty(methodId)){
	            hql.append(" AND ts.methodId = :methodId");
	            paramNames.add("methodId");
	            parameters.add(methodId);
	      }
		 if (!StringUtils.isEmpty(activeTask)){
	            hql.append(" AND ts.activeTask = :activeTask");
	            paramNames.add("activeTask");
	            parameters.add(activeTask);
	      }
		 if (!StringUtils.isEmpty(verifyTarget)){
	            hql.append(" AND ts.verifyTarget = :verifyTarget");
	            paramNames.add("verifyTarget");
	            parameters.add(verifyTarget);
	      }
	}

	@Override
	public Class<TestingSchedule> getEntityClass() {
		return TestingSchedule.class;
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

	public Integer getSampleId() {
		return sampleId;
	}

	public void setSampleId(Integer sampleId) {
		this.sampleId = sampleId;
	}

	public Integer getActiveTask() {
		return activeTask;
	}

	public void setActiveTask(Integer activeTask) {
		this.activeTask = activeTask;
	}

	public Integer getVerifyTarget() {
		return verifyTarget;
	}

	public void setVerifyTarget(Integer verifyTarget) {
		this.verifyTarget = verifyTarget;
	}
	
	

}
