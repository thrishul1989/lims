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
import com.todaysoft.lims.kbm.es.model.Phenotype;
import com.todaysoft.lims.kbm.es.service.IPhenotypeService;

@Service
public class PhenotypeService implements IPhenotypeService
{
    private static Logger log = LoggerFactory.getLogger(PhenotypeService.class);
    
    @Autowired
    private Client client;
    
    @Autowired
    private Configs configs;
    
    @Override
    public boolean index(Phenotype phenotype)
    {
        if (StringUtils.isEmpty(phenotype.getId()))
        {
            log.warn("Phenotype id is empty, ignore the index operation.");
            return false;
        }
        
        String json = ObjectMapperUtils.toJson(phenotype);
        
        if (StringUtils.isEmpty(json))
        {
            log.warn("Phenotype to json is empty, ignore the index operation.");
            return false;
        }
        
        // 索引前先删除历史记录
        remove(phenotype.getId());
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to index phenotype json {}", json);
        }
        
        IndexRequestBuilder builder = client.prepareIndex(configs.getElasticsearchIndex(), ES_TYPE, phenotype.getId());
        IndexResponse response = builder.setSource(json).execute().actionGet();
        boolean created = response.isCreated();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to index phenotype json {}, result is {}", json, created);
        }
        
        return created;
    }
    
    @Override
    public boolean remove(String id)
    {
        if (StringUtils.isEmpty(id))
        {
            log.warn("Remove id is empty, ignore the phenotype remove operation.");
            return false;
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to remove phenotype {}", id);
        }
        
        DeleteRequestBuilder builder = client.prepareDelete(configs.getElasticsearchIndex(), ES_TYPE, id);
        DeleteResponse response = builder.execute().actionGet();
        boolean found = response.isFound();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to remove phenotype {}, result is {}", id, found);
        }
        
        return found;
    }
    
    @Override
    public List<Phenotype> search(String keywords, int offset, int limit)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Search phenotype for keywords {}, offset {}, limit {}", keywords, offset, limit);
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
                log.debug("Search phenotype result is empty for keywords {}, offset {}, limit {}.", keywords, offset, limit);
            }
            
            return Collections.emptyList();
        }
        
        Phenotype phenotype;
        List<Phenotype> phenotypes = new ArrayList<Phenotype>();
        
        for (SearchHit hit : hits)
        {
            String json = hit.getSourceAsString();
            phenotype = ObjectMapperUtils.fromJson(json, Phenotype.class);
            
            if (null != phenotype)
            {
                phenotypes.add(phenotype);
            }
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Search phenotype result size {} for keywords {}, offset {}, limit {}.", phenotypes.size(), keywords, offset, limit);
        }
        
        return phenotypes;
    }
    
    private String[] getDefaultMultiMatchQueryFields()
    {
        return new String[] {"name", "enname", "diseases"};
    }
}
