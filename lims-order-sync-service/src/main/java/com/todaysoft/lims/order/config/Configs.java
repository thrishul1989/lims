package com.todaysoft.lims.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class Configs {
    @Value("${datasource.driverClassName}")
    private String dataSourceDriverClassName;

    @Value("${datasource.url}")
    private String dataSourceUrl;

    @Value("${datasource.username}")
    private String dataSourceUsername;

    @Value("${datasource.password}")
    private String dataSourcePassword;

    @Value("${datasource.publicKey}")
    private String dataSourcePublicKey;

    @Value("${datasource.max.active}")
    private Integer dataSourceMaxActive;

    @Value("${datasource.initial.size}")
    private Integer dataSourceInitialSize;

    public String getDataSourceDriverClassName() {
        return dataSourceDriverClassName;
    }

    public void setDataSourceDriverClassName(String dataSourceDriverClassName) {
        this.dataSourceDriverClassName = dataSourceDriverClassName;
    }

    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    public void setDataSourceUrl(String dataSourceUrl) {
        this.dataSourceUrl = dataSourceUrl;
    }

    public String getDataSourceUsername() {
        return dataSourceUsername;
    }

    public void setDataSourceUsername(String dataSourceUsername) {
        this.dataSourceUsername = dataSourceUsername;
    }

    public String getDataSourcePassword() {
        return dataSourcePassword;
    }

    public void setDataSourcePassword(String dataSourcePassword) {
        this.dataSourcePassword = dataSourcePassword;
    }

    public String getDataSourcePublicKey() {
        return dataSourcePublicKey;
    }

    public void setDataSourcePublicKey(String dataSourcePublicKey) {
        this.dataSourcePublicKey = dataSourcePublicKey;
    }

    public Integer getDataSourceMaxActive() {
        return dataSourceMaxActive;
    }

    public void setDataSourceMaxActive(Integer dataSourceMaxActive) {
        this.dataSourceMaxActive = dataSourceMaxActive;
    }

    public Integer getDataSourceInitialSize() {
        return dataSourceInitialSize;
    }

    public void setDataSourceInitialSize(Integer dataSourceInitialSize) {
        this.dataSourceInitialSize = dataSourceInitialSize;
    }

}
