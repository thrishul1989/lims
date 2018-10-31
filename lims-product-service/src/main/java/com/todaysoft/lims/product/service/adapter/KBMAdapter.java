package com.todaysoft.lims.product.service.adapter;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.product.entity.Phenotype;
import com.todaysoft.lims.product.entity.Product;
import com.todaysoft.lims.product.entity.disease.Disease;
import com.todaysoft.lims.product.entity.disease.Gene;
import com.todaysoft.lims.product.listener.ESModel.ESDisease;
import com.todaysoft.lims.product.listener.ESModel.ESGene;
import com.todaysoft.lims.product.listener.ESModel.ESPhenotype;
import com.todaysoft.lims.product.listener.ESModel.ESProduct;

@Component
public class KBMAdapter
{
    private static final String SERVICE_NAME = "lims-kbm-es-service";
    
    @Autowired
    private RestTemplate template;
    
    public boolean createProductIndex(ESProduct product)
    {
        String url = "http://" + SERVICE_NAME + "/es/product/index";
        
        try
        {
            template.postForLocation(url, product);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Product> searchProduct(String keywords, Integer offset, Integer limit)
    {
        String url = "http://" + SERVICE_NAME + "/es/product/search/{keywords}/{offset}/{limit}";
        return template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>()
        {
        }, keywords, offset, limit).getBody();
    }
    
    public boolean createDiseaseIndex(ESDisease disease)
    {
        String url = "http://" + SERVICE_NAME + "/es/disease/index";
        try
        {
            template.postForLocation(url, disease);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Disease> searchDisease(String keywords, Integer offset, Integer limit)
    {
        String url = "http://" + SERVICE_NAME + "/es/disease/search/{keywords}/{offset}/{limit}";
        return template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Disease>>()
        {
        }, keywords, offset, limit).getBody();
    }
    
    public boolean createGeneIndex(ESGene gene)
    {
        String url = "http://" + SERVICE_NAME + "/es/gene/index";
        try
        {
            template.postForLocation(url, gene);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }
    
    public List<Gene> searchGene(String keywords, Integer offset, Integer limit)
    {
        String url = "http://" + SERVICE_NAME + "/es/gene/search/{keywords}/{offset}/{limit}";
        return template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Gene>>()
        {
        }, keywords, offset, limit).getBody();
    }
    
    public boolean createPhenotypeIndex(ESPhenotype phenotype)
    {
        String url = "http://" + SERVICE_NAME + "/es/phenotype/index";
        try
        {
            template.postForLocation(url, phenotype);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }
    
    public List<Phenotype> searchPhenotype(String keywords, Integer offset, Integer limit)
    {
        String url = "http://" + SERVICE_NAME + "/es/phenotype/search/{keywords}/{offset}/{limit}";
        return template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Phenotype>>()
        {
        }, keywords, offset, limit).getBody();
    }
    
    public void removeIndex(String id)
    {
        String url = "http://" + SERVICE_NAME + "/es/product/remove/{id}";
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    public void removeDisIndex(String id)
    {
        String url = "http://" + SERVICE_NAME + "/es/disease/remove/{id}";
        template.delete(url, Collections.singletonMap("id", id));
        
    }
    
    public void removeGeneIndex(String id)
    {
        String url = "http://" + SERVICE_NAME + "/es/gene/remove/{id}";
        template.delete(url, Collections.singletonMap("id", id));
        
    }
    
    public void removePhenoIndex(String id)
    {
        String url = "http://" + SERVICE_NAME + "/es/phenotype/remove/{id}";
        template.delete(url, Collections.singletonMap("id", id));
        
    }
    
}
