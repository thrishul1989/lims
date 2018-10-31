package com.todaysoft.lims.testing.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class Configs
{
    @Value("${jdbc.driverClassName}")
    private String jdbcDriver;
    
    @Value("${jdbc.username}")
    private String jdbcUsername;
    
    @Value("${jdbc.url}")
    private String jdbcUrl;
    
    @Value("${jdbc.password}")
    private String jdbcPassword;
    
    @Value("${jdbc.publicKey}")
    private String jdbcPublicKey;
    
    @Value("${jdbc.maxActive}")
    private Integer maxActive;
    
    @Value("${jdbc.initialSize}")
    private Integer initialSize;
    
    @Value("${hibernate.show_sql}")
    private boolean hibernateShowsql;
    
    @Value("${hibernate.format_sql}")
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
    
    @Value("${aliyun.ons.producer}")
    private String aliyunONSProducer;
    
    @Value("${aliyun.ons.consumer.sample}")
    private String aliyunONSSampleConsumer;
    
    @Value("${aliyun.ons.consumer.order}")
    private String aliyunONSOrderConsumer;
    
    @Value("${aliyun.ons.consumer.report}")
    private String aliyunONSReportConsumer;
    
    @Value("${aliyun.ons.consumer.biologyAnalysisCommentConsumer}")
    private String aliyunONSBiologyAnalysisCreateConsumer;
    
    public String getJdbcPublicKey()
    {
        return jdbcPublicKey;
    }
    
    public void setJdbcPublicKey(String jdbcPublicKey)
    {
        this.jdbcPublicKey = jdbcPublicKey;
    }
    
    public String getJdbcDriver()
    {
        return jdbcDriver;
    }
    
    public void setJdbcDriver(String jdbcDriver)
    {
        this.jdbcDriver = jdbcDriver;
    }
    
    public String getJdbcUrl()
    {
        return jdbcUrl;
    }
    
    public void setJdbcUrl(String jdbcUrl)
    {
        this.jdbcUrl = jdbcUrl;
    }
    
    public String getJdbcUsername()
    {
        return jdbcUsername;
    }
    
    public void setJdbcUsername(String jdbcUsername)
    {
        this.jdbcUsername = jdbcUsername;
    }
    
    public String getJdbcPassword()
    {
        return jdbcPassword;
    }
    
    public void setJdbcPassword(String jdbcPassword)
    {
        this.jdbcPassword = jdbcPassword;
    }
    
    public String getHibernateDialect()
    {
        return hibernateDialect;
    }
    
    public void setHibernateDialect(String hibernateDialect)
    {
        this.hibernateDialect = hibernateDialect;
    }
    
    public boolean getHibernateShowsql()
    {
        return hibernateShowsql;
    }
    
    public void setHibernateShowsql(boolean hibernateShowsql)
    {
        this.hibernateShowsql = hibernateShowsql;
    }
    
    public boolean getHibernateFormatSql()
    {
        return hibernateFormatSql;
    }
    
    public void setHibernateFormatSql(boolean hibernateFormatSql)
    {
        this.hibernateFormatSql = hibernateFormatSql;
    }
    
    public Integer getMaxActive()
    {
        return maxActive;
    }
    
    public void setMaxActive(Integer maxActive)
    {
        this.maxActive = maxActive;
    }
    
    public Integer getInitialSize()
    {
        return initialSize;
    }
    
    public void setInitialSize(Integer initialSize)
    {
        this.initialSize = initialSize;
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
    
    public String getAliyunONSProducer()
    {
        return aliyunONSProducer;
    }
    
    public void setAliyunONSProducer(String aliyunONSProducer)
    {
        this.aliyunONSProducer = aliyunONSProducer;
    }
    
    public String getAliyunONSSampleConsumer()
    {
        return aliyunONSSampleConsumer;
    }
    
    public void setAliyunONSSampleConsumer(String aliyunONSSampleConsumer)
    {
        this.aliyunONSSampleConsumer = aliyunONSSampleConsumer;
    }
    
    public String getAliyunONSOrderConsumer()
    {
        return aliyunONSOrderConsumer;
    }
    
    public void setAliyunONSOrderConsumer(String aliyunONSOrderConsumer)
    {
        this.aliyunONSOrderConsumer = aliyunONSOrderConsumer;
    }
    
    public String getAliyunONSReportConsumer()
    {
        return aliyunONSReportConsumer;
    }
    
    public void setAliyunONSReportConsumer(String aliyunONSReportConsumer)
    {
        this.aliyunONSReportConsumer = aliyunONSReportConsumer;
    }
    
    public String getAliyunONSBiologyAnalysisCreateConsumer()
    {
        return aliyunONSBiologyAnalysisCreateConsumer;
    }
    
    public void setAliyunONSBiologyAnalysisCreateConsumer(String aliyunONSBiologyAnalysisCreateConsumer)
    {
        this.aliyunONSBiologyAnalysisCreateConsumer = aliyunONSBiologyAnalysisCreateConsumer;
    }
}
