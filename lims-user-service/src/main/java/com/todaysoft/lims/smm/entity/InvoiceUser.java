package com.todaysoft.lims.smm.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_INVOICE_USER")
public class InvoiceUser extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -2750417728764636392L;
    
    private String testInstitution;//检测机构
    
    private List<InvoiceUserModel> userList;
    
    private boolean delFlag;
    
    @Column(name = "TEST_INSTITUTION")
    public String getTestInstitution()
    {
        return testInstitution;
    }
    
    public void setTestInstitution(String testInstitution)
    {
        this.testInstitution = testInstitution;
    }
    
    @OneToMany(mappedBy = "invoiceUser", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<InvoiceUserModel> getUserList()
    {
        return userList;
    }
    
    public void setUserList(List<InvoiceUserModel> userList)
    {
        this.userList = userList;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
}
