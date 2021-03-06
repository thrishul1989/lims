package com.todaysoft.lims.technical.adapter.bcm;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.technical.model.TaskConfig;
import com.todaysoft.lims.technical.model.TestingNode;
import com.todaysoft.lims.technical.model.TestingNodeSearchRequest;
import com.todaysoft.lims.technical.model.response.Disease;
import com.todaysoft.lims.technical.model.response.Gene;
import com.todaysoft.lims.technical.model.response.Phenotype;

@Component
public class BCMAdapter
{
    private static final String SERVICE_NAME = "lims-product-service";
    
    @Autowired
    private RestTemplate template;
    
    public TaskConfig getTaskConfigBySemantic(String semantic)
    {
        String url = "http://" + SERVICE_NAME + "/bcm/task/getBySemantic/{semantic}";
        return template.getForObject(url, TaskConfig.class, Collections.singletonMap("semantic", semantic));
    }
    
    public BigDecimal getTestingDatasize(String productId, String testingMethodId)
    {
        String url = "http://" + SERVICE_NAME + "/bcm/product/{pid}/methods/{mid}/datasize";
        return template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<BigDecimal>()
        {
        }, productId, testingMethodId).getBody();
    }
    
    public List<String> getAnalyCoordinates(String productId, String testingMethodId)
    {
        String url = "http://" + SERVICE_NAME + "/bcm/product/{pid}/methods/{mid}/coordinates";
        return template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>()
        {
        }, productId, testingMethodId).getBody();
    }
    
    public BigDecimal getCaptureSampleInputVolumeForRQT()
    {
        String value = getConfigValue("RQT_CAPTURE_SAMPLE_INPUT_VOLUME");
        return null == value ? BigDecimal.valueOf(35D) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getLibrarySampleInputVolumeForRQT()
    {
        String value = getConfigValue("RQT_LIBRARY_SAMPLE_INPUT_VOLUME");
        return null == value ? BigDecimal.valueOf(5D) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getNGSFirstDiluteConcn()
    {
        String value = getConfigValue("NGS_FDT_CONCN");
        return null == value ? BigDecimal.valueOf(4D) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getNGSFirstDiluteSampleInputVolume()
    {
        String value = getConfigValue("NGS_FDT_SAMPLE_INPUT_VOLUME");
        return null == value ? BigDecimal.valueOf(20D) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getNGSRTSampleInputVolume()
    {
        String value = getConfigValue("NGS_RT_SAMPLE_INPUT_VOLUME");
        return null == value ? BigDecimal.valueOf(5D) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getNGSRTReagentInputVolume()
    {
        String value = getConfigValue("NGS_RT_REAGENT_INPUT_VOLUME");
        return null == value ? BigDecimal.valueOf(5D) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getNGSSecondDiluteConcn()
    {
        String value = getConfigValue("NGS_SDT_CONCN");
        return null == value ? BigDecimal.valueOf(20D) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getNGSFinalConcn()
    {
        String value = getConfigValue("NGS_FINAL_CONCN");
        return null == value ? BigDecimal.valueOf(1.8D) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getNGSFinalInputVolume()
    {
        String value = getConfigValue("NGS_FINAL_VOLUME");
        return null == value ? BigDecimal.valueOf(1500D) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getFirstPcrMinInputVolume()
    {
        String value = getConfigValue("FIRST_PCR_MIN_VOLUME");
        return null == value ? BigDecimal.valueOf(0.5) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getPcrNgsRqtConcn()
    {
        String value = getConfigValue("PCR_NGS_RQT_CON");
        return null == value ? BigDecimal.valueOf(5) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getPcrNgsRqtDataSize()
    {
        String value = getConfigValue("PCR_NGS_RQT_DATA_SIZE");
        return null == value ? BigDecimal.valueOf(0.01) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getPcrNgsRqtCapVol()
    {
        String value = getConfigValue("PCR_NGS_RQT_CAP_VOL");
        return null == value ? BigDecimal.valueOf(5) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public BigDecimal getFirstPcrMaxInputVolume()
    {
        String value = getConfigValue("FIRST_PCR_MAX_VOLUME");
        return null == value ? BigDecimal.valueOf(3) : BigDecimal.valueOf(Double.valueOf(value));
    }
    
    public String getConfigValue(String key)
    {
        String url = "http://" + SERVICE_NAME + "/bcm/configs/{key}";
        List<String> values = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>()
        {
        }, key).getBody();
        
        return CollectionUtils.isEmpty(values) ? null : values.get(0);
    }
    
    public Disease getDiseaseById(String id)
    {
        String url = "http://" + SERVICE_NAME + "/bcm/disease/getDiseaseById/{id}";
        return template.getForObject(url, Disease.class, Collections.singletonMap("id", id));
    }
    
    public Gene getGeneById(String id)
    {
        String url = "http://" + SERVICE_NAME + "/bcm/disease/getGeneById/{id}";
        return template.getForObject(url, Gene.class, Collections.singletonMap("id", id));
    }
    
    public Phenotype getPhenotypeById(String id)
    {
        String url = "http://" + SERVICE_NAME + "/bcm/phenotype/getPhenotype/{id}";
        return template.getForObject(url, Phenotype.class, Collections.singletonMap("id", id));
    }
    
    public List<TestingNode> getTestingNodes(String sampleId, String inputSampleTypeId)
    {
        TestingNodeSearchRequest request = new TestingNodeSearchRequest();
        request.setSourceId(sampleId);
        request.setTargetId(inputSampleTypeId);
        String url = "http://" + SERVICE_NAME + "/bcm/sample/getPreTestingTaskNode";
        ResponseEntity<List<TestingNode>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingNodeSearchRequest>(request), new ParameterizedTypeReference<List<TestingNode>>()
            {
            });
        return exchange.getBody();
    }
}
