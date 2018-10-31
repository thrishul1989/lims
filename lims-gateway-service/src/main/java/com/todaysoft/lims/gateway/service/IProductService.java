package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Product;
import com.todaysoft.lims.gateway.model.product.ProductProbe;
import com.todaysoft.lims.gateway.model.request.ProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProductListRequest;
import com.todaysoft.lims.gateway.model.request.ProductPagingRequest;

public interface IProductService
{
    Pagination<Product> paging(ProductPagingRequest request);
    
    Pagination<Product> productSelect(ProductPagingRequest request);
    
    boolean unique(String code);
    
    List<Product> list(ProductListRequest request);
    
    Product getProduct(Integer id);
    
    Integer create(ProductCreateRequest request);
    
    void deleteProduct(Integer id);
    
    void modifyProduct(ProductCreateRequest request);
    
    void enable(ProductCreateRequest request);
    
    List<Product> getContactProducts(List<Integer> productIds);
    
    boolean validate(ProductCreateRequest request);
    
    List<ProductProbe> getProbeList(ProductCreateRequest request);
}
