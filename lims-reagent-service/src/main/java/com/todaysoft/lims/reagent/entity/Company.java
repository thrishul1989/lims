package com.todaysoft.lims.reagent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "APP_COMPANY")
public class Company extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
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
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "PY")
    public String getPy()
    {
        return py;
    }
    
    public void setPy(String py)
    {
        this.py = py;
    }
    
    @Column(name = "PINYIN")
    public String getPinyin()
    {
        return pinyin;
    }
    
    public void setPinyin(String pinyin)
    {
        this.pinyin = pinyin;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "LEVEL")
    public String getLevel()
    {
        return level;
    }
    
    public void setLevel(String level)
    {
        this.level = level;
    }
    
   
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROVINCE")
    @NotFound(action = NotFoundAction.IGNORE)
    public Area getProvince()
    {
        return province;
    }

    public void setProvince(Area province)
    {
        this.province = province;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CITY")
    @NotFound(action = NotFoundAction.IGNORE)
    public Area getCity()
    {
        return city;
    }

    public void setCity(Area city)
    {
        this.city = city;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COUNTY")
    @NotFound(action = NotFoundAction.IGNORE)
    public Area getCounty()
    {
        return county;
    }

    public void setCounty(Area county)
    {
        this.county = county;
    }

    @Column(name = "AREA_ID")
    public String getAreaId()
    {
        return areaId;
    }
    
    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }
    
    @Column(name = "ADDRESS")
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    @Column(name = "HOTLINE")
    public String getHotline()
    {
        return hotline;
    }
    
    public void setHotline(String hotline)
    {
        this.hotline = hotline;
    }
    
    @Column(name = "WEBSITE")
    public String getWebsite()
    {
        return website;
    }
    
    public void setWebsite(String website)
    {
        this.website = website;
    }
    
    @Column(name = "REMARKS")
    public String getRemarks()
    {
        return remarks;
    }
    
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }
    
    @Column(name = "CREATE_BY")
    public String getCreator()
    {
        return creator;
    }
    
    public void setCreator(String creator)
    {
        this.creator = creator;
    }
    
    @Column(name = "CREATE_DATE")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "UPDATE_BY")
    public String getUpdater()
    {
        return updater;
    }
    
    public void setUpdater(String updater)
    {
        this.updater = updater;
    }
    
    @Column(name = "UPDATE_DATE")
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Transient
    public String getOtherName()
    {
        return otherName;
    }
    
    public void setOtherName(String otherName)
    {
        this.otherName = otherName;
    }
}
