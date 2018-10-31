package com.todaysoft.lims.kbm.es.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class Configs
{
    @Value("${es.cluster.name}")
    private String elasticsearchClusterName;
    
    @Value("${es.cluster.nodes}")
    private String elasticsearchClusterNodes;
    
    @Value("${es.index.name}")
    private String elasticsearchIndex;
    
    public String getElasticsearchClusterName()
    {
        return elasticsearchClusterName;
    }
    
    public void setElasticsearchClusterName(String elasticsearchClusterName)
    {
        this.elasticsearchClusterName = elasticsearchClusterName;
    }
    
    public String getElasticsearchClusterNodes()
    {
        return elasticsearchClusterNodes;
    }
    
    public void setElasticsearchClusterNodes(String elasticsearchClusterNodes)
    {
        this.elasticsearchClusterNodes = elasticsearchClusterNodes;
    }
    
    public String getElasticsearchIndex()
    {
        return elasticsearchIndex;
    }
    
    public void setElasticsearchIndex(String elasticsearchIndex)
    {
        this.elasticsearchIndex = elasticsearchIndex;
    }
}
