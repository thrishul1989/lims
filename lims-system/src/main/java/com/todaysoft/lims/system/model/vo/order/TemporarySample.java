package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.model.vo.TestingtechnicalanalyrecordTempory;
import com.todaysoft.lims.system.model.vo.samplereceiving.ReceivedSample;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;

public class TemporarySample
{
    /**
     * 
     * 检测样本
     * **/
    private Order order;
    
    private Product product;
    
    private TestingMethod testingmethod;
    
    private OrderPrimarySample orderprimarysample;
    
    private OrderSubsidiarySample ordersubsidiarysample;
    
    private TestingSchedule schedule;
    
    private TestingScheduleHistory testingschedulehistory;
    
    private OrderResearchSample orderresearchsample;
    
    public ReceivedSample receivedsample;
    
    private TestingtechnicalanalyrecordTempory tempory;
    
    private String sequenceCode;
    
    private Date updateDate;

    private String testingMethodName;

    private String chromosomalLocation;

    private String sampleName;

    private Integer businessType;
    
    public TestingtechnicalanalyrecordTempory getTempory()
    {
        return tempory;
    }
    
    public void setTempory(TestingtechnicalanalyrecordTempory tempory)
    {
        this.tempory = tempory;
    }
    
    public ReceivedSample getReceivedsample()
    {
        return receivedsample;
    }
    
    public void setReceivedsample(ReceivedSample receivedsample)
    {
        this.receivedsample = receivedsample;
    }
    
    public TemporarySample(Product product, TestingMethod testingmethod, TestingSchedule schedule, TestingScheduleHistory testingschedulehistory,
        ReceivedSample receivedsample, TestingtechnicalanalyrecordTempory tempory, String sequenceCode,Date updateDate,String testingMethodName,String chromosomalLocation,String sampleName,Integer businessType)
    {
        super();
        this.product = product;
        this.testingmethod = testingmethod;
        this.schedule = schedule;
        this.testingschedulehistory = testingschedulehistory;
        this.receivedsample = receivedsample;
        this.tempory = tempory;
        this.sequenceCode = sequenceCode;
        this.updateDate = updateDate;
        this.testingMethodName = testingMethodName;
        this.chromosomalLocation = chromosomalLocation;
        this.sampleName = sampleName;
        this.businessType = businessType;
    }
    
    public TemporarySample(Product product, TestingMethod testingmethod, TestingSchedule schedule, TestingScheduleHistory testingschedulehistory,
        ReceivedSample receivedsample, String sequenceCode,Date updateDate)
    {
        super();
        this.product = product;
        this.testingmethod = testingmethod;
        this.schedule = schedule;
        this.testingschedulehistory = testingschedulehistory;
        this.receivedsample = receivedsample;
        this.sequenceCode = sequenceCode;
        this.updateDate = updateDate;
    }
    
    /*  public TemporarySample(Product product, TestingMethod testingmethod, TestingSchedule schedule, TestingScheduleHistory testingschedulehistory,
          TestingSample testingsample)
      {
          super();
          this.product = product;
          this.testingmethod = testingmethod;
          this.schedule = schedule;
          this.testingschedulehistory = testingschedulehistory;
          this.testingsample = testingsample;
      }*/
    
    public TemporarySample()
    {
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    public TestingMethod getTestingmethod()
    {
        return testingmethod;
    }
    
    public void setTestingmethod(TestingMethod testingmethod)
    {
        this.testingmethod = testingmethod;
    }
    
    public OrderPrimarySample getOrderprimarysample()
    {
        return orderprimarysample;
    }
    
    public void setOrderprimarysample(OrderPrimarySample orderprimarysample)
    {
        this.orderprimarysample = orderprimarysample;
    }
    
    public OrderSubsidiarySample getOrdersubsidiarysample()
    {
        return ordersubsidiarysample;
    }
    
    public void setOrdersubsidiarysample(OrderSubsidiarySample ordersubsidiarysample)
    {
        this.ordersubsidiarysample = ordersubsidiarysample;
    }
    
    public TestingSchedule getSchedule()
    {
        return schedule;
    }
    
    public void setSchedule(TestingSchedule schedule)
    {
        this.schedule = schedule;
    }
    
    public TestingScheduleHistory getTestingschedulehistory()
    {
        return testingschedulehistory;
    }
    
    public void setTestingschedulehistory(TestingScheduleHistory testingschedulehistory)
    {
        this.testingschedulehistory = testingschedulehistory;
    }
    
    public OrderResearchSample getOrderresearchsample()
    {
        return orderresearchsample;
    }
    
    public void setOrderresearchsample(OrderResearchSample orderresearchsample)
    {
        this.orderresearchsample = orderresearchsample;
    }
    
    public String getSequenceCode()
    {
        return sequenceCode;
    }
    
    public void setSequenceCode(String sequenceCode)
    {
        this.sequenceCode = sequenceCode;
    }

    public Date getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getChromosomalLocation() {
        return chromosomalLocation;
    }

    public void setChromosomalLocation(String chromosomalLocation) {
        this.chromosomalLocation = chromosomalLocation;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getTestingMethodName() {
        return testingMethodName;
    }

    public void setTestingMethodName(String testingMethodName) {
        this.testingMethodName = testingMethodName;
    }
}
