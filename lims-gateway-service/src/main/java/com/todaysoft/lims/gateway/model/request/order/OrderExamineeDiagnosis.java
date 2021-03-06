package com.todaysoft.lims.gateway.model.request.order;

import com.todaysoft.lims.gateway.model.request.disease.Disease;
import com.todaysoft.lims.persist.UuidKeyEntity;




/**
 * 订单-受检人-临床诊断
 * @author admin
 *
 */


public class OrderExamineeDiagnosis extends UuidKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OrderExaminee orderExaminee; //订单受检人
	
	private Disease disease; //临床诊断
	
	public OrderExamineeDiagnosis() {
		super();
	}
	
	
	public OrderExaminee getOrderExaminee() {
		return orderExaminee;
	}

	public void setOrderExaminee(OrderExaminee orderExaminee) {
		this.orderExaminee = orderExaminee;
	}

	
	
	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}
	
	
	
	
	
	
	
	
}
