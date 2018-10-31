package com.todaysoft.lims.gateway.service.adapter;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Product;
import com.todaysoft.lims.gateway.model.product.ProductProbe;
import com.todaysoft.lims.gateway.model.request.ProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProductListRequest;
import com.todaysoft.lims.gateway.model.request.ProductPagingRequest;

@Component
public class ProductAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-product-service";
    
    @Autowired
    private RestTemplate template;
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Pagination<Product> paging(ProductPagingRequest request)
    {
        String url = getServiceUrl("/product/paging");
        ResponseEntity<Pagination<Product>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProductPagingRequest>(request), new ParameterizedTypeReference<Pagination<Product>>()
            {
            });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Pagination<Product> productSelect(ProductPagingRequest request)
    {
        String url = getServiceUrl("/product/productSelect");
        ResponseEntity<Pagination<Product>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProductPagingRequest>(request), new ParameterizedTypeReference<Pagination<Product>>()
            {
            });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Product> list(ProductListRequest request)
    {
        String url = getServiceUrl("/product/list");
        ResponseEntity<List<Product>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProductListRequest>(request), new ParameterizedTypeReference<List<Product>>()
            {
            });
        return exchange.getBody();
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public boolean unique(String code)
    {
        String url = getServiceUrl("/product/unique/{code}");
        return template.getForObject(url, Boolean.class, Collections.singletonMap("code", code));
        
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Product getProduct(Integer id)
    {
        String url = getServiceUrl("/product/{id}");
        return template.getForObject(url, Product.class, Collections.singletonMap("id", id));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public Integer create(ProductCreateRequest request)
    {
        String url = getServiceUrl("/product/create");
        return template.postForObject(url, request, Integer.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void deleteProduct(Integer id)
    {
        String url = getServiceUrl("/product/{id}");
        template.delete(url, Collections.singletonMap("id", id));
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void modifyProduct(ProductCreateRequest request)
    {
        String url = getServiceUrl("/product/modify");
        template.postForObject(url, request, Boolean.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public void enable(ProductCreateRequest request)
    {
        String url = getServiceUrl("/product/enable");
        template.postForObject(url, request, Boolean.class);
    }
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<Product> getContactProducts(List<Integer> productIds)
    {
        String url = getServiceUrl("/product/contact_products");
        ResponseEntity<List<Product>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<List<Integer>>(productIds), new ParameterizedTypeReference<List<Product>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    
    public boolean validate(ProductCreateRequest request)
    {
        String url = getServiceUrl("/product/validate");
        return template.postForObject(url, request, boolean.class);
    }
    
    public List<ProductProbe> getProbeList(ProductCreateRequest request)
    {
        String url = getServiceUrl("/product/getProbeList");
        ResponseEntity<List<ProductProbe>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProductCreateRequest>(request), new ParameterizedTypeReference<List<ProductProbe>>()
            {
            });
        return exchange.getBody();
    }
}
