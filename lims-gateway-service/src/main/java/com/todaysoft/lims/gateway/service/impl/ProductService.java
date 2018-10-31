package com.todaysoft.lims.gateway.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Product;
import com.todaysoft.lims.gateway.model.product.ProductProbe;
import com.todaysoft.lims.gateway.model.request.ProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProductListRequest;
import com.todaysoft.lims.gateway.model.request.ProductPagingRequest;
import com.todaysoft.lims.gateway.service.IProductService;
import com.todaysoft.lims.gateway.service.adapter.ProductAdapter;
import com.todaysoft.lims.utils.StringUtils;

/**
 * Created by HSHY-032 on 2016/6/8.
 */
@Service
public class ProductService implements IProductService
{
    
    @Autowired
    private ProductAdapter productAdapter;
    
    @Override
    public Pagination<Product> paging(ProductPagingRequest request)
    {
        return productAdapter.paging(request);
    }
    
    @Override
    public List<Product> list(ProductListRequest request)
    {
        return productAdapter.list(request);
    }
    
    @Override
    public boolean unique(String code)
    {
        return productAdapter.unique(code);
    }
    
    @Override
    public Product getProduct(Integer id)
    {
        Product entity = productAdapter.getProduct(id);
        return entity;
    }
    
    //project传来的产品id数组，保存成list
    public List<Integer> getProductList(String array)
    {
        if (StringUtils.isNotEmpty(array))
        {
            return Arrays.stream(array.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        }
        return null;
    }
    
    @Override
    public Integer create(ProductCreateRequest request)
    {
        return productAdapter.create(request);
    }
    
    @Override
    public void deleteProduct(Integer id)
    {
        productAdapter.deleteProduct(id);
    }
    
    @Override
    public void modifyProduct(ProductCreateRequest request)
    {
        productAdapter.modifyProduct(request);
        
    }
    
    @Override
    public List<Product> getContactProducts(List<Integer> productIds)
    {
        return productAdapter.getContactProducts(productIds);
    }
    
    @Override
    public Pagination<Product> productSelect(ProductPagingRequest request)
    {
        return productAdapter.productSelect(request);
    }
    
    @Override
    public boolean validate(ProductCreateRequest request)
    {
        return productAdapter.validate(request);
    }
    
    @Override
    public List<ProductProbe> getProbeList(ProductCreateRequest request)
    {
        return productAdapter.getProbeList(request);
    }
    
    @Override
    public void enable(ProductCreateRequest request)
    {
        productAdapter.enable(request);
    }
}
