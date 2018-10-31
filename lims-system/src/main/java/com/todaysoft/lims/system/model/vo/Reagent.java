package com.todaysoft.lims.system.model.vo;

import java.util.Date;

public class Reagent
{
    
    private String id;
    
    private String code; //原料编号
    
    private String abbr; //缩写代号
    
    private String name;//原料名称
    
    private String specification;//规格（ml/瓶、盒、包、箱、支）
    
    private Date createTime;
    
    private Date deleteTime;
    
    private boolean delFlag;
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getAbbr()
    {
        return abbr;
    }
    
    public void setAbbr(String abbr)
    {
        this.abbr = abbr;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSpecification()
    {
        return specification;
    }
    
    public void setSpecification(String specification)
    {
        this.specification = specification;
    }
    
    /*public String getFeedSource() {
    	return feedSource;
    }

    public void setFeedSource(String feedSource) {
    	this.feedSource = feedSource;
    }

    public Date getManufactureDate() {
    	return manufactureDate;
    }

    public void setManufactureDate(Date manufactureDate) {
    	this.manufactureDate = manufactureDate;
    }

    public double getPrice() {
    	return price;
    }

    public void setPrice(double price) {
    	this.price = price;
    }

    public Date getReagentValidity() {
    	return reagentValidity;
    }

    public void setReagentValidity(Date reagentValidity) {
    	this.reagentValidity = reagentValidity;
    }

    public int getLeadTime() {
    	return leadTime;
    }

    public void setLeadTime(int leadTime) {
    	this.leadTime = leadTime;
    }


    public String getStorageLocation() {
    	return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
    	this.storageLocation = storageLocation;
    }

    public String getSupplier() {
    	return supplier;
    }

    public void setSupplier(String supplier) {
    	this.supplier = supplier;
    }

    public int getAmount() {
    	return amount;
    }

    public void setAmount(int amount) {
    	this.amount = amount;
    }

    public String getBewrite() {
    	return bewrite;
    }

    public void setBewrite(String bewrite) {
    	this.bewrite = bewrite;
    }

    public String getState() {
    	return state;
    }

    public void setState(String state) {
    	this.state = state;
    }

    public String getLeadingOfficial() {
    	return leadingOfficial;
    }

    public void setLeadingOfficial(String leadingOfficial) {
    	this.leadingOfficial = leadingOfficial;
    }
    */
    
}
