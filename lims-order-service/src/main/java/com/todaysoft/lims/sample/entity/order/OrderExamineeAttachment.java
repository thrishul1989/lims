package com.todaysoft.lims.sample.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 订单受检人表
 * @author admin
 *
 */

@Entity
@Table(name = "LIMS_ORDER_EXAMINEE_ATTACHMENT")
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

	
	@ManyToOne(cascade = CascadeType.ALL ,fetch= FetchType.EAGER) 
	@JoinColumn(name="OE_ID")
	@JsonIgnore
	public OrderExaminee getOrderExaminee() {
		return orderExaminee;
	}

	public void setOrderExaminee(OrderExaminee orderExaminee) {
		this.orderExaminee = orderExaminee;
	}
	
	
	
	
	
	
}
