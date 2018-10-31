package com.todaysoft.lims.kbm.es.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.kbm.es.model.Product;
import com.todaysoft.lims.kbm.es.service.IProductService;

@RestController
@RequestMapping("/es/product")
public class ProductController
{
    @Autowired
    private IProductService service;
    
    @RequestMapping("/index")
    public void index(@RequestBody Product product)
    {
        service.index(product);
    }
    
    @RequestMapping("/remove/{id}")
    public void remove(@PathVariable String id)
    {
        service.remove(id);
    }
    
    @RequestMapping("/search")
    public List<Product> search(String keywords, Integer offset, Integer limit)
    {
        return service.search(keywords, null == offset ? 0 : offset.intValue(), null == limit ? 20 : limit.intValue());
    }
}
