package com.todaysoft.lims.system.model.vo.order.customerOrderReportForm;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class ReportSystemCustomerInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = -4804713763532826810L;

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
    private String countSubCustomer;
    private String subCustomer;
    private String creatorName;
    private String currentName;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getActivateStatus() {
        return activateStatus;
    }

    public void setActivateStatus(String activateStatus) {
        this.activateStatus = activateStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getLandLine() {
        return landLine;
    }

    public void setLandLine(String landLine) {
        this.landLine = landLine;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCooperate() {
        return cooperate;
    }

    public void setCooperate(String cooperate) {
        this.cooperate = cooperate;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getResearchFiled() {
        return researchFiled;
    }

    public void setResearchFiled(String researchFiled) {
        this.researchFiled = researchFiled;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    public String getCountSubCustomer() {
        return countSubCustomer;
    }

    public void setCountSubCustomer(String countSubCustomer) {
        this.countSubCustomer = countSubCustomer;
    }

    public String getSubCustomer() {
        return subCustomer;
    }

    public void setSubCustomer(String subCustomer) {
        this.subCustomer = subCustomer;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
}
