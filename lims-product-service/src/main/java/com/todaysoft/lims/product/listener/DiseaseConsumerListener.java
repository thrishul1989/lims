package com.todaysoft.lims.product.listener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.product.entity.Phenotype;
import com.todaysoft.lims.product.entity.disease.Disease;
import com.todaysoft.lims.product.entity.disease.DiseaseAlias;
import com.todaysoft.lims.product.entity.disease.DiseaseGene;
import com.todaysoft.lims.product.entity.disease.DiseasePhenotype;
import com.todaysoft.lims.product.entity.disease.Gene;
import com.todaysoft.lims.product.listener.ESModel.ESDisease;
import com.todaysoft.lims.product.model.request.DocumentRequest;
import com.todaysoft.lims.product.service.IDiseaseService;
import com.todaysoft.lims.product.service.IDocumentService;
import com.todaysoft.lims.product.service.IPhenotypeService;
import com.todaysoft.lims.product.service.adapter.DiseaseCosumerEvent;
import com.todaysoft.lims.product.service.adapter.KBMAdapter;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Component("diseaseConsumerListener")
public class DiseaseConsumerListener implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(DiseaseConsumerListener.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private KBMAdapter adapter;
    
    @Autowired
    private IDocumentService service;
    
    @Autowired
    private IDiseaseService disservice;
    
    @Autowired
    private IPhenotypeService phenotypeservice;
    
    @Override
    @Transactional
    public Action consume(Message message, ConsumeContext context)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to consume message, topic {}, tag {}, id {}, key {}.", message.getTopic(), message.getTag(), message.getMsgID(), message.getKey());
        }
        
        String json = new String(message.getBody());
        
        if (log.isDebugEnabled())
        {
            log.debug("Message body as json {}.", json);
        }
        
        DiseaseCosumerEvent event = new Gson().fromJson(json, DiseaseCosumerEvent.class);
        
        if (StringUtils.isEmpty(event.getId()))
        {
            return Action.CommitMessage;
        }
        Disease disease = baseDaoSupport.get(Disease.class, event.getId());
        
        if (StringUtils.isNotEmpty(event.getActionType()) && StringUtils.isNotEmpty(disease))
        {
            if ("create".equals(event.getActionType()) || "modify".equals(event.getActionType()))
            {
                
                //疾病相关索引
                if (StringUtils.isNotEmpty(disease))
                {
                    Set<Object> geneNames = new HashSet<Object>();
                    Set<Object> phenotypeNames = new HashSet<Object>();
                    Set<Object> aliasNames = new HashSet<Object>();
                    String document = "";
                    for (DiseaseGene dg : disease.getGeneList())
                    {
                        geneNames.add(dg.getGene().getSymbol());
                        
                        //新增、修改同步基因
                        Gene gene = dg.getGene();
                        disservice.createRelationGene(gene.getId());
                        
                    }
                    for (DiseasePhenotype dp : disease.getPhenotypeList())
                    {
                        phenotypeNames.add(dp.getPhenotype().getName());
                        
                        //新增、修改同步表型
                        Phenotype p = dp.getPhenotype();
                        disservice.createRelationPhenotype(p.getId());
                        
                    }
                    
                    for (DiseaseAlias da : disease.getAlias())
                    {
                        if (StringUtils.isNotEmpty(da))
                        {
                            aliasNames.add(da.getName());
                        }
                    }
                    DocumentRequest request = new DocumentRequest();
                    request.setId(disease.getId());
                    List<String> list = service.getDocumentByDisease(request);
                    if (Collections3.isNotEmpty(list))
                    {
                        document = String.join(" ", list.toArray(new String[0]));
                    }
                    
                    ESDisease esDisease =
                        ESDisease.builder()
                            .alias(String.join(" ", aliasNames.toArray(new String[0])))
                            .documents(document)
                            .id(disease.getId())
                            .name(disease.getName())
                            .omim(disease.getCode())
                            .enname(disease.getNameEn())
                            .genes(String.join(" ", geneNames.toArray(new String[0])))
                            .phenotypes(String.join(" ", phenotypeNames.toArray(new String[0])))
                            .build();
                    //疾病索引
                    adapter.createDiseaseIndex(esDisease);
                    //修改时同步产品
                    if ("modify".equals(event.getActionType()))
                    {
                        List<String> proresult = disservice.getEnableAndNotModelProductIdDiease(disease.getId());
                        if (Collections3.isNotEmpty(proresult))
                        {
                            for (String p : proresult)
                            {
                                disservice.createRelationProduct(p);
                            }
                        }
                    }
                    
                }
                
            }
            
            if ("delete".equals(event.getActionType()))
            {
                adapter.removeDisIndex(disease.getId());
            }
        }
        return Action.CommitMessage;
    }
}
