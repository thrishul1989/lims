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
import com.todaysoft.lims.kbm.es.model.Disease;
import com.todaysoft.lims.kbm.es.service.IDiseaseService;

@Service
public class DiseaseService implements IDiseaseService
{
    private static Logger log = LoggerFactory.getLogger(DiseaseService.class);
    
    @Autowired
    private Client client;
    
    @Autowired
    private Configs configs;
    
    @Override
    public boolean index(Disease disease)
    {
        if (StringUtils.isEmpty(disease.getId()))
        {
            log.warn("Disease id is empty, ignore the index operation.");
            return false;
        }
        
        String json = ObjectMapperUtils.toJson(disease);
        
        if (null == json)
        {
            log.warn("Disease to json is empty, ignore the index operation.");
            return false;
        }
        
        // 索引前先删除历史记录
        remove(disease.getId());
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to index disease json {}", json);
        }
        
        IndexRequestBuilder builder = client.prepareIndex(configs.getElasticsearchIndex(), ES_TYPE, disease.getId());
        IndexResponse response = builder.setSource(json).execute().actionGet();
        boolean created = response.isCreated();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to index disease json {}, result is {}", json, created);
        }
        
        return created;
    }
    
    @Override
    public boolean remove(String id)
    {
        if (StringUtils.isEmpty(id))
        {
            log.warn("Remove id is empty, ignore the disease remove operation.");
            return false;
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to remove disease {}", id);
        }
        
        DeleteRequestBuilder builder = client.prepareDelete(configs.getElasticsearchIndex(), ES_TYPE, id);
        DeleteResponse response = builder.execute().actionGet();
        boolean found = response.isFound();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to remove disease {}, result is {}", id, found);
        }
        
        return found;
    }
    
    @Override
    public List<Disease> search(String keywords, int offset, int limit)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Search disease for keywords {}, offset {}, limit {}", keywords, offset, limit);
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
                log.debug("Search disease result is empty for keywords {}, offset {}, limit {}.", keywords, offset, limit);
            }
            
            return Collections.emptyList();
        }
        
        Disease disease;
        List<Disease> diseases = new ArrayList<Disease>();
        
        for (SearchHit hit : hits)
        {
            String json = hit.getSourceAsString();
            disease = ObjectMapperUtils.fromJson(json, Disease.class);
            
            if (null != disease)
            {
                diseases.add(disease);
            }
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Search disease result size {} for keywords {}, offset {}, limit {}.", diseases.size(), keywords, offset, limit);
        }
        
        return diseases;
    }
    
    private String[] getDefaultMultiMatchQueryFields()
    {
        return new String[] {"name", "enname", "alias", "genes", "phenotypes", "documents"};
    }
}
