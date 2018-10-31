package com.todaysoft.lims.product.service;

import java.math.BigDecimal;
import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.MetadataSample;
import com.todaysoft.lims.product.entity.Product;
import com.todaysoft.lims.product.entity.ProductProbe;
import com.todaysoft.lims.product.entity.SimpleProduct;
import com.todaysoft.lims.product.model.ProductProbeConfig;
import com.todaysoft.lims.product.model.ProductSimpleModel;
import com.todaysoft.lims.product.model.request.ProductCreateRequest;
import com.todaysoft.lims.product.model.request.ProductDataListRequest;
import com.todaysoft.lims.product.model.request.ProductListRequest;
import com.todaysoft.lims.product.model.request.ProductPagingRequest;

public interface IProductService
{
    Pagination<Product> paging(ProductPagingRequest request);
    
    Pagination<SimpleProduct> productSelect(ProductPagingRequest request);
    
    boolean unique(String code);
    
    List<Product> list(ProductListRequest request);
    
    Product getProduct(String id);
    
    String create(ProductCreateRequest request);
    
    String deleteProduct(String id);
    
    String modifyProduct(ProductCreateRequest request);
    
    String enable(ProductCreateRequest request);
    
    List<Product> getContactProducts(List<Integer> productIds);
    
    boolean validate(ProductCreateRequest request);
    
    List<ProductProbe> getProbeList(ProductCreateRequest request);
    
    List<Double> getProbeDataSize(ProductDataListRequest request);
    
    Double getDefaultDataSize(ProductDataListRequest request);
    
    List<ProductProbeConfig> getProbeConfigs(String productId, String testingMethodId);
    
    BigDecimal getTestingDatasize(String productId, String testingMethodId);
    
    List<String> getAnalyCoordinates(String productId, String testingMethodId);
    
    ProductSimpleModel getProductSimpleModel(String id);
    
    void sendProduct(String productId, String string);
    
    List<MetadataSample> getSampleByProductIds(ProductListRequest productIds);
}
