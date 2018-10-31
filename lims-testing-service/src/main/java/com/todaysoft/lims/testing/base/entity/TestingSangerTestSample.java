package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;

@Entity
@Table(name = "LIMS_TESTING_SANGER_TEST_SAMPLE")
public class TestingSangerTestSample extends UuidKeyEntity
{
    private static final long serialVersionUID = -2353741272926277709L;

    private String scheduleId;

    private TestingSample outputSample;

    private TestingTask testingTask;

    private Primer primer;

    private String combineCode;

    @ManyToOne
    @JoinColumn(name="OUTPUT_SAMPLE_ID")
    public TestingSample getOutputSample() {
        return outputSample;
    }

    public void setOutputSample(TestingSample outputSample) {
        this.outputSample = outputSample;
    }

    @Column(name="SCHEDULE_ID")
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    @OneToOne
    @JoinColumn(name="TASK_ID")
    public TestingTask getTestingTask() {
        return testingTask;
    }

    public void setTestingTask(TestingTask testingTask) {
        this.testingTask = testingTask;
    }

    @ManyToOne
    @JoinColumn(name="PRIMER_ID")
    public Primer getPrimer() {
        return primer;
    }

    public void setPrimer(Primer primer) {
        this.primer = primer;
    }

    @Column(name="COMBINE_CODE")
    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }
}
