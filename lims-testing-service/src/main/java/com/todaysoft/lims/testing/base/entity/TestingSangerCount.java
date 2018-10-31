package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LIMS_TESTING_SANGER_TEST_COUNT")
public class TestingSangerCount extends UuidKeyEntity
{

    private static final long serialVersionUID = -4174053390504187248L;

    private TestingSchedule schedule;

    private Integer totalNum;

    private Integer cancerNum;

    private Integer completeNum;


    @OneToOne
    @JoinColumn(name = "SCHEDULE_ID")
    public TestingSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(TestingSchedule schedule) {
        this.schedule = schedule;
    }

    @Column(name="TOTAL_NUM")
    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    @Column(name="CANCEL_NUM")
    public Integer getCancerNum() {
        return cancerNum;
    }

    public void setCancerNum(Integer cancerNum) {
        this.cancerNum = cancerNum;
    }

    @Column(name="COMPLETE_NUM")
    public Integer getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(Integer completeNum) {
        this.completeNum = completeNum;
    }
}
