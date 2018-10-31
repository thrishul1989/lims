package com.todaysoft.lims.invoice.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_USER")
public class User extends UuidKeyEntity
{
    private static final long serialVersionUID = 7665484135147093640L;
    
    private String username;
    
    private String password;
    
    /* private List<ProductPrincipal> principalList;
     
     @OneToMany(mappedBy = "principal", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
     @Fetch(FetchMode.SUBSELECT)
     @NotFound(action = NotFoundAction.IGNORE)
     @JsonIgnore
     public List<ProductPrincipal> getPrincipalList()
     {
         return principalList;
     }
     
     public void setPrincipalList(List<ProductPrincipal> principalList)
     {
         this.principalList = principalList;
     }*/
    
    @Column(name = "USERNAME")
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    @Column(name = "PASSWORD")
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
}
