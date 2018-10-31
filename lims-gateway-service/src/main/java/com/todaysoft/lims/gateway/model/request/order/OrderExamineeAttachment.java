package com.todaysoft.lims.gateway.model.request.order;

import com.todaysoft.lims.persist.UuidKeyEntity;



/**
 * 订单受检人表
 * @author admin
 *
 */

public class OrderExamineeAttachment extends UuidKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String attachmentType;
	
	private Attachment attachment;
	
	private OrderExaminee orderExaminee; //订单受检人
	
	public OrderExamineeAttachment() {
		super();
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	
	public OrderExaminee getOrderExaminee() {
		return orderExaminee;
	}

	public void setOrderExaminee(OrderExaminee orderExaminee) {
		this.orderExaminee = orderExaminee;
	}
	
	
	
	
	
	
}
