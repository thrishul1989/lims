package com.todaysoft.lims.testing.base.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_QT_SEQUENCING")
public class SequencingRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = 7592484208458835126L;
    
    private TestingSample sample;
    
    private boolean reference;
    
    private String qtSheetId;
    
    private BigDecimal qtQbitValue;
    
    private BigDecimal qtQPCRValue;
    
    private BigDecimal qt2100Value;
    
    private Integer qtFragmentLength;
    
    private BigDecimal qtSequencingConcn;
    
    private Date qtTime;
    
    private String sequencingSheetId;
    
    private Integer sequencingCluster;
    
    private BigDecimal sequencingEffectiveRate;
    
    private BigDecimal sequencingEffectiveSize;
    
    private BigDecimal sequencingQ30;
    
    private Date sequencingTime;
    
    @ManyToOne
    @JoinColumn(name = "SAMPLE_ID")
    public TestingSample getSample()
    {
        return sample;
    }
    
    public void setSample(TestingSample sample)
    {
        this.sample = sample;
    }
    
    @Column(name = "IS_REFERENCE")
    public boolean isReference()
    {
        return reference;
    }
    
    public void setReference(boolean reference)
    {
        this.reference = reference;
    }
    
    @Column(name = "QT_SHEET_ID")
    public String getQtSheetId()
    {
        return qtSheetId;
    }
    
    public void setQtSheetId(String qtSheetId)
    {
        this.qtSheetId = qtSheetId;
    }
    
    @Column(name = "QT_QBIT_VALUE")
    public BigDecimal getQtQbitValue()
    {
        return qtQbitValue;
    }
    
    public void setQtQbitValue(BigDecimal qtQbitValue)
    {
        this.qtQbitValue = qtQbitValue;
    }
    
    @Column(name = "QT_QPCR_VALUE")
    public BigDecimal getQtQPCRValue()
    {
        return qtQPCRValue;
    }
    
    public void setQtQPCRValue(BigDecimal qtQPCRValue)
    {
        this.qtQPCRValue = qtQPCRValue;
    }
    
    @Column(name = "QT_2100_VALUE")
    public BigDecimal getQt2100Value()
    {
        return qt2100Value;
    }
    
    public void setQt2100Value(BigDecimal qt2100Value)
    {
        this.qt2100Value = qt2100Value;
    }
    
    @Column(name = "QT_FRAGMENT_LENGTH")
    public Integer getQtFragmentLength()
    {
        return qtFragmentLength;
    }
    
    public void setQtFragmentLength(Integer qtFragmentLength)
    {
        this.qtFragmentLength = qtFragmentLength;
    }
    
    @Column(name = "QT_SEQUENCING_CONCN")
    public BigDecimal getQtSequencingConcn()
    {
        return qtSequencingConcn;
    }
    
    public void setQtSequencingConcn(BigDecimal qtSequencingConcn)
    {
        this.qtSequencingConcn = qtSequencingConcn;
    }
    
    @Column(name = "QT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getQtTime()
    {
        return qtTime;
    }
    
    public void setQtTime(Date qtTime)
    {
        this.qtTime = qtTime;
    }
    
    @Column(name = "SEQUENCING_SHEET_ID")
    public String getSequencingSheetId()
    {
        return sequencingSheetId;
    }
    
    public void setSequencingSheetId(String sequencingSheetId)
    {
        this.sequencingSheetId = sequencingSheetId;
    }
    
    @Column(name = "SEQUENCING_CLUSTER")
    public Integer getSequencingCluster()
    {
        return sequencingCluster;
    }
    
    public void setSequencingCluster(Integer sequencingCluster)
    {
        this.sequencingCluster = sequencingCluster;
    }
    
    @Column(name = "SEQUENCING_ER")
    public BigDecimal getSequencingEffectiveRate()
    {
        return sequencingEffectiveRate;
    }
    
    public void setSequencingEffectiveRate(BigDecimal sequencingEffectiveRate)
    {
        this.sequencingEffectiveRate = sequencingEffectiveRate;
    }
    
    @Column(name = "SEQUENCING_ES")
    public BigDecimal getSequencingEffectiveSize()
    {
        return sequencingEffectiveSize;
    }
    
    public void setSequencingEffectiveSize(BigDecimal sequencingEffectiveSize)
    {
        this.sequencingEffectiveSize = sequencingEffectiveSize;
    }
    
    @Column(name = "SEQUENCING_Q30")
    public BigDecimal getSequencingQ30()
    {
        return sequencingQ30;
    }
    
    public void setSequencingQ30(BigDecimal sequencingQ30)
    {
        this.sequencingQ30 = sequencingQ30;
    }
    
    @Column(name = "SEQUENCING_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSequencingTime()
    {
        return sequencingTime;
    }
    
    public void setSequencingTime(Date sequencingTime)
    {
        this.sequencingTime = sequencingTime;
    }
}
