package com.todaysoft.lims.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by HSHY-032 on 2016/7/6.
 */
public class ProductTask {

    private Integer id;

    private Product product;  //产品id

    private Integer taskId;    //任务id

    private Integer taskOrder;  //产品关联的任务配置顺序

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(Integer taskOrder) {
        this.taskOrder = taskOrder;
    }
}
