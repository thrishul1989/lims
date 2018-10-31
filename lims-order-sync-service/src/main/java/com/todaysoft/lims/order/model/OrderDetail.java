package com.todaysoft.lims.order.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author dinghairong
 * @Date 2018/10/18 10:58
 * @Description
 */
public class OrderDetail {

    private String orderFormKey;

    private String orderFormCode;

    private String createTime;

    private String customerKey;

    private String contractKey;

    private String orderFormStatus;

    private String orderFormType;

    private String customerName;

    private String customerPhone;

    private String contractCode;

    private String contractName;

    private String unit;

    private String sale;

    private String payStatus;

    private String isParticipate;

    private String startTime;

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private String urgent;

    private String name;

    private String nation;

    private String sex;

    private String birthday;

    private String age;

    private String contacts;

    private String contactsPhone;

    private String contactsMail;

    private String caseNo;

    private String diseaseOmimIds;

    private String diseaseNames;

    private String geneOmimIds;

    private String geneSymbols;

    private String phenotypeHpos;

    private String phenotypeNames;

    private String diseaseHistory;

    private String fhxNote;

    private String reason;

    private List<OrderSampleDetail> samples = Lists.newArrayList();

    private List<OrderPicUrl> casePics = Lists.newArrayList();

    private List<OrderPicUrl> familyPic = Lists.newArrayList();

    private List<OrderProductDetail> orderProducts = Lists.newArrayList();

    private OrderDatas orderDatas;

    public String getOrderFormKey() {
        return orderFormKey;
    }

    public void setOrderFormKey(String orderFormKey) {
        this.orderFormKey = orderFormKey;
    }

    public String getOrderFormCode() {
        return orderFormCode;
    }

    public void setOrderFormCode(String orderFormCode) {
        this.orderFormCode = orderFormCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

    public String getContractKey() {
        return contractKey;
    }

    public void setContractKey(String contractKey) {
        this.contractKey = contractKey;
    }

    public String getOrderFormStatus() {
        return orderFormStatus;
    }

    public void setOrderFormStatus(String orderFormStatus) {
        this.orderFormStatus = orderFormStatus;
    }

    public String getOrderFormType() {
        return orderFormType;
    }

    public void setOrderFormType(String orderFormType) {
        this.orderFormType = orderFormType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getIsParticipate() {
        return isParticipate;
    }

    public void setIsParticipate(String isParticipate) {
        this.isParticipate = isParticipate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getContactsMail() {
        return contactsMail;
    }

    public void setContactsMail(String contactsMail) {
        this.contactsMail = contactsMail;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getDiseaseOmimIds() {
        return diseaseOmimIds;
    }

    public void setDiseaseOmimIds(String diseaseOmimIds) {
        this.diseaseOmimIds = diseaseOmimIds;
    }

    public String getDiseaseNames() {
        return diseaseNames;
    }

    public void setDiseaseNames(String diseaseNames) {
        this.diseaseNames = diseaseNames;
    }

    public String getGeneOmimIds() {
        return geneOmimIds;
    }

    public void setGeneOmimIds(String geneOmimIds) {
        this.geneOmimIds = geneOmimIds;
    }

    public String getGeneSymbols() {
        return geneSymbols;
    }

    public void setGeneSymbols(String geneSymbols) {
        this.geneSymbols = geneSymbols;
    }

    public String getPhenotypeHpos() {
        return phenotypeHpos;
    }

    public void setPhenotypeHpos(String phenotypeHpos) {
        this.phenotypeHpos = phenotypeHpos;
    }

    public String getPhenotypeNames() {
        return phenotypeNames;
    }

    public void setPhenotypeNames(String phenotypeNames) {
        this.phenotypeNames = phenotypeNames;
    }

    public String getDiseaseHistory() {
        return diseaseHistory;
    }

    public void setDiseaseHistory(String diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    public String getFhxNote() {
        return fhxNote;
    }

    public void setFhxNote(String fhxNote) {
        this.fhxNote = fhxNote;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<OrderSampleDetail> getSamples() {
        return samples;
    }

    public void setSamples(List<OrderSampleDetail> samples) {
        this.samples = samples;
    }

    public List<OrderPicUrl> getCasePics() {
        return casePics;
    }

    public void setCasePics(List<OrderPicUrl> casePics) {
        this.casePics = casePics;
    }

    public List<OrderPicUrl> getFamilyPic() {
        return familyPic;
    }

    public void setFamilyPic(List<OrderPicUrl> familyPic) {
        this.familyPic = familyPic;
    }

    public List<OrderProductDetail> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductDetail> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public OrderDatas getOrderDatas() {
        return orderDatas;
    }

    public void setOrderDatas(OrderDatas orderDatas) {
        this.orderDatas = orderDatas;
    }
}
