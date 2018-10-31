package com.todaysoft.lims.gateway.model.request.order;

import com.todaysoft.lims.gateway.model.Phenotype;
import com.todaysoft.lims.persist.UuidKeyEntity;





/**
 * 订单-受检人-临床表型
 * @author admin
 *
 */
public class OrderExamineePhenotype extends UuidKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OrderExaminee orderExaminee; //订单受检人
	
	private Phenotype phenotype; //临床表型
	

	public OrderExamineePhenotype() {
		super();
	}
	
	public OrderExaminee getOrderExaminee() {
		return orderExaminee;
	}

	public void setOrderExaminee(OrderExaminee orderExaminee) {
		this.orderExaminee = orderExaminee;
	}

	
	public Phenotype getPhenotype() {
		return phenotype;
	}

	public void setPhenotype(Phenotype phenotype) {
		this.phenotype = phenotype;
	}	
	
	
	
	
	
}
