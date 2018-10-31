package com.todaysoft.lims.system.model.vo.order.customerOrderReportForm;

import com.todaysoft.lims.system.model.vo.BusinessInfo;
import com.todaysoft.lims.system.model.vo.Customer;

import java.util.List;

public class CustomerInfoModel
{
//    -------------客户信息----------
    private String customerName;//客户姓名
    private String sex;//性别
    private String activateStatus;//激活状态:0 未激活 1已激活
    private String companyName;//所属单位
    private String dept;//所属科室
    private String email;//邮箱
    private String phoneNum;//手机号
    private String roomNum;//办公楼/房间号
    private String landLine;//座机
    private String birthday;//出生日期
    private String position;//职位/职称
    private String cooperate;//合作方向
    private String analyseType;//客户参与方式
    private String address;//单位地址
    private String characteristic;//就诊特点
    private String researchFiled;//擅长疾病，领域
    private String introduction;//简介
    private Integer countSubCustomer;//附属账号数，额外字段附属账号信息
    private String subCustomerList;//附属账号信息
    private String creatorName;//初始业务员
    private String currentName;//当前业务员

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

    public String getAnalyseType() {
        return analyseType;
    }

    public void setAnalyseType(String analyseType) {
        this.analyseType = analyseType;
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

    public Integer getCountSubCustomer() {
        return countSubCustomer;
    }

    public void setCountSubCustomer(Integer countSubCustomer) {
        this.countSubCustomer = countSubCustomer;
    }

    public String getSubCustomerList() {
        return subCustomerList;
    }

    public void setSubCustomerList(String subCustomerList) {
        this.subCustomerList = subCustomerList;
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
