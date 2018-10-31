package com.todaysoft.lims.sample.entity.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单受检人表
 * @author admin
 *
 */

@Entity
@Table(name = "LIMS_ORDER_EXAMINEE")
public class OrderExaminee extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /*****************受检人基本信息******************/
    
    private String name;
    
    private String sex;//性别
    
    private String nation;//民族
    
    private String birthday;//出生日期
    
    private String ageSnapshot; //申请检测时年龄（X/X/X）
    
    private String origin; //籍贯
    
    private String contactName;//联系人
    
    private String contactPhone;//联系电话
    
    private String contactEmail;//联系邮箱
    
    private String recordNo; //病历号
    
    private String medicalHistory;//既往病史
    
    private String familyMedicalHistory; // 家族史摘要
    
    private String clinicalRecommend; //临床推荐理由
    
    private Order order; //订单对象
    
    private List<OrderExamineeDiagnosis> orderExamineeDiagnosis = new ArrayList<OrderExamineeDiagnosis>(); //受检人临床诊断
    
    private List<OrderExamineeGene> orderExamineeGene = new ArrayList<OrderExamineeGene>(); //受检人重点基因
    
    private List<OrderExamineePhenotype> orderExamineePhenotype = new ArrayList<OrderExamineePhenotype>(); //受检人临床表型
    
    private String consentFigures;// '知情同意书附件（多附件逗号隔开）'
    
    private String recordFigures;// '病历附件（多附件逗号隔开）',
    
    private String familyFigures;//家系图附件（多附件逗号隔开）',
    
    public OrderExaminee()
    {
        super();
    }
    
    @Column(name = "CLINICAL_RECOMMEND")
    public String getClinicalRecommend()
    {
        return clinicalRecommend;
    }
    
    public void setClinicalRecommend(String clinicalRecommend)
    {
        this.clinicalRecommend = clinicalRecommend;
    }
    
    @Column(name = "FAMILY_MEDICAL_HISTORY")
    public String getFamilyMedicalHistory()
    {
        return familyMedicalHistory;
    }
    
    public void setFamilyMedicalHistory(String familyMedicalHistory)
    {
        this.familyMedicalHistory = familyMedicalHistory;
    }
    
    @Column(name = "NATION")
    public String getNation()
    {
        return nation;
    }
    
    public void setNation(String nation)
    {
        this.nation = nation;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "SEX")
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    @Column(name = "BIRTHDAY")
    public String getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }
    
    @Column(name = "AGE_SNAPSHOT")
    public String getAgeSnapshot()
    {
        return ageSnapshot;
    }
    
    public void setAgeSnapshot(String ageSnapshot)
    {
        this.ageSnapshot = ageSnapshot;
    }
    
    @Column(name = "ORIGIN")
    public String getOrigin()
    {
        return origin;
    }
    
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }
    
    @Column(name = "CONTACT_NAME")
    public String getContactName()
    {
        return contactName;
    }
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    @Column(name = "CONTACT_PHONE")
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    @Column(name = "CONTACT_EMAIL")
    public String getContactEmail()
    {
        return contactEmail;
    }
    
    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }
    
    @Column(name = "RECORD_NO")
    public String getRecordNo()
    {
        return recordNo;
    }
    
    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }
    
    @Column(name = "MEDICAL_HISTORY")
    public String getMedicalHistory()
    {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory)
    {
        this.medicalHistory = medicalHistory;
    }
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    @JsonIgnore
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    @OneToMany(mappedBy = "orderExaminee", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    public List<OrderExamineeDiagnosis> getOrderExamineeDiagnosis()
    {
        return orderExamineeDiagnosis;
    }
    
    public void setOrderExamineeDiagnosis(List<OrderExamineeDiagnosis> orderExamineeDiagnosis)
    {
        this.orderExamineeDiagnosis = orderExamineeDiagnosis;
    }
    
    @OneToMany(mappedBy = "orderExaminee", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    public List<OrderExamineeGene> getOrderExamineeGene()
    {
        return orderExamineeGene;
    }
    
    public void setOrderExamineeGene(List<OrderExamineeGene> orderExamineeGene)
    {
        this.orderExamineeGene = orderExamineeGene;
    }
    
    @OneToMany(mappedBy = "orderExaminee", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    public List<OrderExamineePhenotype> getOrderExamineePhenotype()
    {
        return orderExamineePhenotype;
    }
    
    public void setOrderExamineePhenotype(List<OrderExamineePhenotype> orderExamineePhenotype)
    {
        this.orderExamineePhenotype = orderExamineePhenotype;
    }
    
    @Column(name = "CONSENT_FIGURES")
    public String getConsentFigures()
    {
        return consentFigures;
    }
    
    public void setConsentFigures(String consentFigures)
    {
        this.consentFigures = consentFigures;
    }
    
    @Column(name = "RECORD_FIGURES")
    public String getRecordFigures()
    {
        return recordFigures;
    }
    
    public void setRecordFigures(String recordFigures)
    {
        this.recordFigures = recordFigures;
    }
    
    @Column(name = "FAMILY_FIGURES")
    public String getFamilyFigures()
    {
        return familyFigures;
    }
    
    public void setFamilyFigures(String familyFigures)
    {
        this.familyFigures = familyFigures;
    }
    
}
