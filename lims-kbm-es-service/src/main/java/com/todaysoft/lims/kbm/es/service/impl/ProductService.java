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
import com.todaysoft.lims.kbm.es.model.Product;
import com.todaysoft.lims.kbm.es.service.IProductService;

@Service
public class ProductService implements IProductService
{
    private static Logger log = LoggerFactory.getLogger(ProductService.class);
    
    @Autowired
    private Client client;
    
    @Autowired
    private Configs configs;
    
    @Override
    public boolean index(Product product)
    {
        if (StringUtils.isEmpty(product.getId()))
        {
            log.warn("Product id is empty, ignore the index operation.");
            return false;
        }
        
        String json = ObjectMapperUtils.toJson(product);
        
        if (StringUtils.isEmpty(json))
        {
            log.warn("Product to json is empty, ignore the index operation.");
            return false;
        }
        
        // 索引前先删除历史记录
        remove(product.getId());
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to index product json {}", json);
        }
        
        IndexRequestBuilder builder = client.prepareIndex(configs.getElasticsearchIndex(), ES_TYPE, product.getId());
        IndexResponse response = builder.setSource(json).execute().actionGet();
        boolean created = response.isCreated();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to index product json {}, result is {}", json, created);
        }
        
        return created;
    }
    
    @Override
    public boolean remove(String id)
    {
        if (StringUtils.isEmpty(id))
        {
            log.warn("Remove id is empty, ignore the product remove operation.");
            return false;
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to remove product {}", id);
        }
        
        DeleteRequestBuilder builder = client.prepareDelete(configs.getElasticsearchIndex(), ES_TYPE, id);
        DeleteResponse response = builder.execute().actionGet();
        boolean found = response.isFound();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to remove product {}, result is {}", id, found);
        }
        
        return found;
    }
    
    @Override
    public List<Product> search(String keywords, int offset, int limit)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Search product for keywords {}, offset {}, limit {}", keywords, offset, limit);
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
                log.debug("Search product result is empty for keywords {}, offset {}, limit {}.", keywords, offset, limit);
            }
            
            return Collections.emptyList();
        }
        
        Product product;
        List<Product> products = new ArrayList<Product>();
        
        for (SearchHit hit : hits)
        {
            String json = hit.getSourceAsString();
            product = ObjectMapperUtils.fromJson(json, Product.class);
            
            if (null != product)
            {
                products.add(product);
            }
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Search product result size {} for keywords {}, offset {}, limit {}.", products.size(), keywords, offset, limit);
        }
        
        return products;
    }
    
    private String[] getDefaultMultiMatchQueryFields()
    {
        return new String[] {"name", "category", "subcategory", "description", "genes", "diseases", "phenotypes"};
    }
}
