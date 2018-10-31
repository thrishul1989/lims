package com.todaysoft.lims.maintain.indexes.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.maintain.commons.Monitor;
import com.todaysoft.lims.maintain.commons.ObjectMapperUtils;
import com.todaysoft.lims.maintain.config.Configs;
import com.todaysoft.lims.maintain.entity.Disease;
import com.todaysoft.lims.maintain.entity.DiseaseAlias;
import com.todaysoft.lims.maintain.entity.Document;
import com.todaysoft.lims.maintain.entity.Gene;
import com.todaysoft.lims.maintain.entity.GeneAlias;
import com.todaysoft.lims.maintain.entity.Phenotype;
import com.todaysoft.lims.maintain.entity.Product;
import com.todaysoft.lims.maintain.indexes.model.ESDisease;
import com.todaysoft.lims.maintain.indexes.model.ESGene;
import com.todaysoft.lims.maintain.indexes.model.ESPhenotype;
import com.todaysoft.lims.maintain.indexes.model.ESProduct;
import com.todaysoft.lims.maintain.indexes.service.ICoreService;
import com.todaysoft.lims.maintain.util.ProductConstant;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class CoreService implements ICoreService
{
    private static Logger log = LoggerFactory.getLogger(CoreService.class);
    
    private Monitor productMonitor = new Monitor();
    
    private Monitor geneMonitor = new Monitor();
    
    private Monitor diseaseMonitor = new Monitor();
    
    private Monitor phenotypeMonitor = new Monitor();
    
    @Autowired
    private Client client;
    
    @Autowired
    private Configs configs;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public void indexForProducts()
    {
        if (!productMonitor.isStartable())
        {
            return;
        }
        
        productMonitor.setStart(true);
        productMonitor.setFinish(false);
        
        int pageNo = 1;
        int pageSize = 200;
        
        try
        {
            NamedQueryer queryer = new NamedQueryer();
            queryer.setHql("FROM Product p WHERE p.deleted = false and p.ifMade =:ifMade and p.enabled =:enabled  ORDER BY p.createTime");
            queryer.setNames(Lists.newArrayList("ifMade", "enabled"));
            queryer.setValues(Lists.newArrayList(ProductConstant.PRODUCT_MADE_NO, ProductConstant.PRODUCT_ENABLE_YES));
            
            int processedCount = 0;
            Pagination<Product> pagination;
            
            do
            {
                pagination = baseDaoSupport.find(queryer, pageNo++, pageSize, Product.class);
                productMonitor.setTotalCount(pagination.getTotalCount());
                
                ESProduct esp;
                
                for (Product product : pagination.getRecords())
                {
                    esp = new ESProduct();
                    esp.setId(product.getId());
                    esp.setName(product.getName());
                    esp.setCategory(null == product.getCategory() ? null : product.getCategory().getName());
                    esp.setSubcategory(null == product.getSubcategory() ? null : product.getSubcategory().getName());
                    esp.setDescription(product.getDescription());
                    
                    Set<Object> diseaseNames = new HashSet<Object>(), geneNames = new HashSet<Object>(), phentypeNames = new HashSet<Object>();
                    
                    List<Gene> genes = getProductGenes(product.getId());
                    List<Disease> diseases = getProductDiseases(product.getId());
                    List<Phenotype> phenotypes = getProductPhenotypes(product.getId());
                    
                    if (!CollectionUtils.isEmpty(genes))
                    {
                        for (Gene gene : genes)
                        {
                            geneNames.add(gene.getSymbol());
                        }
                    }
                    
                    if (!CollectionUtils.isEmpty(diseases))
                    {
                        for (Disease disease : diseases)
                        {
                            diseaseNames.add(disease.getName());
                        }
                    }
                    
                    if (!CollectionUtils.isEmpty(phenotypes))
                    {
                        for (Phenotype phenotype : phenotypes)
                        {
                            phentypeNames.add(phenotype.getName());
                        }
                    }
                    
                    esp.setGenes(String.join(" ", geneNames.toArray(new String[0])));
                    esp.setDiseases(String.join(" ", diseaseNames.toArray(new String[0])));
                    esp.setPhenotypes(String.join(" ", phentypeNames.toArray(new String[0])));
                    indexForProduct(esp);
                    productMonitor.setProcessedCount(++processedCount);
                }
                
            } while (!pagination.isLastPage());
            
            productMonitor.setFinish(true);
        }
        catch (Exception e)
        {
            log.error("Index for products error", e);
            productMonitor.setFinish(true);
        }
    }
    
    @Override
    public Monitor monitorForProducts()
    {
        return productMonitor;
    }
    
    @Override
    public void indexForGenes()
    {
        if (!geneMonitor.isStartable())
        {
            return;
        }
        
        geneMonitor.setStart(true);
        geneMonitor.setFinish(false);
        
        int pageNo = 1;
        int pageSize = 200;
        
        try
        {
            NamedQueryer queryer = new NamedQueryer();
            queryer.setHql("FROM Gene g WHERE g.deleted = false ORDER BY g.createTime");
            queryer.setNames(new ArrayList<String>());
            queryer.setValues(new ArrayList<Object>());
            
            int processedCount = 0;
            Pagination<Gene> pagination;
            
            do
            {
                pagination = baseDaoSupport.find(queryer, pageNo++, pageSize, Gene.class);
                geneMonitor.setTotalCount(pagination.getTotalCount());
                
                ESGene esg;
                
                for (Gene gene : pagination.getRecords())
                {
                    esg = new ESGene();
                    BeanUtils.copyProperties(gene, esg);
                    
                    Set<String> diseaseNames = new HashSet<String>();
                    Set<String> genealiasNames = new HashSet<String>();
                    Set<String> documentNames = new HashSet<String>();
                    
                    List<GeneAlias> alias = getGeneAlias(gene.getId());
                    
                    for (GeneAlias da : alias)
                    {
                        genealiasNames.add(da.getSymbol());
                    }
                    
                    List<Disease> diseases = getGeneDiseases(gene.getId());
                    
                    for (Disease disease : diseases)
                    {
                        diseaseNames.add(disease.getName());
                    }
                    
                    List<Document> documents = getGeneDocuments(gene.getId());
                    
                    for (Document document : documents)
                    {
                        documentNames.add(document.getTitle());
                    }
                    
                    esg.setOmim(gene.getCode());
                    esg.setAlias(String.join(" ", genealiasNames.toArray(new String[0])));
                    esg.setDiseases(String.join(" ", diseaseNames.toArray(new String[0])));
                    esg.setDocuments(String.join(" ", documentNames.toArray(new String[0])));
                    
                    indexForGene(esg);
                    geneMonitor.setProcessedCount(++processedCount);
                }
                
            } while (!pagination.isLastPage());
            
            geneMonitor.setFinish(true);
        }
        catch (Exception e)
        {
            log.error("Index for genes error", e);
            geneMonitor.setFinish(true);
        }
    }
    
    @Override
    public Monitor monitorForGenes()
    {
        return geneMonitor;
    }
    
    @Override
    public void indexForDiseases()
    {
        if (!diseaseMonitor.isStartable())
        {
            return;
        }
        
        diseaseMonitor.setStart(true);
        diseaseMonitor.setFinish(false);
        
        int pageNo = 1;
        int pageSize = 200;
        
        try
        {
            NamedQueryer queryer = new NamedQueryer();
            queryer.setHql("FROM Disease d WHERE d.deleted = false ORDER BY d.createTime");
            queryer.setNames(new ArrayList<String>());
            queryer.setValues(new ArrayList<Object>());
            
            int processedCount = 0;
            Pagination<Disease> pagination;
            
            do
            {
                pagination = baseDaoSupport.find(queryer, pageNo++, pageSize, Disease.class);
                diseaseMonitor.setTotalCount(pagination.getTotalCount());
                
                ESDisease esd;
                
                for (Disease disease : pagination.getRecords())
                {
                    esd = new ESDisease();
                    
                    Set<String> geneNames = new HashSet<String>();
                    Set<String> aliasNames = new HashSet<String>();
                    Set<String> phenotypeNames = new HashSet<String>();
                    Set<String> documentNames = new HashSet<String>();
                    
                    List<Gene> genes = getDiseaseGenes(disease.getId());
                    
                    for (Gene gene : genes)
                    {
                        geneNames.add(gene.getSymbol());
                    }
                    
                    List<Phenotype> phenotypes = getDiseasePhenotypes(disease.getId());
                    
                    for (Phenotype phenotype : phenotypes)
                    {
                        phenotypeNames.add(phenotype.getName());
                    }
                    
                    List<DiseaseAlias> alias = getDiseaseAlias(disease.getId());
                    
                    for (DiseaseAlias da : alias)
                    {
                        aliasNames.add(da.getName());
                    }
                    
                    List<Document> documents = getDiseaseDocuments(disease.getId());
                    
                    for (Document document : documents)
                    {
                        documentNames.add(document.getTitle());
                    }
                    
                    esd.setId(disease.getId());
                    esd.setName(disease.getName());
                    esd.setOmim(disease.getCode());
                    esd.setEnname(disease.getNameEn());
                    esd.setAlias(String.join(" ", aliasNames.toArray(new String[0])));
                    esd.setGenes(String.join(" ", geneNames.toArray(new String[0])));
                    esd.setPhenotypes(String.join(" ", phenotypeNames.toArray(new String[0])));
                    esd.setDocuments(String.join(" ", documentNames.toArray(new String[0])));
                    
                    indexForDisease(esd);
                    diseaseMonitor.setProcessedCount(++processedCount);
                }
                
            } while (!pagination.isLastPage());
            
            diseaseMonitor.setFinish(true);
        }
        catch (Exception e)
        {
            log.error("Index for diseases error", e);
            diseaseMonitor.setFinish(true);
        }
    }
    
    @Override
    public Monitor monitorForDiseases()
    {
        return diseaseMonitor;
    }
    
    @Override
    public void indexForPhenotypes()
    {
        if (!phenotypeMonitor.isStartable())
        {
            return;
        }
        
        phenotypeMonitor.setStart(true);
        phenotypeMonitor.setFinish(false);
        
        int pageNo = 1;
        int pageSize = 200;
        
        try
        {
            NamedQueryer queryer = new NamedQueryer();
            queryer.setHql("FROM Phenotype p WHERE p.deleted = false ORDER BY p.createTime ");
            queryer.setNames(new ArrayList<String>());
            queryer.setValues(new ArrayList<Object>());
            
            int processedCount = 0;
            Pagination<Phenotype> pagination;
            
            do
            {
                pagination = baseDaoSupport.find(queryer, pageNo++, pageSize, Phenotype.class);
                phenotypeMonitor.setTotalCount(pagination.getTotalCount());
                
                ESPhenotype esp;
                
                for (Phenotype p : pagination.getRecords())
                {
                    esp = new ESPhenotype();
                    BeanUtils.copyProperties(p, esp);
                    esp.setOmim(p.getCode());
                    esp.setEnname(p.getEnName());
                    
                    Set<Object> diseaseNames = new HashSet<Object>();
                    
                    List<Disease> diseases = getPhenotypeDiseases(p.getId());
                    
                    for (Disease disease : diseases)
                    {
                        diseaseNames.add(disease.getName());
                    }
                    
                    esp.setDiseases(String.join(" ", diseaseNames.toArray(new String[0])));
                    
                    indexForPhenotype(esp);
                    phenotypeMonitor.setProcessedCount(++processedCount);
                }
                
            } while (!pagination.isLastPage());
            
            phenotypeMonitor.setFinish(true);
        }
        catch (Exception e)
        {
            log.error("Index for phenotypes error", e);
            phenotypeMonitor.setFinish(true);
        }
    }
    
    @Override
    public Monitor monitorForPhenotypes()
    {
        return phenotypeMonitor;
    }
    
    private List<Gene> getProductGenes(String id)
    {
        String hql = "FROM Gene g WHERE EXISTS (SELECT pg.id FROM ProductGene pg WHERE pg.productId = :id AND pg.geneId = g.id)";
        return baseDaoSupport.findByNamedParam(Gene.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    private List<Disease> getProductDiseases(String id)
    {
        String hql = "FROM Disease d WHERE EXISTS (SELECT pd.id FROM ProductDisease pd WHERE pd.productId = :id AND pd.diseaseId = d.id)";
        return baseDaoSupport.findByNamedParam(Disease.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    private List<Phenotype> getProductPhenotypes(String id)
    {
        String hql =
            "FROM Phenotype p WHERE EXISTS (SELECT dp.id FROM DiseasePhenotype dp WHERE dp.phenotypeId = p.id AND EXISTS (SELECT pd.id FROM ProductDisease pd WHERE pd.productId = :id AND pd.diseaseId = dp.diseaseId))";
        return baseDaoSupport.findByNamedParam(Phenotype.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    private List<Phenotype> getDiseasePhenotypes(String id)
    {
        String hql = "FROM Phenotype p WHERE EXISTS (SELECT dp.id FROM DiseasePhenotype dp WHERE dp.diseaseId = :id AND dp.phenotypeId = p.id)";
        return baseDaoSupport.findByNamedParam(Phenotype.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    private List<Gene> getDiseaseGenes(String id)
    {
        String hql = "FROM Gene g WHERE EXISTS (SELECT dg.id FROM DiseaseGene dg WHERE dg.diseaseId = :id AND dg.geneId = g.id)";
        return baseDaoSupport.findByNamedParam(Gene.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    private List<DiseaseAlias> getDiseaseAlias(String id)
    {
        String hql = "FROM DiseaseAlias da WHERE da.diseaseId = :id";
        return baseDaoSupport.findByNamedParam(DiseaseAlias.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    private List<GeneAlias> getGeneAlias(String id)
    {
        String hql = "FROM GeneAlias ga WHERE ga.geneId = :id";
        return baseDaoSupport.findByNamedParam(GeneAlias.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    private List<Disease> getGeneDiseases(String id)
    {
        String hql = "FROM Disease d WHERE EXISTS (SELECT dg.id FROM DiseaseGene dg WHERE dg.geneId = :id AND dg.diseaseId = d.id)";
        return baseDaoSupport.findByNamedParam(Disease.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    private List<Document> getGeneDocuments(String id)
    {
        String hql = "FROM Document d WHERE EXISTS (SELECT dk.id FROM DocumentKnowledge dk WHERE dk.geneId = :id AND dk.documentId = d.id)";
        return baseDaoSupport.findByNamedParam(Document.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    private List<Document> getDiseaseDocuments(String id)
    {
        String hql = "FROM Document d WHERE EXISTS (SELECT dk.id FROM DocumentKnowledge dk WHERE dk.diseaseId = :id AND dk.documentId = d.id)";
        return baseDaoSupport.findByNamedParam(Document.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    private List<Disease> getPhenotypeDiseases(String id)
    {
        String hql = "FROM Disease d WHERE EXISTS (SELECT dp.id FROM DiseasePhenotype dp WHERE dp.phenotypeId = :id AND dp.diseaseId = d.id)";
        return baseDaoSupport.findByNamedParam(Disease.class, hql, new String[] {"id"}, new Object[] {id});
    }
    
    public boolean indexForProduct(ESProduct product)
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
        removeForProduct(product.getId());
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to index product json {}", json);
        }
        
        IndexRequestBuilder builder = client.prepareIndex(configs.getElasticsearchIndex(), "PRODUCT", product.getId());
        IndexResponse response = builder.setSource(json).execute().actionGet();
        boolean created = response.isCreated();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to index product json {}, result is {}", json, created);
        }
        
        return created;
    }
    
    public boolean removeForProduct(String id)
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
        
        DeleteRequestBuilder builder = client.prepareDelete(configs.getElasticsearchIndex(), "PRODUCT", id);
        DeleteResponse response = builder.execute().actionGet();
        boolean found = response.isFound();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to remove product {}, result is {}", id, found);
        }
        
        return found;
    }
    
    public boolean indexForGene(ESGene gene)
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
        removeForGene(gene.getId());
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to index gene json {}", json);
        }
        
        IndexRequestBuilder builder = client.prepareIndex(configs.getElasticsearchIndex(), "GENE", gene.getId());
        IndexResponse response = builder.setSource(json).execute().actionGet();
        boolean created = response.isCreated();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to index gene json {}, result is {}", json, created);
        }
        
        return created;
    }
    
    public boolean removeForGene(String id)
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
        
        DeleteRequestBuilder builder = client.prepareDelete(configs.getElasticsearchIndex(), "GENE", id);
        DeleteResponse response = builder.execute().actionGet();
        boolean found = response.isFound();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to remove gene {}, result is {}", id, found);
        }
        
        return found;
    }
    
    public boolean indexForDisease(ESDisease disease)
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
        removeForDisease(disease.getId());
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to index disease json {}", json);
        }
        
        IndexRequestBuilder builder = client.prepareIndex(configs.getElasticsearchIndex(), "DISEASE", disease.getId());
        IndexResponse response = builder.setSource(json).execute().actionGet();
        boolean created = response.isCreated();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to index disease json {}, result is {}", json, created);
        }
        
        return created;
    }
    
    public boolean removeForDisease(String id)
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
        
        DeleteRequestBuilder builder = client.prepareDelete(configs.getElasticsearchIndex(), "DISEASE", id);
        DeleteResponse response = builder.execute().actionGet();
        boolean found = response.isFound();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to remove disease {}, result is {}", id, found);
        }
        
        return found;
    }
    
    public boolean indexForPhenotype(ESPhenotype phenotype)
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
        removeForPhenotype(phenotype.getId());
        
        if (log.isDebugEnabled())
        {
            log.debug("Start to index phenotype json {}", json);
        }
        
        IndexRequestBuilder builder = client.prepareIndex(configs.getElasticsearchIndex(), "PHENOTYPE", phenotype.getId());
        IndexResponse response = builder.setSource(json).execute().actionGet();
        boolean created = response.isCreated();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to index phenotype json {}, result is {}", json, created);
        }
        
        return created;
    }
    
    public boolean removeForPhenotype(String id)
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
        
        DeleteRequestBuilder builder = client.prepareDelete(configs.getElasticsearchIndex(), "PHENOTYPE", id);
        DeleteResponse response = builder.execute().actionGet();
        boolean found = response.isFound();
        
        if (log.isDebugEnabled())
        {
            log.debug("End to remove phenotype {}, result is {}", id, found);
        }
        
        return found;
    }
}
