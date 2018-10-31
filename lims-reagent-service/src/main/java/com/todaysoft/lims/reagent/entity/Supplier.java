package com.todaysoft.lims.reagent.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "LIMS_SUPPLIER")
public class Supplier extends AutoKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2355997690261172076L;
    
    private String code;
    
    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    
    public List<SupplierContacter> getSupplierContacterList()
    {
        return supplierContacterList;
    }
    
    public void setSupplierContacterList(List<SupplierContacter> supplierContacterList)
    {
        this.supplierContacterList = supplierContacterList;
    }
    
    private String name;
    
    private String address;
    
    private List<SupplierContacter> supplierContacterList;
    
    private List<SupplierReagentKit> supplierReagentKitList;
    
    private List<SupplierReagent> supplierReagentList;
    
    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public List<SupplierReagent> getSupplierReagentList()
    {
        return supplierReagentList;
    }
    
    public void setSupplierReagentList(List<SupplierReagent> supplierReagentList)
    {
        this.supplierReagentList = supplierReagentList;
    }
    
    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public List<SupplierReagentKit> getSupplierReagentKitList()
    {
        return supplierReagentKitList;
    }
    
    public void setSupplierReagentKitList(List<SupplierReagentKit> supplierReagentKitList)
    {
        this.supplierReagentKitList = supplierReagentKitList;
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
    
    @Column(name = "ADDRESS")
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    @Transient
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    @Transient
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
}
