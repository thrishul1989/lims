package com.todaysoft.lims.kbm.es.service;

import java.util.List;

import com.todaysoft.lims.kbm.es.model.Product;

public interface IProductService
{
    String ES_TYPE = "PRODUCT";
    
    boolean index(Product product);
    
    boolean remove(String id);
    
    List<Product> search(String keywords, int offset, int limit);
}
