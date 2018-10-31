package com.todaysoft.lims.system.model.vo;

import java.util.Date;

import com.todaysoft.lims.utils.excel.annotation.ExcelField;

public class Company
{
    private String id;
    
    private String name;//单位名称
    
    private String py;//首拼
    
    private String pinyin;//全拼
    
    private String code;//单位编码
    
    private String level;//单位等级
    
    private Area province;//所在区域-省
    
    private Area city;//所在区域-市
    
    private Area county;//所在区域-县
    
    private String areaId;//地区唯一标示
    
    private String address;//单位地址
    
    private String hotline;//联系电话
    
    private String website;//官方网站
    
    private String remarks;//备注
    
    private String creator;//创建者
    
    private Date createTime;//创建时间
    
    private String updater;//更新者
    
    private Date updateTime;//更新时间
    
    private boolean deleted;//删除标记(0正常1删除)
    
    private String otherName;
    
    private String provinceName;
    
    private String cityName;
    
    private String countyName;
    
    private String some;
    
    @ExcelField(title = "序号", align = 1, sort = 1)
    public String getSome()
    {
        return some;
    }
    
    public void setSome(String some)
    {
        this.some = some;
    }
    
    public String getProvinceName()
    {
        return provinceName;
    }
    
    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }
    
    public String getCityName()
    {
        return cityName;
    }
    
    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }
    
    public String getCountyName()
    {
        return countyName;
    }
    
    public void setCountyName(String countyName)
    {
        this.countyName = countyName;
    }
    
    public String getOtherName()
    {
        return otherName;
    }
    
    public void setOtherName(String otherName)
    {
        this.otherName = otherName;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @ExcelField(title = "单位名称", align = 1, sort = 10)
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPy()
    {
        return py;
    }
    
    public void setPy(String py)
    {
        this.py = py;
    }
    
    public String getPinyin()
    {
        return pinyin;
    }
    
    public void setPinyin(String pinyin)
    {
        this.pinyin = pinyin;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getLevel()
    {
        return level;
    }
    
    public void setLevel(String level)
    {
        this.level = level;
    }
    
    @ExcelField(title = "省", align = 1, sort = 20)
    //    @ExcelField(title = "省", align = 1, sort = 20)
    public Area getProvince()
    {
        return province;
    }
    
    public void setProvince(Area province)
    {
        this.province = province;
    }
    
    @ExcelField(title = "市", align = 1, sort = 30)
    //    @ExcelField(title = "市", align = 1, sort = 30)
    public Area getCity()
    {
        return city;
    }
    
    public void setCity(Area city)
    {
        this.city = city;
    }
    
    @ExcelField(title = "区", align = 1, sort = 40)
    //    @ExcelField(title = "区", align = 1, sort = 40)
    public Area getCounty()
    {
        return county;
    }
    
    public void setCounty(Area county)
    {
        this.county = county;
    }
    
    public String getAreaId()
    {
        return areaId;
    }
    
    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }
    
    @ExcelField(title = "详细地址", align = 1, sort = 50)
    // @ExcelField(title = "详细地址", align = 1, sort = 50)
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getHotline()
    {
        return hotline;
    }
    
    public void setHotline(String hotline)
    {
        this.hotline = hotline;
    }
    
    public String getWebsite()
    {
        return website;
    }
    
    public void setWebsite(String website)
    {
        this.website = website;
    }
    
    public String getRemarks()
    {
        return remarks;
    }
    
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }
    
    public String getCreator()
    {
        return creator;
    }
    
    public void setCreator(String creator)
    {
        this.creator = creator;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getUpdater()
    {
        return updater;
    }
    
    public void setUpdater(String updater)
    {
        this.updater = updater;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
}
