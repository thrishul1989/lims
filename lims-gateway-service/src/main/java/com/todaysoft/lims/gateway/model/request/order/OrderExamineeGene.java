package com.todaysoft.lims.gateway.model.request.order;

import com.todaysoft.lims.gateway.model.request.disease.Gene;
import com.todaysoft.lims.persist.UuidKeyEntity;




/**
 * 订单-受检人-重点关注基因
 * @author admin
 *
 */
public class OrderExamineeGene extends UuidKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OrderExaminee orderExaminee; //订单受检人
	
	private Gene gene; //重点关注基因
	

	public OrderExamineeGene() {
		super();
	}
	
	public OrderExaminee getOrderExaminee() {
		return orderExaminee;
	}

	public void setOrderExaminee(OrderExaminee orderExaminee) {
		this.orderExaminee = orderExaminee;
	}

	
	public Gene getGene() {
		return gene;
	}

	public void setGene(Gene gene) {
		this.gene = gene;
	}

	
	
	
	
	
}
