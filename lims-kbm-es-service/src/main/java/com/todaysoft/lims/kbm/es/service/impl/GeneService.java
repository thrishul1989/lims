package com.todaysoft.lims.kbm.es.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lang3.ArrayUtils;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.kbm.es.config.Configs;
import com.todaysoft.lims.kbm.es.model.Gene;
import com.todaysoft.lims.kbm.es.service.IGeneService;

@Service
public class GeneService implements IGeneService
{
    private static Logger log = LoggerFactory.getLogger(GeneService.class);
    
    @Autowired
    private Client client;
    
    @Autowired
    private Configs configs;
    
    @Override
    public boolean index(Gene gene)
    {
        if (StringUtils.isEmpty(gene.getId()))
        {
            log.warn("Gene id is empty, ignore the index operation.");
            return false;
        }
        
        String json = ObjectMapperUtils.toJson(gene);
        
        if (null == json)
        {
            log.warn("Gene to json is empty, ignore the index operation.");
            return false;
        }
        
        // 索引前先删除历史记录
        remove(gene.getId());
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to index gene json {}", json);
        }
        
        IndexRequestBuilder builder = client.prepareIndex(configs.getElasticsearchIndex(), ES_TYPE, gene.getId());
        IndexResponse response = builder.setSource(json).execute().actionGet();
        boolean created = response.isCreated();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to index gene json {}, result is {}", json, created);
        }
        
        return created;
    }
    
    @Override
    public boolean remove(String id)
    {
        if (StringUtils.isEmpty(id))
        {
            log.warn("Remove id is empty, ignore the gene remove operation.");
            return false;
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to remove gene {}", id);
        }
        
        DeleteRequestBuilder builder = client.prepareDelete(configs.getElasticsearchIndex(), ES_TYPE, id);
        DeleteResponse response = builder.execute().actionGet();
        boolean found = response.isFound();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to remove gene {}, result is {}", id, found);
        }
        
        return found;
    }
    
    @Override
    public List<Gene> search(String keywords, int offset, int limit)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Search gene for keywords {}, offset {}, limit {}", keywords, offset, limit);
        }
        
        QueryBuilder query;
        
        if (StringUtils.isEmpty(keywords))
        {
            query = QueryBuilders.matchAllQuery();
        }
        else
        {
            query = QueryBuilders.multiMatchQuery(keywords, getDefaultMultiMatchQueryFields());
        }
        
        SearchRequestBuilder request = client.prepareSearch(configs.getElasticsearchIndex()).setTypes(ES_TYPE).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        request.setQuery(query).addSort("_score", SortOrder.DESC).setFrom(offset).setSize(limit);
        SearchHit[] hits = request.execute().actionGet().getHits().getHits();
        
        if (ArrayUtils.isEmpty(hits))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Search gene result is empty for keywords {}, offset {}, limit {}.", keywords, offset, limit);
            }
            
            return Collections.emptyList();
        }
        
        Gene gene;
        List<Gene> genes = new ArrayList<Gene>();
        
        for (SearchHit hit : hits)
        {
            String json = hit.getSourceAsString();
            gene = ObjectMapperUtils.fromJson(json, Gene.class);
            
            if (null != gene)
            {
                genes.add(gene);
            }
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Search gene result size {} for keywords {}, offset {}, limit {}.", genes.size(), keywords, offset, limit);
        }
        
        return genes;
    }
    
    private String[] getDefaultMultiMatchQueryFields()
    {
        return new String[] {"symbol", "name", "alias", "diseases", "documents"};
    }
}
