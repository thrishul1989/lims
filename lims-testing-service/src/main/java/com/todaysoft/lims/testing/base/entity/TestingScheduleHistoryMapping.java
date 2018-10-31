package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LIMS_SCHEDULE_HISTORY_MAPPING")
public class TestingScheduleHistoryMapping implements Serializable
{
    private static final long serialVersionUID = 4214375119882181362L;
    
    private String id;
    
    private TestingSchedule schedule;
    
    private Order order;
    
    private Product product;
    
    private TestingMethod testingMethod;
    
    private String taskId;
    
    private TestingSample inputSample;
    
    @Id
    @Column(name = "ID")
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SCHEDULE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingSchedule getSchedule()
    {
        return schedule;
    }
    
    public void setSchedule(TestingSchedule schedule)
    {
        this.schedule = schedule;
    }
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ORDER_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PRODUCT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "METHOD_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingMethod getTestingMethod()
    {
        return testingMethod;
    }
    
    public void setTestingMethod(TestingMethod testingMethod)
    {
        this.testingMethod = testingMethod;
    }
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @ManyToOne
    @JoinColumn(name = "INPUT_SAMPLE_ID")
    public TestingSample getInputSample()
    {
        return inputSample;
    }
    
    public void setInputSample(TestingSample inputSample)
    {
        this.inputSample = inputSample;
    }
}
