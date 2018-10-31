package com.todaysoft.lims.reagent.config;

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
}
