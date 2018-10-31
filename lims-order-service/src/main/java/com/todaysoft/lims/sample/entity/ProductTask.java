package com.todaysoft.lims.sample.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.AutoKeyEntity;

import javax.persistence.*;

/**
 * Created by HSHY-032 on 2016/7/6.
 */
@Entity
@Table(name = "LIMS_PRODUCT_TASK")
public class ProductTask  extends AutoKeyEntity {

    private Product product;  //产品id

    private Integer taskId;    //任务id

    private Integer taskOrder;  //产品关联的任务配置顺序

    @ManyToOne(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinColumn(name="PRODUCT_ID")
    @JsonIgnore
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "TASK_ID")
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Column(name = "TASK_ORDER")
    public Integer getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(Integer taskOrder) {
        this.taskOrder = taskOrder;
    }
}
