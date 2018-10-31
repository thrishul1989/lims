package com.todaysoft.lims.sample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.sample.entity.Product;
import com.todaysoft.lims.sample.service.IproductService;
import com.todaysoft.lims.sample.service.adapter.ProductAdapter;

/**
 * Created by HSHY-032 on 2016/8/20.
 */
@Service
public class ProductService implements IproductService
{
    
    @Autowired
    private ProductAdapter productAdapter;
    
    @Override
    public Product getProduct(String productId)
    {
        return productAdapter.getProduct(productId);
    }
}
