package com.todaysoft.lims.invoice.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LIMS_CONTRACT")
public class Contract extends UuidKeyEntity
{
    
    /**
     * 业务库-合同主表
     */
    private static final long serialVersionUID = 1L;
    
    private String code;//合同编号
    
    private String name;//合同名称
    
    private String status;//合同状态：0-草稿 1-待确认 2-已确认
    
    private String remark;//合同备注
    
    private Integer marketingCenter;//营销中心，对应原有的检测类型
    
    private String signUser;//业务员
    
    private Date signDate;//签订日期
    
    private Date effectiveStart;//有效期-开始日期
    
    private Date effectiveEnd;//有效期-结束日期
    
    private String original;//合同原件
    
    private String creatorId;//创建人ID
    
    private String creatorName;//创建人姓名
    
    private Date createTime;//创建时间
    
    private String updateId;//最后一次更新人ID
    
    private String updateName;//最后一次更新人姓名
    
    private Date updateTime;//最后一次更新人时间
    
    private boolean deleted;//删除标记，0：未删除；1：已删除
    
    private Date deleteTime;//删除时间
    
    private String hospitalAdmited; //是否入院
    
    private String confirmerId;
    
    private String confirmerName;
    
    private Date confirmTime;
    
    private BigDecimal amount;
    
    private Integer incomingAmount;
    
    private Integer startMode;//启动方式
    
    private Integer invoiceForm;//开票形式 
    
    private String knotName;//结项人姓名
    
    private String knotId;//结项人id
    
    private Date knotTime;//结项日期
    private List<ContractProduct> conProducts = new ArrayList<ContractProduct>();

    @OneToMany(mappedBy = "contract", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    //控制方
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    public List<ContractProduct> getConProducts()
    {
        return conProducts;
    }

    public void setConProducts(List<ContractProduct> conProducts)
    {
        this.conProducts = conProducts;
    }

    
    @Column(name = "KNOT_NAME")
    public String getKnotName()
    {
        return knotName;
    }
    
    public void setKnotName(String knotName)
    {
        this.knotName = knotName;
    }
    
    @Column(name = "KNOT_ID")
    public String getKnotId()
    {
        return knotId;
    }
    
    public void setKnotId(String knotId)
    {
        this.knotId = knotId;
    }
    
    @Column(name = "KNOT_TIME")
    public Date getKnotTime()
    {
        return knotTime;
    }
    
    public void setKnotTime(Date knotTime)
    {
        this.knotTime = knotTime;
    }
    
    @Column(name = "START_MODE")
    public Integer getStartMode()
    {
        return startMode;
    }
    
    public void setStartMode(Integer startMode)
    {
        this.startMode = startMode;
    }
    
    @Column(name = "INVOICE_FORM")
    public Integer getInvoiceForm()
    {
        return invoiceForm;
    }
    
    public void setInvoiceForm(Integer invoiceForm)
    {
        this.invoiceForm = invoiceForm;
    }
    
    @Column(name = "AMOUNT")
    public BigDecimal getAmount()
    {
        return amount;
    }
    
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "INCOMING_AMOUNT")
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
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
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "EFFECTIVE_START")
    public Date getEffectiveStart()
    {
        return effectiveStart;
    }
    
    public void setEffectiveStart(Date effectiveStart)
    {
        this.effectiveStart = effectiveStart;
    }
    
    @Column(name = "EFFECTIVE_END")
    public Date getEffectiveEnd()
    {
        return effectiveEnd;
    }
    
    public void setEffectiveEnd(Date effectiveEnd)
    {
        this.effectiveEnd = effectiveEnd;
    }
    
    @Column(name = "CREATOR_ID")
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    @Column(name = "ORIGINAL")
    public String getOriginal()
    {
        return original;
    }
    
    public void setOriginal(String original)
    {
        this.original = original;
    }
    
    @Column(name = "CREATOR_NAME")
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
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
    
    @Column(name = "STATUS")
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
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
    
    @Column(name = "MARKETING_CENTER")
    public Integer getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(Integer marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    @Column(name = "SIGN_USER")
    public String getSignUser()
    {
        return signUser;
    }
    
    public void setSignUser(String signUser)
    {
        this.signUser = signUser;
    }
    
    @Column(name = "SIGN_DATE")
    public Date getSignDate()
    {
        return signDate;
    }
    
    public void setSignDate(Date signDate)
    {
        this.signDate = signDate;
    }
    
    @Column(name = "UPDATOR_ID")
    public String getUpdateId()
    {
        return updateId;
    }
    
    public void setUpdateId(String updateId)
    {
        this.updateId = updateId;
    }
    
    @Column(name = "UPDATOR_NAME")
    public String getUpdateName()
    {
        return updateName;
    }
    
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }
    
    @Column(name = "UPDATE_TIME")
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
    
    @Column(name = "DELETE_TIME")
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @Column(name = "CONFIRMER_ID")
    public String getConfirmerId()
    {
        return confirmerId;
    }
    
    public void setConfirmerId(String confirmerId)
    {
        this.confirmerId = confirmerId;
    }
    
    @Column(name = "CONFIRMER_NAME")
    public String getConfirmerName()
    {
        return confirmerName;
    }
    
    public void setConfirmerName(String confirmerName)
    {
        this.confirmerName = confirmerName;
    }
    
    @Column(name = "CONFIRM_TIME")
    public Date getConfirmTime()
    {
        return confirmTime;
    }
    
    public void setConfirmTime(Date confirmTime)
    {
        this.confirmTime = confirmTime;
    }
    
    @Column(name = "HOSPITAL_ADMITED")
    public String getHospitalAdmited()
    {
        return hospitalAdmited;
    }
    
    public void setHospitalAdmited(String hospitalAdmited)
    {
        this.hospitalAdmited = hospitalAdmited;
    }
}
