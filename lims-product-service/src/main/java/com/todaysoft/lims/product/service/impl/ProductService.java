package com.todaysoft.lims.product.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.google.gson.Gson;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.config.Configs;
import com.todaysoft.lims.product.dao.searcher.ProductSearcher;
import com.todaysoft.lims.product.entity.MeasureUnit;
import com.todaysoft.lims.product.entity.MetadataSample;
import com.todaysoft.lims.product.entity.Probe;
import com.todaysoft.lims.product.entity.Product;
import com.todaysoft.lims.product.entity.ProductAmountRule;
import com.todaysoft.lims.product.entity.ProductDisease;
import com.todaysoft.lims.product.entity.ProductGene;
import com.todaysoft.lims.product.entity.ProductPrincipal;
import com.todaysoft.lims.product.entity.ProductProbe;
import com.todaysoft.lims.product.entity.ProductSample;
import com.todaysoft.lims.product.entity.ProductTestingMethod;
import com.todaysoft.lims.product.entity.SimpleProduct;
import com.todaysoft.lims.product.entity.TestingMethod;
import com.todaysoft.lims.product.entity.TestingType;
import com.todaysoft.lims.product.model.ProductProbeConfig;
import com.todaysoft.lims.product.model.ProductSimpleModel;
import com.todaysoft.lims.product.model.request.ProductCreateRequest;
import com.todaysoft.lims.product.model.request.ProductDataListRequest;
import com.todaysoft.lims.product.model.request.ProductListRequest;
import com.todaysoft.lims.product.model.request.ProductPagingRequest;
import com.todaysoft.lims.product.model.request.ProductProbeRequest;
import com.todaysoft.lims.product.service.IMetadataSampleService;
import com.todaysoft.lims.product.service.IProductService;
import com.todaysoft.lims.product.service.adapter.ProductCosumerEvent;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ProductService implements IProductService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;
    
    @Autowired
    private IMetadataSampleService metaService;
    
    @Override
    public Pagination<Product> paging(ProductPagingRequest request)
    {
        ProductSearcher searcher = new ProductSearcher(baseDaoSupport);
        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        searcher.setProduct(product);
        searcher.setProductDutyName(request.getProductDutyName());
        searcher.setDataAuthoritySearcher(request.getDataAuthoritySearcher());
        searcher.setTestingSubtype(request.getTestingSubtype());
        searcher.setPrincipalName(request.getPrincipalName());
        searcher.setProductProbe(request.getProductProbe());
        
        Pagination<Product> paging = baseDaoSupport.find(searcher.toAuthQuery(), request.getPageNo(), request.getPageSize(), Product.class);
        for (Product p : paging.getRecords())
        {
            //防止关联查询
            p.setProductDiseaseList(null);
            p.setProductGeneList(null);
            
        }
        return paging;
        
    }
    
    @Override
    public List<Product> list(ProductListRequest request)
    {
        ProductSearcher searcher = new ProductSearcher(baseDaoSupport);
        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        searcher.setProduct(product);
        searcher.setLikeSearch(request.isLikeSearch());
        return baseDaoSupport.find(searcher);
    }
    
    @Override
    @Transactional
    public String create(ProductCreateRequest request)
    {
        Product entity = new Product();
        BeanUtils.copyProperties(request, entity, new String[] {"probeArray"});
        entity.setDelFlag(0);
        entity.setPrice((int)(Double.parseDouble((request.getPrice())) * 100));
        baseDaoSupport.insert(entity);
        // 插入负责人
        for (ProductPrincipal principal : request.getProductPrincipalList())
        {
            ProductPrincipal prin = new ProductPrincipal();
            prin.setProduct(entity);
            prin.setPrincipal(principal.getPrincipal());
            
            baseDaoSupport.insert(prin);
        }
        // 插入产品推荐样本
        for (ProductSample productSample : request.getProductSampleList())
        {
            
            productSample.setProduct(entity);
            baseDaoSupport.insert(productSample);
        }
        // 插入产品检测方法
        for (ProductTestingMethod testingMethod : request.getProductTestingMethodList())
        {
            
            testingMethod.setProduct(entity);
            baseDaoSupport.insert(testingMethod);
            // 插入关联探针
            for (ProductProbeRequest productprobe : request.getProductProbeList())
            {
                if (productprobe.getMethodId().equals(testingMethod.getTestingMethod().getId()))
                {// 是该检测方法的探针
                    for (Probe probe : productprobe.getProbes())
                    {
                        ProductProbe pp = new ProductProbe();
                        pp.setDataSize(probe.getDataSize());
                        pp.setProbeId(probe.getId());
                        
                        pp.setProductTestingMethod(testingMethod);
                        baseDaoSupport.insert(pp);
                    }
                }
            }
        }
        
        //插入疾病和基因
        if (null != request.getProductDiseaseList())
        {
            for (ProductDisease productDisease : request.getProductDiseaseList())
            {
                productDisease.setProduct(entity);
                
                baseDaoSupport.insert(productDisease);
                
            }
            
        }
        if (null != request.getProductGeneList())
        {
            for (ProductGene productGene : request.getProductGeneList())
            {
                productGene.setProduct(entity);
                
                baseDaoSupport.insert(productGene);
                
            }
            
        }
        
        for (com.todaysoft.lims.product.model.request.ProductAmountRule rule : request.getProductAmountRuleList())
        {
            ProductAmountRule amountRule = new ProductAmountRule();
            amountRule.setProduct(entity);
            amountRule.setCount(rule.getCount());
            amountRule.setAmount(rule.getAmount().multiply(new BigDecimal("100")).intValue());
            baseDaoSupport.insert(amountRule);
        }
        return entity.getId();
        
    }
    
    private void createProbe(List<ProductProbe> probeList, Product entity)
    {
        ProductProbe productProbe = null;
        if (Collections3.isNotEmpty(probeList))
        {
            for (ProductProbe p : probeList)
            {
                productProbe = new ProductProbe();
                BeanUtils.copyProperties(p, productProbe, new String[] {"productId"});
                
                baseDaoSupport.insert(productProbe);
            }
        }
    }
    
    @Override
    public boolean unique(String code)
    {
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql("FROM Product s WHERE s.code = :code");
        queryer.setNames(Arrays.asList("code"));
        queryer.setValues(Arrays.asList((Object)code));
        List<Product> records = baseDaoSupport.find(queryer, Product.class);
        return records.isEmpty();
    }
    
    @Override
    public Product getProduct(String id)
    {
        Product p = baseDaoSupport.get(Product.class, id);
        return p;
    }
    
    @Override
    @Transactional
    public String deleteProduct(String id)
    {
        
        Product p = baseDaoSupport.get(Product.class, id);
        if (StringUtils.isNotEmpty(p))
        {
            p.setDelFlag(1);
            baseDaoSupport.update(p);
            return p.getId();
        }
        return "";
    }
    
    @Override
    @Transactional
    public String modifyProduct(ProductCreateRequest request)
    {
        Product entity = new Product();
        BeanUtils.copyProperties(request, entity, new String[] {"probeArray", "productAmountRule"});
        entity.setDelFlag(0);
        entity.setPrice((int)(Double.parseDouble((request.getPrice())) * 100));
        baseDaoSupport.update(entity);
        // 清空推荐样本和检测方法，探針,负责人
        baseDaoSupport.execute("delete from ProductSample p where p.product.id='" + entity.getId() + "'");
        baseDaoSupport.execute("delete from ProductTestingMethod p where p.product.id='" + entity.getId() + "'");
        baseDaoSupport.execute("delete from ProductPrincipal p where p.product.id='" + entity.getId() + "'");
        
        baseDaoSupport.execute("delete from ProductAmountRule p where p.product.id='" + entity.getId() + "'");
        for (com.todaysoft.lims.product.model.request.ProductAmountRule rule : request.getProductAmountRuleList())
        {
            ProductAmountRule amountRule = new ProductAmountRule();
            amountRule.setProduct(entity);
            amountRule.setCount(rule.getCount());
            amountRule.setAmount(rule.getAmount().multiply(new BigDecimal("100")).intValue());
            baseDaoSupport.insert(amountRule);
        }
        
        //插入负责人
        for (ProductPrincipal principal : request.getProductPrincipalList())
        {
            ProductPrincipal prin = new ProductPrincipal();
            prin.setProduct(entity);
            prin.setPrincipal(principal.getPrincipal());
            baseDaoSupport.insert(prin);
        }
        // 插入产品推荐样本
        for (ProductSample productSample : request.getProductSampleList())
        {
            
            productSample.setProduct(entity);
            baseDaoSupport.insert(productSample);
        }
        // 插入产品检测方法
        for (ProductTestingMethod testingMethod : request.getProductTestingMethodList())
        {
            
            testingMethod.setProduct(entity);
            baseDaoSupport.insert(testingMethod);
            // 插入关联探针
            for (ProductProbeRequest productprobe : request.getProductProbeList())
            {
                if (productprobe.getMethodId().equals(testingMethod.getTestingMethod().getId()))
                {//是该检测方法的探针
                    for (Probe probe : productprobe.getProbes())
                    {
                        ProductProbe pp = new ProductProbe();
                        pp.setDataSize(probe.getDataSize());
                        pp.setProbeId(probe.getId());
                        
                        pp.setProductTestingMethod(testingMethod);
                        baseDaoSupport.insert(pp);
                        
                    }
                }
                
            }
            
        }
        
        //先清空
        baseDaoSupport.execute("delete from ProductDisease p where p.product.id='" + entity.getId() + "'");
        baseDaoSupport.execute("delete from ProductGene p where p.product.id='" + entity.getId() + "'");
        //插入疾病和基因
        if (null != request.getProductDiseaseList())
        {
            for (ProductDisease productDisease : request.getProductDiseaseList())
            {
                productDisease.setProduct(entity);
                
                baseDaoSupport.insert(productDisease);
                
            }
            
        }
        if (null != request.getProductGeneList())
        {
            for (ProductGene productGene : request.getProductGeneList())
            {
                productGene.setProduct(entity);
                
                baseDaoSupport.insert(productGene);
                
            }
            
        }
        return entity.getId();
        
    }
    
    @Transactional
    private void updateProductProbe(ProductCreateRequest request, Product entity)
    {
        String hql = "delete ProductProbe as t where t.productId.id = " + entity.getId();
        baseDaoSupport.execute(hql);
        createProbe(request.getProbeList(), entity); // 保存对应的探针
    }
    
    @Override
    public List<Product> getContactProducts(List<Integer> productIds)
    {
        if (null == productIds || productIds.isEmpty())
        {
            return null;
        }
        String hql = "FROM Product p WHERE p.id IN :productIds";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Arrays.asList("productIds")).values(Arrays.asList((Object)productIds)).build();
        List<Product> products = baseDaoSupport.find(queryer, Product.class);
        return products;
    }
    
    @Override
    public Pagination<SimpleProduct> productSelect(ProductPagingRequest request)
    {
        Set<String> set = new HashSet<String>();
        if (StringUtils.isNotEmpty(request.getId()))
        {
            String[] strs = request.getId().split(",");
            for (String str : strs)
            {
                if (StringUtils.isNotEmpty(str))
                {
                    set.add(str.trim());
                }
            }
            
        }
        if (StringUtils.isNotEmpty(request.gettType()))
        {
            TestingType tt = new TestingType();
            tt.setId(request.gettType());
            request.setTestingType(tt);
        }
        StringBuffer hql = new StringBuffer("FROM SimpleProduct p WHERE (p.code LIKE :code or p.name LIKE :name) and p.enabled = 1 and p.delFlag = 0");
        if (null != request.getTestingType() && StringUtils.isNotEmpty(request.getTestingType().getId()))
        {
            hql.append(" and p.testingType.id = '" + request.getTestingType().getId() + "'");
        }
        NamedQueryer queryer = null;
        if (Collections3.isNotEmpty(set))
        {
            hql.append(" and p.id not in (:list)");
            queryer =
                NamedQueryer.builder()
                    .hql(hql.toString())
                    .names(Arrays.asList("code", "name", "list"))
                    .values(Arrays.asList((Object)("%" + request.getCode() + "%"), (Object)("%" + request.getCode() + "%"), (Object)set))
                    .build();
        }
        else
        {
            queryer =
                NamedQueryer.builder()
                    .hql(hql.toString())
                    .names(Arrays.asList("code", "name"))
                    .values(Arrays.asList((Object)("%" + request.getCode() + "%"), (Object)("%" + request.getCode() + "%")))
                    .build();
        }
        return baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), SimpleProduct.class);
    }
    
    @Override
    public boolean validate(ProductCreateRequest request)
    {
        
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Product.class, request, "name", "code")))
        {
            
            return false;
        }
        return true;
    }
    
    @Override
    public List<ProductProbe> getProbeList(ProductCreateRequest request)
    {
        String hql = "FROM ProductProbe p WHERE p.productId.id =:id";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Arrays.asList("id")).values(Arrays.asList((Object)(request.getId()))).build();
        List<ProductProbe> result = baseDaoSupport.find(queryer, ProductProbe.class);
        /*
         * for(ProductProbe p :result){ Probe probe = baseDaoSupport.get(Probe.class,
         * p.getProbeId()); if(StringUtils.isNotEmpty(probe)){ p.setProbeName(probe.getName()); } }
         */
        return result;
    }
    
    /**
     * 根据产品id后者探针名称获取数据量
     */
    @Override
    public List<Double> getProbeDataSize(ProductDataListRequest request)
    {
        StringBuffer hql = new StringBuffer("select p.dataSize FROM ProductProbe p WHERE 1=1 ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCondition(request, hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return baseDaoSupport.find(queryer, Double.class);
    }
    
    private void addCondition(ProductDataListRequest request, StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(request.getProductId()))
        {
            hql.append(" and p.productId.id =:id");
            paramNames.add("id");
            parameters.add(request.getProductId());
        }
        if (StringUtils.isNotEmpty(request.getProbeName()))
        {
            hql.append(" and p.probeName =:name");
            paramNames.add("name");
            parameters.add(request.getProbeName());
        }
    }
    
    /**
     * 根据探针id获取探针默认数据量
     */
    @Override
    public Double getDefaultDataSize(ProductDataListRequest request)
    {
        if (StringUtils.isNotEmpty(request.getProbeId()))
        {
            Probe pro = baseDaoSupport.get(Probe.class, request.getProbeId());
            if (pro != null)
            {
                return 0D;
            }
            else
            {
                throw new RuntimeException("根据id：" + request.getProbeId() + "找不到探针默认数量");
            }
        }
        else
        {
            return (double)0;
        }
    }
    
    @Override
    public List<ProductProbeConfig> getProbeConfigs(String productId, String testingMethodId)
    {
        String hql = "FROM ProductProbe p WHERE p.productTestingMethod.product.id = :productId AND p.productTestingMethod.testingMethod.id= :testingMethodId";
        List<ProductProbe> productProbes =
            baseDaoSupport.findByNamedParam(ProductProbe.class, hql, new String[] {"productId", "testingMethodId"}, new Object[] {productId, testingMethodId});
        
        if (CollectionUtils.isEmpty(productProbes))
        {
            return Collections.emptyList();
        }
        
        Probe probe;
        ProductProbeConfig config;
        List<ProductProbeConfig> configs = new ArrayList<ProductProbeConfig>();
        
        for (ProductProbe productProbe : productProbes)
        {
            probe = baseDaoSupport.get(Probe.class, productProbe.getProbeId());
            
            if (null == probe)
            {
                throw new IllegalStateException();
            }
            
            config = new ProductProbeConfig();
            config.setProbeId(probe.getId());
            config.setProbeName(probe.getName());
            config.setProbeDatasize(BigDecimal.valueOf(productProbe.getDataSize()));
            configs.add(config);
        }
        
        return configs;
    }
    
    @Override
    public BigDecimal getTestingDatasize(String productId, String testingMethodId)
    {
        String hql = "FROM ProductTestingMethod ptm WHERE ptm.product.id = :productId AND ptm.testingMethod.id = :testingMethodId";
        
        List<ProductTestingMethod> records =
            baseDaoSupport.findByNamedParam(ProductTestingMethod.class, hql, new String[] {"productId", "testingMethodId"}, new Object[] {productId,
                testingMethodId});
        
        if (CollectionUtils.isEmpty(records))
        {
            return BigDecimal.ZERO;
        }
        
        // 产品+检测方法应该只对应一条记录
        if (records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        ProductTestingMethod record = records.get(0);
        TestingMethod method = record.getTestingMethod();
        
        // 只有非捕获的NGS方法的产品有检测数据量这个概念
        if (!"1".equals(method.getPlatForm()) || method.isCapture())
        {
            throw new IllegalStateException();
        }
        
        return record.getTestingDatasize();
    }
    
    @Override
    public List<String> getAnalyCoordinates(String productId, String testingMethodId)
    {
        String hql = "FROM ProductTestingMethod ptm WHERE ptm.product.id = :productId AND ptm.testingMethod.id = :testingMethodId";
        
        List<ProductTestingMethod> records =
            baseDaoSupport.findByNamedParam(ProductTestingMethod.class, hql, new String[] {"productId", "testingMethodId"}, new Object[] {productId,
                testingMethodId});
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        // 产品+检测方法应该只对应一条记录
        if (records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        ProductTestingMethod record = records.get(0);
        TestingMethod method = record.getTestingMethod();
        
        if (!method.isAnalyse())
        {
            return Collections.emptyList();
        }
        
        String text = record.getCoordinate();
        
        if (StringUtils.isEmpty(text))
        {
            return Collections.emptyList();
        }
        
        return Arrays.asList(text.split(","));
    }
    
    @Override
    @Transactional
    public String enable(ProductCreateRequest request)
    {
        Product product = baseDaoSupport.get(Product.class, request.getId());
        product.setEnabled(request.getEnabled());
        baseDaoSupport.merge(product);
        return product.getId();
    }
    
    @Override
    public ProductSimpleModel getProductSimpleModel(String id)
    {
        Product entity = getProduct(id);
        
        if (null == entity)
        {
            return null;
        }
        
        ProductSimpleModel model = new ProductSimpleModel();
        model.setId(entity.getId());
        model.setCode(entity.getCode());
        model.setName(entity.getName());
        model.setTestingDuration(entity.getTestingDuration());
        model.setTestingSampleType(entity.getTestingStartSample());
        
        List<ProductPrincipal> principals = entity.getProductPrincipalList();
        
        if (!CollectionUtils.isEmpty(principals))
        {
            List<String> principalNames = new ArrayList<String>();
            
            for (ProductPrincipal principal : principals)
            {
                if (null == principal.getPrincipal() || null == principal.getPrincipal().getArchive())
                {
                    continue;
                }
                
                principalNames.add(principal.getPrincipal().getArchive().getName());
            }
            
            model.setTechnicalPrincipals(principalNames);
        }
        
        List<String> supportedSampleTypes = new ArrayList<String>();
        List<ProductSample> samples = entity.getProductSampleList();
        
        if (!CollectionUtils.isEmpty(samples))
        {
            for (ProductSample sample : samples)
            {
                supportedSampleTypes.add(sample.getSample().getId());
            }
            
            model.setSupportedSampleTypes(supportedSampleTypes);
        }
        
        return model;
    }
    
    @Override
    public void sendProduct(String productId, String actionType)
    {
        ProductCosumerEvent event = new ProductCosumerEvent();
        event.setId(productId);
        event.setActionType(actionType);
        Message msg = new Message(configs.getAliyunONSTopic(), "productTag", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult.getMessageId());
        
    }
    
    @Override
    public List<MetadataSample> getSampleByProductIds(ProductListRequest productIds)
    {
        String hql = "select distinct m from MetadataSample m ,ProductSample p where m.id = p.sample.id and p.product.id in (:ids) ";
        List<MetadataSample> result =
            baseDaoSupport.findByNamedParam(MetadataSample.class, hql, new String[] {"ids"}, new Object[] {productIds.getProductIds()});
        List<MeasureUnit> unitList = metaService.unitList();
        for (MetadataSample sample : result)
        {
            metaService.wrapSampleUnit(sample, unitList);
        }
        return result;
    }
}
