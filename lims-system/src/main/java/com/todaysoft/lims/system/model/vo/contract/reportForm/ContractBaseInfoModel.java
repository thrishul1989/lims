package com.todaysoft.lims.system.model.vo.contract.reportForm;

import java.util.List;

public class ContractBaseInfoModel
{
    private String code;//合同编号
    private String name;//合同名称
    private String status;//合同状态
    private String startTime;//有效期开始
    private String endTime;//截止日期
    private String marketingCenter;//营销中心
    private String signUser;//业务员
    private String signDate;//签订日期
    private String hospitalAdmited;//是否入院
    private String invoiceMethod;//开票形式
    private String startMode;//启动方式
    private String remark;//备注
    private List<PartyAInfo> partyAInfoList;
    private List<PartyBInfo> partyBInfoList;
    private List<SuccessDelivery> successDeliveryList;
    private List<SettlementInfo> settlementInfoList;
    private List<ProductInfo> productInfoList;
    private List<TestInfo> testInfoList;
    private List<ContractOriginal> contractOriginalList;
    private List<ContractUserInfo> contractUserInfoList;
    private List<ChangeUserInfo> changeUserInfoList;
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getStartTime()
    {
        return startTime;
    }
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    public String getEndTime()
    {
        return endTime;
    }
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    public String getSignUser()
    {
        return signUser;
    }
    public void setSignUser(String signUser)
    {
        this.signUser = signUser;
    }
    public String getSignDate()
    {
        return signDate;
    }
    public void setSignDate(String signDate)
    {
        this.signDate = signDate;
    }
    public String getHospitalAdmited()
    {
        return hospitalAdmited;
    }
    public void setHospitalAdmited(String hospitalAdmited)
    {
        this.hospitalAdmited = hospitalAdmited;
    }
    public String getInvoiceMethod()
    {
        return invoiceMethod;
    }
    public void setInvoiceMethod(String invoiceMethod)
    {
        this.invoiceMethod = invoiceMethod;
    }
    public String getStartMode()
    {
        return startMode;
    }
    public void setStartMode(String startMode)
    {
        this.startMode = startMode;
    }
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    public List<PartyAInfo> getPartyAInfoList()
    {
        return partyAInfoList;
    }
    public void setPartyAInfoList(List<PartyAInfo> partyAInfoList)
    {
        this.partyAInfoList = partyAInfoList;
    }
    public List<PartyBInfo> getPartyBInfoList()
    {
        return partyBInfoList;
    }
    public void setPartyBInfoList(List<PartyBInfo> partyBInfoList)
    {
        this.partyBInfoList = partyBInfoList;
    }
    public List<SuccessDelivery> getSuccessDeliveryList()
    {
        return successDeliveryList;
    }
    public void setSuccessDeliveryList(List<SuccessDelivery> successDeliveryList)
    {
        this.successDeliveryList = successDeliveryList;
    }
    public List<SettlementInfo> getSettlementInfoList()
    {
        return settlementInfoList;
    }
    public void setSettlementInfoList(List<SettlementInfo> settlementInfoList)
    {
        this.settlementInfoList = settlementInfoList;
    }
    public List<ProductInfo> getProductInfoList()
    {
        return productInfoList;
    }
    public void setProductInfoList(List<ProductInfo> productInfoList)
    {
        this.productInfoList = productInfoList;
    }
    public List<TestInfo> getTestInfoList()
    {
        return testInfoList;
    }
    public void setTestInfoList(List<TestInfo> testInfoList)
    {
        this.testInfoList = testInfoList;
    }
    public List<ContractOriginal> getContractOriginalList()
    {
        return contractOriginalList;
    }
    public void setContractOriginalList(List<ContractOriginal> contractOriginalList)
    {
        this.contractOriginalList = contractOriginalList;
    }
    public List<ContractUserInfo> getContractUserInfoList()
    {
        return contractUserInfoList;
    }
    public void setContractUserInfoList(List<ContractUserInfo> contractUserInfoList)
    {
        this.contractUserInfoList = contractUserInfoList;
    }
    public List<ChangeUserInfo> getChangeUserInfoList()
    {
        return changeUserInfoList;
    }
    public void setChangeUserInfoList(List<ChangeUserInfo> changeUserInfoList)
    {
        this.changeUserInfoList = changeUserInfoList;
    }
    
}
