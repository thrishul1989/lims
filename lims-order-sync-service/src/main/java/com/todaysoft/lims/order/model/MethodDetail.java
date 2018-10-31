package com.todaysoft.lims.order.model;

/**
 * @Author dinghairong
 * @Date 2018/10/18 15:23
 * @Description
 */
public class MethodDetail {

    private String productKey;

    private String methodKey;

    private String methodName;

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getMethodKey() {
        return methodKey;
    }

    public void setMethodKey(String methodKey) {
        this.methodKey = methodKey;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
