package com.todaysoft.lims.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.google.gson.Gson;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.IEntityWrapper;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.config.Configs;
import com.todaysoft.lims.product.dao.searcher.DiseaseGeneSearcher;
import com.todaysoft.lims.product.dao.searcher.DiseaseSearcher;
import com.todaysoft.lims.product.entity.Phenotype;
import com.todaysoft.lims.product.entity.Product;
import com.todaysoft.lims.product.entity.ProductDisease;
import com.todaysoft.lims.product.entity.ProductGene;
import com.todaysoft.lims.product.entity.disease.Disease;
import com.todaysoft.lims.product.entity.disease.DiseaseAlias;
import com.todaysoft.lims.product.entity.disease.DiseaseGene;
import com.todaysoft.lims.product.entity.disease.DiseaseHereditary;
import com.todaysoft.lims.product.entity.disease.DiseasePhenotype;
import com.todaysoft.lims.product.entity.disease.Gene;
import com.todaysoft.lims.product.entity.disease.GeneAlias;
import com.todaysoft.lims.product.listener.ESModel.ESDisease;
import com.todaysoft.lims.product.listener.ESModel.ESGene;
import com.todaysoft.lims.product.listener.ESModel.ESPhenotype;
import com.todaysoft.lims.product.listener.ESModel.ESProduct;
import com.todaysoft.lims.product.model.request.DiseaseFormRequest;
import com.todaysoft.lims.product.model.request.DiseaseGeneFormRequest;
import com.todaysoft.lims.product.model.request.DiseaseGenePagingRequest;
import com.todaysoft.lims.product.model.request.DiseasePagingRequest;
import com.todaysoft.lims.product.model.request.DocumentRequest;
import com.todaysoft.lims.product.model.response.DiseasePageModel;
import com.todaysoft.lims.product.model.response.GenePageModel;
import com.todaysoft.lims.product.model.response.PhenotypePageModel;
import com.todaysoft.lims.product.model.response.SimpleDisease;
import com.todaysoft.lims.product.service.IDiseaseService;
import com.todaysoft.lims.product.service.IDocumentService;
import com.todaysoft.lims.product.service.IPhenotypeService;
import com.todaysoft.lims.product.service.adapter.DiseaseCosumerEvent;
import com.todaysoft.lims.product.service.adapter.GeneCosumerEvent;
import com.todaysoft.lims.product.service.adapter.KBMAdapter;
import com.todaysoft.lims.product.util.ProductConstant;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DiseaseService implements IDiseaseService, IEntityWrapper<Disease, DiseasePageModel>
{
    private static Logger log = LoggerFactory.getLogger(DiseaseService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;
    
    @Autowired
    private IDocumentService docservice;
    
    @Autowired
    private KBMAdapter adapter;
    
    @Autowired
    private IPhenotypeService pheservice;
    
    @Override
    @Transactional
    public String create(DiseaseGeneFormRequest request)
    {
        Gene entity = new Gene();
        BeanUtils.copyProperties(request, entity);
        entity.setDeleted(false);
        entity.setCreateTime(new Date());
        baseDaoSupport.insert(entity);
        
        createAlias(request, entity);
        
        return entity.getId();
    }
    
    @Override
    public void sendGeneProduce(String geneId, String actionType)
    {
        GeneCosumerEvent event = new GeneCosumerEvent();
        event.setId(geneId);
        event.setActionType(actionType);
        Message msg = new Message(configs.getAliyunONSTopic(), "geneTag", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult.getMessageId());
        
    }
    
    private void createAlias(DiseaseGeneFormRequest request, Gene entity)
    {
        for (GeneAlias alias : request.getAlias())
        {
            GeneAlias p = new GeneAlias();
            BeanUtils.copyProperties(alias, p, new String[] {"geneId", "id"});
            p.setGeneId(entity);
            baseDaoSupport.insert(p);
        }
    }
    
    @Override
    @Transactional
    public String updateGene(DiseaseGeneFormRequest request)
    {
        Gene entity = baseDaoSupport.get(Gene.class, request.getId());
        BeanUtils.copyProperties(request, entity, "alias");
        entity.setCreateTime(new Date());
        baseDaoSupport.update(entity);
        try
        {
            baseDaoSupport.executeHql("delete GeneAlias s where s.geneId = ?", new Object[] {entity});
            createAlias(request, entity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return entity.getId();
    }
    
    @Override
    public Pagination<GenePageModel> paging(DiseaseGenePagingRequest request)
    {
        DiseaseGeneSearcher searcher = new DiseaseGeneSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<Gene> page = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), Gene.class);
        
        Pagination<GenePageModel> result = new Pagination<GenePageModel>(page);
        List<GenePageModel> records = new ArrayList<GenePageModel>();
        for (Gene g : page.getRecords())
        {
            GenePageModel mo = new GenePageModel();
            BeanUtils.copyProperties(g, mo, "diseaseGeneList");
            if (Collections3.isNotEmpty(g.getDiseaseGeneList()))
            {
                List<SimpleDisease> li = new ArrayList<SimpleDisease>();
                for (DiseaseGene gg : g.getDiseaseGeneList())
                {
                    SimpleDisease sim = new SimpleDisease();
                    BeanUtils.copyProperties(gg.getDisease(), sim);
                    li.add(sim);
                }
                mo.setDiseaseGeneList(li);
            }
            
            records.add(mo);
        }
        
        result.setRecords(records);
        return result;
    }
    
    @Override
    @Transactional
    public String delete(String id)
    {
        Gene gene = baseDaoSupport.get(Gene.class, id);
        if (StringUtils.isNotEmpty(gene))
        {
            gene.setDeleted(true);
            gene.setDeleteTime(new Date());
            baseDaoSupport.update(gene);
            return gene.getId();
        }
        
        /* baseDaoSupport.delete(getGene(id));*/
        return "";
    }
    
    private GenePageModel getGene(String id)
    {
        Gene gene = baseDaoSupport.get(Gene.class, id);
        if (StringUtils.isNotEmpty(gene))
        {
            GenePageModel model = new GenePageModel();
            convertGeneToPage(gene, model);
            return model;
        }
        return null;
        
    }
    
    private GenePageModel convertGeneToPage(Gene gene, GenePageModel model)
    {
        if (StringUtils.isNotEmpty(gene))
        {
            BeanUtils.copyProperties(gene, model, "diseaseGeneList");
            if (StringUtils.isNotEmpty(gene.getDiseaseGeneList()))
            {
                List<DiseaseGene> result = gene.getDiseaseGeneList();
                List<SimpleDisease> li = new ArrayList<SimpleDisease>();
                for (DiseaseGene gg : result)
                {
                    SimpleDisease sim = new SimpleDisease();
                    BeanUtils.copyProperties(gg.getDisease(), sim);
                    li.add(sim);
                }
                model.setDiseaseGeneList(li);
            }
            
        }
        return model;
    }
    
    private Disease getDisease(String id)
    {
        return baseDaoSupport.get(Disease.class, id);
    }
    
    @Override
    @Transactional
    public String createDisease(DiseaseFormRequest request)
    {
        Disease entity = new Disease();
        BeanUtils.copyProperties(request, entity, new String[] {"geneList", "phenotypeList"});
        entity.setCreateTime(new Date());
        entity.setDeleted(false);
        baseDaoSupport.insert(entity);
        
        /**
         * 保存疾病别名
         */
        for (DiseaseAlias alias : request.getAlias())
        {
            DiseaseAlias p = new DiseaseAlias();
            BeanUtils.copyProperties(alias, p, new String[] {"geneId"});
            p.setDiseaseId(entity);
            baseDaoSupport.insert(p);
        }
        /**
         * 保存遗传方式
         */
        if (StringUtils.isNotEmpty(request.getHereditaryType()))
        {
            String[] typeId = request.getHereditaryType().split(",");
            for (int i = 0; i < typeId.length; i++)
            {
                DiseaseHereditary diseaseHereditary = new DiseaseHereditary();
                diseaseHereditary.setDisease(entity);
                diseaseHereditary.setHereditaryType(typeId[i]);
                baseDaoSupport.insert(diseaseHereditary);
            }
        }
        
        /**
         * 关联基因
         */
        for (Gene gene : request.getGeneList())
        {
            DiseaseGene p = new DiseaseGene();
            p.setDisease(entity);
            p.setGene(gene);
            p.setGeneScore(StringUtils.isNotEmpty(gene.getGeneScore()) ? gene.getGeneScore() : 0d);
            baseDaoSupport.insert(p);
        }
        
        /**
         * 关联表型
         */
        for (Phenotype phenotype : request.getPhenotypeList())
        {
            DiseasePhenotype p = new DiseasePhenotype();
            p.setDisease(entity);
            p.setPhenotype(phenotype);
            p.setPhneotypeScore(StringUtils.isNotEmpty(phenotype.getPhneotypeScore()) ? phenotype.getPhneotypeScore() : 0d);
            p.setDependency("相关描述信息...");
            baseDaoSupport.insert(p);
        }
        
        return entity.getId();
        
    }
    
    @Override
    public void sendDiseaseProduce(String diseaseId, String actionType)
    {
        DiseaseCosumerEvent event = new DiseaseCosumerEvent();
        event.setId(diseaseId);
        event.setActionType(actionType);
        Message msg = new Message(configs.getAliyunONSTopic(), "diseaseTag", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        log.info("推送疾病结果 {}" + sendResult.getMessageId());
        
    }
    
    @Override
    @Transactional
    public String updateDisease(DiseaseFormRequest request)
    {
        Disease entity = new Disease();
        BeanUtils.copyProperties(request, entity, new String[] {"geneList", "phenotypeList"});
        entity.setCreateTime(new Date());
        baseDaoSupport.update(entity);
        try
        {
            baseDaoSupport.executeHql("delete DiseaseAlias s where s.diseaseId.id = ?", new Object[] {entity.getId()});
            for (DiseaseAlias alias : request.getAlias())
            {
                DiseaseAlias p = new DiseaseAlias();
                BeanUtils.copyProperties(alias, p, new String[] {"diseaseId"});
                p.setDiseaseId(entity);
                baseDaoSupport.insert(p);
            }
            
            /**
             * 保存遗传方式
             */
            baseDaoSupport.executeHql("delete DiseaseHereditary s where s.disease.id = ?", new Object[] {entity.getId()});
            if (StringUtils.isNoneEmpty(request.getHereditaryType()))
            {
                String[] typeId = request.getHereditaryType().split(",");
                for (int i = 0; i < typeId.length; i++)
                {
                    DiseaseHereditary diseaseHereditary = new DiseaseHereditary();
                    diseaseHereditary.setDisease(entity);
                    diseaseHereditary.setHereditaryType(typeId[i]);
                    baseDaoSupport.insert(diseaseHereditary);
                }
            }
            
            /**
             * 关联基因
             */
            baseDaoSupport.executeHql("delete DiseaseGene s where s.disease.id = ?", new Object[] {entity.getId()});
            for (Gene gene : request.getGeneList())
            {
                DiseaseGene p = new DiseaseGene();
                p.setDisease(entity);
                p.setGene(gene);
                p.setGeneScore(StringUtils.isNotEmpty(gene.getGeneScore()) ? gene.getGeneScore() : 0d);
                baseDaoSupport.insert(p);
            }
            
            /**
             * 关联表型
             */
            baseDaoSupport.executeHql("delete DiseasePhenotype s where s.disease.id = ?", new Object[] {entity.getId()});
            for (Phenotype phenotype : request.getPhenotypeList())
            {
                DiseasePhenotype p = new DiseasePhenotype();
                p.setDisease(entity);
                p.setPhenotype(phenotype);
                p.setPhneotypeScore(StringUtils.isNotEmpty(phenotype.getPhneotypeScore()) ? phenotype.getPhneotypeScore() : 0d);
                p.setDependency("相关描述信息...");
                baseDaoSupport.insert(p);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return entity.getId();
    }
    
    @Override
    @Transactional
    public String deleteDisease(String id)
    {
        Disease d = getDisease(id);
        if (StringUtils.isNotEmpty(d))
        {
            d.setDeleted(true);
            d.setDeleteTime(new Date());
            baseDaoSupport.update(d);
            return d.getId();
        }
        else
        {
            return "";
        }
        
        /*  baseDaoSupport.delete(getDisease(id));*/
        
    }
    
    @Override
    public Pagination<DiseasePageModel> pagingDisease(DiseasePagingRequest request)
    {
        DiseaseSearcher searcher = new DiseaseSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), this);
    }
    
    @Override
    public List<GenePageModel> geneList(DiseaseGenePagingRequest request)
    {
        DiseaseGeneSearcher searcher = new DiseaseGeneSearcher();
        BeanUtils.copyProperties(request, searcher);
        List<Gene> result = baseDaoSupport.find(searcher);
        List<GenePageModel> model = new ArrayList<GenePageModel>();
        for (Gene g : result)
        {
            GenePageModel mo = new GenePageModel();
            BeanUtils.copyProperties(g, mo, "diseaseGeneList");
            if (Collections3.isNotEmpty(g.getDiseaseGeneList()))
            {
                List<SimpleDisease> li = new ArrayList<SimpleDisease>();
                for (DiseaseGene gg : g.getDiseaseGeneList())
                {
                    SimpleDisease sim = new SimpleDisease();
                    BeanUtils.copyProperties(gg.getDisease(), sim);
                    li.add(sim);
                }
                mo.setDiseaseGeneList(li);
            }
            
            model.add(mo);
        }
        return model;
    }
    
    @Override
    public GenePageModel getGeneById(String id)
    {
        GenePageModel ge = getGene(id);
        
        return ge;
    }
    
    @Override
    public boolean validateDiseaseName(DiseaseFormRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Disease.class, request, "name")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean validateGeneName(DiseaseGeneFormRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Gene.class, request, "symbol")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public DiseasePageModel getDiseaseById(String id)
    {
        Disease d = baseDaoSupport.get(Disease.class, id);
        return new DiseasePageModel(d);
    }
    
    @Override
    public boolean validateDiseaseCode(DiseaseFormRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Disease.class, request, "code")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean validateGeneCode(DiseaseFormRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Gene.class, request, "code")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public DiseasePageModel getDiseaseByCode(String code)
    {
        List<Disease> list = baseDaoSupport.find(Disease.class, "from Disease d where d.code='" + code + "' and d.deleted = false");
        if (null != list && !list.isEmpty())
        {
            return new DiseasePageModel(list.get(0));
            
        }
        return null;
    }
    
    @Override
    public GenePageModel getGeneByCode(String code)
    {
        List<Gene> list = baseDaoSupport.find(Gene.class, "from Gene g where g.code='" + code + "' and g.deleted = false");
        GenePageModel model = new GenePageModel();
        if (null != list && !list.isEmpty())
        {
            model = convertGeneToPage(list.get(0), model);
            return model;
            
        }
        return null;
    }
    
    @Override
    public List<DiseasePageModel> diseaseSelect(DiseasePagingRequest request)
    {
        DiseaseSearcher searcher = new DiseaseSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher, this);
    }
    
    @Override
    public DiseasePageModel wrap(Disease entity)
    {
        return new DiseasePageModel(entity);
    }
    
    @Override
    public Integer getProductDiease(String diseaseId)
    {
        List<ProductDisease> list = baseDaoSupport.find(ProductDisease.class, "from ProductDisease pd where pd.disease.id ='" + diseaseId + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.size();
        }
        return 0;
    }
    
    @Override
    public Integer getProductGenes(String geneId)
    {
        List<ProductGene> list = baseDaoSupport.find(ProductGene.class, "from ProductGene pg where pg.gene.id ='" + geneId + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.size();
        }
        return 0;
    }
    
    @Override
    public DiseasePageModel getDiseaseByENName(DiseaseGeneFormRequest request)
    {
        List<Disease> list =
            baseDaoSupport.find(Disease.class, "from Disease d where d.nameEn='" + request.getName().replaceAll("'", "''") + "' and d.deleted = false");
        if (null != list && !list.isEmpty())
        {
            return new DiseasePageModel(list.get(0));
            
        }
        return null;
    }
    
    @Override
    public List<String> getProductIdDiease(String diseaseId)
    {
        List<String> list = new ArrayList<String>();
        list = baseDaoSupport.find(String.class, "select product.id from ProductDisease pd where pd.disease.id ='" + diseaseId + "'");
        return list;
    }
    
    @Override
    public List<String> getEnableAndNotModelProductIdDiease(String diseaseId)
    {
        List<String> list = new ArrayList<String>();
        String hql =
            "select pd.product.id from ProductDisease pd , Product p where  pd.disease.id =:diseaseId pd.product.id = p.id and p.enabled =:enabled and p.ifMade =:ifMade ";
        list =
            baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"diseaseId", "enabled", "ifMade"}, new Object[] {diseaseId,
                ProductConstant.PRODUCT_ENABLE_YES, ProductConstant.PRODUCT_MADE_NO});
        return list;
    }
    
    @Override
    public boolean createRelationDisease(String id)
    {
        Disease disease = baseDaoSupport.get(Disease.class, id);
        if (StringUtils.isNotEmpty(disease))
        {
            Set<Object> geneNames = new HashSet<Object>();
            Set<Object> phenotypeNames = new HashSet<Object>();
            Set<Object> aliasNames = new HashSet<Object>();
            String document = "";
            for (DiseaseGene dg : disease.getGeneList())
            {
                geneNames.add(dg.getGene().getSymbol());
            }
            for (DiseasePhenotype dp : disease.getPhenotypeList())
            {
                phenotypeNames.add(dp.getPhenotype().getName());
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
            List<String> list = docservice.getDocumentByDisease(request);
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
            
        }
        else
        {
            log.warn("Disease is empty.");
        }
        return true;
    }
    
    @Override
    public boolean createRelationProduct(String id)
    {
        Product product = baseDaoSupport.get(Product.class, id);
        if (StringUtils.isNotEmpty(product) && product.getEnabled().equals(1) && product.getIfMade().equals(0)) //非定制、可用状态
        {
            ESProduct target = new ESProduct();
            Set<Object> disName = new HashSet<Object>();
            Set<Object> geneName = new HashSet<Object>();
            Set<Object> phentypeName = new HashSet<Object>();
            
            //疾病索引
            for (ProductDisease pd : product.getProductDiseaseList())
            {
                Disease dis = pd.getDisease();
                if (StringUtils.isNotEmpty(dis))
                {
                    disName.add(dis.getName());
                    
                    for (DiseasePhenotype dp : dis.getPhenotypeList())
                    {
                        phentypeName.add(dp.getPhenotype().getName());
                    }
                    
                }
                
            }
            //基因索引
            for (ProductGene pg : product.getProductGeneList())
            {
                Gene gene = pg.getGene();
                geneName.add(gene.getSymbol());
            }
            
            BeanUtils.copyProperties(product, target);
            target.setDiseases(String.join(" ", disName.toArray(new String[0])));
            target.setGenes(String.join(" ", geneName.toArray(new String[0])));
            target.setPhenotypes(String.join(" ", phentypeName.toArray(new String[0])));
            target.setCategory(product.getTestingType() != null ? product.getTestingType().getName() : "");
            target.setSubcategory(product.getTestingSubtype() != null ? product.getTestingSubtype().getName() : "");
            adapter.createProductIndex(target);
        }
        else
        {
            log.warn("Product  is empty.");
        }
        return true;
    }
    
    @Override
    public boolean createRelationGene(String id)
    {
        
        Gene gene = baseDaoSupport.get(Gene.class, id);
        if (StringUtils.isNotEmpty(gene))
        {
            Set<Object> diseaseNames = new HashSet<Object>();
            Set<Object> genealiasNames = new HashSet<Object>();
            
            ESGene esgene = new ESGene();
            BeanUtils.copyProperties(gene, esgene);
            for (GeneAlias da : gene.getAlias())
            {
                genealiasNames.add(da.getSymbol());
            }
            
            for (DiseaseGene da : gene.getDiseaseGeneList())
            {
                diseaseNames.add(da.getDisease().getName());
            }
            
            DocumentRequest request = new DocumentRequest();
            request.setId(gene.getId());
            List<String> list = docservice.getDocumentByGene(request);
            String genedocument = String.join(" ", list.toArray(new String[0]));
            
            esgene.setOmim(gene.getCode());
            esgene.setAlias(String.join(" ", genealiasNames.toArray(new String[0])));
            esgene.setDiseases(String.join(" ", diseaseNames.toArray(new String[0])));
            esgene.setDocuments(genedocument);
            adapter.createGeneIndex(esgene);
        }
        else
        {
            log.warn("Gene  is empty.");
        }
        return true;
        
    }
    
    @Override
    public boolean createRelationPhenotype(String id)
    {
        Phenotype p = baseDaoSupport.get(Phenotype.class, id);
        if (StringUtils.isNotEmpty(p))
        {
            Set<Object> disNames = new HashSet<Object>();
            ESPhenotype esPhenotype = new ESPhenotype();
            BeanUtils.copyProperties(p, esPhenotype);
            
            esPhenotype.setOmim(p.getCode());
            esPhenotype.setEnname(p.getEnName());
            PhenotypePageModel pm = pheservice.getPhenotypeById(p.getId());
            if (StringUtils.isNotEmpty(pm))
            {
                List<SimpleDisease> dis = pm.getList();
                if (Collections3.isNotEmpty(dis))
                {
                    for (SimpleDisease s : dis)
                    {
                        disNames.add(s.getName());
                    }
                }
            }
            esPhenotype.setDiseases(String.join(" ", disNames.toArray(new String[0])));
            adapter.createPhenotypeIndex(esPhenotype);
        }
        return true;
    }
    
}
