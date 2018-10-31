package com.todaysoft.lims.gateway.model.request.order;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.UuidKeyEntity;


/**
 * 订单受检人表
 * @author admin
 *
 */

public class OrderExaminee extends UuidKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private int sex;//性别
	private String nation;//民族
	private String birthday;//出生日期
	private String ageSnapshot; //申请检测时年龄（X/X/X）
	private String origin; //籍贯
	private String contactName;//联系人
	private String contactPhone;//联系电话
	private String contactEmail;//联系邮箱
	private String recordNo; //病历号
	private String medicalHistory;//既往病史
	private Order order; //订单对象
	
	
	private List<OrderExamineeDiagnosis> orderExamineeDiagnosis = new ArrayList<OrderExamineeDiagnosis>(); //受检人临床诊断
	
	private List<OrderExamineeGene> orderExamineeGene = new ArrayList<OrderExamineeGene>(); //受检人重点基因
	
	private List<OrderExamineePhenotype> orderExamineePhenotype = new ArrayList<OrderExamineePhenotype>(); //受检人临床表型
	
	
	
	public OrderExaminee() {
		super();
	}
	
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAgeSnapshot() {
		return ageSnapshot;
	}

	public void setAgeSnapshot(String ageSnapshot) {
		this.ageSnapshot = ageSnapshot;
	}
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}
	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	
	
	public List<OrderExamineeDiagnosis> getOrderExamineeDiagnosis() {
		return orderExamineeDiagnosis;
	}

	public void setOrderExamineeDiagnosis(
			List<OrderExamineeDiagnosis> orderExamineeDiagnosis) {
		this.orderExamineeDiagnosis = orderExamineeDiagnosis;
	}

	public List<OrderExamineeGene> getOrderExamineeGene() {
		return orderExamineeGene;
	}

	public void setOrderExamineeGene(List<OrderExamineeGene> orderExamineeGene) {
		this.orderExamineeGene = orderExamineeGene;
	}
	
	public List<OrderExamineePhenotype> getOrderExamineePhenotype() {
		return orderExamineePhenotype;
	}

	public void setOrderExamineePhenotype(
			List<OrderExamineePhenotype> orderExamineePhenotype) {
		this.orderExamineePhenotype = orderExamineePhenotype;
	}
	
	
	
	
	
}
