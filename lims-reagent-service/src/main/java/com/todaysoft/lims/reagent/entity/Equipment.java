package com.todaysoft.lims.reagent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_EQUIPMENT")
public class Equipment extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7656401382739867514L;
    
    private String equipmentNo; // 设备编号
    
    private String name; // 设备名称
    
    private String serialNo; // 机身编号
    
    private String specificationNo; // 规格编号
    
    private Double price; // 价格
    
    private String vendor; // 生产厂商
    
    private Date acceptDate; // 接受日期
    
    private Date useDate; // 使用日期
    
    private String purpose; // 用途
    
    private String principal; // 设备负责人
    
    private String status; // 状态
    
    private String serviceMan; // 维修联系人
    
    private String servicePhone; // 维修联系电话
    
    private String position; //摆放位置
    
    private String remark; //备注
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    @Column(name = "EQUIPMENT_NO")
    public String getEquipmentNo()
    {
        return equipmentNo;
    }
    
    public void setEquipmentNo(String equipmentNo)
    {
        this.equipmentNo = equipmentNo;
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
    
    @Column(name = "SERIAL_NO")
    public String getSerialNo()
    {
        return serialNo;
    }
    
    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }
    
    @Column(name = "SPECIFICATION_NO")
    public String getSpecificationNo()
    {
        return specificationNo;
    }
    
    public void setSpecificationNo(String specificationNo)
    {
        this.specificationNo = specificationNo;
    }
    
    @Column(name = "PRICE")
    public Double getPrice()
    {
        return price;
    }
    
    public void setPrice(Double price)
    {
        this.price = price;
    }
    
    @Column(name = "VENDOR")
    public String getVendor()
    {
        return vendor;
    }
    
    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }
    
    @Column(name = "ACCEPT_DATE")
    public Date getAcceptDate()
    {
        return acceptDate;
    }
    
    public void setAcceptDate(Date acceptDate)
    {
        this.acceptDate = acceptDate;
    }
    
    @Column(name = "USE_DATE")
    public Date getUseDate()
    {
        return useDate;
    }
    
    public void setUseDate(Date useDate)
    {
        this.useDate = useDate;
    }
    
    @Column(name = "PURPOSE")
    public String getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(String purpose)
    {
        this.purpose = purpose;
    }
    
    @Column(name = "PRINCIPAL")
    public String getPrincipal()
    {
        return principal;
    }
    
    public void setPrincipal(String principal)
    {
        this.principal = principal;
    }
    
    @Column(name = "STATUS")
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Column(name = "SERVICE_MAN")
    public String getServiceMan()
    {
        return serviceMan;
    }
    
    public void setServiceMan(String serviceMan)
    {
        this.serviceMan = serviceMan;
    }
    
    @Column(name = "SERVICE_PHONE")
    public String getServicePhone()
    {
        return servicePhone;
    }
    
    public void setServicePhone(String servicePhone)
    {
        this.servicePhone = servicePhone;
    }
    
    @Column(name = "POSITION")
    public String getPosition()
    {
        return position;
    }
    
    public void setPosition(String position)
    {
        this.position = position;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DELETE_TIME")
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
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
    
}
