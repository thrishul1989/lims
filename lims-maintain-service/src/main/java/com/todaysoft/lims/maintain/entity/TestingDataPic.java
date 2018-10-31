package com.todaysoft.lims.maintain.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "LIMS_TESTING_DATA_PIC")
public class TestingDataPic extends UuidKeyEntity
{
    private String sheetId;

    private String orderId;

    private String productId;

    private String sampleId;

    private String methodId;

    private String dataCode;

    private String picName;

    private String picUrl;


    @Column(name="SHEET_ID")
    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    @Column(name="ORDER_ID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name="PRODUCT_ID")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name="SAMPLE_ID")
    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    @Column(name="METHOD_ID")
    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    @Column(name="DATA_CODE")
    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    @Column(name="PIC_URL")
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Column(name="PIC_NAME")
    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }
}
