package com.todaysoft.lims.gateway.model.request.order;

import com.todaysoft.lims.gateway.model.Product;
import com.todaysoft.lims.persist.UuidKeyEntity;


/**
 * 订单-主样本-产品
 * @author admin
 *
 */

public class OrderResearchProduct extends UuidKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OrderResearchSample orderResearchSample;
	
	private Product product;
	
	public OrderResearchProduct() {
		super();
	}


	public OrderResearchSample getOrderResearchSample() {
		return orderResearchSample;
	}

	public void setOrderResearchSample(OrderResearchSample orderResearchSample) {
		this.orderResearchSample = orderResearchSample;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	
	
	
	
}
