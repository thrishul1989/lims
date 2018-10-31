package com.todaysoft.lims.testing.base.entity;


import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="LIMS_PROBE")
public class Probe extends UuidKeyEntity implements Serializable
{
    private String code;

    private String name;

    private String testingMethodIds;

    private boolean deleted;

    private Integer enabled;

    @Column(name="CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name="NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="TESTING_PLATFORM")
    public String getTestingMethodIds() {
        return testingMethodIds;
    }

    public void setTestingMethodIds(String testingMethodIds) {
        this.testingMethodIds = testingMethodIds;
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

    @Column(name = "ENABLED")
    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
