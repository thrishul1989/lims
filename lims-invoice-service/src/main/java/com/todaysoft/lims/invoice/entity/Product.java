package com.todaysoft.lims.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PRODUCT")
public class Product extends UuidKeyEntity
{
    private static final long serialVersionUID = -8842899239080161688L;
    
    private String testingInstitution;

    @Column(name = "PRICE")
    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Column(name = "TEST_INSTITUTION")
    public String getTestingInstitution()
    {
        return testingInstitution;
    }
    
    public void setTestingInstitution(String testingInstitution)
    {
        this.testingInstitution = testingInstitution;
    }
}
