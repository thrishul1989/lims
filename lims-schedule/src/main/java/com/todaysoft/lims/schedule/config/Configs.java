package com.todaysoft.lims.schedule.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class Configs
{
    @Value("${datasource.driverClassName}")
    private String dataSourceDriver;
    
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
    
    @Value("${hibernate.sql.show}")
    private boolean hibernateShowSql;
    
    @Value("${hibernate.sql.format}")
    private boolean hibernateFormatSql;
    
    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    
    @Value("${aliyun.ons.url}")
    private String aliyunONSUrl;
    
    @Value("${aliyun.ons.access.key}")
    private String aliyunONSAccessKey;
    
    @Value("${aliyun.ons.secret.key}")
    private String aliyunONSSecretKey;
    
    @Value("${aliyun.ons.topic}")
    private String aliyunONSTopic;
    
    @Value("${aliyun.ons.consumer.schedule}")
    private String aliyunONSScheduleConsumer;
    
    public String getDataSourceDriver()
    {
        return dataSourceDriver;
    }
    
    public void setDataSourceDriver(String dataSourceDriver)
    {
        this.dataSourceDriver = dataSourceDriver;
    }
    
    public String getDataSourceUrl()
    {
        return dataSourceUrl;
    }
    
    public void setDataSourceUrl(String dataSourceUrl)
    {
        this.dataSourceUrl = dataSourceUrl;
    }
    
    public String getDataSourceUsername()
    {
        return dataSourceUsername;
    }
    
    public void setDataSourceUsername(String dataSourceUsername)
    {
        this.dataSourceUsername = dataSourceUsername;
    }
    
    public String getDataSourcePassword()
    {
        return dataSourcePassword;
    }
    
    public void setDataSourcePassword(String dataSourcePassword)
    {
        this.dataSourcePassword = dataSourcePassword;
    }
    
    public String getDataSourcePublicKey()
    {
        return dataSourcePublicKey;
    }
    
    public void setDataSourcePublicKey(String dataSourcePublicKey)
    {
        this.dataSourcePublicKey = dataSourcePublicKey;
    }
    
    public Integer getDataSourceMaxActive()
    {
        return dataSourceMaxActive;
    }
    
    public void setDataSourceMaxActive(Integer dataSourceMaxActive)
    {
        this.dataSourceMaxActive = dataSourceMaxActive;
    }
    
    public Integer getDataSourceInitialSize()
    {
        return dataSourceInitialSize;
    }
    
    public void setDataSourceInitialSize(Integer dataSourceInitialSize)
    {
        this.dataSourceInitialSize = dataSourceInitialSize;
    }
    
    public boolean isHibernateShowSql()
    {
        return hibernateShowSql;
    }
    
    public void setHibernateShowSql(boolean hibernateShowSql)
    {
        this.hibernateShowSql = hibernateShowSql;
    }
    
    public boolean isHibernateFormatSql()
    {
        return hibernateFormatSql;
    }
    
    public void setHibernateFormatSql(boolean hibernateFormatSql)
    {
        this.hibernateFormatSql = hibernateFormatSql;
    }
    
    public String getHibernateDialect()
    {
        return hibernateDialect;
    }
    
    public void setHibernateDialect(String hibernateDialect)
    {
        this.hibernateDialect = hibernateDialect;
    }
    
    public String getAliyunONSUrl()
    {
        return aliyunONSUrl;
    }
    
    public void setAliyunONSUrl(String aliyunONSUrl)
    {
        this.aliyunONSUrl = aliyunONSUrl;
    }
    
    public String getAliyunONSAccessKey()
    {
        return aliyunONSAccessKey;
    }
    
    public void setAliyunONSAccessKey(String aliyunONSAccessKey)
    {
        this.aliyunONSAccessKey = aliyunONSAccessKey;
    }
    
    public String getAliyunONSSecretKey()
    {
        return aliyunONSSecretKey;
    }
    
    public void setAliyunONSSecretKey(String aliyunONSSecretKey)
    {
        this.aliyunONSSecretKey = aliyunONSSecretKey;
    }
    
    public String getAliyunONSTopic()
    {
        return aliyunONSTopic;
    }
    
    public void setAliyunONSTopic(String aliyunONSTopic)
    {
        this.aliyunONSTopic = aliyunONSTopic;
    }
    
    public String getAliyunONSScheduleConsumer()
    {
        return aliyunONSScheduleConsumer;
    }
    
    public void setAliyunONSScheduleConsumer(String aliyunONSScheduleConsumer)
    {
        this.aliyunONSScheduleConsumer = aliyunONSScheduleConsumer;
    }
}
