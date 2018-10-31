package com.todaysoft.lims.report.entity.system;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "REPORT_SYSTEM_CUSTOMER_INFO")
public class ReprotSystemCustomerInfo extends UuidKeyEntity
{
    private String taskId;

    private String userId;

    private String customerName;

    private String sex;

    private String activateStatus;

    private String companyName;

    private String dept;

    private String email;

    private String phoneNum;

    private String roomNum;

    private String landLine;

    private String birthday;

    private String position;

    private String cooperate;

    private String analysisType;

    private String address;

    private String characteristic;

    private String researchFiled;

    private String introduction;

    private Integer countSubCustomer;

    private String subCustomer;

    private String creatorName;

    private String currentName;

    @Column(name="TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Column(name="USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name="CUSTOMER_NAME")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Column(name="SEX")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name="ACTIVATE_STATUS")
    public String getActivateStatus() {
        return activateStatus;
    }

    public void setActivateStatus(String activateStatus) {
        this.activateStatus = activateStatus;
    }

    @Column(name="COMPANY_NAME")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name="DEPT")
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Column(name="EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="PHONE_NUM")
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Column(name="ROOM_NUM")
    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    @Column(name="LANDLINE")
    public String getLandLine() {
        return landLine;
    }

    public void setLandLine(String landLine) {
        this.landLine = landLine;
    }

    @Column(name="BIRTHDAY")
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Column(name="POSITION")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name="COOPERATE")
    public String getCooperate() {
        return cooperate;
    }

    public void setCooperate(String cooperate) {
        this.cooperate = cooperate;
    }

    @Column(name="ANALYSIS_TYPE")
    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    @Column(name="ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name="CHARACTERISTIC")
    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    @Column(name="RESEARCH_FILED")
    public String getResearchFiled() {
        return researchFiled;
    }

    public void setResearchFiled(String researchFiled) {
        this.researchFiled = researchFiled;
    }

    @Column(name="INTRODUCTION")
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Column(name="CountSubCustomer")
    public Integer getCountSubCustomer() {
        return countSubCustomer;
    }

    public void setCountSubCustomer(Integer countSubCustomer) {
        this.countSubCustomer = countSubCustomer;
    }

    @Column(name="SubCustomer")
    public String getSubCustomer() {
        return subCustomer;
    }

    public void setSubCustomer(String subCustomer) {
        this.subCustomer = subCustomer;
    }

    @Column(name="CreatorName")
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Column(name="CurrentName")
    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
}
