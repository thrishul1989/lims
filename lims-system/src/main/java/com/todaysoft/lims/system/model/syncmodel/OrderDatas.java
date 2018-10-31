package com.todaysoft.lims.system.model.syncmodel;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author dinghairong
 * @Date 2018/10/18 15:05
 * @Description
 */
public class OrderDatas {

    private List<ProductSample> productSamples = Lists.newArrayList();

    private List<ValidateSample> validateSamples = Lists.newArrayList();

    public List<ProductSample> getProductSamples() {
        return productSamples;
    }

    public void setProductSamples(List<ProductSample> productSamples) {
        this.productSamples = productSamples;
    }

    public List<ValidateSample> getValidateSamples() {
        return validateSamples;
    }

    public void setValidateSamples(List<ValidateSample> validateSamples) {
        this.validateSamples = validateSamples;
    }
}
